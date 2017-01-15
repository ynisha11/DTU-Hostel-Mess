package com.example.android.dtuhostelmess;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import utils.AppPreferences;
import utils.Constants;
import utils.GlobalVariables;
import utils.MyAsyncTask;
import utils.OpenHelper;
import utils.URLS;

public class MainActivity extends AppCompatActivity implements Animation.AnimationListener {
    TextView t;
    EditText e1, e2, e3;
    String RNo, Pass;
    Button b1;
    ProgressBar progressBar;
    Switch aSwitch;
    Spinner dropdown1, dropdown2;
    Animation animFadeIn, animFadeOut;
    LinearLayout lSt;
    private AppPreferences prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefManager= AppPreferences.getInstance(this);

        lSt = (LinearLayout) findViewById(R.id.LinearStudent);
        lSt.setVisibility(View.VISIBLE);

        // load animations
        animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_out);

        // set animation listeners
        animFadeIn.setAnimationListener(this);
        animFadeOut.setAnimationListener(this);

        aSwitch = (Switch) findViewById(R.id.btnStart);

        t = (TextView) findViewById(R.id.textView);
        b1 = (Button) findViewById(R.id.btLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        e1 = (EditText) findViewById(R.id.etRollNo);
        e2 = (EditText) findViewById(R.id.etPassword);
        e3 = (EditText) findViewById(R.id.etUsername);

        dropdown1 = (Spinner) findViewById(R.id.spinner1);
        dropdown2 = (Spinner) findViewById(R.id.spinner2);
        String[] rollYearItems=new String[8];
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int index= 0;
        for(int i=currentYear-6;i<=currentYear+1;i++)
        {
            rollYearItems[index++]= Integer.toString(i).replaceFirst("0","K")+"/";
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rollYearItems);
        dropdown1.setAdapter(adapter1);
        String[] items2 = new String[]{"HO/", "HO/G/"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        t.setText("FOR STUDENTS");
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


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        // Take any action after completing the animation

        // if animation is fade out hide them after completing animation
        if (animation == animFadeOut) {

            lSt.setVisibility(View.VISIBLE);

        }

        if (animation == animFadeIn) {
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

            t.setText("FOR ADMINISTRATORS");

            dropdown1.startAnimation(animFadeOut);
            dropdown2.startAnimation(animFadeOut);
            e1.startAnimation(animFadeOut);

            lSt.setVisibility(View.GONE);
            lSt.clearAnimation();

            // start fade in animation
            e3.startAnimation(animFadeIn);
            e3.setVisibility(View.VISIBLE);
        } else {

            // start fade out animation
            e3.startAnimation(animFadeOut);
            e3.setVisibility(View.GONE);

            setContentView(R.layout.activity_main);

            lSt = (LinearLayout) findViewById(R.id.LinearStudent);
            lSt.setVisibility(View.VISIBLE);

            // load animations
            animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.fade_in);
            animFadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
                    R.anim.fade_out);

            // set animation listeners
            animFadeIn.setAnimationListener(this);
            animFadeOut.setAnimationListener(this);

            aSwitch = (Switch) findViewById(R.id.btnStart);

            t = (TextView) findViewById(R.id.textView);
            b1 = (Button) findViewById(R.id.btLogin);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            e1 = (EditText) findViewById(R.id.etRollNo);
            e2 = (EditText) findViewById(R.id.etPassword);
            e3 = (EditText) findViewById(R.id.etUsername);

            dropdown1 = (Spinner) findViewById(R.id.spinner1);
            dropdown2 = (Spinner) findViewById(R.id.spinner2);
            String[] rollYearItems=new String[8];
            Calendar c = Calendar.getInstance();
            int currentYear = c.get(Calendar.YEAR);
            int index= 0;
            for(int i=currentYear-6;i<=currentYear+1;i++)
            {
                rollYearItems[index++]= Integer.toString(i).replaceFirst("0","K")+"/";
            }

            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rollYearItems);
            dropdown1.setAdapter(adapter1);
            String[] items2 = new String[]{"HO/", "HO/G/"};
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
            dropdown2.setAdapter(adapter2);

            t.setText("FOR STUDENTS");
            dropdown1.setVisibility(View.VISIBLE);
            dropdown2.setVisibility(View.VISIBLE);
            e1.setVisibility(View.VISIBLE);
        }
    }

    public void login(View v) {
        if (aSwitch.isChecked()) {
            RNo = e3.getText().toString();
            GlobalVariables.isAdminLogged = 1;
        } else {
            String RnoPart1 = dropdown1.getSelectedItem().toString();
            String RnoPart2 = dropdown2.getSelectedItem().toString();
            RNo = RnoPart1 + RnoPart2 + e1.getText().toString();
            GlobalVariables.isAdminLogged =0;
        }
        Pass = e2.getText().toString();
        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("roll_number", RNo);
            jsonObject.put("password", Pass);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_SHORT).show();
            // System.out.println("Exception in json encoding "+e);
        }

        new MyAsyncTask(MainActivity.this, jsonObject.toString(), URLS.API_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");
                    if (resultedMessage.equals("success")) {
                        JSONObject response1 = response.getJSONObject("payload");
                        response = response1.getJSONObject("user_profile");

                        int isVeg = response.getInt("is_veg");
                        String isAdmin = response.getString("is_admin");
                        String name = response.getString("name");
                        String phoneNumber = response.getString("phone_number");
                        String emailId = response.getString("email_id");
                        String hostel = response.getString("hostel");
                        String billAmount = response.getString("bill_amount");
                        String rollNumber = response.getString("roll_number");
                        String roomNumber = response.getString("room_no");

                        GlobalVariables.currentMessBill = billAmount;
                        GlobalVariables.currentRollNo = rollNumber;
                        GlobalVariables.currentName = name;
                        GlobalVariables.currentHostel = hostel;
                        GlobalVariables.currentEmailID = emailId;
                        GlobalVariables.currentPhoneNo = phoneNumber;
                        GlobalVariables.currentRoomNo = roomNumber;
                        if (isVeg == 1) {
                            GlobalVariables.currentVegOrNon = "Veg";
                        } else {
                            GlobalVariables.currentVegOrNon = "Non-Veg";
                        }

                        JSONArray responseArr = response1.getJSONArray("counters_subscribed");

                        for (int i = 0; i < responseArr.length(); i++) {

                            JSONObject childJSONObject = responseArr.getJSONObject(i);

                            String counterId = childJSONObject.getString("counter_id");
                            String counterName = childJSONObject.getString("name");

                            OpenHelper h1 = new OpenHelper(MainActivity.this);
                            String col1[] = {"Id", "CounterName", "MenuVersion"};
                            SQLiteDatabase db1 = h1.getReadableDatabase();
                            Cursor c1 = db1.query("Counter", col1, null, null, null, null, null);

                            int flag=0;

                            while (c1.moveToNext()) {

                                String id = c1.getString(0);

                                if (id.equals(counterId)) {
                                 //   Toast.makeText(MainActivity.this, "counter EXISTS in local db table", Toast.LENGTH_SHORT).show();
                                    flag=1;
                                    break;
                                }
                            }

                            if(flag==0) {
                                OpenHelper h = new OpenHelper(MainActivity.this);
                                SQLiteDatabase db = h.getWritableDatabase();
                                ContentValues c = new ContentValues();

                                c.put("Id", counterId);
                                c.put("CounterName", counterName);
                                c.put("MenuVersion", "0");
                                long id1 = db.insert("Counter", null, c);

                                if (id1 == -1)
                                    Toast.makeText(MainActivity.this, "Error in inserting counters in local db", Toast.LENGTH_SHORT).show();
                                else {
                                   // Toast.makeText(MainActivity.this, "counter ADDED in local db table", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }

                        prefManager.putString(Constants.RollNumber, rollNumber);
                        prefManager.putString(Constants.CurrentEmailId, emailId);
                        prefManager.putString(Constants.CurrentHostel, hostel);
                        prefManager.putString(Constants.CurrentMessBill, billAmount);
                        prefManager.putString(Constants.CurrentName, name);
                        prefManager.putString(Constants.CurrentPhoneNumber, phoneNumber);
                        prefManager.putString(Constants.CurrentRoomNumber, roomNumber);
                        prefManager.putString(Constants.CurrentVegOrNonVeg, isVeg == 1 ? "Veg" : "Non-Veg");
                        prefManager.putString(Constants.IsAdmin, isAdmin);

                       if(GlobalVariables.isAdminLogged ==  1){
                            startActivity(new Intent(MainActivity.this, CurrentOrders.class));
                        }

                        else
                            startActivity(new Intent(MainActivity.this, MessMenu.class));


                    } else {
                        response = response.getJSONObject("payload");
                        String errorMessage = response.getString("message");
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                }
            }
        }).execute();
    }


    public void register(View v) {
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }

};
