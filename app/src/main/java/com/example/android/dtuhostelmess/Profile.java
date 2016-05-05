package com.example.android.dtuhostelmess;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import utils.GlobalVariables;
import utils.MyAsyncTask;
import utils.URLS;

public class Profile extends AppCompatActivity {
    TextView tvHeaderName, tvHeaderBill, e2, e6, e7, newE1, newE2;
    EditText e1, e3, e4, e5, e8;
    Switch aSwitch;
    Spinner dropdownHostel;
    ProgressBar progressBar;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        e1 = (EditText) findViewById(R.id.tvName);
        e2 = (TextView) findViewById(R.id.tvRollNo);
        e3 = (EditText) findViewById(R.id.tvRoom);
        e4 = (EditText) findViewById(R.id.tvEmail);
        e5 = (EditText) findViewById(R.id.tvPhoneNo);
        e6 = (TextView) findViewById(R.id.tvBill);
        e7 = (TextView) findViewById(R.id.tvVegOrNon);
        e8 = (EditText) findViewById(R.id.phone);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        dropdownHostel = (Spinner) findViewById(R.id.spinnerHostel);

        String[] items = new String[]{"KCH", "SNH", "Type II", "Type III", "Aryabhatta", "BCH", "CVR", "HJB", "JCB", "Ramanujan", "Type – II B-5", "VMH", "VVS"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdownHostel.setAdapter(adapter);
        dropdownHostel.setSelection(adapter.getPosition(GlobalVariables.currentHostel));

        e1.setText(GlobalVariables.currentName);
        e2.setText(GlobalVariables.currentRollNo);
        e3.setText(GlobalVariables.currentRoomNo);
        e4.setText(GlobalVariables.currentEmailID);
        e5.setText(GlobalVariables.currentPhoneNo);
        e6.setText(GlobalVariables.currentMessBill);
        e7.setText(GlobalVariables.currentVegOrNon);
        aSwitch = (Switch) findViewById(R.id.toggleButton);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                //Closing drawer on item click
                drawerLayout.closeDrawers();
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.messMenu: {
                        Toast.makeText(getApplicationContext(), "View Mess Menu", Toast.LENGTH_SHORT).show();
                        ContentFragment fragment = new ContentFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        startActivity(new Intent(Profile.this, MessMenu.class));
                    }
                    return true;

                    case R.id.monthly_bill:
                        startActivity(new Intent(Profile.this, MonthlyBill.class));
                        return true;

                    case R.id.mess_off:
                        startActivity(new Intent(Profile.this, MessOff.class));
                        return true;

                    case R.id.profile: {
                        Toast.makeText(getApplicationContext(), "View your Profile Details", Toast.LENGTH_SHORT).show();
                        //startActivity(new Intent(Profile.this, Profile.class));
                        return true;
                    }


                    case R.id.feedbackMail: {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"ynisha11@gmail.com", "maskaravivek@gmail.com"});
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback: DTU Hostel Mess App");
                        intent.putExtra(Intent.EXTRA_TEXT, "We would love to hear your feedback!");
                        startActivity(Intent.createChooser(intent, ""));
                        return true;
                    }



                    case R.id.buy: {
                        Toast.makeText(getApplicationContext(), "Buy Food Item", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Profile.this, Buy.class));
                        return true;
                    }


                    case R.id.billPay:
                        goToUrl("https://www.onlinesbi.com/prelogin/icollecthome.htm");
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
        mActivityTitle = getTitle().toString();

        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tvHeaderName = (TextView) findViewById(R.id.headerName);
        tvHeaderBill = (TextView) findViewById(R.id.headerBill);
        tvHeaderName.setText(GlobalVariables.currentName);
        tvHeaderBill.setText("Current Mess Bill : " + GlobalVariables.currentMessBill);
    }


    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            //Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            // Called when a drawer has settled in a completely closed state.
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void toggle(View v) {
        if (aSwitch.isChecked()) {
            newE1.setText("Username : ");
            newE2.setText("Mess Assigned : ");
        } else {
            newE1.setText("Roll Number : ");
            newE2.setText("Hostel : ");
        }
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void save(View v) {
        String name, room, email, phone;
        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
        try {
            name = e1.getText().toString();
            GlobalVariables.currentName = name;

            room = e3.getText().toString();
            GlobalVariables.currentRoomNo = room;
            GlobalVariables.currentHostel = dropdownHostel.getSelectedItem().toString();

            email = e4.getText().toString();
            GlobalVariables.currentEmailID = email;

            phone = e5.getText().toString();
            GlobalVariables.currentPhoneNo = phone;

            jsonObject.put("name", name);
            jsonObject.put("roll_number", GlobalVariables.currentRollNo);
            jsonObject.put("hostel", GlobalVariables.currentHostel);
            jsonObject.put("phone_number", phone);
            jsonObject.put("room_no", room);
            jsonObject.put("email_id", email);

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("DATA NOT INSERTED. Please try again!" + e);
        }

        new MyAsyncTask(Profile.this, jsonObject.toString(), URLS.API_UpdateProfile_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");

                    if (resultedMessage.equals("success")) {
                        Toast.makeText(Profile.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Profile.this, MessMenu.class));
                    } else {
                        String errorMessage = response.getString("message");
                        Toast.makeText(Profile.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(Profile.this, "Please try again! Exception ", Toast.LENGTH_LONG).show();
                }

            }
        }).execute();
    }

}