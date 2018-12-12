package com.example.contextmanagement.HTTP;

import android.util.Log;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.contextmanagement.ContextManagementActivity;
import com.example.contextmanagement.ContextState.RoomContextState;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RoomContextHttpManager {

    private ContextManagementActivity contextManagementActivity;
    public RoomContextHttpManager (ContextManagementActivity contextManagementActivity){
        this.contextManagementActivity = contextManagementActivity;
    }


    public void retrieveRoomContextState(String roomId){

        String url =  "https://faircorp-arnaud-patra.cleverapps.io/api/lights"+ "/" + roomId + "/";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextManagementActivity);

        //get room sensed context
        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id").toString();
                            int lightLevel = Integer.parseInt(response.getJSONObject("light").get("level").toString());
                            String lightStatus = response.getJSONObject("light").get("status").toString();
                            int roomId = Integer.parseInt(response.getJSONObject("light").get("roomId").toString());;

                            contextManagementActivity.onRoomUpdate(new RoomContextState(id, lightLevel,lightStatus));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Some error to access URL : Room may not exists...
                    }
                });

        queue.add(contextRequest);

    }


    public void switchLight(RoomContextState state, String room){

        RequestQueue queue = Volley.newRequestQueue(contextManagementActivity);


        String url = "https://faircorp-arnaud-patra.cleverapps.io/api/lights/";
        StringRequest putRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", "19");

                return params;
            }

        };

        queue.add(putRequest);

    }

}
