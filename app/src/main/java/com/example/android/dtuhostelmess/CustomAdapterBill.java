package com.example.android.dtuhostelmess;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import utils.ListModel;

public class CustomAdapterBill extends BaseAdapter implements View.OnClickListener {

    private static LayoutInflater inflater = null;
    public Resources res;
    ListModel tempValues = null;
    int i = 0;
    private Activity activity;
    private ArrayList data;

    // CustomAdapterforConfirm Constructor
    public CustomAdapterBill(Activity a, ArrayList d, Resources resLocal) {
        //Take passed values
        activity = a;
        data = d;
        res = resLocal;
        //Layout inflator to call external xml layout ()
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // What is the size of Passed Arraylist Size
    public int getCount() {
        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    // Depends upon data size called for each row , Create each ListView row
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            //Inflate tabitem.xml file for each row ( Defined below )
            vi = inflater.inflate(R.layout.tabitembill, null);

            //View Holder Object to contain tabitem.xml file elements
            holder = new ViewHolder();
            holder.date = (TextView) vi.findViewById(R.id.tv1);
            holder.mess = (TextView) vi.findViewById(R.id.tv2);
            holder.food = (TextView) vi.findViewById(R.id.tv3);
            holder.total = (TextView) vi.findViewById(R.id.tv4);

            // Set holder with LayoutInflater
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
//            holder.date.setText("Please");
//            holder.mess.setText("Select");
//            holder.food.setText("and Click");
//            holder.total.setText("Go!");
        } else {
            //Get each Model object from Arraylist
            tempValues = null;
            tempValues = (ListModel) data.get(position);

            //Set Model values in Holder elements
            holder.date.setText(tempValues.getDateTime());
            holder.mess.setText(tempValues.getMess());
            holder.food.setText(tempValues.getFood());
            if(tempValues.getMessOffDeduct()==""){
                holder.total.setText("  Rs " + tempValues.getTotal());
            }
           else holder.total.setText("- Rs " + tempValues.getMessOffDeduct());

            //Set Item Click Listner for LayoutInflater for each row
            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapterforConfirm", "=====Row button clicked");
    }

    // Create a holder to contain inflated xml file elements
    public static class ViewHolder {
        public TextView date;
        public TextView mess;
        public TextView food;
        public TextView total;
    }

    // Called when Item click in ListView
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            MonthlyBill sct = (MonthlyBill) activity;
            sct.onItemClick(mPosition);
        }
    }

}
