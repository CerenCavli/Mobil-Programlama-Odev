package com.ceren.cavli.homework.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.ceren.cavli.homework.R;
import com.ceren.cavli.homework.adapter.QuestionRecyclerAdapter;
import com.ceren.cavli.homework.database.QuestionsLocalStorage;
import com.ceren.cavli.homework.model.QuestionModel;

import java.util.ArrayList;

public class ShowQuestionsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<QuestionModel> questions;
    String fn, ln;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_questions);
        setTitle("Sorular");

        recyclerView = findViewById(R.id.showQuestionsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ShowQuestionsActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        QuestionsLocalStorage db = new QuestionsLocalStorage(ShowQuestionsActivity.this);
        questions = db.getAllQuestions();

        QuestionRecyclerAdapter questionRecyclerAdapter = new QuestionRecyclerAdapter(questions, ShowQuestionsActivity.this);
        recyclerView.setAdapter(questionRecyclerAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShowQuestionsActivity.this, MenuActivity.class));
    }
}