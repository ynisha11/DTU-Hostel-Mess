package com.example.android.dtuhostelmess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Models.CounterItem;
import Storage.CountersTable;
import utils.GlobalVariables;
import utils.MyAsyncTask;
import utils.URLS;

public class MessSubscribe extends AppCompatActivity {

    ProgressBar progressBar;
    String listOfCounters;
    int onStartCount = 0;
    CustomAdapterMessSubscribe adapter;
    ListView listView;
    private ArrayList<CounterItem> selectedCounters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_subscribe);

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

        ArrayList<CounterItem> counters = new ArrayList<CounterItem>();

        counters.add( new CounterItem("0","Mess", false, true));
        counters.add( new CounterItem("1","SNH Mess", false, false));
        counters.add( new CounterItem("0","Mess", true, true));
        counters.add( new CounterItem("2","Aryabhatta Mess", true, false));
        counters.add( new CounterItem("3","CVR Mess", true, false));
        counters.add( new CounterItem("4","HJB Mess", true, false));
        counters.add( new CounterItem("5","VVS Mess", true, false));

        adapter = new CustomAdapterMessSubscribe(this, counters);

        listView = (ListView) findViewById(R.id.mess_counters_list);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mess_subscribe, menu);
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


    public void subscribe(View v) {

        int sizeOfList = adapter.getCount();
        selectedCounters= new ArrayList<CounterItem>();

        for (int x = 0; x < sizeOfList; x++) {

            View view = listView.getChildAt(x);
            if(view!=null)
            {
                CheckBox cb = (CheckBox) view.findViewById(R.id.counter);
                if(cb!=null)
                {
                    if (cb.isChecked()) {
                        if(!adapter.isSeperator(x))
                        {
                            addToList(adapter.getCounterId(x));
                            selectedCounters.add((CounterItem) adapter.getItem(x));
                        }
                    }
                }
            }
        }

        Toast.makeText(MessSubscribe.this, listOfCounters, Toast.LENGTH_LONG).show();
        subscribeToCounter(listOfCounters);
    }

    public void addToList(String addCounter) {
        listOfCounters = listOfCounters + addCounter + ",";
    }

    public void subscribeToCounter(String counterNumbersList) {

        int length = counterNumbersList.length();
        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("roll_number", GlobalVariables.currentRollNo);
            jsonObject.put("counter_ids", counterNumbersList);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("DATA NOT INSERTED. Please try again!" + e);
        }

        new MyAsyncTask(MessSubscribe.this, jsonObject.toString(), URLS.API_messSubscription_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                progressBar.setVisibility(View.GONE);
                try {

                    JSONObject response = new JSONObject(output);
                    response = response.getJSONObject("payload");
                    try {
                        for(CounterItem item:selectedCounters)
                        {
                            long insertId= CountersTable.InsertCounter(getBaseContext(), item);
                        }
                       // Toast.makeText(MessSubscribe.this, "Mess subscribed successfully.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MessSubscribe.this, MessMenu.class));
                    } catch (Exception er) {
                        Toast.makeText(MessSubscribe.this, "Some error occured.", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(MessSubscribe.this, MessMenu.class));
                    }

                } catch (Exception e) {
                    Toast.makeText(MessSubscribe.this, "Data not inserted. Error : " + e, Toast.LENGTH_LONG).show();
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
