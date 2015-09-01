package com.example;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by joelm_000 on 8/21/2015.
 */
public class championData {


        @SerializedName("data")
        Map<String,champObject> data;

        public Map<String,champObject> getMap(){
            return data;
        }
    }



