package com.ceren.cavli.homework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ceren.cavli.homework.R;
import com.ceren.cavli.homework.database.QuestionsLocalStorage;
import com.ceren.cavli.homework.model.QuestionModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class AddQuestionActivity extends AppCompatActivity {

    EditText question, optionA, optionB, optionC, optionD, optionE;
    RadioGroup radioButtons;
    Button addQuestion;
    String questionText, answerA, answerB, answerC, answerD, answerE, answer, filePath;
    File src, dst;
    private static final int PICKFILE_RESULT_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_question);

        setTitle("Soru Ekleme");

        question = findViewById(R.id.addQuestionQuestion);
        optionA = findViewById(R.id.addQuestionOptionA);
        optionB = findViewById(R.id.addQuestionOptionB);
        optionC = findViewById(R.id.addQuestionOptionC);
        optionD = findViewById(R.id.addQuestionOptionD);
        optionE = findViewById(R.id.addQuestionOptionE);
        radioButtons = findViewById(R.id.addQuestionRadioButtons);
        addQuestion = findViewById(R.id.addQuestionSubmit);
        buttonActions();
    }

    public void buttonActions() {
        addQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionText = question.getText().toString();
                answerA = optionA.getText().toString();
                answerB = optionB.getText().toString();
                answerC = optionC.getText().toString();
                answerD = optionD.getText().toString();
                answerE = optionE.getText().toString();
                answer = ((RadioButton) findViewById(radioButtons.getCheckedRadioButtonId())).getText().toString();
                if (questionText.isEmpty() || answerA.isEmpty() || answerB.isEmpty() || answerC.isEmpty() || answerD.isEmpty() || answerE.isEmpty() || answer.isEmpty() || answer.isEmpty()) {
                    question.setText("");
                    optionA.setText("");
                    optionB.setText("");
                    optionC.setText("");
                    optionD.setText("");
                    optionE.setText("");
                    Toast.makeText(AddQuestionActivity.this, "\n" + "Lütfen tüm boşlukları doldurun.", Toast.LENGTH_SHORT).show();
                } else {
                    QuestionsLocalStorage db = new QuestionsLocalStorage(AddQuestionActivity.this);
                    try {
                        copyFile(src, dst);

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    QuestionModel question = new QuestionModel(questionText, answerA, answerB, answerC, answerD, answerE, answer, filePath, -1, MainActivity.userID);
                    db.addNewQuestion(question);
                    Toast.makeText(AddQuestionActivity.this, "Soru başarıyla kaydedildi!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }

            }

        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {

                    Uri fileUri = data.getData();
                    filePath = fileUri.toString();
                    String[] parts = fileUri.getLastPathSegment().split(":");
                    src = new File(Environment.getExternalStorageDirectory() + File.separator + parts[1]);
                    dst = new File(getFilesDir() + File.separator + "QuestionAttachments");
                    if (!dst.exists())
                        dst.mkdir();
                    dst = new File(getFilesDir() + File.separator + "QuestionAttachments" + File.separator + new File(fileUri.getPath()).getName());
                    filePath = getFilesDir() + File.separator + "QuestionAttachments" + File.separator + new File(fileUri.getPath()).getName();
                }
        }
    }

    private void copyFile(File src, File dst) throws IOException {
        FileInputStream inStream = new FileInputStream(src);
        if (!dst.exists()) {
            dst.createNewFile();
        }
        if (!dst.canWrite()) {
            System.out.print("CAN'T WRITE");
            return;
        }
        FileOutputStream outStream = new FileOutputStream(dst);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
    }
}