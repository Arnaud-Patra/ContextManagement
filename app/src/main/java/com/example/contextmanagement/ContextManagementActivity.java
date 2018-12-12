package com.example.contextmanagement;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.contextmanagement.ContextState.LightContextState;
import com.example.contextmanagement.ContextState.RoomContextState;
import com.example.contextmanagement.HTTP.LightContextHttpManager;
import com.example.contextmanagement.HTTP.RoomContextHttpManager;

public class ContextManagementActivity extends Activity {

    private String room = "Room1";//"";
    //public RoomContextHttpManager roomContextHttpManager;
    RoomContextHttpManager roomContextHttpManager = new RoomContextHttpManager(this);
    LightContextHttpManager lightContextHttpManager = new LightContextHttpManager(this);
    private RoomContextState RoomState;
    private LightContextState LightState;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the roomId from EditText
        ((Button) findViewById(R.id.buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String lightId = ((EditText) findViewById(R.id.editText1))
                        .getText().toString();
                //roomContextHttpManager.retrieveRoomContextState(room);
                lightContextHttpManager.retrieveLightContextState(lightId);
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
        //roomContextHttpManager.retrieveRoomContextState(room);
    }

    public void switchLight(View view) {

        roomContextHttpManager.switchLight(RoomState,room);
    }


    public void onUpdate(LightContextState lightContextState){
        this.LightState = lightContextState;
        setContentView(R.layout.lightinfo);

        //See light info
        TextView idView = (TextView) findViewById(R.id.idView);
        idView.setText(LightState.getLightId());

        TextView statusView = (TextView) findViewById(R.id.statusView);
        statusView.setText(LightState.getStatus());

        TextView roomView = (TextView) findViewById(R.id.roomIdView);
        roomView.setText(String.valueOf(LightState.getRoomId()));

        TextView levelView = (TextView) findViewById(R.id.levelView);
        levelView.setText(String.valueOf(LightState.getLevel()));





    }

    public void onRoomUpdate(RoomContextState roomContextState) {

    }
}