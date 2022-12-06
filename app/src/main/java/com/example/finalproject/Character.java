package com.example.finalproject;

import java.io.Serializable;
import java.util.Arrays;

public class Character implements Serializable {

    private static final String TAG = "Character_Class";
    public static final int MAX_HP = 100;
    private String name;
    private int image;
    private String[] attackMoves;
    private String chosenAttack;
    private int hp;
    private boolean isAlive;
    private Character[] counter;
    private int difficultyLevel;
    private int attackDamage;
    private int characterWins;
    private int characterLoses;

    public Character(){
        this("No-Name-Assigned",0, new String[]{"No-FirstAttack-Assigned","No-SecondAttack-Assigned","No-ThirdAttack-Assigned"});}
    public Character(String name, int image, String[] attackMoves){
        this.name = name;
        this.image = image;
        this.attackMoves = attackMoves;
        hp = MAX_HP;
        isAlive = true;
        chosenAttack = "no-attack-defined";
        counter = null;
        characterWins = 0;
        characterLoses = 0;
    }

    //--------------------Setters------------------------

    public void setName(String name) {
        this.name = name;
    }
    public void setImage(int image) {
        this.image = image;
    }
    public void setAttackMoves(String[] attackMoves) {
        this.attackMoves = attackMoves;
    }
    public void setChosenAttack(String attack){chosenAttack = attack;}
    public void setCounter(Character[] counter){
        this.counter = counter;
    }

    //-------------------Getters-------------------------

    public String getName() {
        return name;
    }
    public int getImage() {
        return image;
    }
    public String[] getAllAttackMoves() {
        return attackMoves;
    }
    public int getHp(){
        return hp;
    }
    public Boolean isAlive(){
        return isAlive;
    }
    public Character[] getCounter(){
        return counter;
    }
    public String getFirstAttack(){
        if(attackMoves[0] != null) {
            attackDamage = 10;
            return attackMoves[0];
        }
        return "this index is null";
    }
    public String getSecondAttack(){
        if (attackMoves[1] != null) {
            attackDamage = 20;
            return attackMoves[1];
        }
        return "this index is null";
    }
    public String getThirdAttack(){
        if (attackMoves[2] != null) {
            attackDamage = 25;
            return attackMoves[2];
        }
        return "this index is null";
    }
    public String getChosenAttack(){
        if (chosenAttack != null) {
            return chosenAttack;
        }
        else{
            return "no-attack-chosen";
        }
    }

    public int getAttackDamage(){
        return attackDamage;
    }

    //-------------------Custom Methods--------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Character)) return false;
        Character character = (Character) o;
        return image == character.image && name.equals(character.name) && Arrays.equals(attackMoves, character.attackMoves);
    }
    public void increaseHpBy(int heal){
        hp = hp + heal;
    }
    public void restoreHp(){
        hp = MAX_HP;
    }
    public void decreaseHpBy(int damage){
        hp = hp - damage;
        if(hp <= 0){
            isAlive = false;
        }
    }

    public void restoreAttackDamage(){
        attackDamage = 0;
    }

    public void addCharacterWin(){
        characterWins++;
    }
    public void addCharacterLoses(){
        characterLoses++;
    }

    public int getCharacterWins() {
        return characterWins;
    }

    public int getCharacterLoses() {
        return characterLoses;
    }
}