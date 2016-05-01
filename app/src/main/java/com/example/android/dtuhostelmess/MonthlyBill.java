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

import java.util.ArrayList;

import utils.MyAsyncTask;
import utils.URLS;


public class MonthlyBill extends AppCompatActivity {

    public ListView list2;
    public CustomAdapterBill adapter;
    public  MonthlyBill CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();


    TextView tv;
    TextView tvHeaderName, tvHeaderBill;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    TextView Veg, Basic, des, mo;

    Spinner month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_bill);

        tv = (TextView)findViewById(R.id.tvBill);
        des = (TextView)findViewById(R.id.tvDes);
        mo = (TextView)findViewById(R.id.tvMon);
        Veg = (TextView)findViewById(R.id.tvbasicDes);
        Basic = (TextView)findViewById(R.id.tvbasicAmo);

        month = (Spinner) findViewById(R.id.spinnerMonth);
        year = (Spinner) findViewById(R.id.spinnerYear);
        String[] items1 = new String[]{"January", "February", "March", "April", "May", "June", "July","August", "September", "October", "November", "December"};
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
                    case R.id.messMenu: {
                        Toast.makeText(getApplicationContext(), "View Mess Menu", Toast.LENGTH_SHORT).show();
                        ContentFragment fragment = new ContentFragment();
                        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.frame, fragment);
                        fragmentTransaction.commit();
                        startActivity(new Intent(MonthlyBill.this, MessMenu.class));
                    }
                    return true;
                    // For rest of the options we just show a toast on click
                    case R.id.monthly_bill:
                       // startActivity(new Intent(MonthlyBill.this, MonthlyBill.class));
                        return true;

                    case R.id.mess_off:
                        startActivity(new Intent(MonthlyBill.this, MessOff.class));
                        return true;

                    case R.id.profile: {
                        Toast.makeText(getApplicationContext(), "View your Profile Details", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MonthlyBill.this, Profile.class));
                    }
                    return true;
                    case R.id.allmail:
                        Toast.makeText(getApplicationContext(), "All Mail Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.buy: {
                        Toast.makeText(getApplicationContext(), "Buy Food Item", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MonthlyBill.this, Buy.class));
                    }
                    return true;

                    case R.id.billPay:{

                        goToUrl( "https://www.onlinesbi.com/prelogin/icollecthome.htm");
                    }

                    return true;

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });
        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer, R.string.closeDrawer){
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
        tvHeaderName = (TextView)findViewById(R.id.headerName);
        tvHeaderBill = (TextView)findViewById(R.id.headerBill);
        tvHeaderName.setText(GlobalVariables.currentName);
        tvHeaderBill.setText("Current Mess Bill : " + GlobalVariables.currentMessBill);


        CustomListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
       // setListData();

        Resources res =getResources();
        list2=(ListView)findViewById(R.id.listBill);

        /**************** Create Custom Adapter *********/
        adapter=new CustomAdapterBill(CustomListView, CustomListViewValuesArr,res);
        list2.setAdapter(adapter);



    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation!");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
            /** Called when a drawer has settled in a completely closed state. */
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
        if (id == R.id.action_settings) {
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }


    /****** Function to set data in ArrayList *************/
    public void go(View view){

        final String selectedMonth = month.getSelectedItem().toString();
        String selectedYear = year.getSelectedItem().toString();

        int year = Integer.parseInt(selectedYear);


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("roll_number", GlobalVariables.currentRollNo);
            jsonObject.put("month", selectedMonth);
            jsonObject.put("year", year);


        } catch (Exception e) {
            Toast.makeText(MonthlyBill.this, "" + e, Toast.LENGTH_LONG).show();
            // System.out.println("Exception in json encoding "+e);
        }


        new MyAsyncTask(MonthlyBill.this, jsonObject.toString(), URLS.API_BillHistory_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");


                    if (resultedMessage.equals("success")) {

                        response = response.getJSONObject("payload");

                        String totalBill = response.getString("bill_amount");

                        JSONArray responseArr = response.getJSONArray("history");

                        if(responseArr.length() ==0){

                            AlertDialog alertDialog = new AlertDialog.Builder(MonthlyBill.this).create(); //Read Update
                            alertDialog.setTitle("No History");
                            alertDialog.setMessage("You haven't bought any food items in the selected Month and Year\nPlease choose again!");
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
                            String yy = date.substring(0,4);

                            String time = date.substring(11);

                            tv.setText("Total Mess Bill : Rs " + totalBill);
                            Veg.setText(" "+GlobalVariables.currentVegOrNon);


                            des.setText(" Basic Bill ");
                            mo.setText(selectedMonth+" Month ");
                            if(GlobalVariables.currentVegOrNon.equals("Vegetarian")){
                                Basic.setText("Rs 1875");
                            }

                            else {
                                Basic.setText("Rs 1925");
                            }

                            final  ListModel sched = new ListModel();

                            /******* Firstly take data in model object ******/
                            sched.setDateTime(dd+"-"+mm+yy+" "+time);
                            sched.setMess(mess);
                            sched.setFood(food);
                            sched.setTotal(total);


                            /******** Take Model Object in ArrayList **********/
                            CustomListViewValuesArr.add(sched);
                        }



                        Resources res = getResources();
                        adapter = new CustomAdapterBill(CustomListView, CustomListViewValuesArr,res);
                        list2.setAdapter(adapter);


                    } else {

                        response = response.getJSONObject("payload");
                        String errorMessage = response.getString("message");
                        Toast.makeText(MonthlyBill.this, errorMessage, Toast.LENGTH_LONG).show();

                    }

                } catch (Exception e) {
                    Toast.makeText(MonthlyBill.this, "" + e, Toast.LENGTH_LONG).show();
                }

            }
        }).execute();

    }



    public void onItemClick(int mPosition)
    {
        ListModel tempValues = (ListModel) CustomListViewValuesArr.get(mPosition);

        Toast.makeText(CustomListView,
                "" + tempValues.getItemName() +  " \nCost:" + tempValues.getCost(),
                Toast.LENGTH_LONG)
                .show();
    }


}
