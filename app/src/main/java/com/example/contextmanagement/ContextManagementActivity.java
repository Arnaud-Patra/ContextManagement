package com.example.contextmanagement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ContextManagementActivity extends Activity {

    private String room = "Room1";//"";
    //public RoomContextHttpManager roomContextHttpManager;
    RoomContextHttpManager roomContextHttpManager = new RoomContextHttpManager(this);
    private RoomContextState state;

    public static void retrieveRoomContextState(String room) {
        // Feed Me
    }

    public void onUpdate(RoomContextState context){
        this.state = context;

        //updateContextView;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the Id from EditText
        ((Button) findViewById(R.id.buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                room = ((EditText) findViewById(R.id.editText1))
                        .getText().toString();
                retrieveRoomContextState(room);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    public void checkRoom(View view) {
        roomContextHttpManager.retrieveRoomContextState(room);
    }

    public void switchLight(View view) {

        roomContextHttpManager.switchLight(state,room);
    }


}