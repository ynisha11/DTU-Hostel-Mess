package com.example.android.dtuhostelmess;

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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import utils.AppPreferences;
import utils.AppUtils;
import utils.Constants;
import utils.GlobalVariables;
import utils.ListModel;
import utils.MyAsyncTask;
import utils.URLS;

public class MessOff extends AppCompatActivity {

    public ListView list3;
    public CustomAdapterMessOff adapter;
    public MessOff CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();

    TextView tvHeaderName, tvHeaderBill;
    Button btMessOff;
    ProgressBar progressBar;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private AppPreferences prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_off);
        prefManager= AppPreferences.getInstance(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btMessOff = (Button) findViewById(R.id.btMessOff);

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

                        startActivity(new Intent(MessOff.this, MessMenu.class));
                        return true;
                    }


                    case R.id.monthly_bill:
                        startActivity(new Intent(MessOff.this, MonthlyBill.class));
                        return true;

                    case R.id.mess_off:
                        // startActivity(new Intent(MessOff.this, MessOff.class));
                        return true;

                    case R.id.profile: {
                        startActivity(new Intent(MessOff.this, Profile.class));
                        return true;
                    }


                    case R.id.feedbackMail: {
                        startActivity(Intent.createChooser(AppUtils.SendFeedBack(), ""));
                        return true;
                    }


                    case R.id.buy: {
                        startActivity(new Intent(MessOff.this, Buy.class));
                        return true;
                    }

                    case R.id.logout:
                    {
                        prefManager.putString(Constants.RollNumber, "");
                        startActivity(new Intent(MessOff.this, MainActivity.class));
                        return true;
                    }


                    case R.id.billPay:
                        goToUrl(Constants.MessBillPaymentUrl);
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

        CustomListView = this;

        // Take some data in Arraylist ( CustomListViewValuesArr )
        Resources res = getResources();
        list3 = (ListView) findViewById(R.id.listMessOff);

        // Create Custom Adapter
        adapter = new CustomAdapterMessOff(CustomListView, CustomListViewValuesArr, res);
        list3.setAdapter(adapter);

        list3.setVisibility(View.INVISIBLE);
        btMessOff.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("roll_number", GlobalVariables.currentRollNo);
        } catch (Exception e) {
            Toast.makeText(MessOff.this, "" + e, Toast.LENGTH_LONG).show();
            // System.out.println("Exception in json encoding "+e);
        }

        new MyAsyncTask(MessOff.this, jsonObject.toString(), URLS.API_MessOffDates_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");

                    if (resultedMessage.equals("success")) {

                        response = response.getJSONObject("payload");
                        JSONArray responseArr = response.getJSONArray("holidays");

                        for (int i = 0; i < responseArr.length(); i++) {
                            JSONObject childJSONObject = responseArr.getJSONObject(i);
                            String id = childJSONObject.getString("id");
                            String date = childJSONObject.getString("date");
                            String holiday = childJSONObject.getString("holiday");
                            int isMessOff = childJSONObject.getInt("is_mess_off");

                            String day = date.substring(0, 2);
                            String month = date.substring(3, 5);
                            String offDate = day + " " + getMonth(Integer.parseInt(month));

                            final ListModel sched = new ListModel();

                            //Firstly take data in model object
                            sched.setId(id);
                            sched.setDate(offDate);
                            sched.setHoliday(holiday);
                            sched.setMessOff(isMessOff);

                            //Take Model Object in ArrayList
                            CustomListViewValuesArr.add(sched);
                        }

                        Resources res = getResources();
                        adapter = new CustomAdapterMessOff(CustomListView, CustomListViewValuesArr, res);
                        list3.setAdapter(adapter);
                        list3.setVisibility(View.VISIBLE);
                        btMessOff.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        response = response.getJSONObject("payload");
                        String errorMessage = response.getString("message");
                        Toast.makeText(MessOff.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(MessOff.this, "" + e, Toast.LENGTH_SHORT).show();
                }

            }
        }).execute();
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
        getMenuInflater().inflate(R.menu.menu_mess_off, menu);
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

    public void messOff(View v) {

        progressBar.setVisibility(View.VISIBLE);

        int sizeOfList = CustomListViewValuesArr.size();
        CheckBox cb;
        TextView tv;
        String offList = "";

        for (int x = 0; x < sizeOfList; x++) {

            View view = list3.getChildAt(x);
            cb = (CheckBox) view.findViewById(R.id.cbMessOff);
            tv = (TextView) view.findViewById(R.id.tvMessOff);

            if (cb.isChecked()) {
                offList = offList.concat(tv.getText().toString() + ",");
            }

        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("holidays", offList.substring(0, offList.length() - 1));
            jsonObject.put("roll_number", GlobalVariables.currentRollNo);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            System.out.println("DATA NOT INSERTED. Please try again!" + e);
        }

        new MyAsyncTask(MessOff.this, jsonObject.toString(), URLS.API_AddMessOff_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                progressBar.setVisibility(View.GONE);
                try {

                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");

                    if (resultedMessage.equals("success")) {
                        Toast.makeText(MessOff.this, "Successfully added Mess Off!.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MessOff.this, MessMenu.class));
                    } else {
                        String errorMessage = response.getString("message");
                        Toast.makeText(MessOff.this, errorMessage, Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(MessOff.this, "Please try again! Exception ", Toast.LENGTH_SHORT).show();
                }

            }
        }).execute();

    }

    public void onItemClick(int mPosition) {
        ListModel tempValues = (ListModel) CustomListViewValuesArr.get(mPosition);
      //  Toast.makeText(CustomListView, "" + tempValues.getItemName() + " \nCost:" + tempValues.getCost(), Toast.LENGTH_LONG).show();
    }

    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

}
