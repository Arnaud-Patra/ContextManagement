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
import java.util.List;

public class ContextManagementActivity extends Activity {

    //private String room = "Room1";//"";
    //public RoomContextHttpManager roomContextHttpManager;
    RoomContextHttpManager roomContextHttpManager = new RoomContextHttpManager(this);
    LightContextHttpManager lightContextHttpManager = new LightContextHttpManager(this);
    MyAdapter myAdapter = new MyAdapter(this);
    private RoomContextState RoomState;
    private LightContextState LightState;

    public ArrayList<LightContextState> lightList;
    public ArrayList<RoomContextState> roomList;
    public int numberOfLights;

    //Recycler
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Spinner mySpinner;
    private RoomContextState myRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        //Get the rooms when first opening the app.
        GetRooms(null);

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

    /*Update the lights with a new light*/
    public void onUpdate(LightContextState lightContextState){

        this.LightState = lightContextState;
        //Update lightList with a new light
        this.lightList.add(lightContextState);

        //Automatically lunch the recyclerView if we have all the lights.
        if (lightList.size() == numberOfLights){
            UpdateRecyclerView();
        }

    }


    //Launch the GET to retrieveAllRoomsContextState. To remove later.
    public void GetRooms(View view) {
        roomContextHttpManager.retrieveAllRoomsContextState();
    }


    //update room spinner
    public void onRoomUpdate(ArrayList<RoomContextState> rooms) {
        setContentView(R.layout.welcome_page);

        this.roomList = rooms; //Set global variable

        ArrayList<String> roomNames = new ArrayList<String>();
        for (RoomContextState tempRoom : roomList){
            roomNames.add(tempRoom.getName());
        }

        this.mySpinner = (Spinner) findViewById(R.id.spinnerRoom);
        this.mySpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomNames));

    }


    /*Function to get the lights from a room in the spinner*/
    public void GetLightsOfRoom(View view) {

        //Get roomId from spinner
        Spinner mySpinner = (Spinner) findViewById(R.id.spinnerRoom);
        String roomName = mySpinner.getSelectedItem().toString();
        String roomId = "";
        for (RoomContextState tempRoom : roomList){
            if(roomName.equals(tempRoom.getName())){
                roomId = tempRoom.getRoomId();
                this.myRoom = tempRoom;
            }
        }

        //Get all lights of a room
        roomContextHttpManager.retrieveAllLightsContextState(roomId);

        //initialisation of lightList. Really useful?
        this.lightList = new ArrayList<LightContextState>();
    }

    public void updateLightList(ArrayList<String> lightsList){
        //update the number of lights we have on a room.
        this.numberOfLights = lightsList.size();

        //Convert ArrayList to String[].
        String[] lightsArr = new String[lightsList.size()];
        lightsArr = lightsList.toArray(lightsArr);


        //Update lightList
        for(int i = 0 ; i<lightsArr.length ; i++) {
            lightContextHttpManager.retrieveLightContextState(lightsArr[i]);
        }

    }


    //Update list of light on RecyclerView
    public void UpdateRecyclerView(){
        //Getting the room name
        String roomName = this.mySpinner.getSelectedItem().toString();

        String otherName = this.myRoom.getName();


        //Set the header for the lightInfo view.
        setContentView(R.layout.lightinfo);
        TextView headerRoomName = (TextView) findViewById(R.id.LightIdToFill);
        headerRoomName.setText(this.myRoom.getName());

        TextView headerRoomFloor = (TextView) findViewById(R.id.textViewFloor);
        headerRoomFloor.setText(Integer.toString(this.myRoom.getFloor()));

        TextView headerBuildinId = (TextView) findViewById(R.id.textViewbuildinId);
        headerBuildinId.setText(this.myRoom.getName());

        mRecyclerView = (RecyclerView) findViewById(R.id.my_lights_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(lightList, lightContextHttpManager);
        mRecyclerView.setAdapter(mAdapter);

    }


}