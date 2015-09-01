package com.example;

import com.google.gson.annotations.SerializedName;
import com.example.SummonerStats;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by joelm_000 on 8/20/2015.
 */
public class SummonerStats implements Serializable{
    @SerializedName("champions")
    private List<ChampionStats> championList;
    //getters and setters
    public List<ChampionStats> getList(){
        return championList;
    }
    public void setList(List<ChampionStats> aList){
        championList = aList;
    }
    public List<ChampionStats> getChampionList(){return championList;}
}
