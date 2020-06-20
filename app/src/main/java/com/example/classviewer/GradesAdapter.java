package com.example.classviewer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.GradeViewHolder> {

    private List<Grade> gradeList;
    private Context context;


    public GradesAdapter(List<Grade> grades,Context context){
        this.gradeList = grades;
        this.context = context;
    }

    public void setGrades(List<Grade> grades){this.gradeList = grades;}

    @NonNull
    @Override
    public GradeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.recycler_view_grade,parent,false);
        return new GradeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GradeViewHolder holder, int position) {
        if (gradeList != null){
            Grade currentGrade = gradeList.get(position);
            holder.bindTo(currentGrade);
        }
    }

    @Override
    public int getItemCount() {
        if (gradeList != null){
            return gradeList.size();
        }
        return 0;
    }

    public class GradeViewHolder extends RecyclerView.ViewHolder {

        private TextView className;
        private LinearLayout linLayout;


        public GradeViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.grade_title);
            linLayout = itemView.findViewById(R.id.grade_layout);
        }
        public void bindTo(Grade currentGrade){
            className.setText(currentGrade.getSubject());
            HashMap<String,String> grades = currentGrade.getGrades();
            for(Map.Entry<String,String> entry:grades.entrySet()){
                TextView tx = new TextView(context);
                tx.setText(entry.getKey().substring(3) + " -->" +entry.getValue());
                tx.setTextColor(context.getResources().getColor(R.color.classText));
                linLayout.addView(tx,0);

            }

        }
    }
}
