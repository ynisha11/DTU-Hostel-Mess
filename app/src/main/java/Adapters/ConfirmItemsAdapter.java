package Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.dtuhostelmess.R;

import java.util.ArrayList;

import Models.CounterItem;
import Models.FoodItem;
import utils.Constants;

/**
 * Created by vivek on 09/05/16.
 */
public class ConfirmItemsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<FoodItem> mList;

    public ConfirmItemsAdapter(Context context, ArrayList list) {
        mContext = context;
        mList = list;
    }

    public void setItemList(ArrayList list)
    {
        this.mList= list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        final FoodItem contact = mList.get(position);
        int itemViewType = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                view = inflater.inflate(R.layout.confirm_purchase_item, null);
        }
        else {
            view = convertView;
        }

        if(view!=null)
        {
            final Spinner itemQuantity = (Spinner) view.findViewById(R.id.confirm_item_spinner);
            TextView itemName= (TextView)view.findViewById(R.id.confirm_item_name);
            final TextView itemPrice= (TextView)view.findViewById(R.id.confirm_item_price);

            itemName.setText(contact.getmFoodName());
            itemPrice.setText(""+contact.getAmount());

            String[] items = new String[]{"1", "2", "3", "4", "5", "0"};
            ArrayAdapter adapter = ArrayAdapter.createFromResource(itemQuantity.getContext(), R.array.item_quantity_array, android.R.layout.simple_spinner_item);
            // Set the layout to use for each dropdown item
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            itemQuantity.setAdapter(adapter);
            itemQuantity.setSelection(1);

            itemQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    int quantity= Integer.parseInt(itemQuantity.getSelectedItem().toString());
                    contact.setQuantity(quantity);
                    itemPrice.setText("Rs. "+contact.getAmount());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        return view;
    }
}