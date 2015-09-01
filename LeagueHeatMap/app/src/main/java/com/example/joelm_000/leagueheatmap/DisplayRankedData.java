package com.example.joelm_000.leagueheatmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ChampionStats;
import com.example.SummonerStats;
import com.example.champObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.example.championData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DisplayRankedData extends AppCompatActivity {
   // static int topwins=0,toplosses=0,topplayed=0;
   // static int botwins=0,botlosses=0,botplayed=0;
    //static int midwins=0,midlosses=0,midplayed=0;
  //  static int junwins=0,junlosses=0,junplayed=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_ranked_data);
        Intent intent = getIntent();
        SummonerStats rankedStats = (SummonerStats)intent.getSerializableExtra("rankedStatsByChampion");
        List<ChampionStats> championstats = rankedStats.getList();
        Map<Integer,champObject> finalChampionList = getChampionList();

        int[]winsplayed = new int[10];
        winsplayed[0]=0;winsplayed[1]=0;winsplayed[2]=0;winsplayed[3]=0;winsplayed[4]=0;winsplayed[5]=0;winsplayed[6]=0;winsplayed[7]=0;
        for(int i=0; i<championstats.size()-1;i++){
            ChampionStats nextChamp=championstats.get(i);
            addStatsToLane(finalChampionList,nextChamp,winsplayed);
        }

        setTextBoxes(winsplayed);


        //wins/totalgames = .5

        //Toast toast = Toast.makeText(this, "" +topwins+" " + toplosses+" " + topplayed, Toast.LENGTH_SHORT);
        //toast.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_ranked_data, menu);
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

    Map<Integer,champObject> getChampionList() {
        String url = "https://global.api.pvp.net/api/lol/static-data/na/v1.2/champion?";
        String champData = "";
        try {
            champData =
                    Ion.with(this)
                            .load(url)
                            .asString().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        championData championdata = gson.fromJson(champData, championData.class);
        Map<String, champObject> championInfo;
        Map<Integer, champObject> finalChampionInfo = new HashMap<Integer, champObject>();
        championInfo = championdata.getMap();



        for (Map.Entry<String, champObject> entry : championInfo.entrySet()) {
            //System.out.println(entry.getKey() + "/" + entry.getValue());
            champObject champobject = new champObject();
            champobject.setID(entry.getValue().getID());
            champobject.setName(entry.getValue().getname());
            finalChampionInfo.put(entry.getValue().getID(), champobject);
        }
        return finalChampionInfo;
    }
    void addStatsToLane(Map<Integer,champObject> champList , ChampionStats nextChamp,int[]winsplayed){
        // ID of the champion that we passed in from nextChamp
        int id=nextChamp.getchampID();
        //name of the champion we passed in acquired from champList
        if(id == 0){
            return;
        }
        String champName =champList.get(id).getname();
        //champion object pulled out by ID from map champList
        champObject champion = champList.get(id);
        Map<String,Integer> rankedStats = nextChamp.getMap();
        SharedPreferences sharedPref = getSharedPreferences("mySharedPreferences", MODE_PRIVATE);
        String lane =sharedPref.getString(champName,"default");
        switch (lane){
            case " Top": winsplayed[0] += rankedStats.get("totalSessionsPlayed");
                        winsplayed[1]+=rankedStats.get("totalSessionsWon");
                break;
            case " Mid": winsplayed[2] += rankedStats.get("totalSessionsPlayed");
                winsplayed[3]+=rankedStats.get("totalSessionsWon");
                break;
            case " Jun": winsplayed[4] += rankedStats.get("totalSessionsPlayed");
                winsplayed[5]+=rankedStats.get("totalSessionsWon");
                break;
            case " Bot": winsplayed[6] += rankedStats.get("totalSessionsPlayed");
                winsplayed[7]+=rankedStats.get("totalSessionsWon");
                break;

        }


    }
    void setTextBoxes(int[]winsplayed)
    {
        float topavg=((float)winsplayed[1]/(float)winsplayed[0])*100;
        float midavg=((float)winsplayed[3]/(float)winsplayed[2])*100;
        float junavg=((float)winsplayed[5]/(float)winsplayed[4])*100;
        float botavg=((float)winsplayed[7]/(float)winsplayed[6])*100;

        TextView top = (TextView) findViewById(R.id.Top);
        TextView bot = (TextView) findViewById(R.id.Bot);
        TextView jun = (TextView) findViewById(R.id.Jun);
        TextView mid= (TextView) findViewById(R.id.Mid);



        if(topavg > 50) {
            top.setText("Hot");
            top.setTextColor(Color.RED);
        }

        if(botavg > 50) {
            bot.setText("Hot");
            bot.setTextColor(Color.RED);
        }

        if(junavg > 50) {
            jun.setText("Hot");
            jun.setTextColor(Color.RED);
        }

        if(midavg > 50) {
            mid.setText("Hot");
            mid.setTextColor(Color.RED);
        }

    }

}
