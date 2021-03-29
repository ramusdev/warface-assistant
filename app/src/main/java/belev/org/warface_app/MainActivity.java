package belev.org.warface_app;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import belev.org.warface_app.data.DataDbHelper;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class MainActivity extends AppCompatActivity {

    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    InterstitialAd mInterstitialAd;
    public Toolbar toolbar;

    // private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private AdLoader adLoader;
    public List<UnifiedNativeAd> mNativeAds = new ArrayList<UnifiedNativeAd>();
    public List<NewsModel> newsList = new ArrayList<NewsModel>();
    public DataDbHelper dbHelper;
    public AtomicBoolean isShowedMain = new AtomicBoolean(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Init mobile ads
        // MobileAds.initialize(this, new OnInitializationCompleteListener() {
            // @Override
            // public void onInitializationComplete(InitializationStatus initializationStatus) {
            // }
        // });

        // Open connection to db
        dbHelper = new DataDbHelper(this);

        // Tasks after create
        createTasks();

        // Init fragment
        final DrawerLayout mDrawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView mNavigationView = findViewById(R.id.navigation_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.menu_news));

        //ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        //mDrawerLayout.setDrawerListener(mDrawerToggle);
        //mDrawerToggle.syncState();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();
        mNavigationView.bringToFront();

        // Fragment manager
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        // Check is online
        if (isOnline()) {
            /*
            View decorView = getWindow().getDecorView();
            final int flags = SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(flags);
            toolbar.setVisibility(View.INVISIBLE);
            */

            // mFragmentTransaction.replace(R.id.containerView, new SplashFragment()).commit();
            mFragmentTransaction.replace(R.id.containerView, new NewsFragment()).commit();
            toolbar.setTitle(getResources().getString(R.string.menu_news));
        } else {
            mFragmentTransaction.replace(R.id.containerView, new StartFragment()).commit();
            toolbar.setTitle(getResources().getString(R.string.menu_update));
        }

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

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.bar));
        window.setNavigationBarColor(this.getResources().getColor(R.color.bar));

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem menuItem) {
                mDrawerLayout.closeDrawers();

                if (menuItem.getItemId() == R.id.nav_item_about) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            toolbar.setTitle(getResources().getString(R.string.menu_about));
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.containerView, new AboutFragment()).commit();
                            mNavigationView.setCheckedItem(menuItem);
                        }
                    }, 275);

                }

                if (menuItem.getItemId() == R.id.nav_item_rang) {
                    menuItem.setChecked(true);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            toolbar.setTitle(getResources().getString(R.string.menu_ranks));
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.containerView, new RangFragment()).commit();
                            mNavigationView.setCheckedItem(menuItem);
                        }
                    }, 275);
                }

                if (menuItem.getItemId() == R.id.nav_item_achivment) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // menuItem.setChecked(true);
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            toolbar.setTitle(getResources().getString(R.string.menu_achievements));
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.containerView, new TabFragment()).commit();
                            mNavigationView.setCheckedItem(menuItem);

                        }
                    }, 275);
                }

                if (menuItem.getItemId() == R.id.nav_item_update) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // menuItem.setChecked(true);
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            toolbar.setTitle(getResources().getString(R.string.menu_update));
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            transaction.replace(R.id.containerView, new StartFragment()).commit();
                            mNavigationView.setCheckedItem(menuItem);

                        }
                    }, 275);
                }

                if (menuItem.getItemId() == R.id.nav_item_news) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

                            if (isOnline()) {
                                // menuItem.setChecked(true);
                                toolbar.setTitle(getResources().getString(R.string.menu_news));
                                transaction.replace(R.id.containerView, new NewsFragment()).commit();
                                mNavigationView.setCheckedItem(menuItem);
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 275);
                }

                if (menuItem.getItemId() == R.id.nav_item_statistics) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                            if (isOnline()) {
                                toolbar.setTitle(getResources().getString(R.string.menu_statistics));
                                transaction.replace(R.id.containerView, new StatisticsFragment()).commit();
                                mNavigationView.setCheckedItem(menuItem);
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 275);
                }

                if (menuItem.getItemId() == R.id.nav_item_maps) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                            if (isOnline()) {
                                // menuItem.setChecked(true);
                                toolbar.setTitle(getResources().getString(R.string.menu_locations));
                                transaction.replace(R.id.containerView, new MapsTab()).commit();
                                mNavigationView.setCheckedItem(menuItem);
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 275);
                }

                if (menuItem.getItemId() == R.id.nav_item_rifleman) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                            if (isOnline()) {
                                // menuItem.setChecked(true);
                                toolbar.setTitle(getResources().getString(R.string.menu_rifleman));
                                transaction.replace(R.id.containerView, new RiflemanTab()).commit();
                                mNavigationView.setCheckedItem(menuItem);
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 275);
                }

                if (menuItem.getItemId() == R.id.nav_item_sniper) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                            if (isOnline()) {
                                // menuItem.setChecked(true);
                                toolbar.setTitle(getResources().getString(R.string.menu_sniper));
                                transaction.replace(R.id.containerView, new SniperTab()).commit();
                                mNavigationView.setCheckedItem(menuItem);
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 275);
                }

                if (menuItem.getItemId() == R.id.nav_item_medic) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                            if (isOnline()) {
                                // menuItem.setChecked(true);
                                toolbar.setTitle(getResources().getString(R.string.menu_medic));
                                transaction.replace(R.id.containerView, new MedicTab()).commit();
                                mNavigationView.setCheckedItem(menuItem);
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 275);
                }

                if (menuItem.getItemId() == R.id.nav_item_enginer) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                            if (isOnline()) {
                                // menuItem.setChecked(true);
                                toolbar.setTitle(getResources().getString(R.string.menu_enginer));
                                transaction.replace(R.id.containerView, new EnginerTab()).commit();
                                mNavigationView.setCheckedItem(menuItem);
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 275);
                }

                return false;
            }

        });
    }

    public void updateNewsIfNotExists() {
        NewsLoader newsLoader = new NewsLoader(this.getApplicationContext());
        List<News> news = newsLoader.load();

        if (news.size() <= 0) {
            UpdateNewsAsync updateNewsAsync = new UpdateNewsAsync(this.getApplicationContext());
            updateNewsAsync.execute();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void createTasks() {
        updateNewsIfNotExists();
        createPeriodicTask();
    }

    public void createPeriodicTask() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // calendar.add(Calendar.SECOND, 10);
        long time = calendar.getTimeInMillis();

        Intent intent = new Intent(this, BroadcastReceiverCustom.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, time, TimeUnit.HOURS.toMillis(12), pendingIntent);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network network = connectivityManager.getActiveNetwork();
            if (network == null) return false;
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            );
        } else {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
    }

    @Override
    protected void onDestroy() {
        // dbHelper.close();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }
}
