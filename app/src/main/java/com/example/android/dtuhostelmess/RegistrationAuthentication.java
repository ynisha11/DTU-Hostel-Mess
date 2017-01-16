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

public class RegistrationAuthentication extends AppCompatActivity {

    EditText e1, e2, e3;
    int onStartCount = 0;
    ProgressBar progressBar;
    private AppPreferences prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_authentication);
        prefManager= AppPreferences.getInstance(this);


        onStartCount = 1;
        if (savedInstanceState == null) // 1st time
        {
            this.overridePendingTransition(R.anim.anim_slide_in_right,
                    R.anim.anim_slide_out_right);
        } else // already created so reverse animation
        {
            onStartCount = 2;
        }

        e1 = (EditText) findViewById(R.id.etName);
        e2 = (EditText) findViewById(R.id.etEmail);
        e3 = (EditText) findViewById(R.id.etPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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


    public void proceed(View v) {

        // If emailId and password(and name optional) match only then let the user proceed with registration

        final String name, emailId;
        name = e1.getText().toString();
        emailId = e2.getText().toString();
        String password = e3.getText().toString();

        progressBar.setVisibility(View.VISIBLE);

        GlobalVariables.currentName = name;
        GlobalVariables.currentEmailID = emailId;

        //This might not be safe way, think of an alternative, or maybe erase it
        // (delte the Global variable for storing password) once sent to databse or update with null string.
        GlobalVariables.currentPassword = password;

        progressBar.setVisibility(View.GONE);
                    try {
                            startActivity(new Intent(RegistrationAuthentication.this, RegistrationDetails.class));

                    } catch (Exception e) {
                        Toast.makeText(RegistrationAuthentication.this, "Please try again! Exception ", Toast.LENGTH_LONG).show();
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

