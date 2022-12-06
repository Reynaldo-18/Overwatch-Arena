package com.example.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * MainActivity (Starting Point aka where we pick our character)
 * once the character is chosen it'll be passed to BattleActivity.java
 *
 * We need to add Win/Loss system.
 * */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private FirebaseAuth myAuth;

    //-------------------GlobalVariables---------------------------------
    private CardView genjiCardView, meiCardView, moiraCardView, cassidyCardView;
    private CardView roadhogCardView, reaperCardView,mercyCardView,widowCardView;
    private GameSettings settings;
    private ConstraintLayout background;
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String COLOR = "color";
    public static final String TROPHY_IMG = "trophyImage";
    public static final String ISCOLORCHANGED = "colorBoolean";
    private static Player player;
    private static Enemy enemy;
    private ArrayList<Character>listOfCharacters;


    //-------------ActivityState/OnCreateMethod-----------------------------------------------

   /* @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("settings", settings);
        Bundle bundle = new Bundle();
        bundle.putSerializable("array", (Serializable) listOfCharacters);
        outState.putBundle("charlist", bundle);

    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
       settings = (GameSettings) savedInstanceState.getSerializable("settings");
       listOfCharacters = (ArrayList<Character>) savedInstanceState.getBundle("charlist").getSerializable("array");
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: On MainActivity");
        player = new Player();
        enemy = new Enemy();
        myAuth = FirebaseAuth.getInstance();
        loadCharacters();
        getIntentData();
        loadViews();
        loadSettings();
        reloadData();
    }

    //-------------------------LoadingData-CharacterAndSettings-------------------------------------

    public void loadCharacters(){
        listOfCharacters = new ArrayList<>();
        String[]genjiMoves = {"SHURIKEN", "DEFLECT ONCOMING PROJECTILES BACK", "DRAGONBLADE"};
        Character genji = new Character("Genji", R.drawable.genji,genjiMoves);
        listOfCharacters.add(genji);
        String[] meiMoves ={"ENDOTHERMIC BLASTER","ICE WALL OFF MAP","CAST A BLIZZARD TO FREEZE"};
        Character mei = new Character("Mei", R.drawable.mei, meiMoves);
        listOfCharacters.add(mei);
        String[] moiraMoves = {"SOUL SUCK","CAST BIOTIC ORB","COALESCENCE"};
        Character moira = new Character("Moira", R.drawable.moira,moiraMoves);
        listOfCharacters.add(moira);
        String[] cassidyMoves = {"PEACEKEEPER", "MAGNETIC GRENADE", "DEADEYE - IT'S HIGH NOON"};
        Character cassidy = new Character("Cassidy", R.drawable.cassidy,cassidyMoves);
        listOfCharacters.add(cassidy);
        String[] roadhogMoves = {"SCRAP GUN", "CHAIN HOOK", "WHOLE HOG"};
        Character roadhog = new Character("RoadHog", R.drawable.roadhog, roadhogMoves);
        listOfCharacters.add(roadhog);
        String[] reaperMoves = {"HELLFIRE SHOTGUNS", "THE REAPING", "DEATH BLOSSOM"};
        Character reaper = new Character("Reaper", R.drawable.reaper, reaperMoves);
        listOfCharacters.add(reaper);
        String[] mercyMoves ={"CADUCEUS BLASTER","RESURRECT", "VALKYRIE"};
        Character mercy = new Character("Mercy", R.drawable.mercy, mercyMoves);
        listOfCharacters.add(mercy);
        String[] widowMoves = {"WIDOW'S KISS", "VENOM MINE", "INFRA-SIGHT"};
        Character widow= new Character("Widow", R.drawable.widow, widowMoves);
        listOfCharacters.add(widow);
    }

    public void loadSettings(){
        if(settings == null) {
            settings = new GameSettings();
            background.setBackgroundResource(settings.getBgColor());
        }else {
            background.setBackgroundColor(settings.getBgColor());
        }
    }

    //----------------------LoadViewsComponents and ActionListeners------------------------
    public void loadViews(){
        background = (ConstraintLayout) findViewById(R.id.main_background);
        genjiCardView = (CardView) findViewById(R.id.genji);
        meiCardView = (CardView) findViewById(R.id.mei);
        moiraCardView = (CardView) findViewById(R.id.moira);
        cassidyCardView = (CardView) findViewById(R.id.cassidy);
        roadhogCardView = (CardView) findViewById(R.id.roadhog);
        reaperCardView = (CardView) findViewById(R.id.reaper);
        mercyCardView = (CardView) findViewById(R.id.mercy);
        widowCardView = (CardView) findViewById(R.id.widow);

        genjiCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setSelectedCharacter(listOfCharacters.get(0));
                generateEnemy();
                launchBattle();
            }
        });
        meiCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setSelectedCharacter(listOfCharacters.get(1));
                generateEnemy();
                launchBattle();
            }
        });
        moiraCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setSelectedCharacter(listOfCharacters.get(2));
                generateEnemy();
                launchBattle();
            }
        });
        cassidyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setSelectedCharacter(listOfCharacters.get(3));
                generateEnemy();
                launchBattle();
            }
        });
        roadhogCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setSelectedCharacter(listOfCharacters.get(4));
                generateEnemy();
                launchBattle();
            }
        });
        reaperCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setSelectedCharacter(listOfCharacters.get(5));
                generateEnemy();
                launchBattle();
            }
        });
        mercyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setSelectedCharacter(listOfCharacters.get(6));
                generateEnemy();
                launchBattle();
            }
        });
        widowCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setSelectedCharacter(listOfCharacters.get(7));
                generateEnemy();
                launchBattle();
            }
        });
    }


    //-----------------------------SettingsAndOptionListeners---------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.preferences:
                Log.d(TAG, "onOptionsItemSelected: you clicked on item 1!");
                Intent intent = new Intent(this, SettingsActivity.class);
                intent.putExtra("current_settings", settings);
                startActivity(intent);
                break;

            case R.id.scoreboard:
                Log.d(TAG, "onOptionsItemSelected: you clicked on item 2!");
                Intent scoreboardIntent = new Intent(this, ScoreBoardActivity.class);
                scoreboardIntent.putExtra("player", player);
                Bundle bundle = new Bundle();
                bundle.putSerializable("list_of_characters",(Serializable) listOfCharacters );
                scoreboardIntent.putExtra("characterlist", bundle);
                scoreboardIntent.putExtra("settings" , settings);
                startActivity(scoreboardIntent);
                break;

            case R.id.logOff:
                myAuth.signOut();
                Intent logOff = new Intent(this, LogInUser.class);
                logOff.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logOff);
                finish();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }


    //-----------------------DataHandling and SharedPreferences---------------------------

    public void getIntentData(){
        Intent intent = getIntent();
        if(intent.getSerializableExtra("updated_settings") != null) {
            settings = (GameSettings) intent.getSerializableExtra("updated_settings");
        }
        if(intent.getSerializableExtra("player_win") != null){
            player = (Player) intent.getSerializableExtra("player_win");
            player.restoreHp();
            player.restoreAttackDamage();
            for(int i = 0; i < listOfCharacters.size();i++){
                if(player.getName().equals(listOfCharacters.get(i).getName())){
                    listOfCharacters.get(i).addCharacterWin();
                }
            }
        }
        if(intent.getSerializableExtra("player_lose") != null){
            player = (Player) intent.getSerializableExtra("player_lose");
            player.restoreHp();
            player.restoreAttackDamage();
                for (int i = 0; i < listOfCharacters.size(); i++) {
                    if (player.getName().equals(listOfCharacters.get(i).getName())) {
                        listOfCharacters.get(i).addCharacterLoses();
                    }
                }
        }
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COLOR, settings.getBgColor());
        editor.putInt(TROPHY_IMG, settings.getTrophy());
        editor.putBoolean(ISCOLORCHANGED, settings.getColorChanged());
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        settings.setTrophy(sharedPreferences.getInt(TROPHY_IMG, 0));
        settings.setBgColor(sharedPreferences.getInt(COLOR, 0));
        settings.setColorChanged(sharedPreferences.getBoolean(ISCOLORCHANGED, false));
    }

    public void updateViews(){
        background.setBackgroundColor(settings.getBgColor());
    }

    public void reloadData(){
        loadData();
        updateViews();
        saveData();
    }

    //-----------------------EnemyGenerator and LaunchBattle-------------------------------

    public void generateEnemy(){
        int randNumber = (int) (Math.random() * (listOfCharacters.size()));
        enemy.setGeneratedCharacter(listOfCharacters.get(randNumber));
        Log.d(TAG, "generateEnemy: enemy name is: " + enemy.getName());
        Log.d(TAG, "generateEnemy: player name is: " + player.getName());

        if(enemy.getName().equals(player.getName())){
            generateEnemy();
        }
    }

    public void launchBattle() {
       /* Intent intentArray = new Intent(this, WinnerActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", (Serializable)listOfCharacters);
        intentArray.putExtra("characterlist", bundle);*/
        Intent intent = new Intent(this, BattleActivity.class);
        Log.d(TAG, "launchBattle: player hp is: " + player.getHp());
        Log.d(TAG, "launchBattle: enemy hp is: " + enemy.getHp());
        intent.putExtra("player", player);
        intent.putExtra("enemyGenerated", enemy);
        intent.putExtra("settings", settings);
        startActivity(intent);
    }


}