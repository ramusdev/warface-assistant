package belev.org.warface_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    InterstitialAd mInterstitialAd;
    Uri link;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Init mobile ads
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // Init interstitial
        // mInterstitialAd = new InterstitialAd(this);
        // mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        // AdRequest adRequest = new AdRequest.Builder().build();
        // mInterstitialAd.loadAd(adRequest);

        // Load next ads
        // mInterstitialAd.setAdListener(new AdListener() {
            // @Override
            // public void onAdClosed() {
                // mInterstitialAd.loadAd(new AdRequest.Builder().build());
            // }
        // });

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bar));
            window.setNavigationBarColor(this.getResources().getColor(R.color.bar));
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //mFragmentTransaction.replace(R.id.containerView, new NewsFragment()).commit();

        if (isOnline()) {
            mFragmentTransaction.replace(R.id.containerView, new NewsFragment()).commit();
            toolbar.setTitle(getResources().getString(R.string.menu_news));
        } else {
            mFragmentTransaction.replace(R.id.containerView, new StartFragment()).commit();
            toolbar.setTitle(getResources().getString(R.string.menu_update));
        }



             mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                 if (menuItem.getItemId() == R.id.nav_item_about) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                             toolbar.setTitle(getResources().getString(R.string.menu_about));
                             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                             transaction.replace(R.id.containerView, new AboutFragment());
                             transaction.commit();
                         }
                     }, 300);

                     // showInterstitial();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_rang) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                             toolbar.setTitle(getResources().getString(R.string.menu_ranks));
                             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                             transaction.replace(R.id.containerView, new RangFragment());
                             transaction.commit();
                         }
                     }, 300);

                     // showInterstitial();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_achivment) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                             toolbar.setTitle(getResources().getString(R.string.menu_achievements));
                             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                             transaction.replace(R.id.containerView, new TabFragment());
                             transaction.commit();

                         }
                     }, 300);

                     // showInterstitial();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_update) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                             toolbar.setTitle(getResources().getString(R.string.menu_update));
                             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                             transaction.replace(R.id.containerView, new NativeAds());
                             transaction.commit();

                         }
                     }, 300);

                     // showInterstitial();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_news) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                             if (isOnline()) {
                                 toolbar.setTitle(getResources().getString(R.string.menu_news));
                                 FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                 transaction.replace(R.id.containerView, new NewsFragment());
                                 transaction.commit();
                             } else {
                                 toolbar.setTitle(getResources().getString(R.string.error_connection));
                                 FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                 transaction.replace(R.id.containerView, new ConnectionFragment());
                                 transaction.commit();
                             }
                         }
                     }, 300);

                     // showInterstitial();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_stremers) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                                 toolbar.setTitle(getResources().getString(R.string.menu_vloger));
                                 FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                 transaction.replace(R.id.containerView, new StremersFragment());
                                 transaction.commit();
                         }
                     }, 300);

                     // showInterstitial();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_maps) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                             toolbar.setTitle(getResources().getString(R.string.menu_locations));
                             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                             transaction.replace(R.id.containerView, new MapsTab());
                             transaction.commit();
                         }
                     }, 300);

                     // showInterstitial();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_rifleman) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                             toolbar.setTitle(getResources().getString(R.string.menu_rifleman));
                             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                             transaction.replace(R.id.containerView, new RiflemanTab());
                             transaction.commit();
                         }
                     }, 300);

                     // showInterstitial();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_sniper) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                             toolbar.setTitle(getResources().getString(R.string.menu_sniper));
                             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                             transaction.replace(R.id.containerView, new SniperTab());
                             transaction.commit();
                         }
                     }, 300);

                     // showInterstitial();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_medic) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                             toolbar.setTitle(getResources().getString(R.string.menu_medic));
                             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                             transaction.replace(R.id.containerView, new MedicTab());
                             transaction.commit();
                         }
                     }, 300);

                     // showInterstitial();
                 }

                 if (menuItem.getItemId() == R.id.nav_item_enginer) {
                     new Handler().postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                             toolbar.setTitle(getResources().getString(R.string.menu_enginer));
                             FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                             transaction.replace(R.id.containerView, new EnginerTab());
                             transaction.commit();
                         }
                     }, 300);

                     // showInterstitial();
                 }

                 return false;
            }

        });

    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Toast.makeText(this, "Add did not loaded", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public boolean isFirstRun() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean runBefore = preferences.getBoolean("RunBefore", false);
        if(!runBefore) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RunBefore", true);
            editor.commit();
        }
        return !runBefore;
    }

}