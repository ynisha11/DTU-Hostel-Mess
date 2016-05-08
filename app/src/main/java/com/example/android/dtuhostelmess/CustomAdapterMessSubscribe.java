package com.example.android.dtuhostelmess;

import android.content.Context;
import android.os.Debug;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;

import Models.CounterItem;

/**
 * Created by vivek on 09/05/16.
 */
public class CustomAdapterMessSubscribe extends BaseAdapter {

    private Context mContext;
    private ArrayList<CounterItem> mList;

    // View Type for Separators
    private static final int ITEM_VIEW_TYPE_SEPARATOR = 0;
    // View Type for Regular rows
    private static final int ITEM_VIEW_TYPE_REGULAR = 1;
    // Types of Views that need to be handled
    // -- Separators and Regular rows --
    private static final int ITEM_VIEW_TYPE_COUNT = 2;

    public CustomAdapterMessSubscribe(Context context, ArrayList list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    public String getCounterId(int position) {
        return ((CounterItem)mList.get(position)).mCounterId;
    }

    public boolean isSeperator(int position) {
        return ((CounterItem)mList.get(position)).mIsSeparator;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return ITEM_VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        boolean isSection = mList.get(position).mIsSeparator;

        if (isSection) {
            return ITEM_VIEW_TYPE_SEPARATOR;
        }
        else {
            return ITEM_VIEW_TYPE_REGULAR;
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != ITEM_VIEW_TYPE_SEPARATOR;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        CounterItem contact = (CounterItem) mList.get(position);
        int itemViewType = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (itemViewType == ITEM_VIEW_TYPE_SEPARATOR) {
                // If its a section ?
                view = inflater.inflate(R.layout.mess_section_header, null);
            }
            else {
                // Regular row
                view = inflater.inflate(R.layout.counter_item, null);
            }
        }
        else {
            view = convertView;
        }


        if (itemViewType == ITEM_VIEW_TYPE_SEPARATOR) {
            // If separator

            TextView separatorView = (TextView) view.findViewById(R.id.separator);
            System.out.print("Counter type: "+contact.mBoysCounter);
            separatorView.setText(contact.mBoysCounter?"Boys Mess":"Girls Mess");
        }
        else {
            // If regular

            // Set contact name and number
            TextView counterNameView = (TextView) view.findViewById(R.id.counter);
            System.out.print("Counter name: "+contact.mCounterName);
            counterNameView.setText( contact.mCounterName );
        }

        return view;
    }
}
