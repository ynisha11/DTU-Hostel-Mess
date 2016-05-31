package com.example.android.dtuhostelmess;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import utils.AppPreferences;
import utils.Constants;
import utils.GlobalVariables;
import utils.ListModel;
import utils.MyAsyncTask;
import utils.OpenHelper;
import utils.URLS;

public class CurrentOrders extends AppCompatActivity {

    public ListView listMenu;
    public CustomAdapterMessMenu adapter;
    public CurrentOrders CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
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
                        goToUrl(Constants.MessBillPaymentUrl);
                        return true;

                    case R.id.ViewMessOff:
                       // startActivity(new Intent(CurrentOrders.this, ViewMessOff.class));
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

        CustomListView = this;
        // Take some data in Arraylist ( CustomListViewValuesArr )
        Resources res = getResources();
        listMenu = (ListView) findViewById(R.id.listMenu);

        // Create Custom Adapter
        adapter = new CustomAdapterMessMenu(CustomListView, CustomListViewValuesArr, res);
        listMenu.setAdapter(adapter);

        getMessMenu();
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

    public void getMessMenu() {

        progressBar.setVisibility(View.VISIBLE);

        OpenHelper h = new OpenHelper(this);
        String col[] = {"Id", "CounterName", "MenuVersion"};
        SQLiteDatabase db = h.getReadableDatabase();
        Cursor c = db.query("Counter", col, null, null, null, null, null);

        while (c.moveToNext()) {

            final String id = c.getString(0);
            final String counterName = c.getString(1);
            String menuVersion = c.getString(2);


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("counter", id);
                jsonObject.put("previous_version", menuVersion);
            } catch (Exception e) {
                Toast.makeText(CurrentOrders.this, "" + e, Toast.LENGTH_SHORT).show();
                // System.out.println("Exception in json encoding "+e);
            }



            new MyAsyncTask(CurrentOrders.this, jsonObject.toString(), URLS.API_GetMenu_URL, new MyAsyncTask.AsyncResponse() {
                @Override
                public void processFinish(String output) {

                    try {
                        JSONObject response = new JSONObject(output);
                        String resultedMessage = response.getString("responseType");

                        if (resultedMessage.equals("success")) {
                            response = response.getJSONObject("payload");

                            JSONArray responseArr = response.getJSONArray("menu");

                            int flag =0;



                            for (int i = 0; i < responseArr.length(); i++) {

                                JSONObject childJSONObject = responseArr.getJSONObject(i);

                                String menuId = childJSONObject.getString("menu_id");
                                String day = childJSONObject.getString("day");
                                String startTime = childJSONObject.getString("start_time");
                                String endTime = childJSONObject.getString("end_time");
                                String type = childJSONObject.getString("type");
                                String cost = childJSONObject.getString("cost");
                                String version = childJSONObject.getString("version");

//                                OpenHelper h = new OpenHelper(MessMenu.this);
//                                SQLiteDatabase db = h.getWritableDatabase();
//                                ContentValues c = new ContentValues();
//
//                                c.put("CounterId", id);
//                                c.put("Day", day);
//                                c.put("StartTime", startTime);
//                                c.put("EndTime", endTime);
//                                c.put("MenuType", type);
//                                c.put("Cost", cost);
//                                c.put("Version", version);
//
//                                long id = db.insert("Menu", null, c);
//
//                                if (id == -1)
//                                    Toast.makeText(MessMenu.this, "DATA NOT INSERTED", Toast.LENGTH_SHORT).show();
//                                else {
//                                    Toast.makeText(MessMenu.this, "DATA INSERTED", Toast.LENGTH_SHORT).show();
//
                                //                     }

                                if (flag == 0) {

                                    final ListModel sched0 = new ListModel();
                                    // Firstly take data in model object
                                    sched0.setFoodName("");

                                    // Take Model Object in ArrayList
                                    CustomListViewValuesArr.add(sched0);

                                    final ListModel sched = new ListModel();
                                    // Firstly take data in model object
                                    sched.setCounterName(counterName);
                                    // sched.setFoodName(name);

                                    // Take Model Object in ArrayList
                                    CustomListViewValuesArr.add(sched);
                                    flag = 1;


                                    final ListModel sched4 = new ListModel();
                                    // Firstly take data in model object
                                    sched4.setFoodName("");

                                    // Take Model Object in ArrayList
                                    CustomListViewValuesArr.add(sched4);

                                }

                                String weekDay;
                                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);

                                Calendar calendar = Calendar.getInstance();
                                weekDay = dayFormat.format(calendar.getTime());

                                //  Toast.makeText(MessMenu.this, "Here and weekday is : "+weekDay, Toast.LENGTH_SHORT).show();

                                if (day.equals(weekDay)) {

                                    final ListModel sched = new ListModel();
                                    // Firstly take data in model object
                                    sched.setFoodName(day + " " + type);
                                    // sched.setMealName(day);

                                    // Take Model Object in ArrayList
                                    CustomListViewValuesArr.add(sched);


                                    JSONArray items = childJSONObject.getJSONArray("items");

                                    String wholeName = "";

                                    for (int k = 0; k < items.length(); k++) {

                                        JSONObject childJSONObject2 = items.getJSONObject(k);
                                        String name = childJSONObject2.getString("name");
                                        String type2 = childJSONObject2.getString("type");

//                                        ContentValues c3 = new ContentValues();
//
//                                        c3.put("MenuId", menuId);
//                                        c3.put("Name", name);
//                                        c3.put("Type", type2);
//
//                                        long id3 = db.insert("Item", null, c3);
//
//                                        if (id3 == -1)
//                                            Toast.makeText(MessMenu.this, "DATA NOT INSERTED", Toast.LENGTH_SHORT).show();
//                                        else {
//                                            Toast.makeText(MessMenu.this, "DATA INSERTED", Toast.LENGTH_SHORT).show();


                                        wholeName = wholeName.concat(name + ", ");

                                        //     }


                                    }

                                    final ListModel sched3 = new ListModel();
                                    // Firstly take data in model object
                                    //  sched3.setMealName(type);
                                    sched3.setMealName(wholeName.substring(0, wholeName.length() - 2));

                                    // Take Model Object in ArrayList
                                    CustomListViewValuesArr.add(sched3);

                                    Resources res = getResources();
                                    adapter = new CustomAdapterMessMenu(CustomListView, CustomListViewValuesArr, res);
                                    listMenu.setAdapter(adapter);


                                    final ListModel sched4 = new ListModel();
                                    // Firstly take data in model object
                                    sched4.setFoodName("");

                                    // Take Model Object in ArrayList
                                    CustomListViewValuesArr.add(sched4);


                                }


                            }

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


        }

        progressBar.setVisibility(View.INVISIBLE);
    }




    public void onItemClick(int mPosition) {
        ListModel tempValues = (ListModel) CustomListViewValuesArr.get(mPosition);
        //Toast.makeText(CustomListView,tempValues.getItemName() +  " \nCost:" + tempValues.getCost(), Toast.LENGTH_LONG).show();
    }

}
