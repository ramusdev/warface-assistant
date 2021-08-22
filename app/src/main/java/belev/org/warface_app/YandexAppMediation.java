package belev.org.warface_app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

public class YandexAppMediation implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private static String LOG_TAG = "MyTag";
    // private static String AD_UNIT_ID = "ca-app-pub-3940256099942544/3419835294";
    private static String AD_UNIT_ID = "ca-app-pub-4140002463111288/1805092950";
    // private final int TIME_FOR_AD_LOAD = 10000;
    private AppOpenAd appOpenAd = null;
    private AppOpenAd.AppOpenAdLoadCallback loadCallback;
    private final MyApplication myApplication;
    private Activity currentActivity;
    private static boolean isShowingAd = false;
    private long loadTime = 0;
    InterstitialAd mInterstitialAd;
    public static final String BLOCK_ID = "adf-327594/1201445";
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_DATE = "date";


    public YandexAppMediation(MyApplication myApplication) {
        this.myApplication = myApplication;
        this.myApplication.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    // Request an add
    public void fetchAd() {
        mInterstitialAd = new InterstitialAd(MyApplicationContext.getAppContext());
        mInterstitialAd.setBlockId(BLOCK_ID);

        final AdRequest adRequest = new com.yandex.mobile.ads.common.AdRequest.Builder().build();

        mInterstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {

            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {
            }
        });

        mInterstitialAd.loadAd(adRequest);
    }

    public boolean isInterstitialAllowed() {
        SharedPreferences sharedPreferences = MyApplicationContext.getAppContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        long dateTime = sharedPreferences.getLong(APP_PREFERENCES_DATE, 0);
        long currentDateTime = (new Date()).getTime();
        long dateDifference = currentDateTime - dateTime;
        long numMilliSecondsPerHour = 3600000;
        long hoursInDay = 24;
        long numMilliSecondsInDay = numMilliSecondsPerHour * hoursInDay;

        return dateDifference > numMilliSecondsInDay;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        Log.d(LOG_TAG, "Yandex app mediation: On start");
        MobileAds.initialize(MyApplicationContext.getAppContext(), new InitializationListener() {
            @Override
            public void onInitializationCompleted() {
                if (isInterstitialAllowed()) {
                    fetchAd();
                }
            }
        });
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        currentActivity = activity;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        currentActivity = null;
    }
}
