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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.CurrentOrdersAdapter;
import Models.CurrentOrdersHistory;
import Models.History;
import Models.ResponseData;
import utils.AppPreferences;
import utils.Constants;
import utils.GlobalVariables;
import utils.MyAsyncTask;
import utils.URLS;

public class CurrentOrders extends AppCompatActivity {

    public ListView currentOrdersListView;
    public CurrentOrdersAdapter currentOrdersAdapter;
    public ArrayList<History> currentOrderHistoryList=new ArrayList<History>();
    TextView tvHeaderName, tvHeaderBill;
    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    ProgressBar progressBar;
    private AppPreferences prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);
        prefManager= AppPreferences.getInstance(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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

                    case R.id.ViewCurrentOrders: {
                        //startActivity(new Intent(MessMenu.this, MessMenu.class));
                        return true;
                    }

                    case R.id.EditMessMenu:
                        goToUrl(Constants.MessMenuUpdateUrl);
                        return true;

                    case R.id.ViewMessOff:
                      startActivity(new Intent(CurrentOrders.this, ViewMessOff.class));
                        return true;

                    case R.id.ViewBillDetails: {
                       startActivity(new Intent(CurrentOrders.this, ViewBill.class));
                        return true;
                    }


                    case R.id.logout:
                    {
                        prefManager.putString(Constants.RollNumber, "");
                        startActivity(new Intent(CurrentOrders.this, MainActivity.class));
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
        //  addDrawerItems();
        setupDrawer();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        tvHeaderName = (TextView) findViewById(R.id.headerName);
        tvHeaderBill = (TextView) findViewById(R.id.headerBill);
        tvHeaderName.setText(GlobalVariables.currentName);

        // tvHeaderBill.setText("Current Mess Bill : " + GlobalVariables.currentMessBill);

        // Take some data in Arraylist ( CustomListViewValuesArr )
        Resources res = getResources();
        currentOrdersListView = (ListView) findViewById(R.id.listMenu);

        // Create Custom Adapter
        currentOrdersAdapter = new CurrentOrdersAdapter(CurrentOrders.this, currentOrderHistoryList);
        currentOrdersListView.setAdapter(currentOrdersAdapter);

        getCurrentOrders();
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            //Called when a drawer has settled in a completely open state.
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            //Called when a drawer has settled in a completely closed state.
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

        // Activate the navigation drawer toggle
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

    public void getCurrentOrders() {

        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("counter_id", "4");
        } catch (Exception e) {
            Toast.makeText(CurrentOrders.this, "" + e, Toast.LENGTH_SHORT).show();
            // System.out.println("Exception in json encoding "+e);
        }



            new MyAsyncTask(CurrentOrders.this, jsonObject.toString(), URLS.API_GetCurrentOrders_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {

                    try {
                        Gson gson = new Gson();
                        ResponseData responseData = gson.fromJson(output, ResponseData.class);

                        JSONObject response = new JSONObject(output);

                        if (responseData.getResponseType().equals(Constants.SuccessResponse)) {
                            CurrentOrdersHistory currentOrderHistory=  gson.fromJson(responseData.getPayload(), CurrentOrdersHistory.class);

                            ArrayList<History> currentOrderArrayList= new ArrayList<History>(currentOrderHistory.getHistory());
                            currentOrdersAdapter = new CurrentOrdersAdapter(CurrentOrders.this, currentOrderArrayList);
                            currentOrdersListView.setAdapter(currentOrdersAdapter);

                        } else {
                            response = response.getJSONObject("payload");
                            String errorMessage = response.getString("message");
                            Toast.makeText(CurrentOrders.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    } catch (Exception e) {
                        // Toast.makeText(MainActivity.this,""+ e, Toast.LENGTH_LONG).show();
                    }
                }
            }).execute();

        progressBar.setVisibility(View.INVISIBLE);
    }




    public void onItemClick(int mPosition) {
        //ListModel tempValues = (ListModel) CustomListViewValuesArr.get(mPosition);
        //Toast.makeText(CustomListView,tempValues.getItemName() +  " \nCost:" + tempValues.getCost(), Toast.LENGTH_LONG).show();
    }

}
