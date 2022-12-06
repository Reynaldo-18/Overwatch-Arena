package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreBoardActivity extends AppCompatActivity {

    private static final String TAG = "ScoreboardActivity";
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String COLOR = "color";
    public static final String ISCOLORCHANGED = "colorBoolean";
    public static final String WINS = "totalWins";
    public static final String LOSES = "totalLoses";
    private TextView winCount;
    private TextView loseCount;
    private RecyclerView myRecycleView;
    private ScoreBoardAdapter adapter;
    private ArrayList<Character> characterList;
    private Player player;
    private int totalWins;
    private int totalLose;
    private int wCount;
    private int lCount;
    private GameSettings settings;
    private ConstraintLayout background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        getIntentData();
       // reloadData();
        loadScore();
        loadViews();
        recycleViewSetUp();

    }

    public void loadViews(){
        Log.d(TAG, "loadViews: win count is: " + totalWins);
        Log.d(TAG, "loadViews: lose count is: " + totalLose);
        background = (ConstraintLayout) findViewById(R.id.scoreboard_backgound);
        background.setBackgroundColor(settings.getBgColor());
        winCount = (TextView) findViewById(R.id.total_win_count);
        winCount.setText(Integer.toString(totalWins));
        loseCount = (TextView) findViewById(R.id.total_lose_count);
        loseCount.setText(Integer.toString(totalLose));
    }

    public void loadScore(){
       // wCount = player.getWins();
        //lCount = player.getLoses();
        totalWins = player.getWins();
        totalLose = player.getLoses();
    }

    private void recycleViewSetUp(){
        myRecycleView = findViewById(R.id.recycleview);
        adapter = new ScoreBoardAdapter(this, characterList, player);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1, GridLayoutManager.VERTICAL, false);
        myRecycleView.setLayoutManager(gridLayoutManager);
        myRecycleView.setHasFixedSize(false);
        myRecycleView.setAdapter(adapter);
    }

    public void getIntentData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("characterlist");
        characterList = (ArrayList<Character>) bundle.getSerializable("list_of_characters");
        Log.d(TAG, "getIntentData: first character in list is: " + characterList.get(0).getName());
        Log.d(TAG, "getIntentData: list of character size is: " + characterList.size());
        player = (Player) intent.getSerializableExtra("player");
        Log.d(TAG, "getIntentData: player is : " + player.getName());
        settings = (GameSettings) intent.getSerializableExtra("settings");
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COLOR, settings.getBgColor());
        editor.putBoolean(ISCOLORCHANGED, settings.getColorChanged());
        editor.putInt(WINS, totalWins);
        editor.putInt(LOSES, totalLose);
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        settings.setBgColor(sharedPreferences.getInt(COLOR, 0));
        settings.setColorChanged(sharedPreferences.getBoolean(ISCOLORCHANGED, false));
        totalWins = sharedPreferences.getInt(WINS, 0);
        totalLose = sharedPreferences.getInt(LOSES, 0);
    }

    public void updateViews(){
        background.setBackgroundColor(settings.getBgColor());
        winCount.setText(Integer.toString(totalWins));
        loseCount.setText(Integer.toString(totalLose));
    }

    public void reloadData(){
        saveData();
        loadData();
        updateViews();
    }
}