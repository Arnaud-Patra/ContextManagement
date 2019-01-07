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

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    //Context management
    //private LightContextHttpManager lightContextHttpManager = new LightContextHttpManager(this);
    private ArrayList<LightContextState> mDataset;

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
    //public MyAdapter(String[] myDataset) {
    public MyAdapter(ArrayList<LightContextState> myDataset, LightContextHttpManager lightContextHttpManager) {
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





    // Replace the contents of a view (invoked by the layout manager) void onBindViewHolder (VH holder, int position, List<Object> payloads)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position ) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element



        //Get the light information
        LightContextState Light = mDataset.get(position);

        //Set level
        String level = Integer.toString(Light.getLevel());
        holder.textLevel.setText(level);

        //set lightId :
        holder.textToFill.setText(mDataset.get(position).getLightId());

        //Set state of switch
        String status = mDataset.get(position).getStatus();
        if(status.equals("ON")){
            holder.toggle.setChecked(true);
        }else{
            holder.toggle.setChecked(false);
        }
        //Set level

        //On Switch
        holder.toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    lightContextHttpManager.switchLight(mDataset.get(position).getLightId());
                } else {
                    // The toggle is disabled
                    lightContextHttpManager.switchLight(mDataset.get(position).getLightId());
                }
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}