package com.example.android.dtuhostelmess;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import utils.AppPreferences;
import utils.Constants;
import utils.GlobalVariables;
import utils.MyAsyncTask;
import utils.URLS;

public class RegistrationDetails extends AppCompatActivity {

    EditText e1, e2, e3;
    LinearLayout LinearRoll;
    RadioButton rveg, rnon;
    int basicBill = 0, onStartCount = 0;
    ProgressBar progressBar;
    int isVeg = 0;
    Spinner dropdown1, dropdown2, dropdownHostel, dropdownMess;
    private AppPreferences prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_details);
        prefManager= AppPreferences.getInstance(this);


        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

        LinearRoll = (LinearLayout) findViewById(R.id.LRoll);
        e1 = (EditText) findViewById(R.id.etRollNo);
        e2 = (EditText) findViewById(R.id.etPhoneNo);
        e3 = (EditText) findViewById(R.id.etRoomNo);

        rveg = (RadioButton) findViewById(R.id.veg);
        rnon = (RadioButton) findViewById(R.id.non);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        dropdown1 = (Spinner) findViewById(R.id.spinner1);
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

        dropdown2 = (Spinner) findViewById(R.id.spinner2);
        String[] items2 = new String[]{"HO/", "HO/G/"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        dropdownHostel = (Spinner) findViewById(R.id.spinnerHostel);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Constants.HostelList);
        dropdownHostel.setAdapter(adapter);

        dropdownMess = (Spinner) findViewById(R.id.spinnerMess);
        ArrayAdapter<String> adapterM = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Constants.MessList);
        dropdownMess.setAdapter(adapterM);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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


    public void submit(View v) {

        if (rveg.isChecked()) {
            basicBill = 1875;
        } else if (rnon.isChecked())
            basicBill = 1925;

        String phoneNumber = e2.getText().toString();
        if (phoneNumber.length() != 10) {
            Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_LONG).show();
            e2.setText(null);
        } else {

            final String rollNo, hostel;

            String RnoPart1 = dropdown1.getSelectedItem().toString();
            String RnoPart2 = dropdown2.getSelectedItem().toString();
            rollNo = RnoPart1 + RnoPart2 + e1.getText().toString();
            hostel = dropdownHostel.getSelectedItem().toString();

            final String phoneNo = e2.getText().toString();
            final String room = e3.getText().toString();

            if (rveg.isChecked()) {
                isVeg = 1;
            } else isVeg = 0;

            progressBar.setVisibility(View.VISIBLE);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", GlobalVariables.currentName);
                jsonObject.put("roll_number", rollNo);
                jsonObject.put("hostel", hostel);
                jsonObject.put("phone_number", phoneNo);
                jsonObject.put("room_no", room);
                jsonObject.put("email_id", GlobalVariables.currentEmailID);
                jsonObject.put("is_veg", isVeg);
                jsonObject.put("password", GlobalVariables.currentPassword);

                GlobalVariables.currentRollNo = rollNo;
                GlobalVariables.currentHostel = hostel;
                GlobalVariables.currentRoomNo = room;
                GlobalVariables.currentPhoneNo = phoneNo;
                if (isVeg == 1) {
                    GlobalVariables.currentVegOrNon = "Veg";
                    GlobalVariables.currentMessBill = "1875";
                } else {
                    GlobalVariables.currentVegOrNon = "Non-Veg";
                    GlobalVariables.currentMessBill = "1925";
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                System.out.println("DATA NOT INSERTED. Please try again!" + e);
            }

            new MyAsyncTask(RegistrationDetails.this, jsonObject.toString(), URLS.API_register_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");

                        if (resultedMessage.equals("success")) {
                            prefManager.putString(Constants.RollNumber, rollNo);
                            prefManager.putString(Constants.CurrentEmailId, GlobalVariables.currentEmailID);
                            prefManager.putString(Constants.CurrentHostel, hostel);
                            prefManager.putString(Constants.CurrentMessBill, isVeg==1?"1875":"1925");
                            prefManager.putString(Constants.CurrentName, GlobalVariables.currentName);
                            prefManager.putString(Constants.CurrentPhoneNumber, phoneNo);
                            prefManager.putString(Constants.CurrentRoomNumber, room);
                            prefManager.putString(Constants.CurrentVegOrNonVeg, isVeg==1?"Veg":"Non-Veg");
                            prefManager.putString(Constants.IsAdmin, ""+0);
                            Toast.makeText(RegistrationDetails.this, "Congrats! You have successfully registered.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationDetails.this, MessSubscribe.class));
                        } else {
                            JSONObject payload = response.getJSONObject("payload");
                            String errorMessage = payload.getString("message");
                            Toast.makeText(RegistrationDetails.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(RegistrationDetails.this, "Please try again! Exception ", Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }

        GlobalVariables.currentPassword = "";
    }

    public void login(View v) {
        startActivity(new Intent(this, MainActivity.class));
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
