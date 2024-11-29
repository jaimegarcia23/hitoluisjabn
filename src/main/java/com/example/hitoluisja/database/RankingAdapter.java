package com.example.hitoluisja.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.hitoluisja.R;

import java.util.List;

public class RankingAdapter extends BaseAdapter {

    private Context context;
    private List<User> userList;

    public RankingAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_ranking, parent, false);
        }

        User user = userList.get(position);

        TextView usernameTextView = convertView.findViewById(R.id.usernameTextView);
        TextView scoreTextView = convertView.findViewById(R.id.scoreTextView);
        TextView levelTextView = convertView.findViewById(R.id.levelTextView);
        TextView characterTextView = convertView.findViewById(R.id.characterTextView);

        usernameTextView.setText(user.getUsername());
        scoreTextView.setText("Score: " + user.getScore());
        levelTextView.setText("Level: " + user.getLevel());
        characterTextView.setText("Character: " + user.getCharacter());

        return convertView;
    }
}
