package com.example.joelm_000.leagueheatmap;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;


import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Summoner extends AppCompatActivity{
    static SummonerStats summonerstats= new SummonerStats();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView textview = (TextView) findViewById(R.id.displayName);
        int summID = 0;
        try {
            summID = getSummonerID(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String rankedStats=getRankedStats(summID);
        SummonerStats sumStats=mapOfRankedData(rankedStats);
        textview.setText("Welcome " + message + "\nYour Heat Map is ready!");
        Button nextActivity = (Button) findViewById(R.id.proceed);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summoner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    int getSummonerID(String summname) throws IOException {

        JsonParse summoner= new JsonParse();
        summoner.setSummonerName(summname);
        String url="https://na.api.pvp.net/api/lol/na/v1.4/summoner/by-name/"+summname+"?";


        String summdata=" ";
        try {
            summdata = Ion.with(this)
                  .load(url)
                  .asString().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        int id = summoner.getSummId(summdata);
        return id;
    }
    String getRankedStats(int id) {
        String rankedData = "";
        String url = "https://na.api.pvp.net/api/lol/na/v1.3/stats/by-summoner/" + id + "/ranked?season=SEASON2015&";
        try {
            rankedData = Ion.with(this)
                    .load(url)
                    .asString().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return rankedData;
    }
    SummonerStats mapOfRankedData(String rankedStats){
            Gson gson = new Gson();
            summonerstats =gson.fromJson(rankedStats, SummonerStats.class);

            return summonerstats;

    }
    public void onClickBtn(View v)
    {
        Intent intent = new Intent(this, DisplayRankedData.class);
        intent.putExtra("rankedStatsByChampion", (Serializable) summonerstats);
        startActivity(intent);
    }


}
