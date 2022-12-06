package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DamageActivity extends AppCompatActivity implements Runnable {

    private static final String TAG = "damageActivity";
    private ProgressBar healthbar;
    private ImageView avatarImg;
    private TextView avatarText;
    private TextView damagePrompt;
    private ConstraintLayout background;
    private Thread thread;
    private boolean isRunning = true;
    private Handler handler;
    private int count;
    private Player player;
    private Enemy enemy;
    private GameSettings settings;
    private int damage;
    private boolean isEnemyDamaged;
    private boolean isPlayerDamaged;
    private int countDamage;
    private ValueAnimator valueAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: on DamageActivity");
        setContentView(R.layout.activity_damage);
        isPlayerDamaged = false;
        isEnemyDamaged = false;
        getIntentData();
        calculateHealth();
        loadViews();
        startThread();


    }

    public void loadViews(){

        healthbar = (ProgressBar) findViewById(R.id.damage_healthbar);
        avatarImg = (ImageView) findViewById(R.id.damage_avatar_image);
        avatarText = (TextView) findViewById(R.id.damage_name);
        damagePrompt = (TextView) findViewById(R.id.damage_prompt);
        background = (ConstraintLayout) findViewById(R.id.damage_background);
        background.setBackgroundColor(settings.getBgColor());
        if(isPlayerDamaged){
            avatarImg.setImageResource(player.getImage());
            avatarText.setText(player.getName());
            damagePrompt.setText("You got hit by " + enemy.getName() + "(Enemy)!, your health percentage is" + player.getHp() + "%");
            healthbar.setProgress(player.getHp());
            healthbar.setMax(100);
            count = player.getHp();
            player.decreaseHpBy(damage);
        }else if (isEnemyDamaged){
            avatarImg.setImageResource(enemy.getImage());
            avatarText.setText(enemy.getName());
            damagePrompt.setText( enemy.getName() + "(Enemy) got hit by you! " + player.getName() +", enemy's health percentage is" + enemy.getHp() + "%");
            count = enemy.getHp();
            healthbar.setProgress(enemy.getHp());
            healthbar.setMax(100);
            enemy.decreaseHpBy(damage);
        }else{
            avatarImg.setImageResource(R.drawable.tie);
            damagePrompt.setText("No one gets damaged, Attackmove strenghs are equal");
            avatarText.setText("No Damage");
            healthbar.setProgress(100);
            healthbar.setMax(100);
        }



    }

    public void startThread(){
        valueAnimator = ValueAnimator.ofInt(0, 20,-20,15,-15,10,-10,5,-5,3,-3,1,-1);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                avatarImg.setTranslationX(value);
            }
        });
        thread = new Thread(this);
        handler = new Handler();
        valueAnimator.start();
        thread.start();

    }


    public void getIntentData(){
        Intent intent = getIntent();
        player = (Player) intent.getSerializableExtra("player");
        enemy = (Enemy) intent.getSerializableExtra("enemy");
        settings = (GameSettings) intent.getSerializableExtra("settings");

    }

    @Override
    public void run() {
        countDamage = 0;
        while(isRunning){
            if(countDamage <= damage) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        healthbar.setProgress(count);
                        healthbar.setMax(100);
                    }
                });
                Log.d(TAG, "run: count is :" + count);
                count --;
                countDamage++;
                Log.d(TAG, "run: damage is: " + damage);
            }else{
                isRunning = false;
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!player.isAlive()){
            //Launch Loser ACTIVITY
            launchLoserActivity();
            finish();
        }else if(!enemy.isAlive()){
            //Launch WinningActivity
            launchWinningActivity();
            finish();
        }else {
            launchBattleActivity();
            finish();
        }
    }


    public void calculateHealth(){
        damage = 0;
        if(player.getAttackDamage() > enemy.getAttackDamage()){
            damage = player.getAttackDamage() - enemy.getAttackDamage();

            isEnemyDamaged = true;
        }else if(player.getAttackDamage() < enemy.getAttackDamage()) {
            damage = enemy.getAttackDamage() - player.getAttackDamage();
            isPlayerDamaged = true;
        }
        else{
            damage = 5;
        }


    }

    public void launchWinningActivity() {
        Intent intent = new Intent(this, WinnerActivity.class);
        intent.putExtra("settings_to_winner", settings);
        intent.putExtra("player", player);
        intent.putExtra("enemy", enemy);
        startActivity(intent);
        finish();
    }

    public void launchBattleActivity() {
        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra("settings_to_battle", settings);
        intent.putExtra("player_from_move", player);
        intent.putExtra("enemy_from_move", enemy);
        startActivity(intent);
        finish();
    }

    public void launchLoserActivity() {
        Intent intent = new Intent(this, LoserActivity.class);
        if(player != null) {
            Log.d(TAG, "launchLoserActivity: player is not null");
            intent.putExtra("playerlose_damage", player);
        }
        if(enemy != null) {
            Log.d(TAG, "launchLoserActivity: enemy is not null");
            intent.putExtra("enemywins_damage", enemy);
        }
        startActivity(intent);
        finish();
    }
}