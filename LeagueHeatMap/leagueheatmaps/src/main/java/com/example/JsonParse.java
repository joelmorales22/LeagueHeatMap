package com.example;

import java.io.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.Object;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;

class DataObject {
    private int id;
    private String name;
    private int profileIconId;
    private int summonerLevel;
    private double revisionDate;

    //getter and setter methods
    public int getId(){
        return id;
    }
    @Override
    public String toString() {
        return id +" - " + name + "-" + summonerLevel;
    }

}

public class JsonParse{
    public JsonParse(){}

    String summonerName ="";

    public void setSummonerName(String name){
        summonerName = name;
    }

    // GET API DATA FROM LEAGUE OF LEGENDS SITE

    //acquire ID for that league of legends summoner
    public int getSummId(String responseBody){

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, DataObject>>(){}.getType();
        Map<String, DataObject> myMap = gson.fromJson(responseBody, type);
        int id=0;
        id=myMap.get(summonerName.toLowerCase()).getId();
        System.out.println(myMap);
        System.out.println(myMap);
        return id;
    }


}