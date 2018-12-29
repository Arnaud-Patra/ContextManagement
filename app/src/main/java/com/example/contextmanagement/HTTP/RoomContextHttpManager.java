package com.example.contextmanagement.HTTP;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.contextmanagement.ContextManagementActivity;
import com.example.contextmanagement.ContextState.RoomContextState;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RoomContextHttpManager {

    private ContextManagementActivity contextManagementActivity;
    public RoomContextHttpManager (ContextManagementActivity contextManagementActivity){
        this.contextManagementActivity = contextManagementActivity;
    }

    //Get all the rooms for the spinner view
    public void retrieveAllRoomsContextState(){

        String url =  "https://faircorp-arnaud-patra.cleverapps.io/api/rooms/";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextManagementActivity);

        //get room sensed context
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            ArrayList<String> rooms = new ArrayList<String>();
                            //JSONArray myJsonRooms= new JSONArray(response);
                            for(int i =0;i<response.length();i++){
                                JSONObject room = response.getJSONObject(i);
                                rooms.add(room.getString("id"));
                            }

                            contextManagementActivity.onRoomUpdate(rooms);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Creation of popup message
                        Context context = contextManagementActivity;
                        CharSequence text = "Error : rooms not imported";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

        queue.add(jsonArrayRequest);

    }



    //HTTP request to get the lights of a room, or all lights idk...
    public void retrieveAllLightsContextState(final String roomId) {

        String url =  "https://faircorp-arnaud-patra.cleverapps.io/api/lights/";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextManagementActivity);

        //get room sensed context
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            ArrayList<String> lights = new ArrayList<String>();
                            for(int i =0;i<response.length();i++){
                                JSONObject light = response.getJSONObject(i);

                                if(light.getString("roomId").equals(roomId)){

                                    lights.add(light.getString("id"));
                                }
                            }

                            contextManagementActivity.UpdateRecyclerView(lights);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Creation of popup message
                        Context context = contextManagementActivity;
                        CharSequence text = "Error : lights not imported";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                });

        queue.add(jsonArrayRequest);

    }

}
