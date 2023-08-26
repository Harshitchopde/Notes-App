package com.example.notesapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Notes.class, exportSchema = false, version = 3)
public
abstract
class DataBaseHelper extends RoomDatabase {
    private static final String DB_NOTES = "Notesdb";
    private static DataBaseHelper instance;
public
    static synchronized

    DataBaseHelper getDB(Context context) {//<-this you pass in databaseHelper
        if (instance == null) {
            instance = Room.databaseBuilder(context, DataBaseHelper.class, DB_NOTES)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    // below is to provide abstraction layer to access interface DAO
public abstract NotesDAO notesDAO();
}
