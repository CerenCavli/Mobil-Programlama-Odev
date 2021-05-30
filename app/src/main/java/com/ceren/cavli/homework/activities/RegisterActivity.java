package com.ceren.cavli.homework.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ceren.cavli.homework.R;
import com.ceren.cavli.homework.database.UserLocalStorage;
import com.ceren.cavli.homework.model.UserModel;

import java.io.File;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {



    EditText firstName, lastName, phoneNumber, eMail, password, passwordCheck;
    Button signUpButton;
    private static final int RESULT_LOAD_IMAGE = 1;
    File src, dst;
    String filePath;
    boolean gotAnImage = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.setTitle("Kaydol");

        firstName = findViewById(R.id.signUpFirstName);
        lastName = findViewById(R.id.signUpLastName);
        phoneNumber = findViewById(R.id.signUpPhone);
        eMail = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);
        passwordCheck = findViewById(R.id.signUpPasswordCheck);
        signUpButton = findViewById(R.id.signUpButton);
        signUp();

    }
    public boolean checkInformations(){
        UserLocalStorage db  = new UserLocalStorage(RegisterActivity.this);
        ArrayList<UserModel> users = db.getAllUsers();
        if(firstName.getText().toString().trim().equals("")  || lastName.getText().toString().trim().equals("") || phoneNumber.getText().toString().trim().equals("") ||
                eMail.getText().toString().trim().equals("")|| password.getText().toString().trim().equals("")|| passwordCheck.getText().toString().trim().equals("")){
            Toast.makeText(RegisterActivity.this, "Lütfen boşlukları doldurunuz.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!passwordCheck.getText().toString().equals(password.getText().toString())){
            password.setText("");
            passwordCheck.setText("");
            Toast.makeText(RegisterActivity.this, "Şifreler eşleşmiyor. Tekrar deneyin !", Toast.LENGTH_SHORT).show();
            return false;
        }

        for(UserModel user: users){
            if(user.getFirstName().equals(firstName.getText().toString()) && user.getLastName().equals(lastName.getText().toString())){
                firstName.setText("");
                lastName.setText("");
                Toast.makeText(RegisterActivity.this, "\n" +
                        "Bu kullanıcı daha önce kaydoldu, lütfen giriş yapmayı veya farklı bir adla kaydolmayı deneyin!",Toast.LENGTH_SHORT).show();
                return false;
            }

            if(user.geteMail().equals(eMail.getText().toString())){
                eMail.setText("");
                Toast.makeText(RegisterActivity.this, "\n" + "Bu e-posta daha önce kullanıldı, lütfen başka bir tane deneyin!",Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        UserModel newUser = new UserModel(firstName.getText().toString(),
                lastName.getText().toString(),
                phoneNumber.getText().toString(),
                eMail.getText().toString(),
                password.getText().toString(),
                -1);

        Toast.makeText(RegisterActivity.this, "\n" + "Kayıt tamamlandı!", Toast.LENGTH_SHORT).show();
        db.addNewUser(newUser);

        MainActivity.firstname = firstName.getText().toString();
        MainActivity.lastname = lastName.getText().toString();
        MainActivity.userID = db.getAUserWEMail(eMail.getText().toString()).getId();

        users = db.getAllUsers();
        return true;

    }
    public void signUp(){
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean isUserInformationCorrect = checkInformations();
                    if(isUserInformationCorrect){
                        startActivity(new Intent(RegisterActivity.this, MenuActivity.class));
                    }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}