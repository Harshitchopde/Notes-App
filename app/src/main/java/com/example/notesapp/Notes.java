package com.example.notesapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notes")
public
class Notes {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "word")
    private String word;
    @ColumnInfo(name = "meaning")
    private String meaning;
    @ColumnInfo(name ="boolean" )
    private boolean isSelected=false;

    public Notes(int id ,String word,String meaning){
        this.word = word;
        this.id = id;
        this.meaning = meaning;
    }
    @Ignore
    public Notes(String word,String meaning){
        this.word = word;
        this.meaning = meaning;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public
    int getId() {
        return id;
    }

    public
    void setId(int id) {
        this.id = id;
    }

    public
    String getWord() {
        return word;
    }

    public
    void setWord(String word) {
        this.word = word;
    }

    public
    String getMeaning() {
        return meaning;
    }

    public
    void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
