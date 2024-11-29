package com.example.hitoluisja;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hitoluisja.database.User;
import com.example.hitoluisja.database.UserRankingManager;
import com.example.hitoluisja.database.RankingAdapter;

import java.util.List;

public class RankingActivity extends AppCompatActivity {

    private static final String TAG = "RankingActivity";
    private ListView rankingListView;
    private UserRankingManager userRankingManager;
    private RankingAdapter rankingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        rankingListView = findViewById(R.id.rankingListView);
        userRankingManager = new UserRankingManager(this);

        // Obtener los usuarios ordenados por puntaje
        List<User> userList = userRankingManager.getAllUsers();

        if (userList.isEmpty()) {
            Log.d(TAG, "No users found in the database.");
        } else {
            Log.d(TAG, "Users found: " + userList.size());
        }

        // Configurar el adaptador
        rankingAdapter = new RankingAdapter(this, userList);
        rankingListView.setAdapter(rankingAdapter);
    }
}
