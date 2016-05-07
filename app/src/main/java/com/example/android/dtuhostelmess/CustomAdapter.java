package com.example.android.dtuhostelmess;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import utils.ListModel;

//Adapter class extends with BaseAdapter and implements with OnClickListener
public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

    private static LayoutInflater inflater = null;
    public Resources res;
    ListModel tempValues = null;
    int i = 0;
    //Declare Used Variables
    private Activity activity;
    private ArrayList data;

    //CustomAdapterforConfirm Constructor
    public CustomAdapter(Activity a, ArrayList d, Resources resLocal) {
        //Take passed values
        activity = a;
        data = d;
        res = resLocal;
        //Layout inflator to call external xml layout ()
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //What is the size of Passed Arraylist Size
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

    //Depends upon data size called for each row , Create each ListView row
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            //Inflate tabitem.xml file for each row ( Defined below )
            vi = inflater.inflate(R.layout.tabitem, null);

            // View Holder Object to contain tabitem.xml file elements
            holder = new ViewHolder();
          //  holder.type = (TextView) vi.findViewById(R.id.tvType);
            holder.text = (CheckBox) vi.findViewById(R.id.text);
            holder.text2 = (TextView) vi.findViewById(R.id.text2);
            holder.text3 = (TextView) vi.findViewById(R.id.text3);

            //Set holder with LayoutInflater
            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
            holder.text.setText("No Data");

        } else {
            // Get each Model object from Arraylist
            tempValues = null;
            tempValues = (ListModel) data.get(position);

            if(tempValues.getType().equals("Beverages") || tempValues.getType().equals("Ice Creams") || tempValues.getType().equals("Chips") || tempValues.getType().equals("Biscuits")){
                holder.type.setText(tempValues.getType());
                holder.text.setVisibility(View.INVISIBLE);
                holder.text2.setVisibility(View.INVISIBLE);
            }

            else {
                //Set Model values in Holder elements
                holder.text.setText(tempValues.getItemName());
                holder.text2.setText("Rs " + tempValues.getCost());
                holder.text3.setText(tempValues.getFoodId());
            }

            // Set Item Click Listner for LayoutInflater for each row
            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapterforConfirm", "=====Row button clicked");
    }

    //Create a holder to contain inflated xml file elements
    public static class ViewHolder {
        public CheckBox text;
        public TextView text2;
        public TextView text3;
        private TextView type;
    }

    //Called when Item click in ListView
    private class OnItemClickListener implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            Buy sct = (Buy) activity;
            sct.onItemClick(mPosition);
        }
    }
}