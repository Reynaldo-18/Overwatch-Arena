package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

/*
* This is where you'll see your enemy
* If you choose to run away -> goes back to MainActivity.java
*
* IF you choose to fight -> sends user to MovesActivity.java
*
*
* */

public class BattleActivity extends AppCompatActivity {

    //private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG = ".BattleActivity";
    private TextView playerName, enemyName;
    private ImageView playerImage, enemyImage;
    private ProgressBar playerHealthbar, enemyHealthbar;
    private Player player;
    private Enemy enemy;
    private ConstraintLayout background;
    private GameSettings settings;
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String COLOR = "color";
    public static final String TROPHY_IMG = "trophyImage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        Log.d(TAG, "onCreate: On battleActivity");
        Bundle extras = getIntent().getExtras();
        loadPlayersData(extras);
        loadViews();
        loadSettings(extras);
        reloadData();
    }

    //---------------------------LoadData--------------------------------------

    public void loadPlayersData(Bundle extras){
        player = (Player) extras.getSerializable("player");
        enemy = (Enemy) extras.getSerializable("enemyGenerated");
        Player tempPlayer = (Player) extras.getSerializable("player_from_move");
        Enemy tempEnemy = (Enemy) extras.getSerializable("enemy_from_move");
        if(tempPlayer != null && tempEnemy != null){
            player = tempPlayer;
            enemy = tempEnemy;
        }
    }

    public void launchLoserActivity() {
        Intent intent = new Intent(this, LoserActivity.class);
        if(player != null) {
            Log.d(TAG, "launchLoserActivity: player is not null");
            intent.putExtra("playerlose", player);
        }
        if(enemy != null) {
            Log.d(TAG, "launchLoserActivity: enemy is not null");
            intent.putExtra("enemywins", enemy);
        }
        startActivity(intent);
        finish();
    }

    private void loadViews() {
        background = (ConstraintLayout) findViewById(R.id.battle_background);
        playerName = (TextView) findViewById(R.id.player_name_battle);
        playerName.setText(player.getName());
        playerImage = (ImageView) findViewById(R.id.player_image_battle);
        playerImage.setImageResource(player.getImage());
        enemyName =(TextView) findViewById(R.id.enemy_name_battle);
        enemyName.setText(enemy.getName());
        enemyImage = (ImageView) findViewById(R.id.enemy_image_battle);
        enemyImage.setImageResource(enemy.getImage());
        playerHealthbar = (ProgressBar) findViewById(R.id.player_healthbar);
        playerHealthbar.setProgress(player.getHp());
        playerHealthbar.setMax(100);
        enemyHealthbar = (ProgressBar) findViewById(R.id.enemy_healthbar);
        enemyHealthbar.setProgress(enemy.getHp());
        enemyHealthbar.setMax(100);
    }

    public void loadSettings(Bundle extras){
        settings = (GameSettings) extras.getSerializable("settings");
        GameSettings s = (GameSettings) extras.getSerializable("settings_to_battle");
        if(s != null){
            settings = s;
        }
        if(settings == null){
            settings = new GameSettings();
            background.setBackgroundResource(settings.getBgColor());
        }else{
            background.setBackgroundColor(settings.getBgColor());
        }
        Log.d(TAG, "onCreate: trophy is: " + settings.getTrophyString());

    }


    //-------------------------------ActionListeners----------------------------

    public void onClickFight(View view){
        Intent intent = new Intent(this, MovesActivity.class);
        intent.putExtra("settings", settings);
        intent.putExtra("player", player);
        intent.putExtra("enemy",enemy);
        startActivity(intent);
        finish();
    }

    // If user chickens away, it should add a Loss to their record
    public void onClickRun(View view){
        launchLoserActivity();
    }




    //-------------------------SharedPreferences----------------------------------------------

    public void reloadData(){
        loadData();
        updateViews();
        saveData();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COLOR, settings.getBgColor());
        editor.putInt(TROPHY_IMG, settings.getTrophy());
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        settings.setTrophy(sharedPreferences.getInt(TROPHY_IMG, 0));
        settings.setBgColor(sharedPreferences.getInt(COLOR, 0));
    }

    public void updateViews(){
        background.setBackgroundColor(settings.getBgColor());
    }

}
