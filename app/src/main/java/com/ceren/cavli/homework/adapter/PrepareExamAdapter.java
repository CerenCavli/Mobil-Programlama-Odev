package com.ceren.cavli.homework.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ceren.cavli.homework.BuildConfig;
import com.ceren.cavli.homework.R;
import com.ceren.cavli.homework.model.QuestionModel;

import java.io.File;
import java.util.ArrayList;

public class PrepareExamAdapter extends RecyclerView.Adapter<PrepareExamAdapter.ViewHolder>{
    ArrayList<QuestionModel> questions = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;
    String filePath;



    private static final int PICK_PDF_FILE = 2;
    public PrepareExamAdapter(ArrayList<QuestionModel> questions, Context context){
        this.questions = questions;
        this.context = context;
    }

    @Override
    public PrepareExamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View rowView = layoutInflater.inflate(R.layout.show_question_row, parent, false);
        return new PrepareExamAdapter.ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull PrepareExamAdapter.ViewHolder holder, int position) {
        QuestionModel question = questions.get(position);
        holder.question.setText(questions.get(position).getQuestion());
        String answer = questions.get(position).getAnswer();
        holder.a.setText(questions.get(position).getA());
        holder.b.setText(questions.get(position).getB());
        holder.c.setText(questions.get(position).getC());
        holder.d.setText(questions.get(position).getD());
        holder.e.setText(questions.get(position).getE());
        holder.answer.setText(questions.get(position).getAnswer());
        filePath = questions.get(position).getFilePath();
        int id = questions.get(position).getId();

        holder.deleteButton.setVisibility(View.GONE);
        holder.editButton.setVisibility(View.GONE);
        if(filePath == null){
            holder.inspectFileButton.setVisibility(View.GONE);
        }else{
            holder.inspectFileButton.setVisibility(View.VISIBLE);
        }

        holder.inspectFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filePath  = questions.get(position).getFilePath();
                Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", new File(filePath));
                Intent target = new Intent();
                target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                target.setData(uri);
                target.setAction(Intent.ACTION_VIEW);
                context.startActivity(target);
            }
        });
        holder.questionCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(question.isSelected()){
                    holder.questionCard.setBackgroundColor(Color.WHITE);
                    question.setSelected(false);
                }
                else{
                    holder.questionCard.setBackgroundColor(Color.parseColor("#2a2625"));
                    question.setSelected(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView question, a, b ,c ,d ,e, answer, aa,bb,cc,dd,ee;
        ImageButton inspectFileButton, editButton, deleteButton;
        LinearLayout questionCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.questionText);
            this.a = itemView.findViewById(R.id.optionAText);
            this.b = itemView.findViewById(R.id.optionBText);
            this.c = itemView.findViewById(R.id.optionCText);
            this.d = itemView.findViewById(R.id.optionDText);
            this.e = itemView.findViewById(R.id.optionEText);
            this.aa = itemView.findViewById(R.id.optionA);
            this.bb = itemView.findViewById(R.id.optionB);
            this.cc = itemView.findViewById(R.id.optionC);
            this.dd = itemView.findViewById(R.id.optionD);
            this.ee = itemView.findViewById(R.id.optionE);

            this.answer = itemView.findViewById(R.id.answerText);
            this.inspectFileButton = itemView.findViewById(R.id.rowItemInspectFileButton);
            this.editButton = itemView.findViewById(R.id.rowItemEditButton);
            this.deleteButton = itemView.findViewById(R.id.rowItemDeleteButton);
            this.questionCard =  itemView.findViewById(R.id.linearLayoutQuestion);
        }

    }


}
