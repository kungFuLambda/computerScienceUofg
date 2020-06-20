package com.example.classviewer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.renderscript.ScriptGroup;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.RequiresApi;


import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.jar.Attributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class NetworkUtils{
    public static String logtag = NetworkUtils.class.getSimpleName();
    public static String BASE_URL = "https://frontdoor.spa.gla.ac.uk/timetable";

/*
All request code provided by Vasilios
*/
    public static String fetchDay(String date,String guid,String pwd) {
        CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
        String UID = guid;
        String password = pwd;
        try {
            login(UID, password);
            String classes = getClasses(Long.valueOf(date));
            if (classes.length() != 2){
                return classes;
            }else{return null;}


        } catch (IOException e) {
            e.printStackTrace();
        }return null;
    }
    private static String getClasses(long unixTime) throws IOException {
        Uri getClassesUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath("timetable")
                .appendPath("events")
                .appendQueryParameter("start",Long.toString(unixTime))
                .appendQueryParameter("end",Long.toString(unixTime+86400))
                .build();

        //String url = String.format("https://frontdoor.spa.gla.ac.uk/timetable/timetable/events?start=%d&end=%d", unixTime, unixTime + 86400);

        return request(getClassesUri.toString(), "GET", "");
    }

    private static void login(String UID, String password) throws IOException {
        // Creating the payload to send for the login.
        String payload = "{\"guid\": \"" + UID.trim() + "\", \"password\": \"" + password.trim() + "\", \"rememberMe\": \"true\"}";
        Uri loginUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath("login")
                .build();
        request(loginUri.toString(), "POST", payload);
    }

    private static String request(String url, String requestMethod, String payload) throws IOException {
        URL session = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) session.openConnection();

        connection.setRequestMethod(requestMethod);
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.setRequestProperty("Accept", "application/json");

        // If it's a POST request then we have to write our payload into the request.
        if (requestMethod.equals("POST")) {
            connection.setDoOutput(true);
            connection.getOutputStream().write(payload.getBytes("UTF-8"));
        }

        /*
         * We are creating a buffered reader to read the response, then we go character
         * to character and add it to a string.
         */
        Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder sb = new StringBuilder();

        for (int i; (i = in.read()) >= 0;)
            sb.append((char) i);

        return sb.toString();
    }


    public static String getGrades(String guid,String password) throws IOException {
        String host = "https://studentltc.dcs.gla.ac.uk/login.cfm?login="+guid+"&pw="+password+"&timings=&strength=&elapsed=";
        URL url = new URL(host);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) !=null){
            sb.append(line);
        }
        return sb.toString();


    }
}


