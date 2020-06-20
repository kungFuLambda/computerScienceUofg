package com.example.classviewer;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class DayMaker extends AsyncTask<String,Void,List<Class>>{



    String logTag = DayMaker.class.getSimpleName();
    private MainActivity.updateAdapter updateAdapter;



    public DayMaker(MainActivity.updateAdapter ad) {
        this.updateAdapter = ad;
    }

    @Override
    protected void onPostExecute(List<Class> classes) {
        updateAdapter.updateClassAdapter(classes);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected List<Class> doInBackground(String... strings) {

        //fetch string of classes
        String classes = NetworkUtils.fetchDay(strings[0], strings[1], strings[2]);
        List<Class> listedClasses;
        //if the return value is not null
        if (classes == null){
            return new ArrayList<>();
        }else{
            //else parse the String into classes objects and Use the Main Application to update the view
            try {
                listedClasses = extractClass(classes);
                return listedClasses;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private List<Class> extractClass(String classes) throws JSONException{
        List<Class> classObjects = new ArrayList<>();
        String classesObjs = classes.substring(2,classes.length()-1);
        String[] objs = classesObjs.split(",\\{");
        for (String s:objs){
            JSONObject tempObject = new JSONObject("{"+s);
            long date=(long) tempObject.get("date");
            String start=(String) tempObject.get("start");
            String lecturer = null;
            if (tempObject.has("lecturer")) {
                lecturer = (String) tempObject.get("lecturer");
            }
            String title=(String) tempObject.get("title");
            String roomid=(String) tempObject.get("roomid");
            String room=(String) tempObject.get("room");
            boolean allDay=(boolean)tempObject.get("allDay");
            String course=(String) tempObject.get("course");
            String end=(String) tempObject.get("end");
            String details=null;
            if (tempObject.has("details")) {
                details = (String) tempObject.get("details");
            }
            String location=(String) tempObject.get("location");
            int id=(int) tempObject.get("id");
            String classType=(String) tempObject.get("classType");
            classObjects.add(new Class(date,start,lecturer,title,roomid,room,allDay,course,end,details,location,id,classType));
        }

        return classObjects;
    }

}
