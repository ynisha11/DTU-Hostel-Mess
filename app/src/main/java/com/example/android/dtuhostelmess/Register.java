package com.example.android.dtuhostelmess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import utils.GlobalVariables;
import utils.MyAsyncTask;
import utils.URLS;

public class Register extends AppCompatActivity {

    EditText e1, e2, e3, e4, e5, e6, eRoom;
    LinearLayout LinearRoll;
    RadioButton rveg, rnon;
    CheckBox cbAdmin;
    int basicBill = 0, onStartCount = 0;
    ProgressBar progressBar;
    Switch aSwitch;
    Spinner dropdown1, dropdown2, dropdownHostel, dropdownMess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

        LinearRoll = (LinearLayout) findViewById(R.id.LRoll);
        e1 = (EditText) findViewById(R.id.etRollNo);
        e2 = (EditText) findViewById(R.id.etName);
        e3 = (EditText) findViewById(R.id.etUsername);
        e4 = (EditText) findViewById(R.id.etEmail);
        e5 = (EditText) findViewById(R.id.etPassword);
        e6 = (EditText) findViewById(R.id.etPhoneNo);
        eRoom = (EditText) findViewById(R.id.etRoomNo);
        rveg = (RadioButton) findViewById(R.id.veg);
        rnon = (RadioButton) findViewById(R.id.non);
        cbAdmin = (CheckBox) findViewById(R.id.cb1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        aSwitch = (Switch) findViewById(R.id.toggleButton);

        dropdown1 = (Spinner) findViewById(R.id.spinner1);
        String[] items1 = new String[]{"2K10/", "2K11/", "2K12/", "2K13/", "2K14/", "2K15/", "2K16/"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);

        dropdown2 = (Spinner) findViewById(R.id.spinner2);
        String[] items2 = new String[]{"HO/", "HO/G/"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        dropdown2.setAdapter(adapter2);

        dropdownHostel = (Spinner) findViewById(R.id.spinnerHostel);

        String[] items = new String[]{"KCH", "SNH", "Type II", "Type III", "Aryabhatta", "BCH", "CVR", "HJB", "JCB", "Ramanujan", "Type – II B-5", "VMH", "VVS"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdownHostel.setAdapter(adapter);

        dropdownMess = (Spinner) findViewById(R.id.spinnerMess);
        String[] itemsM = new String[]{"Aryabhatta Mess", "CVR Mess", "HJB Mess", "VVS Mess", "SNH Mess"};
        ArrayAdapter<String> adapterM = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, itemsM);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void toggle(View v) {
        if (aSwitch.isChecked()) {
            LinearRoll.setVisibility(View.GONE);
            dropdownHostel.setVisibility(View.GONE);
            eRoom.setVisibility(View.GONE);

            rveg.setVisibility(View.INVISIBLE);
            rnon.setVisibility(View.INVISIBLE);
            cbAdmin.setVisibility(View.INVISIBLE);

            e3.setVisibility(View.VISIBLE);
            dropdownMess.setVisibility(View.VISIBLE);
        } else {
            e3.setVisibility(View.GONE);
            dropdownMess.setVisibility(View.GONE);

            LinearRoll.setVisibility(View.VISIBLE);
            dropdownHostel.setVisibility(View.VISIBLE);
            eRoom.setVisibility(View.VISIBLE);

            rveg.setVisibility(View.VISIBLE);
            rnon.setVisibility(View.VISIBLE);
            cbAdmin.setVisibility(View.VISIBLE);
        }
    }

    public void submit(View v) {

        if (rveg.isChecked()) {
            basicBill = 1875;
        } else if (rnon.isChecked())
            basicBill = 1925;

        String phoneNumber = e6.getText().toString();
        if (phoneNumber.length() != 10) {
            Toast.makeText(this, "Enter a valid phone number", Toast.LENGTH_LONG).show();
            e6.setText(null);
        } else {

            final String rollNo, hostel;

            if (aSwitch.isChecked()) {
                rollNo = e3.getText().toString();
                hostel = dropdownMess.getSelectedItem().toString();
            } else {
                String RnoPart1 = dropdown1.getSelectedItem().toString();
                String RnoPart2 = dropdown2.getSelectedItem().toString();
                rollNo = RnoPart1 + RnoPart2 + e1.getText().toString();
                hostel = dropdownHostel.getSelectedItem().toString();
            }

            String name = e2.getText().toString();
            String emailId = e4.getText().toString();
            String password = e5.getText().toString();
            String phoneNo = e6.getText().toString();
            String room = eRoom.getText().toString();

            int isVeg = 0;
            if (rveg.isChecked()) {
                isVeg = 1;
            } else isVeg = 0;

            int isAdmin = 0;

            if (cbAdmin.isChecked()) {
                isAdmin = 1;
            } else isAdmin = 0;

            progressBar.setVisibility(View.VISIBLE);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", name);
                jsonObject.put("roll_number", rollNo);
                jsonObject.put("hostel", hostel);
                jsonObject.put("phone_number", phoneNo);
                jsonObject.put("room_no", room);
                jsonObject.put("email_id", emailId);
                jsonObject.put("is_veg", isVeg);
                jsonObject.put("password", password);
                jsonObject.put("is_admin", isAdmin);

                GlobalVariables.currentRollNo = rollNo;
                GlobalVariables.currentName = name;
                GlobalVariables.currentHostel = hostel;
                GlobalVariables.currentRoomNo = room;
                GlobalVariables.currentEmailID = emailId;
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

            new MyAsyncTask(Register.this, jsonObject.toString(), URLS.API_register_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");

                        if (resultedMessage.equals("success")) {
                            Toast.makeText(Register.this, "Congrats! You have successfully registered.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this, MessSubscribe.class));
                        } else {
                            String errorMessage = response.getString("message");
                            Toast.makeText(Register.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                    } catch (Exception e) {
                        Toast.makeText(Register.this, "Please try again! Exception ", Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();
        }
    }

    public void login(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        if (onStartCount > 1) {
            this.overridePendingTransition(R.anim.anim_slide_in_left,
                    R.anim.anim_slide_out_left);

        } else if (onStartCount == 1) {
            onStartCount++;
        }
    }


}
