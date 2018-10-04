package com.example.youssef.googlenews.data;

public class Favourites {

    public static final String TABLE_NAME = "LIST_FAVOURITE";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FAVOURITE = "favourite";
    public static final String COLUMN_TIME = "time";

    private int id;
    private String faviurite;
    private String time;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_FAVOURITE + " TEXT,"
                    + COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Favourites() {
    }

    public Favourites(int id, String favourite, String times) {
        this.id = id;
        this.faviurite = favourite;
        this.time = times;
    }

    public int getId() {
        return id;
    }

    public String getNote() {
        return faviurite;
    }

    public void setNote(String note) {
        this.faviurite = note;
    }

    public String getTimes() {
        return time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTimestamp(String timestamp) {
        this.time = timestamp;
    }
}