package utils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.android.dtuhostelmess.CurrentOrders;
import com.example.android.dtuhostelmess.MainActivity;
import com.example.android.dtuhostelmess.MessMenu;
import com.example.android.dtuhostelmess.R;

/**
 * Created by vivek on 07/05/16.
 */

public class SplashActivity extends Activity {

    // Splash screen timer
    private int mSplashTime= 500;
    private static int SPLASH_TIME_OUT = 3000;
    private AppPreferences prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_layout);
        prefManager= AppPreferences.getInstance(this);

        if(prefManager.isLogin())
        {
            GlobalVariables.currentEmailID= prefManager.getString(Constants.CurrentEmailId,"");
            GlobalVariables.currentHostel= prefManager.getString(Constants.CurrentHostel,"");
            GlobalVariables.currentMessBill= prefManager.getString(Constants.CurrentMessBill,"");
            GlobalVariables.currentName= prefManager.getString(Constants.CurrentName,"");
            GlobalVariables.currentPhoneNo= prefManager.getString(Constants.CurrentPhoneNumber,"");
            GlobalVariables.currentRoomNo= prefManager.getString(Constants.CurrentRoomNumber,"");
            GlobalVariables.currentVegOrNon= prefManager.getString(Constants.CurrentVegOrNonVeg,"");
            GlobalVariables.currentRollNo= prefManager.getString(Constants.RollNumber,"");
            GlobalVariables.isAdminLogged= Integer.parseInt(prefManager.getString(Constants.IsAdmin,""));

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent i = new Intent(SplashActivity.this, GlobalVariables.isAdminLogged==1? CurrentOrders.class:MessMenu.class);
                    startActivity(i);
                    finish();
                }
            }, mSplashTime);

        }
        else
        {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, mSplashTime);
        }
    }

}