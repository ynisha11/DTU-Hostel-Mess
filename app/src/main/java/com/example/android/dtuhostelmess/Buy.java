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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Adapters.BuyItemsAdapter;
import Models.FoodItem;
import Models.FoodItemObject;
import utils.AppPreferences;
import utils.AppUtils;
import utils.Constants;
import utils.GlobalVariables;
import utils.ListModel;
import utils.MyAsyncTask;
import utils.URLS;

public class Buy extends AppCompatActivity {

    public ListView list;
    //public Buy CustomListView = null;
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
    private BuyItemsAdapter buyItemsAdapter;
    private ArrayList<FoodItem> foodItemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        prefManager= AppPreferences.getInstance(this);

       progressBar = (ProgressBar) findViewById(R.id.progressBar);

      //  type = (TextView) findViewById(R.id.tvType);
//        tv = (CheckBox) findViewById(R.id.text);
//        tv1 = (TextView) findViewById(R.id.text2);
//        tv2 = (TextView) findViewById(R.id.text3);
       bt = (Button) findViewById(R.id.btPlaceOrder);

        foodItemsList= new ArrayList<FoodItem>();
        list = (ListView) findViewById(R.id.list);

        buyItemsAdapter = new BuyItemsAdapter(this, foodItemsList);
        list.setAdapter(buyItemsAdapter);


//        cbOthers = (CheckBox) findViewById(R.id.others);
//        cbOthersName = (EditText) findViewById(R.id.othersName);
//        cbOthersPrice = (EditText) findViewById(R.id.othersPrice);

        dropdown1 = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Constants.MessList);
        dropdown1.setAdapter(adapter1);

        list.setVisibility(View.INVISIBLE);
        dropdown1.setVisibility(View.INVISIBLE);

//        cbOthers.setVisibility(View.INVISIBLE);
//        cbOthersName.setVisibility(View.INVISIBLE);
//        cbOthersPrice.setVisibility(View.INVISIBLE);
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



                        Map<String, List<FoodItemObject>> foodItemObjectMap= new HashMap<String, List<FoodItemObject>>();

                        for (int i = 0; i < responseArr.length(); i++) {

                            JSONObject childJSONObject = responseArr.getJSONObject(i);
                            String name = childJSONObject.getString("name");
                            double cost = Integer.parseInt(childJSONObject.getString("cost"));
                            String id = childJSONObject.getString("food_id");
                            String calories = childJSONObject.getString("calories");
                            String nutrition = childJSONObject.getString("nutrition");
                            String picture = childJSONObject.getString("picture");
                            String type = childJSONObject.getString("type");

                            final FoodItemObject foodItemObject= new FoodItemObject(id, name, type, calories, nutrition, picture, cost);

                            List<FoodItemObject> itemList= foodItemObjectMap.get(foodItemObject.getType());
                            if (itemList ==null){
                                itemList = new ArrayList<>();
                                foodItemObjectMap.put(foodItemObject.getType(),itemList);
                            }

                            itemList.add(foodItemObject);
                        }

                        for ( String key : foodItemObjectMap.keySet() ) {
                            foodItemsList.add(new FoodItem(key, true));
                            List<FoodItemObject> itemList= foodItemObjectMap.get(key);
                            for(FoodItemObject item: itemList)
                            {
                                foodItemsList.add(new FoodItem(item, false));
                            }
                        }

                        //adding Other Item
                        foodItemsList.add(new FoodItem(Constants.OtherFoodId, "Others","Others", true));
                        foodItemsList.add(new FoodItem(Constants.OtherFoodId, "Others","Others", false));
                        //adding Other Item

                        buyItemsAdapter.setItemList(foodItemsList);
                        buyItemsAdapter.notifyDataSetChanged();





                        list.setVisibility(View.VISIBLE);
                        dropdown1.setVisibility(View.VISIBLE);

//                        cbOthers.setVisibility(View.VISIBLE);
//                        cbOthersName.setVisibility(View.VISIBLE);
//                        cbOthersPrice.setVisibility(View.VISIBLE);
                        bt.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);


                    } else {
                        response = response.getJSONObject("payload");
                        String errorMessage = response.getString("message");
                        Toast.makeText(Buy.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(Buy.this, "" + e, Toast.LENGTH_LONG).show();
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

        GlobalVariables.selectedCounter = dropdown1.getSelectedItem().toString();
        int sizeOfList = buyItemsAdapter.getCount();

        ArrayList<FoodItem> selectedFoodItems= new ArrayList<FoodItem>();

        for (int x = 0; x < sizeOfList; x++) {
            FoodItem item= (FoodItem) buyItemsAdapter.getItem(x);
            if(!item.ismIsSeparator())
            {
                if(item.ismIsSelected())
                {
                    selectedFoodItems.add(item);
                }
            }
        }

        GlobalVariables.selectedFoodItems= new ArrayList<FoodItem>(selectedFoodItems);
        startActivity(new Intent(Buy.this, ConfirmPurchase.class));
    }


    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}