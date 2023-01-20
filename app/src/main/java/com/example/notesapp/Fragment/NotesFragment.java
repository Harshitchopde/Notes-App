package com.example.notesapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.notesapp.R;

public
class NotesFragment extends Fragment {
    RecyclerView recyclerView;
    LinearLayout llrow;
    Button btnCreate;

    public
    NotesFragment() {
        // Required empty public constructor
    }



    @Override
    public
    View onCreateView(LayoutInflater inflater, ViewGroup container,
                      Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_notes, container, false);
        btnCreate = view.findViewById(R.id.);
        recyclerView = view.findViewById(R.id.);
        llrow = view.findViewById(R.id.linearlayout);
        return view;
    }
}