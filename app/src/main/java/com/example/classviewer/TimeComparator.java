package com.example.classviewer;

import java.util.Comparator;

public class TimeComparator implements Comparator<Class> {
    @Override
    public int compare(Class o1, Class o2) {
        //'2020-01-23 11:00:00'
        int time1 = Integer.valueOf(o1.getStart().split(" ")[1].split(":")[0]);
        int time2 = Integer.valueOf(o2.getStart().split(" ")[1].split(":")[0]);
        return time1-time2;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
