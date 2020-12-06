package belev.org.warface_app;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class MainActivity extends AppCompatActivity {

    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    InterstitialAd mInterstitialAd;
    Toolbar toolbar;

    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private AdLoader adLoader;
    public List<UnifiedNativeAd> mNativeAds = new ArrayList<UnifiedNativeAd>();
    public List<NewsModel> newsList = new ArrayList<NewsModel>();

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

        // Init faragment
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.menu_news));
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();

        // Check is online
        if (isOnline()) {
            View decorView = getWindow().getDecorView();
            final int flags = SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(flags);
            toolbar.setVisibility(View.INVISIBLE);

            mFragmentTransaction.replace(R.id.containerView, new SplashFragment()).commit();
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



        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bar));
            window.setNavigationBarColor(this.getResources().getColor(R.color.bar));
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

                            transaction.replace(R.id.containerView, new AboutFragment()).commit();
                        }
                    }, 200);

                }

                if (menuItem.getItemId() == R.id.nav_item_rang) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            toolbar.setTitle(getResources().getString(R.string.menu_ranks));
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            transaction.replace(R.id.containerView, new RangFragment()).commit();
                        }
                    }, 200);
                }

                if (menuItem.getItemId() == R.id.nav_item_achivment) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            toolbar.setTitle(getResources().getString(R.string.menu_achievements));
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            transaction.replace(R.id.containerView, new TabFragment()).commit();

                        }
                    }, 200);
                }

                if (menuItem.getItemId() == R.id.nav_item_update) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            toolbar.setTitle(getResources().getString(R.string.menu_update));
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            transaction.replace(R.id.containerView, new StartFragment()).commit();

                        }
                    }, 200);
                }

                if (menuItem.getItemId() == R.id.nav_item_news) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

                            if (isOnline()) {
                                toolbar.setTitle(getResources().getString(R.string.menu_news));
                                transaction.replace(R.id.containerView, new NewsFragment()).commit();
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 200);
                }

                if (menuItem.getItemId() == R.id.nav_item_stremers) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            if (isOnline()) {
                                toolbar.setTitle(getResources().getString(R.string.menu_vloger));
                                transaction.replace(R.id.containerView, new StremersFragment()).commit();
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 200);
                }

                if (menuItem.getItemId() == R.id.nav_item_maps) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            if (isOnline()) {
                                toolbar.setTitle(getResources().getString(R.string.menu_locations));
                                transaction.replace(R.id.containerView, new MapsTab()).commit();
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 200);
                }

                if (menuItem.getItemId() == R.id.nav_item_rifleman) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            if (isOnline()) {
                                toolbar.setTitle(getResources().getString(R.string.menu_rifleman));
                                transaction.replace(R.id.containerView, new RiflemanTab()).commit();
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 200);
                }

                if (menuItem.getItemId() == R.id.nav_item_sniper) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            if (isOnline()) {
                                toolbar.setTitle(getResources().getString(R.string.menu_sniper));
                                transaction.replace(R.id.containerView, new SniperTab()).commit();
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 200);
                }

                if (menuItem.getItemId() == R.id.nav_item_medic) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            if (isOnline()) {
                                toolbar.setTitle(getResources().getString(R.string.menu_medic));
                                transaction.replace(R.id.containerView, new MedicTab()).commit();
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 200);
                }

                if (menuItem.getItemId() == R.id.nav_item_enginer) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                            if (isOnline()) {
                                toolbar.setTitle(getResources().getString(R.string.menu_enginer));
                                transaction.replace(R.id.containerView, new EnginerTab()).commit();
                            } else {
                                toolbar.setTitle(getResources().getString(R.string.error_connection));
                                transaction.replace(R.id.containerView, new ConnectionFragment()).commit();
                            }
                        }
                    }, 200);
                }

                return false;
            }

        });


        // NativeAdLoader nativeAdLoader = new NativeAdLoader(this, 4);
        // nativeAdLoader.loadNativeAds();

    }

    /*
    public void loadGroupAds() {
        loadNativeAd(4);
    }

    public void loadNativeAd(final int countAdsToLoad) {
        final AdLoader.Builder builder = new AdLoader.Builder(this, ADMOB_AD_UNIT_ID);

        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        mNativeAds.add(unifiedNativeAd);

                        if (adLoader.isLoading()) {
                        } else {
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.containerView, new NewsFragment());
                            transaction.commit();

                            toolbar.setVisibility(View.VISIBLE);

                            View decorView = getWindow().getDecorView();
                            final int flags = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | SYSTEM_UI_FLAG_LAYOUT_STABLE;
                            decorView.setSystemUiVisibility(flags);
                        }
                    }
                }).build();

        adLoader.loadAds(new AdRequest.Builder().build(), countAdsToLoad);
    }

    public void loadNativeAd(int numberOfAdsToLoad) {
        final AdLoader.Builder builder = new AdLoader.Builder(this, ADMOB_AD_UNIT_ID);

        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        mNativeAds.add(unifiedNativeAd);

                        if (adLoader.isLoading()) {
                        } else {
                            System.out.println("AdLoader -------------------------------->");
                            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.containerView, new NewsFragment());
                            transaction.commit();

                            toolbar.setVisibility(View.VISIBLE);

                            View decorView = getWindow().getDecorView();
                            final int flags = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | SYSTEM_UI_FLAG_LAYOUT_STABLE;
                            decorView.setSystemUiVisibility(flags);
                        }
                    }
                }).withAdListener(new AdListener() {
                   @Override
                   public void onAdLoaded() {
                       super.onAdLoaded();
                       System.out.println("adListener ----------------------------------------->");
                   }
                }).build();

        for (int i = 0; i < numberOfAdsToLoad; ++i) {
            adLoader.loadAd(new AdRequest.Builder().build());
        }
    }
   */

    /*
    public void loadNativeAd(final AfterLoadTask task) {
        AdLoader.Builder builder = new AdLoader.Builder(this, ADMOB_AD_UNIT_ID);
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        mNativeAds.add(unifiedNativeAd);
                    }
                }).withAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                task.makeTask();
                // loadGroupAds();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }
   */

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }
}
