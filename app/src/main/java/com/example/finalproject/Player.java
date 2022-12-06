package com.example.finalproject;

public class Player extends Character{

    private Character selectedCharacter;
    private String name;
    private int image;
    private String[] attackMoves;
    private int wins;
    private int loses;

    public Player(){this("No-Name",0, new String[]{"No-Attacks"});}
    public Player(String name, int image, String[] attackMoves){
        super(name,image,attackMoves);
        this.name =name;
        this.image = image;
        this.attackMoves = attackMoves;
        wins = 0;
        loses = 0;
    }
    public void setSelectedCharacter(Character selectedCharacter){
        this.selectedCharacter = selectedCharacter;
        setName(selectedCharacter.getName());
        setImage(selectedCharacter.getImage());
        setAttackMoves(selectedCharacter.getAllAttackMoves());
    }
    public Character getSelectedCharacter(){
        return selectedCharacter;
    }

    public void addWin(){
        wins++;
    }
    public void addLoss(){
        loses++;
    }

    public int getWins(){
        return wins;
    }
    public int getLoses(){
        return loses;
    }

}
