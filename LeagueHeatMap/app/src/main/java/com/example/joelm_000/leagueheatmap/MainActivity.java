package com.example.joelm_000.leagueheatmap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    public final static  String EXTRA_MESSAGE = "LoL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            setChampionDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }




        final EditText summonerName = (EditText) findViewById(R.id.summonerName);
        summonerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (summonerName.getText().length() > 0){
                    Button activateButton = (Button) findViewById(R.id.getSummData);
                    activateButton.setClickable(true);

                }

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void displaySummoner(View view){
        Intent intent = new Intent(this, Summoner.class);
        EditText summonerName = (EditText) findViewById(R.id.summonerName);
        String name = summonerName.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, name);
        startActivity(intent);


    }
    public void setChampionDatabase() throws IOException {
        //SharedPreferences sharedPref = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPref = getSharedPreferences("mySharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        InputStream is = getResources().openRawResource(R.raw.champ_lanes);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line="";
        while ((line = reader.readLine())!=null) {


            //String[] seperate =line.split(" ");

            editor.putString(line.substring(0, line.length() - 4), line.substring(line.length() - 4));

        }
        editor.commit();
    }
}
