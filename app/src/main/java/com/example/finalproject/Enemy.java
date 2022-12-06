package com.example.finalproject;

public class Enemy extends Character{

    private Character generatedCharacter;
    private String name;
    private int image;
    private String[] attackMoves;

    public Enemy(){
        this("No-Name",0, new String[]{"No-Attacks"});
    }
    public Enemy(String name, int image, String[] attackMoves){
        super(name,image,attackMoves);
    }

    public void setGeneratedCharacter(Character generatedCharacter){
        this.generatedCharacter = generatedCharacter;
        setName(generatedCharacter.getName());
        setImage(generatedCharacter.getImage());
        setAttackMoves(generatedCharacter.getAllAttackMoves());
    }
    public Character getGeneratedCharacter(){
        return generatedCharacter;
    }
}
