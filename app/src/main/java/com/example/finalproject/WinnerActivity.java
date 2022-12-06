package com.example.finalproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

public class WinnerActivity extends AppCompatActivity {

    private static final String TAG = ".WinnerActivity";
    private NotificationManager notificationManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String PRIMARY_CHANNEL_NAME = "my primary notification channel";
    private static final int IMPORTANCE_LEVEL = NotificationManager.IMPORTANCE_DEFAULT;
    private static final int NOTIFICATION_ID_0 = 0;
    private GameSettings settings;
    private ConstraintLayout background;
    private Player player;
    private Enemy enemy;
    private ImageView trophyImg, winnerImg;
    private TextView winnerTitle;
    private String WonBy_Text;
    public static final String SHARED_PREFS = "sharedPrefs";
    private static final String COLOR = "color";
    public static final String TROPHY_IMG = "trophyImage";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winner_activity);

        Bundle extras = getIntent().getExtras();

        winnerTitle = (TextView) findViewById(R.id.score_winning);
        winnerImg = (ImageView) findViewById(R.id.player_image_winning);
        player = (Player) extras.getSerializable("player");
        player.addWin();
        enemy = (Enemy) extras.getSerializable("enemy");

        background = (ConstraintLayout) findViewById(R.id.bg_winner);
        trophyImg = (ImageView) findViewById(R.id.trophy_img);
        settings = (GameSettings) extras.getSerializable("settings");
        if(settings == null){
            settings = new GameSettings();
        }
        trophyImg.setImageResource(settings.getTrophy());
        background.setBackgroundColor(settings.getBgColor());
        Log.d(TAG, "onCreate: trophy is: " + settings.getTrophyString());



        int WonBy = player.getHp() - enemy.getHp();
        WonBy_Text = "Your hero " + player.getName() + " won the enemy hero by " + WonBy + " hp!";
        winnerTitle.setText(WonBy_Text);
        winnerImg.setImageResource(player.getImage());

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sendNotification();

        loadData();
        updateViews();
        saveData();

    }


    public void onClickReturn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("player_win", player);
        startActivity(intent);
        finish();
    }

    protected void sendNotification() {
        NotificationCompat.Builder notificationBuilder;
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID_0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(Build.VERSION.SDK_INT >= 26){
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, PRIMARY_CHANNEL_NAME, IMPORTANCE_LEVEL);
            notificationChannel.setDescription("Reminders");
            notificationManager.createNotificationChannel(notificationChannel);
            notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                    .setContentTitle("You've won!")
                    .setContentText(WonBy_Text)
                    .setSmallIcon(R.drawable.ic_action_victory);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setPriority(IMPORTANCE_LEVEL)
                    .setContentTitle("You've won!").setContentText(WonBy_Text)
                    .setSmallIcon(R.drawable.ic_action_victory);
        }
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID_0, notificationBuilder.build());
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
        trophyImg.setImageResource(settings.getTrophy());
        background.setBackgroundColor(settings.getBgColor());
    }
}
