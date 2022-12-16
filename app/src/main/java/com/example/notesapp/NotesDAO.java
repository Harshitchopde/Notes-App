package com.example.notesapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public
interface NotesDAO {
    @Query("select * from Notes")
    List<Notes> getALlNotes();
    @Insert
    void addNotes(Notes notes);
    @Delete
    void deleteNotes(Notes notes);
    @Update
    void updateNotes(Notes notes);

}
