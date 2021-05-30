package com.ceren.cavli.homework.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ceren.cavli.homework.R;

public class CustomizeExamActivity extends AppCompatActivity {
    SeekBar examTime, pointPerQuestion, diffucultyLevel;
    TextView examTimeText, pointPerQuestionText, diffucultyLevelText;
    Button save;
    int time, questions, diffuculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_exam);
        setTitle("Soruları Özelleştir");

        examTime = findViewById(R.id.customizeExamTime);
        pointPerQuestion = findViewById(R.id.customizeExamPointPerQuestion);
        diffucultyLevel = findViewById(R.id.customizeExamDiffuculty);
        examTimeText = findViewById(R.id.customizeExamTimeText);
        pointPerQuestionText = findViewById(R.id.customizeExamPointPerQuestionText);
        diffucultyLevelText = findViewById(R.id.customizeExamDiffucultyText);
        save = findViewById(R.id.customizeExamSubmit);
        loadData();
        updateView();

        getValues();
    }

    public void getValues(){
        examTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                examTimeText.setText(String.valueOf(progressChangedValue));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pointPerQuestion.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                pointPerQuestionText.setText(String.valueOf(progressChangedValue));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        diffucultyLevel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                diffucultyLevelText.setText(String.valueOf(progressChangedValue));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                onBackPressed();
            }
        });

    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("time", Integer.valueOf(examTimeText.getText().toString()));
        editor.putInt("pointPerQuestion", Integer.valueOf(pointPerQuestionText.getText().toString()));
        editor.putInt("diffucultyLevel", Integer.valueOf(diffucultyLevelText.getText().toString()));

        editor.apply();
        Toast.makeText(this, "\n" + "Tercihleriniz kaydedildi!",Toast.LENGTH_SHORT).show();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        time = sharedPreferences.getInt("time", 30);
        questions = sharedPreferences.getInt("pointPerQuestion", 1);
        diffuculty = sharedPreferences.getInt("diffucultyLevel", 2);
    }

    public void updateView(){
        examTime.setProgress(time);
        pointPerQuestion.setProgress(questions);
        diffucultyLevel.setProgress(diffuculty);
        examTimeText.setText(String.valueOf(time));
        pointPerQuestionText.setText(String.valueOf(questions));
        diffucultyLevelText.setText(String.valueOf(diffuculty));
    }
}