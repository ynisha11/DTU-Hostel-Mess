package com.example.android.dtuhostelmess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.BuyItemsAdapter;
import Adapters.ConfirmItemsAdapter;
import Models.FoodItem;
import utils.GlobalVariables;
import utils.MyAsyncTask;
import utils.URLS;

public class ConfirmPurchase extends Activity {

    Bundle b;
    Button confirm;
    int numberOfItems = 0, onStartCount = 0;
    ProgressBar progressBar;
    private ArrayList<FoodItem> foodItemsList;
    public ListView list;
    private ConfirmItemsAdapter confirmItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_purchase);

        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        foodItemsList= new ArrayList<FoodItem>(GlobalVariables.selectedFoodItems);
        list = (ListView) findViewById(R.id.confirmItemsList);

        confirmItemsAdapter = new ConfirmItemsAdapter(this, foodItemsList);
        list.setAdapter(confirmItemsAdapter);


        //mess = (TextView) findViewById(R.id.tvmess);


        confirm = (Button) findViewById(R.id.btConfirmOrder);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_purchase, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    public void confirmOrder(View v) {
        String counter = null;
        progressBar.setVisibility(View.VISIBLE);

//        if (messSelected.equals("CVR Mess")) {
//            counter = "1";
//        } else if (messSelected.equals("HJB Mess")) {
//            counter = "2";
//        } else if (messSelected.equals("HJB Mess")) {
//            counter = "2";
//        } else if (messSelected.equals("VVS Mess")) {
//            counter = "3";
//        } else if (messSelected.equals("Aryabhatta Mess")) {
//            counter = "4";
//        } else if (messSelected.equals("SNH Mess")) {
//            counter = "5";
//        }

        int sizeOfList = confirmItemsAdapter.getCount();

        for (int x = 0; x < sizeOfList; x++) {
            final FoodItem item= (FoodItem) confirmItemsAdapter.getItem(x);
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("roll_number", GlobalVariables.currentRollNo);
                jsonObject.put("item_id", item.getmFoodId());
                jsonObject.put("quantity", item.getmQuantity());
                jsonObject.put("cost", item.getmCost());
                jsonObject.put("counter_id", "1");
            } catch (Exception e) {
                Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_SHORT).show();
                // System.out.println("Exception in json encoding "+e);
            }
            new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.Purchase_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");

                        if (resultedMessage.equals("success")) {
                            Toast.makeText(ConfirmPurchase.this, item.getmFoodName() + " added to Bill", Toast.LENGTH_SHORT).show();
                        } else {
                            //response = response.getJSONObject("payload");
                            //String errorMessage = response.getString("message");
                            Toast.makeText(ConfirmPurchase.this, item.getmFoodName() + " not bought", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }

        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("roll_number", GlobalVariables.currentRollNo);
        } catch (Exception e) {
            Toast.makeText(ConfirmPurchase.this, "" + e, Toast.LENGTH_LONG).show();
            // System.out.println("Exception in json encoding "+e);
        }
        new MyAsyncTask(ConfirmPurchase.this, jsonObject.toString(), URLS.CurrentBill_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");
                    if (resultedMessage.equals("success")) {
                        response = response.getJSONObject("payload");
                        String latestBill = response.getString("bill_amount");
                        GlobalVariables.currentMessBill = latestBill;
                        startActivity(new Intent(ConfirmPurchase.this, MessMenu.class));
                    } else {
                        response = response.getJSONObject("payload");
                        String errorMessage = response.getString("message");
                        Toast.makeText(ConfirmPurchase.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                }
            }
        }).execute();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);

        } else if (onStartCount == 1) {
            onStartCount++;
        }
    }
}
