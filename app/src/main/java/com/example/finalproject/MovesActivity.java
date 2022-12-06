package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class MovesActivity extends AppCompatActivity {

    //private static final String TAG = MainActivity.class.getSimpleName();
    private static final String TAG = ".MovesActivity";

    private TextView playerName;
    private ImageView playerImage;
    private Button firstAttackButton, secondAttackButton, thirdAttackButton;
    private Player player;
    private Enemy enemy;
    private ConstraintLayout background;
    private GameSettings settings;
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String COLOR = "color";
    public static final String TROPHY_IMG = "trophyImage";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moves);

        Log.d(TAG, "onCreate: On MovesActivity");
        Bundle extras = getIntent().getExtras();
        //Resources res = getResources();
        player = (Player) extras.getSerializable("player");
        enemy = (Enemy) extras.getSerializable("enemy");
        loadViews();
        loadSettings(extras);
        reloadData();
        Log.d(TAG, "onCreate: trophy is: " + settings.getTrophyString());
    }

    private void loadSettings(Bundle extras) {
        settings = (GameSettings) extras.getSerializable("settings");
        if(settings == null){
            settings = new GameSettings();
            background.setBackgroundResource(settings.getBgColor());
        }else{
            background.setBackgroundColor(settings.getBgColor());
        }
    }

    private void loadViews() {
        background = (ConstraintLayout) findViewById(R.id.moves_background);
        playerName = (TextView) findViewById(R.id.player_name_moves);
        playerName.setText(player.getName());
        playerImage = (ImageView) findViewById(R.id.player_image_moves);
        playerImage.setImageResource(player.getImage());
        firstAttackButton = (Button) findViewById(R.id.button_first_attack);
        firstAttackButton.setText(player.getFirstAttack());
        firstAttackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setChosenAttack(player.getFirstAttack());
                generateEnemyMove();
                launchDamageActivity();
            }
        });
        secondAttackButton = (Button) findViewById(R.id.button_second_attack);
        secondAttackButton.setText(player.getSecondAttack());
        secondAttackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setChosenAttack(player.getSecondAttack());
                generateEnemyMove();
                launchDamageActivity();
            }
        });
        thirdAttackButton = (Button) findViewById(R.id.button_third_attack);
        thirdAttackButton.setText(player.getThirdAttack());
        thirdAttackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setChosenAttack(player.getThirdAttack());
                generateEnemyMove();
                launchDamageActivity();

            }
        });
    }

    private void generateEnemyMove(){
        Log.d(TAG, "generateEnemyMove: Array size: " + enemy.getAllAttackMoves().length);
        String enemyGeneratedMove = "";
        int randNumber = (int) (Math.random() * enemy.getAllAttackMoves().length);
        if (randNumber == 0){
            enemyGeneratedMove = enemy.getFirstAttack();
            enemy.setChosenAttack(enemyGeneratedMove);
        } else if(randNumber == 1){
            enemyGeneratedMove = enemy.getSecondAttack();
            enemy.setChosenAttack(enemyGeneratedMove);
        }else if(randNumber == 2){
            enemyGeneratedMove = enemy.getThirdAttack();
            enemy.setChosenAttack(enemyGeneratedMove);
        }
    }
    // NEED TO WORK!!
    // goes to the losers activity screen
    public void launchLoserActivity() {
        Intent intent = new Intent(this, LoserActivity.class);
        startActivity(intent);
    }


    public void launchBattleActivity() {
        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra("settings_to_battle", settings);
        intent.putExtra("player_from_move", player);
        intent.putExtra("enemy_from_move", enemy);
        startActivity(intent);
        finish();
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

    public void reloadData(){
        loadData();
        updateViews();
        saveData();
    }

    public void launchDamageActivity(){
        Intent intent = new Intent(this, DamageActivity.class);
        intent.putExtra("player", player);
        intent.putExtra("enemy", enemy);
        intent.putExtra("settings", settings);
        startActivity(intent);
        finish();
    }
}
