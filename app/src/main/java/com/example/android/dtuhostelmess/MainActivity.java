package com.example.android.dtuhostelmess;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import utils.MyAsyncTask;
import utils.URLS;
/**
 * Created by nisha on 17/4/16.
 */
public class MainActivity extends AppCompatActivity implements Animation.AnimationListener{
    EditText e1, e2, e3;
    String RNo, Pass;
    Button b1;
    TextView t1;
    ProgressBar progressBar;
    Switch aSwitch;
    Spinner dropdown1, dropdown2;

    Animation animFadeIn, animFadeOut;
    LinearLayout lSt;
    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lSt = (LinearLayout)findViewById(R.id.LinearStudent);
        lSt.setVisibility(View.VISIBLE);

        // load animations
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);

        // set animation listeners
        animFadeIn.setAnimationListener(this);
        animFadeOut.setAnimationListener(this);

        aSwitch = (Switch)findViewById(R.id.btnStart);

        b1 = (Button) findViewById(R.id.btLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        e1 = (EditText) findViewById(R.id.etRollNo);
        e2 = (EditText) findViewById(R.id.etPassword);
        e3 = (EditText) findViewById(R.id.etUsername);

        dropdown1 = (Spinner) findViewById(R.id.spinner1);
        dropdown2 = (Spinner) findViewById(R.id.spinner2);
        String[] items1 = new String[]{"2K10/", "2K11/", "2K12/", "2K13/", "2K14/", "2K15/", "2K16/"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);
        String[] items2 = new String[]{"HO/", "HO/G/"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        dropdown1.setVisibility(View.VISIBLE);
        dropdown2.setVisibility(View.VISIBLE);
        e1.setVisibility(View.VISIBLE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // if animation is fade out hide them after completing animation
        if (animation == animFadeOut) {

            lSt.setVisibility(View.VISIBLE);

        }

        if(animation == animFadeIn){
            // do something after fade in completed

            // set visibility of fade in element

        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

    public void toggle(View v) {
        if (aSwitch.isChecked()) {

            dropdown1.startAnimation(animFadeOut);
            dropdown2.startAnimation(animFadeOut);
            e1.startAnimation(animFadeOut);

            lSt.setVisibility(View.GONE);
            lSt.clearAnimation();

            // start fade in animation
            e3.startAnimation(animFadeIn);
            e3.setVisibility(View.VISIBLE);


        }
        else {

            // start fade out animation
            e3.startAnimation(animFadeOut);
            e3.setVisibility(View.GONE);

            setContentView(R.layout.activity_main);

            lSt = (LinearLayout)findViewById(R.id.LinearStudent);
            lSt.setVisibility(View.VISIBLE);

            // load animations
            animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.fade_in);
            animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.fade_out);

            // set animation listeners
            animFadeIn.setAnimationListener(this);
            animFadeOut.setAnimationListener(this);

            aSwitch = (Switch)findViewById(R.id.btnStart);

            b1 = (Button) findViewById(R.id.btLogin);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            e1 = (EditText) findViewById(R.id.etRollNo);
            e2 = (EditText) findViewById(R.id.etPassword);
            e3 = (EditText) findViewById(R.id.etUsername);


            dropdown1 = (Spinner) findViewById(R.id.spinner1);
            dropdown2 = (Spinner) findViewById(R.id.spinner2);
            String[] items1 = new String[]{"2K10/", "2K11/", "2K12/", "2K13/", "2K14/", "2K15/", "2K16/"};
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
            dropdown1.setAdapter(adapter1);
            String[] items2 = new String[]{"HO/", "HO/G/"};
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
            dropdown2.setAdapter(adapter2);

            dropdown1.setVisibility(View.VISIBLE);
            dropdown2.setVisibility(View.VISIBLE);
            e1.setVisibility(View.VISIBLE);


        }
    }


    public void login(View v) {
        if(aSwitch.isChecked()){
            RNo = e3.getText().toString();
        }
        else{
            String RnoPart1 = dropdown1.getSelectedItem().toString();
            String RnoPart2 = dropdown2.getSelectedItem().toString();
            RNo = RnoPart1 +RnoPart2 + e1.getText().toString();
        }
        Pass = e2.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("roll_number", RNo);
            jsonObject.put("password", Pass);
        }catch(Exception e)
        {
            Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
            // System.out.println("Exception in json encoding "+e);
        }
        final Context temp = this;
        new MyAsyncTask(MainActivity.this,jsonObject.toString(), URLS.API_URL,new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");
                    if(resultedMessage.equals("success")) {
                        response = response.getJSONObject("payload");
                        response = response.getJSONObject("user_profile");
                        int isVeg = response.getInt("is_veg");
                        String name = response.getString("name");
                        String phoneNumber = response.getString("phone_number");
                        String emailId = response.getString("email_id");
                        String hostel = response.getString("hostel");
                        String billAmount =response.getString("bill_amount");
                        String rollNumber = response.getString("roll_number");
                        String roomNumber = response.getString("room_no");
//                            Intent i = new Intent(temp, Display.class);
//                            i.putExtra("K1", name);
//                            i.putExtra("K2", hostel);
//                            i.putExtra("K3", emailId);
//                            i.putExtra("K4", rollNumber);
//                            i.putExtra("K5", phoneNumber);
//                            i.putExtra("K6", billAmount);
                        GlobalVariables.currentMessBill = billAmount;
                        GlobalVariables.currentRollNo = rollNumber;
                        GlobalVariables.currentName = name;
                        GlobalVariables.currentHostel = hostel;
                        GlobalVariables.currentEmailID = emailId;
                        GlobalVariables.currentPhoneNo = phoneNumber;
                        GlobalVariables.currentRoomNo=roomNumber;
                        if(isVeg == 1) {
                            GlobalVariables.currentVegOrNon = "Veg";
                        }
                        else {
                            GlobalVariables.currentVegOrNon ="Non-Veg";
                        }
                        // startActivity(i);
                        //  Toast.makeText(temp, "ok 1", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(MainActivity.this, MessMenu.class));
                    }
                    else{
                        response = response.getJSONObject("payload");
                        String errorMessage = response.getString("message");
                        Toast.makeText(temp, errorMessage, Toast.LENGTH_LONG).show();
                    }
                }catch(Exception e)
                {
                    // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                }
            }
        }).execute();
    }
    public void register(View v)
    {
        Intent i=new Intent(this,Register.class);
        startActivity(i);
    }

};
