package com.example.classviewer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GradesActivity extends AppCompatActivity{

    RecyclerView recyclerView;
    GradesAdapter gradesAdapter;
    List<Grade> grades;
    updateAdapter updateAdapter;
    ProgressBar pb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grades_activity);


        pb = findViewById(R.id.grades_bar);
        gradesAdapter = new GradesAdapter(grades,getApplicationContext());
        recyclerView = findViewById(R.id.recycler_view_grades);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(gradesAdapter);
        String[] creds = getCreds();

        updateAdapter = new updateAdapter() {
            @Override
            public void updateGradesAdapter(List<Grade> grades) {
                pb.setVisibility(View.INVISIBLE);
                gradesAdapter.setGrades(grades);
                gradesAdapter.notifyDataSetChanged();
            }
        };


        if (grades == null) {
            if (creds[0] != null) {
                pb.setVisibility(View.VISIBLE);
                new GradesMaker(updateAdapter).execute(creds[0], creds[1]);
            }
        }


    }
    public interface updateAdapter {
        void updateGradesAdapter(List<Grade> classes);
    }
    public String[] getCreds(){
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String guid = shared.getString("guid",null);
        String password;

        if(shared.getBoolean("useSaved",true)){
            password = shared.getString("socsPassword",null);
        }else{
            password = shared.getString("password",null);
        }

        Log.d("get",guid+password);



        return new String[]{guid,password};

    }
}
