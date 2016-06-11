package com.example.android.dtuhostelmess;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import utils.AppPreferences;
import utils.Constants;
import utils.ListModel;
import utils.MyAsyncTask;
import utils.URLS;

public class ViewMessOff extends AppCompatActivity {

    public ListView list2;
    public CustomAdapterViewMessOff adapter;
    public ViewMessOff CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
    TextView tvName, tvRollNo, tvHos, tvRoomNo, TotalNoOff, tvHeaderName;
    Spinner gender, day;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private AppPreferences prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_mess_off);
        prefManager= AppPreferences.getInstance(this);

        tvName = (TextView) findViewById(R.id.tvBill);
        tvRollNo = (TextView) findViewById(R.id.tvDes);
        tvHos = (TextView) findViewById(R.id.tvMon);
        tvRoomNo = (TextView) findViewById(R.id.tvbasicDes);
        TotalNoOff = (TextView) findViewById(R.id.tvbasicAmo);

        gender = (Spinner) findViewById(R.id.spinnerMonth);
        day = (Spinner) findViewById(R.id.spinnerYear);

        //Todo: replace hardcoded month and year names
        String[] items1 = new String[]{"Boys Mess", "Girls Mess"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        gender.setAdapter(adapter1);

        String[] items2 = new String[]{"Today", "Tomorrow"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        day.setAdapter(adapter2);

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

                    case R.id.ViewCurrentOrders: {
                        startActivity(new Intent(ViewMessOff.this, CurrentOrders.class));
                        return true;
                    }

                    case R.id.EditMessMenu:
                        goToUrl(Constants.MessMenuUpdateUrl);
                        return true;

                    case R.id.ViewMessOff:
                       // startActivity(new Intent(ViewMessOff.this, ViewMessOff.class));
                        return true;

                    case R.id.ViewBillDetails: {
                        startActivity(new Intent(ViewMessOff.this, ViewBill.class));
                        return true;
                    }


                    case R.id.logout:
                    {
                        prefManager.putString(Constants.RollNumber, "");
                        startActivity(new Intent(ViewMessOff.this, MainActivity.class));
                        return true;
                    }

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
//        tvHeaderBill = (TextView) findViewById(R.id.headerBill);
//        tvHeaderName.setText(GlobalVariables.currentName);
//        tvHeaderBill.setText("Current Mess Bill : " + GlobalVariables.currentMessBill);

        CustomListView = this;

        // Take some data in Arraylist ( CustomListViewValuesArr )
        Resources res = getResources();
        list2 = (ListView) findViewById(R.id.listBill);

        // Create Custom Adapter
        adapter = new CustomAdapterViewMessOff(CustomListView, CustomListViewValuesArr, res);
        list2.setAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.menu_monthly_bill, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    //Function to set data in ArrayList
    public void go(View view) {
        final String genderSelected,date;

        final String selectedGender = gender.getSelectedItem().toString();
        if(selectedGender.equals("Girls Mess"))
            genderSelected = "female";

        else
            genderSelected = "male";

      //  Toast.makeText(ViewMessOff.this, genderSelected + "", Toast.LENGTH_LONG).show();


        String selectedDay = day.getSelectedItem().toString();
        if(selectedDay.equals("Today")){
           date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
           // Toast.makeText(ViewMessOff.this, date + "", Toast.LENGTH_LONG).show();
        }
        else{
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date tomorrow = calendar.getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
           date = dateFormat.format(tomorrow);
           // Toast.makeText(ViewMessOff.this, date + "", Toast.LENGTH_LONG).show();
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gender", genderSelected);
            jsonObject.put("date", date);

        } catch (Exception e) {
            Toast.makeText(ViewMessOff.this, "" + e, Toast.LENGTH_LONG).show();
            // System.out.println("Exception in json encoding "+e);
        }

        new MyAsyncTask(ViewMessOff.this, jsonObject.toString(), URLS.API_ViewMessOff_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");

                    if (resultedMessage.equals("success")) {
                        CustomListViewValuesArr = new ArrayList<ListModel>();
                        response = response.getJSONObject("payload");


                        JSONObject response2 = response.getJSONObject("mess_off");


                     if (response2.equals(null) || response2==null) {
                            AlertDialog alertDialog = new AlertDialog.Builder(ViewMessOff.this).create(); //Read Update
                            alertDialog.setTitle("No Mess Off");
                            alertDialog.setMessage("None of the students have done Mess Off for the selected date!");
                            alertDialog.show();

                        }

                        String responseOfMessOff = response2.getString("mess_off");
                                if(responseOfMessOff == null || responseOfMessOff.equals(null)){
                                    Toast.makeText(ViewMessOff.this, "here", Toast.LENGTH_SHORT).show();
                                }
                        else {


                            JSONArray responseArr = response.getJSONArray("mess_off");


                            int count = responseArr.length() - 1;

                            for (int i = 0; i < responseArr.length(); i++) {
                                count++;
                                JSONObject childJSONObject = responseArr.getJSONObject(i);
                                String name = childJSONObject.getString("name");
                                String roll_number = childJSONObject.getString("roll_number");
                                String hostel = childJSONObject.getString("hostel");
                                String room_no = childJSONObject.getString("room_no");

                                tvName.setText(name);
                                tvRollNo.setText(roll_number);
                                tvHos.setText(hostel);
                                tvRoomNo.setText(room_no);
                                TotalNoOff.setText(count);

                                final ListModel sched = new ListModel();

                                // Firstly take data in model object
                                sched.setDateTime(name);
                                sched.setMess(roll_number);
                                sched.setFood(hostel);
                                sched.setTotal(room_no);

                                // Take Model Object in ArrayList
                                CustomListViewValuesArr.add(sched);
                            }

                            Resources res = getResources();
                            adapter = new CustomAdapterViewMessOff(CustomListView, CustomListViewValuesArr, res);
                            list2.setAdapter(adapter);

                        }
                    }else {
                        response = response.getJSONObject("payload");
                        String errorMessage = response.getString("message");
                        Toast.makeText(ViewMessOff.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(ViewMessOff.this, "" + e, Toast.LENGTH_SHORT).show();
                }

            }
        }).execute();
    }

    public void onItemClick(int mPosition) {
        ListModel tempValues = (ListModel) CustomListViewValuesArr.get(mPosition);
        //Toast.makeText(CustomListView,tempValues.getItemName() +  " \nCost:" + tempValues.getCost(), Toast.LENGTH_LONG).show();
    }

}
