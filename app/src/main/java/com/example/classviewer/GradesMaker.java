package com.example.classviewer;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.nio.channels.GatheringByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GradesMaker extends AsyncTask<String,Void,List<Grade>> {

    private String logtag = GradesMaker.class.getSimpleName();
    private GradesActivity.updateAdapter updateAdapter;



    public GradesMaker(GradesActivity.updateAdapter ad) {
        this.updateAdapter = ad;
    }

    @Override
    protected void onPostExecute(List<Grade> grades) {
        updateAdapter.updateGradesAdapter(grades);
        super.onPostExecute(grades);
    }

    @Override
    protected List<Grade> doInBackground(String... strings) {
        try {
            String grades =NetworkUtils.getGrades(strings[0],strings[1]);
            String[] relevant  = grades.split("<!-- Contact -->")[1].split("table");
            ArrayList<String> rel = new ArrayList(Arrays.asList(relevant));

            List<Grade> gradesList= new ArrayList<>();
            String subject = "";
            String gradeName = "";


            for (int i=1;i<rel.size();i+=2) {
                Grade newGrade = new Grade();
                String line = rel.get(i);

                for (String l : line.split("\n")) {
                    if (l.contains("<th")) {
                        subject = l.split("colspan=\"4\">")[1].split("<")[0];
                        //Log.d(logtag, subject);


                    }
                    //String gradeName=null;
                    for (String s : l.split("</td>")) {
                        if (s.contains("<td style=\"padding-left:10px; width:30%\">")) {
                            gradeName = s.split("<td style=\"padding-left:10px; width:30%\">")[1];
                            //Log.d(logtag,gradeName);
                        } else if (s.contains("<td style=\"width:40%;\">")) {
                            String grade = s.split("<td style=\"width:40%;\">")[1].replace("\n", "").replace("\t", "").replace("\r", "");
                            //Log.d(logtag, grade);
                            newGrade.addGrade(gradeName,grade);
                        }
                    }
                    newGrade.addSubject(subject);
                    gradesList.add(newGrade);

                }
            }
            return gradesList;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
