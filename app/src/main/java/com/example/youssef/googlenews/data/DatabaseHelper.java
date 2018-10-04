package com.example.youssef.googlenews.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.youssef.googlenews.RecyclerView.Fav;
import com.example.youssef.googlenews.RecyclerView.NewsModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "favourites_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Favourites.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Favourites.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public long insertNote(String favourite) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Favourites.COLUMN_FAVOURITE, favourite);

        // insert row
        long id = db.insert(Favourites.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Favourites getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Favourites.TABLE_NAME,
                new String[]{Favourites.COLUMN_ID, Favourites.COLUMN_FAVOURITE, Favourites.COLUMN_TIME},
                Favourites.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Favourites note = new Favourites(
                cursor.getInt(cursor.getColumnIndex(Favourites.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Favourites.COLUMN_FAVOURITE)),
                cursor.getString(cursor.getColumnIndex(Favourites.COLUMN_TIME)));

        // close the db connection
        cursor.close();

        return note;
    }

    public List<Favourites> getAllNotes() {
        List<Favourites> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Favourites.TABLE_NAME + " ORDER BY " +
                Favourites.COLUMN_TIME + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Favourites note = new Favourites();
                note.setId(cursor.getInt(cursor.getColumnIndex(Favourites.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Favourites.COLUMN_FAVOURITE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Favourites.COLUMN_TIME)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Favourites.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateNote(Favourites note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Favourites.COLUMN_FAVOURITE, note.getNote());

        // updating row
        return db.update(Favourites.TABLE_NAME, values, Favourites.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(Favourites note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Favourites.TABLE_NAME, Favourites.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
}
