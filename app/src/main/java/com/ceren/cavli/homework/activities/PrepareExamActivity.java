package com.ceren.cavli.homework.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ceren.cavli.homework.BuildConfig;
import com.ceren.cavli.homework.R;
import com.ceren.cavli.homework.adapter.PrepareExamAdapter;
import com.ceren.cavli.homework.database.QuestionsLocalStorage;
import com.ceren.cavli.homework.model.QuestionModel;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PrepareExamActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<QuestionModel> questions;
    ImageButton customize, save, share;
    int time, point, diffuculty, numOfExamsCreated;
    File dst;
    public String examTextFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_exam);
        setTitle("Sınav Hazırla");

        recyclerView = findViewById(R.id.prepareAnExamRecyclerView);
        customize = findViewById(R.id.prepareAnExamCustomizeButton);
        save = findViewById(R.id.prepareAnExamSaveExamButton);
        share = findViewById(R.id.prepareAnExamShareButton);

        share.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(PrepareExamActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        QuestionsLocalStorage db = new QuestionsLocalStorage(PrepareExamActivity.this);
        questions = db.getAllQuestions();

        PrepareExamAdapter prepareExamAdapter = new PrepareExamAdapter(questions, PrepareExamActivity.this);
        recyclerView.setAdapter(prepareExamAdapter);
        buttonFunctions();
    }
    public void buttonFunctions(){
        customize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrepareExamActivity.this, CustomizeExamActivity.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedQuestionNumber = 0;
                for(QuestionModel question : questions){
                    if(question.isSelected())
                        selectedQuestionNumber += 1;
                }
                if (selectedQuestionNumber == 0){
                    Toast.makeText(PrepareExamActivity.this, "Soruların hiçbirini değiştirmediniz!", Toast.LENGTH_SHORT).show();
                    return;
                }
                loadData();
                examTextFile = examTextFile = "exampleExam"+ String.valueOf(numOfExamsCreated)+ ".txt";
                dst = new File(getFilesDir() + File.separator + "Exams");
                if(!dst.exists())
                    dst.mkdir();
                dst = new File(getFilesDir() + File.separator + "Exams"+File.separator+ examTextFile);
                try{
                    FileOutputStream fileOutputStream = new FileOutputStream(dst);
                    fileOutputStream.write(("Exam Time : " + String.valueOf(time)).getBytes());
                    fileOutputStream.write(("\n").getBytes());
                    fileOutputStream.write(("Point per Question : " + String.valueOf(point)).getBytes());
                    fileOutputStream.write(("\n").getBytes());
                    fileOutputStream.write(("\n").getBytes());
                    int i = 1;
                    for(QuestionModel question : questions){
                        if(question.isSelected()){
                            fileOutputStream.write(("Question " + String.valueOf((i++)) + " -) ").getBytes());
                            fileOutputStream.write(question.getQuestion().getBytes());
                            fileOutputStream.write(("\n").getBytes());
                            ArrayList<String> options = getOptions(question, diffuculty, Integer.valueOf(question.getAnswer().charAt(0) - 'A'));
                            int j;
                            for(j = 0; j < options.size() ; j++){
                                fileOutputStream.write((char)('A' + j));
                                fileOutputStream.write((" ) ").getBytes());
                                fileOutputStream.write(options.get(j).getBytes());
                                fileOutputStream.write(("\n").getBytes());
                            }
                            fileOutputStream.write(("\n").getBytes());
                        }
                    }
                    Toast.makeText(PrepareExamActivity.this, "Sınav dosyaya başarıyla kaydedildi!", Toast.LENGTH_SHORT).show();
                    fileOutputStream.close();
                }catch (Exception e){
                    System.out.println(e);
                }
                numOfExamsCreated += 1;
                saveData();
                share.setVisibility(View.VISIBLE);
                save.setVisibility(View.GONE);
                customize.setVisibility(View.GONE);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("*/txt");
                shareIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(PrepareExamActivity.this, BuildConfig.APPLICATION_ID + ".provider", dst));
                startActivity(Intent.createChooser(shareIntent, "Choose Application"));
            }
        });
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE );
        time = sharedPreferences.getInt("time", 30);
        point = sharedPreferences.getInt("pointPerQuestion", 1);
        diffuculty = sharedPreferences.getInt("diffucultyLevel", 2);
        numOfExamsCreated = sharedPreferences.getInt("numOfExams", 1);
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("numOfExams", numOfExamsCreated);

        editor.apply();
    }

    public ArrayList<String> getOptions(QuestionModel question, int diffuculty, int orderOfAnswer){
        ArrayList<String> options = new ArrayList<>();
        Random rand = new Random();

        options.add(question.getA());
        options.add(question.getB());
        options.add(question.getC());
        options.add(question.getD());
        options.add(question.getE());
        while(options.size() > diffuculty){
            int optOrder = rand.nextInt(options.size());
            if (optOrder != orderOfAnswer)
                options.remove(optOrder);
        }
        Collections.shuffle(options);
        return options;
    }
}