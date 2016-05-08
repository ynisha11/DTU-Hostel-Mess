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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import utils.AppPreferences;
import utils.AppUtils;
import utils.Constants;
import utils.GlobalVariables;
import utils.ListModel;
import utils.MyAsyncTask;
import utils.URLS;

public class Buy extends AppCompatActivity {

    public ListView list;
    public CustomAdapter adapter;
    public Buy CustomListView = null;
    public ArrayList<ListModel> CustomListViewValuesArr = new ArrayList<ListModel>();
    TextView tv1, tv2, tvHeaderName, tvHeaderBill, type;
    CheckBox tv;
    CheckBox cbOthers;
    EditText cbOthersName, cbOthersPrice;
    Spinner dropdown1;
    Button bt;
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
        setContentView(R.layout.activity_buy);

        prefManager= AppPreferences.getInstance(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

      //  type = (TextView) findViewById(R.id.tvType);
        tv = (CheckBox) findViewById(R.id.text);
        tv1 = (TextView) findViewById(R.id.text2);
        tv2 = (TextView) findViewById(R.id.text3);
        bt = (Button) findViewById(R.id.btPlaceOrder);

        CustomListView = this;
        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        Resources res = getResources();
        list = (ListView) findViewById(R.id.list);

        /**************** Create Custom Adapter *********/
        adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
        list.setAdapter(adapter);


        cbOthers = (CheckBox) findViewById(R.id.others);
        cbOthersName = (EditText) findViewById(R.id.othersName);
        cbOthersPrice = (EditText) findViewById(R.id.othersPrice);

        dropdown1 = (Spinner) findViewById(R.id.spinner1);
        String[] items = new String[]{"Aryabhatta Mess", "CVR Mess", "HJB Mess", "VVS Mess", "SNH Mess"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown1.setAdapter(adapter1);

        list.setVisibility(View.INVISIBLE);
        dropdown1.setVisibility(View.INVISIBLE);
        cbOthers.setVisibility(View.INVISIBLE);
        cbOthersName.setVisibility(View.INVISIBLE);
        cbOthersPrice.setVisibility(View.INVISIBLE);
        bt.setVisibility(View.INVISIBLE);

        progressBar.setVisibility(View.VISIBLE);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("is_paid", 1);


        } catch (Exception e) {
            Toast.makeText(Buy.this, "" + e, Toast.LENGTH_SHORT).show();
            // System.out.println("Exception in json encoding "+e);
        }

        new MyAsyncTask(Buy.this, jsonObject.toString(), URLS.API_PaidItem_URL, new MyAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {

                try {
                    JSONObject response = new JSONObject(output);
                    String resultedMessage = response.getString("responseType");

                    if (resultedMessage.equals("success")) {

                        response = response.getJSONObject("payload");
                        JSONArray responseArr = response.getJSONArray("food_items");

                      //  int flagBeverages=0, flagChips =0, flagBiscuits =0, flagIceCreams=0;



                        for (int i = 0; i < responseArr.length(); i++) {

                            JSONObject childJSONObject = responseArr.getJSONObject(i);
                            String name = childJSONObject.getString("name");
                            String cost = childJSONObject.getString("cost");
                            String id = childJSONObject.getString("food_id");
                            String type = childJSONObject.getString("type");

//                            if(type.equals("Beverages")){



//                              if(flagBeverages ==0){
//                                  final ListModel sched1 = new ListModel();
//                                  sched1.setType("Beverages");
//                                  CustomListViewValuesArr.add(sched1);
//                                  flagBeverages =1;
//
//                                  Resources res = getResources();
//                                  adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
//                                  list.setAdapter(adapter);
//
//                              }

                                final ListModel sched2 = new ListModel();

                                /******* Firstly take data in model object ******/

                                sched2.setItemName(name);
                                sched2.setCost(cost);
                                sched2.setFoodId(id);

                                /******** Take Model Object in ArrayList **********/
                                CustomListViewValuesArr.add(sched2);

                                Resources res = getResources();
                                adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
                                list.setAdapter(adapter);

//                            }
//
//                            if(type.equals("Ice Creams")){
//
//
//
//                                if(flagIceCreams ==0){
//                                    final ListModel sched3 = new ListModel();
//                                    sched3.setType("Ice Creams");
//                                    CustomListViewValuesArr.add(sched3);
//                                    flagIceCreams =1;
//
//                                    Resources res = getResources();
//                                    adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
//                                    list.setAdapter(adapter);
//
//                                }
//
//                               final ListModel sched4 = new ListModel();
//
//                                /******* Firstly take data in model object ******/
//                                sched4.setType("");
//                                sched4.setItemName(name);
//                                sched4.setCost(cost);
//                                sched4.setFoodId(id);
//
//                                /******** Take Model Object in ArrayList **********/
//                                CustomListViewValuesArr.add(sched4);
//
//                                Resources res = getResources();
//                                adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
//                                list.setAdapter(adapter);
//
//                            }
//
//                            if(type.equals("Chips")){
//
//
//                                if(flagChips ==0){
//                                    final ListModel sched5 = new ListModel();
//
//                                    sched5.setType("Chips");
//                                    CustomListViewValuesArr.add(sched5);
//                                    flagChips =1;
//
//                                }
//
//                                final ListModel sched6 = new ListModel();
//
//                                /******* Firstly take data in model object ******/
//                                sched6.setType("");
//                                sched6.setItemName(name);
//                                sched6.setCost(cost);
//                                sched6.setFoodId(id);
//
//                                /******** Take Model Object in ArrayList **********/
//                                CustomListViewValuesArr.add(sched6);
//
//                                Resources res = getResources();
//                                adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
//                                list.setAdapter(adapter);
//
//
//
//                            }
//
//
//                            if(type.equals("Biscuits")){
//
//
//
//                                if(flagBiscuits ==0){
//                                    final ListModel sched7 = new ListModel();
//                                    sched7.setType("Biscuits");
//                                    CustomListViewValuesArr.add(sched7);
//                                    flagBiscuits =1;
//
//                                }
//
//                                final ListModel sched8 = new ListModel();
//                                /******* Firstly take data in model object ******/
//                                sched8.setType("");
//                                sched8.setItemName(name);
//                                sched8.setCost(cost);
//                                sched8.setFoodId(id);
//
//                                /******** Take Model Object in ArrayList **********/
//                                CustomListViewValuesArr.add(sched8);
//
//                                Resources res = getResources();
//                                adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
//                                list.setAdapter(adapter);
//
//                            }

//                            Resources res = getResources();
//                            adapter = new CustomAdapter(CustomListView, CustomListViewValuesArr, res);
//                            list.setAdapter(adapter);
                   }
                        list.setVisibility(View.VISIBLE);
                        dropdown1.setVisibility(View.VISIBLE);
                        cbOthers.setVisibility(View.VISIBLE);
                        cbOthersName.setVisibility(View.VISIBLE);
                        cbOthersPrice.setVisibility(View.VISIBLE);
                        bt.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);


                    } else {
                        response = response.getJSONObject("payload");
                        String errorMessage = response.getString("message");
                        Toast.makeText(Buy.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(Buy.this, "" + e, Toast.LENGTH_SHORT).show();
                }

            }
        }).execute();

        int flagBeverages=0, flagChips =0, flagBiscuits =0, flagIceCreams=0;

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
                        startActivity(new Intent(Buy.this, MessMenu.class));
                        return true;

                    }

