package com.example.contextmanagement;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import com.example.contextmanagement.ContextState.LightContextState;
import com.example.contextmanagement.ContextState.RoomContextState;
import com.example.contextmanagement.HTTP.LightContextHttpManager;
import com.example.contextmanagement.HTTP.RoomContextHttpManager;
import com.example.contextmanagement.Recycler.MyAdapter;
import org.json.JSONArray;

import java.util.ArrayList;

public class ContextManagementActivity extends Activity {

    //private String room = "Room1";//"";
    //public RoomContextHttpManager roomContextHttpManager;
    RoomContextHttpManager roomContextHttpManager = new RoomContextHttpManager(this);
    LightContextHttpManager lightContextHttpManager = new LightContextHttpManager(this);
    MyAdapter myAdapter = new MyAdapter(this);
    private RoomContextState RoomState;
    private LightContextState LightState;

    //Recycler
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        //Get the rooms when first opening the app.
        GetRooms(null);

        /*
        //Get the roomId from EditText
        ((Button) findViewById(R.id.buttonCheck)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String lightId = ((EditText) findViewById(R.id.editText1))
                        .getText().toString();
                lightContextHttpManager.retrieveLightContextState(lightId);
            }

        });


        */
        Context context = this;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void BackToWelcome(View view) {
        setContentView(R.layout.welcome_page);
    }

    /*to remove*/
    public void switchLight(View view) {
        String lightId = LightState.getLightId();
        lightContextHttpManager.switchLight(lightId);
    }

    /*Update the lights*/
    public void onUpdate(LightContextState lightContextState){

        this.LightState = lightContextState;
        //setContentView(R.layout.lightinfo);

        myAdapter.getMyLight(lightContextState);
        //return LightState;


    }



    //Lauch the GET to retrieveAllRoomsContextState. To remove later.
    public void GetRooms(View view) {

        roomContextHttpManager.retrieveAllRoomsContextState();
    }
    //update room spinner
    public void onRoomUpdate(ArrayList<String> rooms) {
        Spinner spinner = (Spinner) findViewById(R.id.spinnerRoom);
        spinner.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, rooms));

    }

    /*Function to get the lights from a room in the spinner*/
    public void GetLightsOfRoom(View view) {
        //Get light from spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerRoom);
        String roomId = mySpinner.getSelectedItem().toString();

        //Get all lights of a room
        roomContextHttpManager.retrieveAllLightsContextState(roomId);
    }

    //Update list of light on RecyclerView
    public void UpdateRecyclerView(ArrayList<String> lightsList){

        //Useless?
        setContentView(R.layout.lightinfo);

        //Convert ArrayList to String[].
        String[] lightsArr = new String[lightsList.size()];
        lightsArr = lightsList.toArray(lightsArr);



        mRecyclerView = (RecyclerView) findViewById(R.id.my_lights_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(lightsArr, lightContextHttpManager);
        mRecyclerView.setAdapter(mAdapter);


    }


}