package com.example.classviewer;
//{"date":1579777200000,"start":"2020-01-23 11:00:00","lecturer":"Sevegnani, Dr Michele","title":"Algs & Data Structures 2 (Lecture) Lecture",
// "roomid":"124B419","room":"Joseph Black Building:B419 Main Lecture Theatre","allDay":false,
// "course":"Algs & Data Structures 2","end":"2020-01-23 12:00:00","details":"Lecture",]
// "location":"Joseph Black Building:B419 Main Lecture Theatre","id":220,"classType":"Lecture"},
public class Class {
    long date;
    String start;
    String lecturer;
    String title;
    String roomid;
    String room;
    boolean allDay;
    String course;
    String end;
    String details;
    String location;
    int id;

    public String getStart() {
        return start;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getEnd() {
        return end;
    }

    public boolean isAllDay() {
        return allDay;
    }
    public String getHourMinute(){
        String start = this.getStart();
        String end = this.getEnd();
        String[] startHourMinute = start.split(" ")[1].split(":");
        String[] endHourMinute = end.split(" ")[1].split(":");
        String startTime = startHourMinute[0]+":"+startHourMinute[1];
        String endTime = endHourMinute[0]+":"+endHourMinute[1];

        return startTime + "-"+endTime;
    }
    public int getDuration(){
        int startHourMinute = Integer.valueOf(start.split(" ")[1].split(":")[0]);
        int endHourMinute = Integer.valueOf(end.split(" ")[1].split(":")[0]);
        return endHourMinute-startHourMinute;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Class{" +
                "date=" + date +
                ", start='" + start + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", title='" + title + '\'' +
                ", roomid='" + roomid + '\'' +
                ", room='" + room + '\'' +
                ", allDay=" + allDay +
                ", course='" + course + '\'' +
                ", end='" + end + '\'' +
                ", details='" + details + '\'' +
                ", location='" + location + '\'' +
                ", id=" + id +
                ", classType='" + classType + '\'' +
                '}';
    }

    public Class(long date, String start, String lecturer, String title, String roomid, String room, boolean allDay, String course, String end, String details, String location, int id, String classType) {
        this.date = date;
        this.start = start;
        this.lecturer = lecturer;
        this.title = title;
        this.roomid = roomid;
        this.room = room;
        this.allDay = allDay;
        this.course = course;
        this.end = end;
        this.details = details;
        this.location = location;
        this.id = id;
        this.classType = classType;
    }

    String classType;

}