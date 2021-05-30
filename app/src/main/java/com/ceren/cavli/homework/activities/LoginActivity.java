package com.ceren.cavli.homework.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ceren.cavli.homework.R;
import com.ceren.cavli.homework.database.UserLocalStorage;
import com.ceren.cavli.homework.model.UserModel;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button loginButton, signUp;
    int attempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Giriş");

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 100);

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.loginButton);
        signUp = findViewById(R.id.logInSignUpButton);
        attempt = 0;
        waitForLoginButton();
    }

    public void controlInformations(){
        String emailText = email.getText().toString();
        String passwordText = password.getText().toString();
        UserLocalStorage db = new UserLocalStorage(LoginActivity.this);
        ArrayList<UserModel> users = db.getAllUsers();
        for(UserModel user : users){
            if(user.geteMail().equals(emailText) && user.getPassword().equals(passwordText)){
                Intent intent =  new Intent(LoginActivity.this, MenuActivity.class);
                MainActivity.firstname = user.getFirstName();
                MainActivity.lastname = user.getLastName();
                MainActivity.userID = user.getId();
                startActivity(intent);
                return;
            }
        }
        attempt += 1;
        email.setText("");
        password.setText("");

        if (attempt == 3){
            Toast.makeText(LoginActivity.this, "Giriş üç kez başarısız oldu. Kaydolmayı deneyin.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            return;
        }
        Toast.makeText(LoginActivity.this, "\n" + "Kullanıcı adı veya şifre yanlış.", Toast.LENGTH_SHORT).show();

    }

    public void waitForLoginButton(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlInformations();
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

}