package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreBoardAdapter extends RecyclerView.Adapter<ScoreBoardAdapter.ScoreHolder> {

    private static final String TAG = "ScoreBoardAdapter";
    private Context context;
    private ArrayList<Character> characterList;
    private Player player;
    private String avatarName;
    private int image;
    private int wins;
    private int loses;


    public ScoreBoardAdapter(Context context, ArrayList<Character> characterList, Player player ){
        Log.d(TAG, "ScoreBoardAdapter: is running");
        this.context = context;
        this.characterList = characterList;
        this.player = player;
    }

    @NonNull
    @Override
    public ScoreBoardAdapter.ScoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.scoreboard,parent,false);
        return new  ScoreBoardAdapter.ScoreHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreBoardAdapter.ScoreHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: this code is running");
        if(characterList.get(position) != null){
            avatarName = characterList.get(position).getName();
            Log.d(TAG, "onBindViewHolder: name of current avatar is: " + avatarName);
            image = characterList.get(position).getImage();
            wins = characterList.get(position).getCharacterWins();
            loses = characterList.get(position).getCharacterLoses();
            holder.avatarTittle.setText(avatarName);
            holder.avatarImg.setImageResource(image);
            holder.avatarWins.setText(Integer.toString(wins));
            holder.avatarLoses.setText(Integer.toString(loses));
        }
    }


    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class ScoreHolder extends RecyclerView.ViewHolder{

        ImageView avatarImg;
        TextView avatarTittle;
        TextView avatarWins;
        TextView avatarLoses;

        public ScoreHolder(@NonNull View itemView) {
            super(itemView);
            avatarImg = itemView.findViewById(R.id.avatar_image);
            avatarTittle = itemView.findViewById(R.id.avatar_title);
            avatarWins = itemView.findViewById(R.id.avatar_wins);
            avatarLoses = itemView.findViewById(R.id.avatar_loses);
        }
    }
}
