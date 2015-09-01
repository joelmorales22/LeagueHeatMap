package com.example;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by joelm_000 on 8/20/2015.
 */
public class ChampionStats implements Serializable{

        @SerializedName("id")
        private int id;

        @SerializedName("stats")
        Map<String,Integer> stat;

        //getter and setter methods
        public Map<String,Integer> getMap(){
            return stat;
        }
        public int getchampID(){
            return id;
        }
        @Override
        public String toString() {
            return id + " " + stat.get(0) ;
        }
        public void setID(int i){id=i;}
        public void setStat(Map<String,Integer>aMap){stat=aMap;}

}
