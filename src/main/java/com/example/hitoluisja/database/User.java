package com.example.hitoluisja.database;

public class User {
    private int id;
    private String username;
    private int score;
    private int level;
    private String character;

    public User(String username, int score, int level, String character) {
        this.username = username;
        this.score = score;
        this.level = level;
        this.character = character;
    }

    // Getters y setters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public int getScore() { return score; }
    public int getLevel() { return level; }
    public String getCharacter() { return character; }

    public void setId(int id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setScore(int score) { this.score = score; }
    public void setLevel(int level) { this.level = level; }
    public void setCharacter(String character) { this.character = character; }
}
