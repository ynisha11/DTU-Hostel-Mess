package com.example.android.dtuhostelmess;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import utils.GlobalVariables;
import utils.OpenHelper;

public class MessMenu extends AppCompatActivity {

    EditText tv1, tv2, tv3, tv4, tv5, tv6;
    TextView tvHeaderName, tvHeaderBill;
    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);

        tv1 = (EditText) findViewById(R.id.tv1);
        tv2 = (EditText) findViewById(R.id.tv2);
        tv3 = (EditText) findViewById(R.id.tv3);
        tv4 = (EditText) findViewById(R.id.tv4);
        tv5 = (EditText) findViewById(R.id.tv5);
        tv6 = (EditText) findViewById(R.id.tv6);

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
                        //startActivity(new Intent(MessMenu.this, MessMenu.class));
                    }
                    return true;

                    case R.id.monthly_bill:
                        startActivity(new Intent(MessMenu.this, MonthlyBill.class));
                        return true;

                    case R.id.mess_off:
                        startActivity(new Intent(MessMenu.this, MessOff.class));
                        return true;

                    case R.id.profile: {
                        Toast.makeText(getApplicationContext(), "View your Profile Details", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MessMenu.this, Profile.class));
                    }
                    return true;

                    case R.id.allmail:
                        Toast.makeText(getApplicationContext(), "All Mail Selected", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.buy: {
                        Toast.makeText(getApplicationContext(), "Buy Food Item", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MessMenu.this, Buy.class));
                    }
                    return true;

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
        if (id == R.id.action_settings) {
            return true;
        }
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

    public void insert(View v) {

        OpenHelper h = new OpenHelper(this);
        SQLiteDatabase db = h.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put("CounterName", tv1.getText().toString());
        c.put("MenuVersion", tv2.getText().toString());

        long id = db.insert("Counter", null, c);

        if (id == -1)
            Toast.makeText(this, "DATA NOT INSERTED", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "DATA INSERTED", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void insert2(View v) {

        OpenHelper h = new OpenHelper(this);
        SQLiteDatabase db = h.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put("MenuId", tv2.getText().toString());
        c.put("Name", tv1.getText().toString());
        c.put("Type", tv4.getText().toString());

        long id = db.insert("Item", null, c);

        if (id == -1)
            Toast.makeText(this, "DATA NOT INSERTED", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "DATA INSERTED", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void insert3(View v) {

        OpenHelper h = new OpenHelper(this);
        SQLiteDatabase db = h.getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put("CounterId", tv2.getText().toString());
        c.put("Day", tv1.getText().toString());
        c.put("StartTime", "2016-05-02 08-00-00");
        c.put("EndTime", "2016-05-02 12-02-03");
        c.put("MenuType", tv4.getText().toString());
        c.put("Cost", tv5.getText().toString());
        c.put("Version", tv6.getText().toString());

        long id = db.insert("Menu", null, c);

        if (id == -1)
            Toast.makeText(this, "DATA NOT INSERTED", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(this, "DATA INSERTED", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void fetch(View v) {

        OpenHelper h = new OpenHelper(this);
        String col[] = {"Id", "CounterName", "MenuVersion"};
        SQLiteDatabase db = h.getReadableDatabase();
        Cursor c = db.query("Counter", col, null, null, null, null, null);

        while (c.moveToNext()) {

            String id = c.getString(0);
            String counterName = c.getString(1);
            String menuVersion = c.getString(2);

            Toast.makeText(this, "DATA FOUND : " + id + " " + counterName + " " + menuVersion, Toast.LENGTH_LONG).show();
        }
    }

    public void fetch2(View v) {

        OpenHelper h = new OpenHelper(this);
        String col[] = {"Id", "MenuId", "Name", "Type"};
        SQLiteDatabase db = h.getReadableDatabase();
        Cursor c = db.query("Item", col, null, null, null, null, null);

        while (c.moveToNext()) {

            String id = c.getString(0);
            String menuId = c.getString(1);
            String name = c.getString(2);
            String type = c.getString(3);

            Toast.makeText(this, "DATA FOUND : " + id + " " + menuId + " " + name + " " + type, Toast.LENGTH_LONG).show();
        }

    }

    public void fetch3(View v) {

        OpenHelper h = new OpenHelper(this);
        String col[] = {"MenuId", "CounterId", "Day", "StartTime", "EndTime", "MenuType", "Cost", "Version"};

        SQLiteDatabase db = h.getReadableDatabase();
        Cursor c = db.query("Menu", col, null, null, null, null, null);

        while (c.moveToNext()) {

            String menuId = c.getString(0);
            String counterId = c.getString(1);
            String day = c.getString(2);
            String startTime = c.getString(3);
            String endTime = c.getString(4);
            String menuType = c.getString(5);
            String cost = c.getString(6);
            String version = c.getString(7);

            Toast.makeText(this, "DATA FOUND : " + menuId + " " + counterId + " " + day + " " + startTime + " " + endTime + " " + menuType + " " + cost + " " + version, Toast.LENGTH_LONG).show();
        }

    }

}
