package com.example.finalproject;

public class UserInfo {
    public String Name;
    public String Email;
    public String Password;
    public String Winner;
    public String Looser;


    public UserInfo(String Winner, String Looser){
        this.Winner = Winner;
        this.Looser = Looser;
    }

    public UserInfo(String Name, String Email, String Password){
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
    }
}
