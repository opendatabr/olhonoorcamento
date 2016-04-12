package projeto.combatecorrupcao.activitys;

/**
 * Created by Angelica on 20/01/2016.
 */


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.v4.app.FragmentActivity;
import android.view.WindowManager;

import projeto.combatecorrupcao.R;


/**
 * Created by Angelica on 20/10/2015.
 */
public class SplashScreenActivity extends FragmentActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1000;
    public String token;
    public static SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.lightPrimaryColor));
        }
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
             /*   String valueDefault = "default";
                sharedPref = getPreferences(Context.MODE_PRIVATE);
                token = sharedPref.getString("Token", valueDefault);*/
                //  Toast.makeText(getApplication(), token, Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
