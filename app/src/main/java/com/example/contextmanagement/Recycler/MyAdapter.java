package com.example.contextmanagement.Recycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.contextmanagement.ContextManagementActivity;
import com.example.contextmanagement.ContextState.LightContextState;
import com.example.contextmanagement.HTTP.LightContextHttpManager;
import com.example.contextmanagement.R;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private String[] mDataset;

    //Context management
    //private LightContextHttpManager lightContextHttpManager = new LightContextHttpManager(this);
    private LightContextState lightState;

    private LightContextHttpManager lightContextHttpManager;
    private ContextManagementActivity contextManagementActivity;

    public MyAdapter(ContextManagementActivity contextManagementActivity ){
        this.contextManagementActivity = contextManagementActivity;
    }



    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView  textToFill;
        public TextView  textLevel;
        public Switch toggle;

        public MyViewHolder(View v) {
            super(v);
            MyViewHolder context = this;
            textToFill = (TextView) v.findViewById(R.id.LightIdToFill);
            textLevel = (TextView) v.findViewById(R.id.textLevel);
            toggle = (Switch) v.findViewById(R.id.switchLight);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(String[] myDataset,LightContextHttpManager lightContextHttpManager) {
        this.mDataset = myDataset;
        this.lightContextHttpManager = lightContextHttpManager;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new card view
        View itemView = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_item, parent, false);

        //Appel du holder
        return new MyViewHolder(itemView);
    }


    public void getMyLight(LightContextState lightContextState){
        this.lightState = lightContextState;
    }


    // Replace the contents of a view (invoked by the layout manager) void onBindViewHolder (VH holder, int position, List<Object> payloads)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position ) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        //initialisation of light TODO : fix This
        //LightContextState lightState = new LightContextState("0", 0,"OFF",-9);

        //Get the light information
        String lightId = mDataset[position];
        lightContextHttpManager.retrieveLightContextState(lightId);

        //Set level
        String level = Integer.toString(lightState.getLevel());
        holder.textLevel.setText(level);

        //set lightId :
        holder.textToFill.setText(mDataset[position]);

        //Set state of switch
        String status = lightState.getStatus();
        if(status.equals("ON")){
            holder.toggle.setChecked(false);
        }else{
            holder.toggle.setChecked(true);
        }
        //Set level

        //On Switch
        holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    lightContextHttpManager.switchLight(mDataset[position]);
                } else {
                    // The toggle is disabled
                    lightContextHttpManager.switchLight(mDataset[position]);
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}