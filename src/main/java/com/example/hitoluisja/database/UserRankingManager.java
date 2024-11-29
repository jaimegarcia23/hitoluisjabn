package com.example.hitoluisja.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

public class UserRankingManager {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private static final String TAG = "UserRankingManager";

    public UserRankingManager(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USERNAME, user.getUsername());
        values.put(DatabaseHelper.COLUMN_SCORE, user.getScore());
        values.put(DatabaseHelper.COLUMN_LEVEL, user.getLevel());
        values.put(DatabaseHelper.COLUMN_CHARACTER, user.getCharacter());
        long result = database.insert(DatabaseHelper.TABLE_USERS, null, values);

        if (result == -1) {
            Log.e(TAG, "Error inserting data into the database");
        } else {
            Log.d(TAG, "Data inserted successfully into the database");
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Cursor cursor = database.query(
                DatabaseHelper.TABLE_USERS,
                null,
                null,
                null,
                null,
                null,
                DatabaseHelper.COLUMN_SCORE + " DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_USERNAME)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SCORE)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_LEVEL)),
                        cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CHARACTER))
                );
                user.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                users.add(user);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return users;
    }
}
