package Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.android.dtuhostelmess.R;

import java.util.ArrayList;

import Models.History;

/**
 * Created by vivek on 01/06/16.
 */

public class CurrentOrdersAdapter extends BaseAdapter {

    /*********** Declare Used Variables *********/
    private Activity activity;
    private ArrayList<History> data;
    private static LayoutInflater inflater=null;
    History tempValues=null;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public CurrentOrdersAdapter(Activity a, ArrayList d) {

        /********** Take passed values **********/
        activity = a;
        data=d;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView orderItemName;
        public TextView orderTimeStamp;
        public TextView orderPrice;
    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.current_order_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.orderItemName = (TextView) vi.findViewById(R.id.order_item_name);
            holder.orderTimeStamp = (TextView) vi.findViewById(R.id.order_item_timestamp);
            holder.orderPrice = (TextView) vi.findViewById(R.id.order_item_price);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.orderItemName.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = (History) data.get(position);

            /************  Set Model values in Holder elements ***********/

            holder.orderItemName.setText( tempValues.getFoodItem().toString());
            holder.orderTimeStamp.setText( tempValues.getTimestamp().toString());
            holder.orderPrice.setText( tempValues.getAmount().toString());

        }
        return vi;
    }
}