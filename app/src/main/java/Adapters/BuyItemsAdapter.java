package Adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.dtuhostelmess.R;

import java.util.ArrayList;

import Models.CounterItem;
import Models.FoodItem;
import utils.Constants;

/**
 * Created by vivek on 09/05/16.
 */
public class BuyItemsAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<FoodItem> mList;

    // View Type for Separators
    public static final int ITEM_VIEW_TYPE_SEPARATOR = 0;
    // View Type for Regular rows
    public static final int ITEM_VIEW_TYPE_REGULAR = 1;

    public static final int ITEM_VIEW_TYPE_OTHER = 2;
    // Types of Views that need to be handled
    // -- Separators and Regular rows --
    private static final int ITEM_VIEW_TYPE_COUNT = 3;

    public BuyItemsAdapter(Context context, ArrayList list) {
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
            if(mList.get(position).mFoodId != Constants.OtherFoodId)
            {
                return ITEM_VIEW_TYPE_REGULAR;
            }
            else
            {
                return ITEM_VIEW_TYPE_OTHER;
            }
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) != ITEM_VIEW_TYPE_SEPARATOR;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;

        final FoodItem contact = mList.get(position);
        int itemViewType = getItemViewType(position);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (itemViewType == ITEM_VIEW_TYPE_SEPARATOR) {
                // If its a section ?
                view = inflater.inflate(R.layout.buy_items_header, null);
            }
            else if(itemViewType== ITEM_VIEW_TYPE_REGULAR) {
                view = inflater.inflate(R.layout.tabitem, null);
            }
            else if(itemViewType== ITEM_VIEW_TYPE_OTHER)
            {
                view = inflater.inflate(R.layout.buy_other_item, null);
            }
            else
            {
                view= null;
            }
        }
        else {
            view = convertView;
        }

        if(view!=null)
        {
            if (itemViewType == ITEM_VIEW_TYPE_SEPARATOR) {
                TextView separatorView = (TextView) view.findViewById(R.id.separator);
                separatorView.setText(contact.mType);
            }
            else if(itemViewType== ITEM_VIEW_TYPE_REGULAR) {
                CheckBox foodNameView = (CheckBox) view.findViewById(R.id.text);

                TextView foodCostView = (TextView) view.findViewById(R.id.text2);
                foodNameView.setText( contact.mFoodName );
                foodCostView.setText("Rs. "+(int)contact.mCost);

                foodNameView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        contact.setmIsSelected(b);
                    }
                });

                foodNameView.setChecked(contact.ismIsSelected());
            }
            else if(itemViewType== ITEM_VIEW_TYPE_OTHER)
            {
                CheckBox foodNameView = (CheckBox) view.findViewById(R.id.text);
                EditText otherFoodName= (EditText)view.findViewById(R.id.othersName);
                EditText otherFoodPrice = (EditText)view.findViewById(R.id.othersPrice);

                foodNameView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        contact.setmIsSelected(b);
                    }
                });

                foodNameView.setChecked(contact.ismIsSelected());

                otherFoodName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        contact.setName(editable.toString());
                    }
                });

                otherFoodPrice.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        if(editable.toString()!=null && !editable.toString().isEmpty())
                        {
                            contact.setCost(Double.parseDouble(editable.toString()));
                        }
                    }
                });
            }

        }
        return view;
    }
}