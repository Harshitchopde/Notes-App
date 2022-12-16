package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public
class MainActivity extends AppCompatActivity {
    FloatingActionButton floatingActionButton;
    Button btnCreate;
    RecyclerView recyclerView;
    LinearLayout llrow;

    DataBaseHelper dataBaseHelper;

    @Override
    protected
    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVal();
        showNotes();


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                // not pass the get application context
                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.adding_update);
                EditText wordl = dialog.findViewById(R.id.wordIP);
                EditText meaningl = dialog.findViewById(R.id.meaningIP);
                Button btnAdd = dialog.findViewById(R.id.addingBtn);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public
                    void onClick(View v) {
                        String word = wordl.getText().toString();
                        String meaning = meaningl.getText().toString();
                        dataBaseHelper.notesDAO().addNotes(new Notes(word,meaning));
                        showNotes();
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                floatingActionButton.performClick();
            }
        });
    }
    public void showNotes(){
        ArrayList<Notes> arrayNotes = (ArrayList<Notes>) dataBaseHelper.notesDAO().getALlNotes();
        if (arrayNotes.size()>0){
            llrow.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new RecycleViewAdapter(this,arrayNotes,dataBaseHelper));

        }
        else {
            llrow.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }
    }

    private
    void initVal() {
        floatingActionButton = findViewById(R.id.floatingBtn);
        btnCreate = findViewById(R.id.btuCreate);
        recyclerView = findViewById(R.id.recycleview);
        llrow = findViewById(R.id.linearlayout);
        // step 1 to set up layout on your recycleView
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        // error is geting because .getDB is pass their and it shoud be of public
        dataBaseHelper = dataBaseHelper.getDB(this);
    }
}