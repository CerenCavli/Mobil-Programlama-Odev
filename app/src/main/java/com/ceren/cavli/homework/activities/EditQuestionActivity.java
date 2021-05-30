package com.ceren.cavli.homework.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ceren.cavli.homework.R;
import com.ceren.cavli.homework.database.QuestionsLocalStorage;
import com.ceren.cavli.homework.model.QuestionModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class EditQuestionActivity extends AppCompatActivity {

    QuestionModel selectedQuestion;
    QuestionsLocalStorage db;
    String questionText, a, b, c, d, e, answer, filePath;
    EditText question, optionA, optionB, optionC, optionD, optionE;
    RadioGroup radioButtons;
    ImageButton submitChangesButton;
    int questionID;
    File src, dst;
    String firstname, lastname;
    private static final int PICKFILE_RESULT_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_question);
        setTitle("Soruyu Düzenle");


        questionID = getIntent().getIntExtra("QID", 0);
        question = findViewById(R.id.editQuestionQuestion);
        optionA = findViewById(R.id.editQuestionOptionA);
        optionB = findViewById(R.id.editQuestionOptionB);
        optionC = findViewById(R.id.editQuestionOptionC);
        optionD = findViewById(R.id.editQuestionOptionD);
        optionE = findViewById(R.id.editQuestionOptionE);
        radioButtons = findViewById(R.id.editQuestionRadioButtons);
        submitChangesButton = findViewById(R.id.editQuestionSubmit);

        db = new QuestionsLocalStorage(EditQuestionActivity.this);
        selectedQuestion = db.getAQuestionWID(questionID);
        question.setText(selectedQuestion.getQuestion());
        optionA.setText(selectedQuestion.getA());
        optionB.setText(selectedQuestion.getB());
        optionC.setText(selectedQuestion.getC());
        optionD.setText(selectedQuestion.getD());
        optionE.setText(selectedQuestion.getE());
        filePath = selectedQuestion.getFilePath();

        ((RadioButton)radioButtons.getChildAt((int)(selectedQuestion.getAnswer().charAt(0) - 'A'))).setChecked(true);

        editQuestion();
    }

    public void editQuestion(){
        submitChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionText = question.getText().toString();
                a = optionA.getText().toString();
                b = optionB.getText().toString();
                c = optionC.getText().toString();
                d = optionD.getText().toString();
                e = optionE.getText().toString();
                answer = ((RadioButton)findViewById(radioButtons.getCheckedRadioButtonId())).getText().toString();
                if(questionText.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty() || e.isEmpty() || answer.isEmpty() || answer.isEmpty()){
                    question.setText("");
                    optionA.setText("");
                    optionB.setText("");
                    optionC.setText("");
                    optionD.setText("");
                    optionE.setText("");
                    Toast.makeText(EditQuestionActivity.this, "Lütfen tüm boşlukları doldurun.", Toast.LENGTH_SHORT).show();
                }
                else{
                    try{
                        copyFile(src,dst);
                    }catch (Exception e){
                        System.out.println(e);
                    }
                    QuestionModel newQuestion = new QuestionModel(questionText,a,b,c,d,e,answer, filePath, -1, MainActivity.userID);
                    db.setAQuestion(questionID, newQuestion);
                    Toast.makeText(EditQuestionActivity.this, "\n" + "Soru başarıyla değiştirildi!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditQuestionActivity.this, ShowQuestionsActivity.class));
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri fileUri = data.getData();
                    filePath = fileUri.getPath();

                    String[] parts = fileUri.getLastPathSegment().split(":");

                    src = new File(Environment.getExternalStorageDirectory()+File.separator+parts[1]);
                    dst = new File(getFilesDir() + File.separator + "QuestionAttachments");
                    if(!dst.exists())
                        dst.mkdir();
                    dst = new File(getFilesDir() + File.separator + "QuestionAttachments"+File.separator+new File(fileUri.getPath()).getName());
                    filePath = getFilesDir() + File.separator + "QuestionAttachments"+File.separator+new File(fileUri.getPath()).getName();
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