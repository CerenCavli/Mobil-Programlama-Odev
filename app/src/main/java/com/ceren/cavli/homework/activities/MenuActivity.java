package com.ceren.cavli.homework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ceren.cavli.homework.R;
import com.ceren.cavli.homework.database.QuestionsLocalStorage;

public class MenuActivity extends AppCompatActivity {

    Button addQuestion, showQuestions, prepareAnExam, customizeExam;
    ImageView profileImage;
    String firstname;
    String lastname;
    QuestionsLocalStorage db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        firstname = MainActivity.firstname;
        lastname = MainActivity.lastname;

        this.setTitle(firstname + " " + lastname);
        addQuestion = findViewById(R.id.menuButtonAddQuestion);
        showQuestions = findViewById(R.id.menuButtonShowQuestions);
        prepareAnExam = findViewById(R.id.menuButtonPrepareAnExam);
        customizeExam = findViewById(R.id.menuButtonExamSettings);
        switchButtons();
    }

    public void switchButtons(){
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, AddQuestionActivity.class));
            }
        });

        showQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new QuestionsLocalStorage(MenuActivity.this);
                if(db.getAllQuestions().size() == 0){
                    Toast.makeText(MenuActivity.this, "Veritabanında henüz soru yok!", Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(new Intent(MenuActivity.this, ShowQuestionsActivity.class));
                }

            }
        });

        prepareAnExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new QuestionsLocalStorage(MenuActivity.this);
                if(db.getAllQuestions().size() == 0){
                    Toast.makeText(MenuActivity.this, "\n" + "Veritabanında henüz soru yok!\"", Toast.LENGTH_SHORT).show();
                }else {
                    startActivity(new Intent(MenuActivity.this, PrepareExamActivity.class));
                }
            }
        });

        customizeExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, CustomizeExamActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
    }
}