                    case R.id.monthly_bill:
                        startActivity(new Intent(Buy.this, MonthlyBill.class));
                        return true;

                    case R.id.mess_off:
                        startActivity(new Intent(Buy.this, MessOff.class));
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(Buy.this, Profile.class));
                        return true;

                    case R.id.feedbackMail: {
                        startActivity(Intent.createChooser(AppUtils.SendFeedBack(), ""));
                        return true;
                    }

                    case R.id.buy:
                        // startActivity(new Intent(Buy.this, Buy.class));
                        return true;

                    case R.id.billPay:
                        goToUrl(Constants.MessBillPaymentUrl);
                        return true;

                    case R.id.logout:
                    {
                        prefManager.putString(Constants.RollNumber, "");
                        startActivity(new Intent(Buy.this, MainActivity.class));
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
        tvHeaderBill.setText("Current Mess Bill : " + GlobalVariables.currentMessBill);
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
        getMenuInflater().inflate(R.menu.menu_buy, menu);
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

    public void placeOrder(View v) {

        String boughtInMess = dropdown1.getSelectedItem().toString();
        int sizeOfList = CustomListViewValuesArr.size();
        Intent i = new Intent(this, ConfirmPurchase.class);

        boolean check = false;
        if (cbOthers.isChecked() && !(cbOthersPrice.getText().toString()).equals("")) {

            check = true;
            i.putExtra("OthersChecked", "1");
            i.putExtra("OthersName", cbOthersName.getText().toString());
            i.putExtra("OthersPrice", cbOthersPrice.getText().toString());
        } else {
            i.putExtra("OthersChecked", "0");
        }

        CheckBox cb;
        TextView tv2, tv3;
        int count = 1, total = 0;

        for (int x = 0; x < sizeOfList; x++) {

            View view = list.getChildAt(x);
            if(view!=null)
            {
                cb = (CheckBox) view.findViewById(R.id.text);
                tv2 = (TextView) view.findViewById(R.id.text2);
                tv3 = (TextView) view.findViewById(R.id.text3);

                if (cb.isChecked()) {

                    total = total + 1;
                    i.putExtra(count + "", cb.getText().toString());
                    i.putExtra(count + 1 + "", tv2.getText().toString());
                    i.putExtra(count + 2 + "", tv3.getText().toString());
                    count = count + 3;
                }
            }
        }

        i.putExtra("TotalItem", total + "");
        i.putExtra("mess", boughtInMess);



        if (total == 0 && check == false) {
            AlertDialog alertDialog = new AlertDialog.Builder(Buy.this).create(); //Read Update
            alertDialog.setTitle("Please Select");
            alertDialog.setMessage("You haven't selected any food item to Place Order!");
            alertDialog.show();
        } else if (total > 7) {
            AlertDialog alertDialog = new AlertDialog.Builder(Buy.this).create(); //Read Update
            alertDialog.setTitle("Try Again");
            alertDialog.setMessage("Please select at max any 7 items in one go!");
            alertDialog.show();
        } else {
            startActivity(i);
        }
    }

    public void onItemClick(int mPosition) {
        ListModel tempValues = (ListModel) CustomListViewValuesArr.get(mPosition);
        // Toast.makeText(CustomListView,tempValues.getItemName() +  " \nCost:" + tempValues.getCost(),Toast.LENGTH_LONG).show();
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}