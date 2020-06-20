package com.example.classviewer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.sql.Time;
import java.text.ParseException;
import java.time.Instant;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    //testing feature
    static String logTag = MainActivity.class.getSimpleName();

    //Main layout adapter,for listed Classes, and for Recycler View, Progress bar to show user
    public List<Class> listedClasses;
    private updateAdapter updt;
    private RecyclerView recyclerView;
    private ClassAdapter classAdapter;
    private ProgressBar pb;
    private String[] creds;
    private FloatingActionButton dateButton;
    private FloatingActionButton gradesButton;
    private TextView dayName;

    int selectedYear;
    int selectedMonth;
    int selectedDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //set layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if darkMode settign on
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("darkMode",true)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        //get saved credentials, are null if not set
        creds = getCreds();

        //set progress bar
        pb = findViewById(R.id.main_bar);
        dateButton = findViewById(R.id.choose_date_button);
        gradesButton = findViewById(R.id.get_grades_button);

        dayName = findViewById(R.id.current_day);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(v);

            }
        });
        gradesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(MainActivity.this,GradesActivity.class);
                startActivity(newIntent);
            }
        });

        //initiate adapter and recycler view
        classAdapter = new ClassAdapter(listedClasses,getApplicationContext());
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(classAdapter);



        //interface to update adapter
        updt = new updateAdapter() {
            @Override
            public void updateClassAdapter(List<Class> classes) {
                pb.setVisibility(View.INVISIBLE);
                Log.d(logTag,classes.toString());
                classAdapter.setClasses(classes);
                classAdapter.notifyDataSetChanged();
            }

        };


        //if not already loaded, and creds valid, load results
        if(listedClasses == null && creds[0] != null && creds[1] != null){
            //display date
            Calendar c= Calendar.getInstance();
            dayName.setText("Today "+ getDate(c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH),c.get(Calendar.YEAR)));
            String time = Long.toString(c.getTimeInMillis()/1000);
            pb.setVisibility(View.VISIBLE);
            String dummyDate = "1569920400";
            new DayMaker(updt).execute(time,creds[0],creds[1]);
        }


    }


    //show date picker when button clicked
    public void showDatePicker(View view) {
        DialogFragment fragment = new DatePickerFragment(this.selectedYear,this.selectedDay,this.selectedMonth);
        fragment.show(getSupportFragmentManager(),"date");
    }


    //get date from picker
    public void getDateClasses(long unixTime,int day,int month,int year){
        if (creds[0] != null) {
            pb.setVisibility(View.VISIBLE);
            dayName.setText(getDate(day, month, year));
            this.selectedYear = year;
            this.selectedDay = day;
            this.selectedMonth = month;

            new DayMaker(updt).execute(Long.toString(unixTime), creds[0], creds[1]);
        }
    }

    public String getDate(int day,int month,int year){
        Calendar c = Calendar.getInstance();
        c.set(year,month,day);
        String dayOfWeeek = c.getDisplayName(Calendar.DAY_OF_WEEK,Calendar.LONG, Locale.getDefault());
        String monthText = c.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.getDefault());
        return dayOfWeeek +" " + day +" " + monthText;
    }


    //menu inflater
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    //menu listener
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.settings:
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


    //get credentials from settings
    public String[] getCreds(){
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String guid = shared.getString("guid",null);
        String password = shared.getString("password",null);
        return new String[]{guid,password};

    }


    //remove all shared preferences, testing feature
    public void clearSharedPrefs(){
        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        shared.edit().remove("guid").commit();
        shared.edit().remove("password").commit();
    }
    public interface updateAdapter {
        void updateClassAdapter(List<Class> classes);
    }


}