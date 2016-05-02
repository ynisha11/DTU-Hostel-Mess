package com.example.android.dtuhostelmess;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import utils.MyAsyncTask;
import utils.URLS;

public class MessSubscribe extends AppCompatActivity {

    CheckBox cb1, cb2, cb3, cb4,cb5;
    ProgressBar progressBar;
    String listOfCounters;
    Spinner dropdown1;
    int onStartCount = 0;

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

        cb1=(CheckBox)findViewById(R.id.counter1);
        cb2=(CheckBox)findViewById(R.id.counter2);
        cb3=(CheckBox)findViewById(R.id.counter3);
        cb4=(CheckBox)findViewById(R.id.counter4);
        cb5=(CheckBox)findViewById(R.id.counter5);


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void subscribe(View v) {

        boolean checked;
        String counter;

        checked = cb1.isChecked();
        if (checked) {
            counter = "1";
            addToList(counter);
            //subscribeToCounter(counter);
        }


        checked = cb2.isChecked();
        if (checked) {
            counter = "2";
            addToList(counter);
            // subscribeToCounter(counter);
        }

        checked = cb3.isChecked();
        if (checked) {
            counter = "3";
            addToList(counter);
            // subscribeToCounter(counter);
        }

        checked = cb4.isChecked();
        if (checked) {
            counter = "4";
            addToList(counter);
            //subscribeToCounter(counter);
        }


        checked = cb5.isChecked();
        if (checked) {
            counter = "5";
            addToList(counter);
            //subscribeToCounter(counter);
        }

        subscribeToCounter(listOfCounters);
    }

    public void addToList(String addCounter){

        listOfCounters = listOfCounters+ addCounter +",";

    }

    public void subscribeToCounter(String counterNumbersList){

        int length = counterNumbersList.length();

        counterNumbersList = counterNumbersList.substring(4,length-1);


        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("roll_number", GlobalVariables.currentRollNo);
            jsonObject.put("counter_ids", counterNumbersList);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("DATA NOT INSERTED. Please try again!" + e);
        }


        final Context temp = this;


        new MyAsyncTask(MessSubscribe.this, jsonObject.toString(), URLS.API_messSubscription_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                progressBar.setVisibility(View.GONE);
                try {

                    JSONObject response = new JSONObject(output);

                    response = response.getJSONObject("payload");
                    // String jsonStr = response.toString();

                    try{

                        String resultedMessage = response.getString("message");

                        Toast.makeText(temp, resultedMessage, Toast.LENGTH_LONG).show();
                    }

                    catch (Exception er){
                        Toast.makeText(temp, "Congrats! You have successfully subscribed.", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MessSubscribe.this, MessMenu.class));

                    }

                    //startActivity(new Intent(temp, MainActivity.class));

                } catch (Exception e) {
                    Toast.makeText(temp, "Data not inserted. Error : "+e, Toast.LENGTH_LONG).show();
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
