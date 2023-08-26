package com.example.notesapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public
class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.ViewHolder> {
    DataBaseHelper dataBaseHelper;
    Context context;
    ArrayList<Notes> arrayNotes;
    Toolbar toolbar;


    RecycleViewAdapter(Context context, ArrayList<Notes> arrayNotes, DataBaseHelper dataBaseHelper, Toolbar toolbar) {
        this.arrayNotes = arrayNotes;
        this.context = context;
        this.dataBaseHelper = dataBaseHelper;
        this.toolbar = toolbar;

    }

    @Override
    public
    RecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notes_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public
    void onBindViewHolder(@NonNull RecycleViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // set text to the cotent to bind the data to the layout
        int n = arrayNotes.size()-1;

        final Notes model = arrayNotes.get(n-position);

        String title = model.getWord();
        String detail = model.getMeaning();
        holder.textWord.setText(title);
        holder.textMeaning.setText(detail);
//        holder.llout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public
//            void onClick(View v) {
//
////
//            }
//        });
        holder.llout.setBackgroundColor(Color.WHITE);

        holder.textWord.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public
            boolean onLongClick(View v) {
                // check point do not pass getapplcation contxt  other wise it give null point exception
//                Toast.makeText(context, "cl", Toast.LENGTH_SHORT).show();
//                deleteHolder(position);

                model.setSelected(!model.isSelected());
                // below is for custom colour
//                holder.llout.setBackgroundColor(model.isSelected() ? ContextCompat.getColor(context,R.color.purple_500) : Color.WHITE);

                holder.llout.setBackgroundColor(model.isSelected() ?Color.LTGRAY : Color.WHITE);
                holder.shareBTN.setBackgroundColor(model.isSelected() ?Color.LTGRAY : Color.WHITE);


                return true;
            }


        });


        holder.ishare.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                Intent ishared = new Intent(Intent.ACTION_SEND);
                ishared.setType("text/plain")
                        .putExtra(Intent.EXTRA_TEXT,""+title+"\n"+detail);
                context.startActivity(Intent.createChooser(ishared,"Share to"));

            }
        });
    }

    @Override
    public
    int getItemCount() {
        return arrayNotes.size();
    }

    public
    class ViewHolder extends RecyclerView.ViewHolder {
        // what ever you want to show declear have to declear
        TextView textWord, textMeaning;
        LinearLayout llout;
        AppCompatImageButton ishare;
        RecyclerView recyclerView;
        AppCompatImageButton shareBTN;

        public
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textWord = itemView.findViewById(R.id.wordid);
            textMeaning = itemView.findViewById(R.id.meaningid);
            llout = itemView.findViewById(R.id.llrow);
            shareBTN = itemView.findViewById(R.id.shareBTN);
            recyclerView = itemView.findViewById(R.id.recycleview);
            ishare = itemView.findViewById(R.id.shareBTN);

        }
    }

    public
    void deleteHolder(int pos) {
        AlertDialog alertDia = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure want to delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public
                    void onClick(DialogInterface dialog, int which) {
                        dataBaseHelper.notesDAO().deleteNotes(new Notes(arrayNotes.get(pos).getId(), arrayNotes.get(pos).getWord(), arrayNotes.get(pos).getMeaning()));
                        ((MainActivity) context).showNotes();

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    public
    void update(int pos) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.adding_update);
        TextView addUpdate = dialog.findViewById(R.id.add);
        Button btn = dialog.findViewById(R.id.addingBtn);
        EditText word = dialog.findViewById(R.id.wordIP);
        EditText meaning = dialog.findViewById(R.id.meaningIP);
        word.setText(arrayNotes.get(pos).getWord());
        addUpdate.setText("Update text");
        meaning.setText(arrayNotes.get(pos).getMeaning());
        btn.setText("Update");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public
            void onClick(View v) {
                String Word = word.getText().toString();
                String Meaning = meaning.getText().toString();
                if(TextUtils.isEmpty(Word)){
                    word.setError("Title can't be empty");
                }
                else if (TextUtils.isEmpty(Meaning)){
                    meaning.setError("Content can not be empty");
                }
                else {
                    dataBaseHelper.notesDAO().updateNotes(new Notes(arrayNotes.get(pos).getId(), Word, Meaning));
                    ((MainActivity) context).showNotes();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();


    }
}
