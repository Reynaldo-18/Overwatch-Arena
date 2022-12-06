package com.example.finalproject;

import android.graphics.Color;

import java.io.Serializable;

public class GameSettings implements Serializable {

    private int bgColor;
    private int trophy;
    private int difficultyLevel;
    private Difficulty difficulty;
    private int[] colors;
    private boolean colorChanged;
    private String trophyString;


    public GameSettings(){
        this(R.color.white, R.drawable.thumbs_up,Difficulty.EASY);
    }
    public GameSettings(int bgColor, int trophy, Difficulty difficulty){
        this.bgColor = bgColor;
        this.trophy = trophy;
        this.difficulty = difficulty;
        colorChanged = false;
        colors = new int[]{R.color.white,R.color.black,R.color.teal_700};
        trophyString = "thumbs-up";
        switch (difficulty){
            case EASY:
                difficultyLevel = 1;
                break;
            case NORMAL:
                difficultyLevel = 2;
                break;
            case HARD:
                difficultyLevel = 3;
                break;
            default:
                difficultyLevel = 1;
                break;
        }
    }

    public String getTrophyString(){
        if(trophyString.equals("thumbs-up")){
            return "thumbs-up";
        }else if(trophyString.equals("cup")){
            return "cup";
        }else
            return "medal";
    }
    public void setTrophyString(String trophyString){
        this.trophyString = trophyString;
    }

    public int getBgColor() {
        return bgColor;
    }

    public int getTrophy() {
        return trophy;
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public boolean getColorChanged(){
        return this.colorChanged;
    }

    public void setBgColor(int newColor){
        bgColor = newColor;
    }

    public void setTrophy(int newTrophy){
        trophy = newTrophy;
    }
    public void setColorChanged(boolean colorChanged){
        this.colorChanged = colorChanged;
    }
}
