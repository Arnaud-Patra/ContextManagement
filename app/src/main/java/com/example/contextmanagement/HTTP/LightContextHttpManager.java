package com.example.contextmanagement.HTTP;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.contextmanagement.ContextManagementActivity;
import com.example.contextmanagement.ContextState.LightContextState;
import com.example.contextmanagement.ContextState.RoomContextState;
import com.example.contextmanagement.Recycler.MyAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LightContextHttpManager {

    private ContextManagementActivity contextManagementActivity;

    public LightContextHttpManager(ContextManagementActivity contextManagementActivity){
        this.contextManagementActivity = contextManagementActivity;
    }


    public void retrieveLightContextState(String lightId){


        String url =  "https://faircorp-arnaud-patra.cleverapps.io/api/lights"+ "/" + lightId + "/";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(contextManagementActivity);

        //change Light status
        JsonObjectRequest contextRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {



                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String id = response.getString("id").toString();
                            int lightLevel = response.getInt("level");
                            String lightStatus = response.getString("status").toString();
                            int roomId = response.getInt("roomId");

                            contextManagementActivity.onUpdate(new LightContextState(id, lightLevel,lightStatus,roomId));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Creation of popup message
                        Context context = contextManagementActivity;
                        CharSequence text = "Error : light not retrieved";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        // Some error to access URL : Room may not exists...
                    }
                });

        queue.add(contextRequest);

    }



    public void switchLight(final String lightId){

        String url = "https://faircorp-arnaud-patra.cleverapps.io/api/lights/"+lightId+"/switch";

        RequestQueue queue = Volley.newRequestQueue(contextManagementActivity);

        StringRequest putRequest = new StringRequest
                (Request.Method.PUT, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        //Update the info
                        retrieveLightContextState(lightId);
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        CharSequence text = "Error : light could not be switched";
                        int duration = Toast.LENGTH_SHORT;
                        Context context = contextManagementActivity;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();

                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        );

        queue.add(putRequest);

    }

}
