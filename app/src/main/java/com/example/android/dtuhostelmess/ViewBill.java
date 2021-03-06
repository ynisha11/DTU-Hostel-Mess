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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import utils.AppPreferences;
import utils.Constants;
import utils.GlobalVariables;
import utils.ListModel;
import utils.MyAsyncTask;
import utils.URLS;

public class ViewBill extends AppCompatActivity {

    public ListView list2;
    public CustomAdapterBill adapter;
    public ViewBill CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
    TextView tv, tvHeaderName, tvHeaderBill, Veg, Basic, des, mo;
    EditText e1;
    Spinner month, year;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private AppPreferences prefManager;
    Spinner dropdown1, dropdown2;
    String RNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bill);
        prefManager= AppPreferences.getInstance(this);

        tv = (TextView) findViewById(R.id.tvBill);
        des = (TextView) findViewById(R.id.tvDes);
        mo = (TextView) findViewById(R.id.tvMon);
        Veg = (TextView) findViewById(R.id.tvbasicDes);
        Basic = (TextView) findViewById(R.id.tvbasicAmo);

        e1 = (EditText) findViewById(R.id.etRollNo);

        dropdown1 = (Spinner) findViewById(R.id.spinner1);
        String[] rollYearItems=new String[8];
        Calendar c = Calendar.getInstance();
        int currentYear = c.get(Calendar.YEAR);
        int index= 0;
        for(int i=currentYear-6;i<=currentYear+1;i++)
        {
            rollYearItems[index++]= Integer.toString(i).replaceFirst("0","K")+"/";
        }
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, rollYearItems);
        dropdown1.setAdapter(adapter3);

        dropdown2 = (Spinner) findViewById(R.id.spinner2);
        String[] items4 = new String[]{"HO/", "HO/G/"};
        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items4);
        dropdown2.setAdapter(adapter4);

        month = (Spinner) findViewById(R.id.spinnerMonth);
        year = (Spinner) findViewById(R.id.spinnerYear);

        //Todo: replace hardcoded month and year names
        String[] items1 = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        month.setAdapter(adapter1);

        String[] items2 = new String[]{"2016", "2017", "2018"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        year.setAdapter(adapter2);

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
                        startActivity(new Intent(ViewBill.this, CurrentOrders.class));
                        return true;
                    }

                    case R.id.EditMessMenu:
                        goToUrl(Constants.MessMenuUpdateUrl);
                        return true;

                    case R.id.ViewMessOff:
                         startActivity(new Intent(ViewBill.this, ViewMessOff.class));
                        return true;

                    case R.id.ViewBillDetails: {
                        return true;
                    }


                    case R.id.logout:
                    {
                        prefManager.putString(Constants.RollNumber, "");
                        startActivity(new Intent(ViewBill.this, MainActivity.class));
                        return true;
                    }


                    case R.id.billPay:
                        goToUrl(Constants.MessBillPaymentUrl);
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Something's wrong. Please try again!", Toast.LENGTH_SHORT).show();
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
        list2 = (ListView) findViewById(R.id.listBill);

        // Create Custom Adapter
        adapter = new CustomAdapterBill(CustomListView, CustomListViewValuesArr, res);
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

        final String selectedMonth = month.getSelectedItem().toString();
        String selectedYear = year.getSelectedItem().toString();
        int year = Integer.parseInt(selectedYear);

        JSONObject jsonObject = new JSONObject();
        try {

            String RnoPart1 = dropdown1.getSelectedItem().toString();
            String RnoPart2 = dropdown2.getSelectedItem().toString();
            RNo = RnoPart1 + RnoPart2 + e1.getText().toString();

            jsonObject.put("roll_number", RNo);
            jsonObject.put("month", selectedMonth);
            jsonObject.put("year", year);
        } catch (Exception e) {
            Toast.makeText(ViewBill.this, "" + e, Toast.LENGTH_LONG).show();
            // System.out.println("Exception in json encoding "+e);
        }

        new MyAsyncTask(ViewBill.this, jsonObject.toString(), URLS.API_BillHistory_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");

                    if (resultedMessage.equals("success")) {
                        CustomListViewValuesArr= new ArrayList<ListModel>();
                        response = response.getJSONObject("payload");
                        String totalBill = response.getString("bill_amount");
                        JSONArray responseArr = response.getJSONArray("history");

                        JSONArray responseArr2 = response.getJSONArray("mess_off");

                        if (responseArr.length() == 0 && responseArr2.length()==0) {
                            AlertDialog alertDialog = new AlertDialog.Builder(ViewBill.this).create(); //Read Update
                            alertDialog.setTitle("No History");
                            alertDialog.setMessage("The selected Roll No. hasn't bought any food items in the selected Month and Year\nPlease choose again!");
                            alertDialog.show();
                        }

                        for (int i = 0; i < responseArr.length(); i++) {
                            JSONObject childJSONObject = responseArr.getJSONObject(i);
                            String date = childJSONObject.getString("timestamp");
                            String mess = childJSONObject.getString("counter_name");
                            String food = childJSONObject.getString("food_item");
                            String total = childJSONObject.getString("amount");

                            String dd = date.substring(8, 10);
                            String mm = date.substring(5, 8);
                            String yy = date.substring(0, 4);
                            String time = date.substring(11);

                            tv.setText("Total Mess Bill: ₹ " + totalBill);
                            Veg.setText(" " + GlobalVariables.currentVegOrNon);

                            des.setText(" Basic Bill ");
                            mo.setText(selectedMonth + " Month ");
                            if (GlobalVariables.currentVegOrNon.equals("Vegetarian")) {
                                Basic.setText("₹ 1875");
                            } else {
                                Basic.setText("₹ 1925");
                            }

                            final ListModel sched = new ListModel();

                            // Firstly take data in model object
                            sched.setDateTime(dd + "-" + mm + yy + " " + time);
                            sched.setMess(mess);
                            sched.setFood(food);
                            sched.setTotal(total);

                            // Take Model Object in ArrayList
                            CustomListViewValuesArr.add(sched);
                        }


                        for (int i = 0; i < responseArr2.length(); i++) {

                            JSONObject childJSONObject = responseArr2.getJSONObject(i);
                            String date = childJSONObject.getString("date");
                            String food = childJSONObject.getString("holiday");

                            final ListModel sched = new ListModel();

                            // Firstly take data in model object
                            sched.setDateTime(date);
                            sched.setMess("All Mess");
                            sched.setFood(food);
                            sched.setMessOffDeduct("65");

                            // Take Model Object in ArrayList
                            CustomListViewValuesArr.add(sched);
                        }


                        Resources res = getResources();
                        adapter = new CustomAdapterBill(CustomListView, CustomListViewValuesArr, res);
                        list2.setAdapter(adapter);

                    } else {
                        response = response.getJSONObject("payload");
                        String errorMessage = response.getString("message");
                        Toast.makeText(ViewBill.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(ViewBill.this, "" + e, Toast.LENGTH_SHORT).show();
                }

            }
        }).execute();
    }

    public void onItemClick(int mPosition) {
        ListModel tempValues = (ListModel) CustomListViewValuesArr.get(mPosition);
        //Toast.makeText(CustomListView,tempValues.getItemName() +  " \nCost:" + tempValues.getCost(), Toast.LENGTH_LONG).show();
    }

}
