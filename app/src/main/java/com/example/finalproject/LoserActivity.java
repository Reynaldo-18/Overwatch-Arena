package com.example.finalproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class LoserActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private NotificationManager notificationManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String PRIMARY_CHANNEL_NAME = "my primary notification channel";
    private static final int IMPORTANCE_LEVEL = NotificationManager.IMPORTANCE_DEFAULT;
    private static final int NOTIFICATION_ID_0 = 0;
    private Player player;
    private Enemy enemy;
    private ImageView playerImg;
    private TextView losingPrompt;
    private String lostScore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loser_activity);

        player = new Player();
        enemy = new Enemy();
        Bundle extras = getIntent().getExtras();
        losingPrompt = (TextView) findViewById(R.id.lose_prompt);
        playerImg = (ImageView) findViewById(R.id.playerimg_lose);



        if(extras.getSerializable("playerlose") != null) {
            Log.d(TAG, "onCreate: player is not null");
            player = (Player) extras.getSerializable("playerlose");
        }
        if(extras.getSerializable("enemywins") != null) {
            Log.d(TAG, "onCreate: enemy is not null");
            enemy = (Enemy) extras.getSerializable("enemywins");
        }

        Player tempPlayer = (Player) extras.getSerializable("playerlose_damage");
        Enemy tempEnemy = (Enemy) extras.getSerializable("enemywins_damage");
        if(tempPlayer != null && tempEnemy != null){
            player = tempPlayer;
            enemy = tempEnemy;
        }

        int LostBy = player.getHp() - enemy.getHp();
        lostScore = "Your hero " + player.getName() + " lost the enemy hero by " + LostBy + " hp!";
        losingPrompt.setText(lostScore);
        playerImg.setImageResource(player.getImage());

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sendNotification();
    }

    public void onClickReturn(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("player_lose", player);
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
                    .setContentTitle("You've lost..")
                    .setContentText(lostScore)
                    .setSmallIcon(R.drawable.ic_action_victory);
        } else {
            notificationBuilder = new NotificationCompat.Builder(this)
                    .setPriority(IMPORTANCE_LEVEL)
                    .setContentTitle("You've lost..").setContentText(lostScore)
                    .setSmallIcon(R.drawable.ic_action_victory);
        }
        notificationBuilder.setContentIntent(pendingIntent);
        notificationManager.notify(NOTIFICATION_ID_0, notificationBuilder.build());
    }
}
