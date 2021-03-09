package belev.org.warface_app;

import android.util.Log;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;

public class AppOpenManager {
    private static String LOG_TAG = "MyTag";
    private static String AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";
    private AppOpenAd appOpenAd = null;

    private AppOpenAd.AppOpenAdLoadCallback loadCallback;

    private final MyApplication myApplication;

    public AppOpenManager(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    // Request an add
    public void fetchAd() {
        if (isAdAvailable()) {
            return;
        }

        loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
            @Override
            public void onAdLoaded(AppOpenAd ad) {
                AppOpenManager.this.appOpenAd = ad;
            }

            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                Log.d("MyTag", "AppOpenManager: Ad failed to load");
            }
        };

        AdRequest request = getAdRequest();
        AppOpenAd.load(myApplication, AD_UNIT_ID, request, AppOpenAd.APP_OPEN_AD_ORIENTATION_PORTRAIT, loadCallback);
    }

    // Creates and returns ad request
    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    // Utility that checks if ad exists and can be shown
    public boolean isAdAvailable() {
        return appOpenAd != null;
    }
}
