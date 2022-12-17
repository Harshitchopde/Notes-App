package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public
class MainActivity extends AppCompatActivity {
    private static final String CHHANEL = "My Channel";
    private static final int ID = 100;
    private static final int REQUESTCODE = 101;
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
                        if(word.equals("")){
                            wordl.setError("Title can't be empty");
                            Toast.makeText(MainActivity.this, "hii", Toast.LENGTH_SHORT).show();
                        }
                        else if (TextUtils.isEmpty(meaning)){
                            meaningl.setError("Content can not be empty");
                        }
                        else {

                            Toast.makeText(MainActivity.this, "hi", Toast.LENGTH_SHORT).show();

                            dataBaseHelper.notesDAO().addNotes(new Notes(word, meaning));
                            showNotes();
                            dialog.dismiss();
                        }


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

        // Drawable image to bitmap
        Drawable drawable = ResourcesCompat.getDrawable(getResources(),R.drawable.icon_pic,null);
        BitmapDrawable bm = (BitmapDrawable) drawable;
        assert bm != null;
        Bitmap bitmap = bm.getBitmap();
        Intent iNotify = new Intent(getApplicationContext(),MainActivity.class);
        //below is do if intent activitu is already is in the stack than it will clear the top all activity
        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // i made that setFlags that is why my app crash
        // Create pending intent
        PendingIntent pi =PendingIntent.getActivity(this,REQUESTCODE,iNotify,PendingIntent.FLAG_IMMUTABLE);

        // Show the notification on your app when your app is opened
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             notification =new Notification.Builder(this)
                    .setContentText("New Message")
                            .setLargeIcon(bitmap)
                     .setContentIntent(pi)
                                    .setSmallIcon(R.drawable.icon_pic)
                                            .setSubText("Create new notes")
                                                    .setChannelId(CHHANEL)
                    .build();
            nm.createNotificationChannel(new NotificationChannel(CHHANEL,"My app",NotificationManager.IMPORTANCE_HIGH));
            
        }
        else {
            notification =new Notification.Builder(this)
                    .setContentText("New Message")
                    .setLargeIcon(bitmap)
                    .setSmallIcon(R.drawable.icon_pic)
                    .setContentIntent(pi)
                    .setSubText("Create new notes")
                    .build();


        }
        nm.notify(ID,notification);


        // step 1 to set up layout on your recycleView
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        // error is geting because .getDB is pass their and it shoud be of public
        dataBaseHelper = dataBaseHelper.getDB(this);


    }
}