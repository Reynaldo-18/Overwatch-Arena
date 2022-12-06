package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import yuku.ambilwarna.AmbilWarnaDialog;

public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = ".SettingsActivity";
    private GameSettings settings;
    private ImageView trophyIcon;
    private Button colorButton;
    private Button saveButton;
    private int defaultColor;
    private CardView colorPicked;
    private Spinner spinner;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TROPHY_IMG = "trophyImage";
    public static final String ISCOLORCHANGED = "colorBoolean";
    public static final String COLOR = "color";
    private int trophy;
    private int color;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("spinner", spinner.getSelectedItemPosition());

        // do this for each or your Spinner
        // You might consider using Bundle.putStringArray() instead
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getIntentData();
        loadView();
        loadData();
        updateViews();
      //  colorPicked.setCardBackgroundColor(settings.getBgColor());
       // trophyIcon.setImageResource(settings.getTrophy());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int storeIndexItem = savedInstanceState.getInt("spinner");

    }


    public void loadView(){
        colorButton = (Button) findViewById(R.id.color_button);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }});
        saveButton =(Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
                sendIntentData();
            }});
        defaultColor = ContextCompat.getColor(SettingsActivity.this, R.color.colorPrimary);
        trophyIcon = (ImageView) findViewById(R.id.trophy_icon);
        colorPicked = (CardView) findViewById(R.id.color_picked);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.trophies, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equals("Select Trophy")){
                    //do nothing...
                } else if (parent.getItemAtPosition(position).toString().equals("Thumbs-up")) {
                    settings.setTrophy(R.drawable.thumbs_up);
                    settings.setTrophyString("thumbs-up");
                    trophyIcon.setImageResource(settings.getTrophy());
                } else if (parent.getItemAtPosition(position).toString().equals("Cup")) {
                    settings.setTrophy(R.drawable.cup);
                    settings.setTrophyString("cup");
                    trophyIcon.setImageResource(settings.getTrophy());
                } else if (parent.getItemAtPosition(position).toString().equals("Medal")) {
                    settings.setTrophy(R.drawable.medal);
                    settings.setTrophyString("medal");
                    trophyIcon.setImageResource(settings.getTrophy());
                    //settings.setTrophy(parent.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                trophyIcon.setImageResource(settings.getTrophy());
            }
        });

    }


    public void openColorPicker(){
        AmbilWarnaDialog ambilWarnaDialog =new AmbilWarnaDialog(this, defaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {

                //defaultColor = color;
                settings.setBgColor(color);
                colorPicked.setCardBackgroundColor(settings.getBgColor());
                settings.setColorChanged(true);

            }
        });
        ambilWarnaDialog.show();
    }

    public void getIntentData(){
        Intent intent = getIntent();
        settings = (GameSettings) intent.getSerializableExtra("current_settings");
    }

    public void sendIntentData(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("updated_settings", settings);
        startActivity(intent);
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(TROPHY_IMG,settings.getTrophy());
        editor.putInt(COLOR, settings.getBgColor());
        editor.putBoolean(ISCOLORCHANGED, settings.getColorChanged());
        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        trophy = sharedPreferences.getInt(TROPHY_IMG, 0);
        color = sharedPreferences.getInt(COLOR, 0);
        settings.setColorChanged(sharedPreferences.getBoolean(ISCOLORCHANGED, false));
    }

    public void updateViews(){
        trophyIcon.setImageResource(trophy);
        colorPicked.setCardBackgroundColor(color);
    }

}