package belev.org.warface_app;

import android.util.Log;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import androidx.annotation.NonNull;

public class AdmobInterstitialAd {

    public String adId;
    public MainActivity mainActivity;
    public boolean isShow = true;
    public InterstitialAd mInterstitialAd;

    public static class Builder {
        AdmobInterstitialAd newAdmobInterstitialAd;

        public Builder() {
            newAdmobInterstitialAd = new AdmobInterstitialAd();
        }

        public Builder show(boolean isShow) {
            newAdmobInterstitialAd.isShow = isShow;
            return this;
        }

        public Builder activity(MainActivity mainActivity) {
            newAdmobInterstitialAd.mainActivity = mainActivity;
            return this;
        }

        public Builder testMode(boolean isTestMode) {
            if (isTestMode) {
                // MediationTestSuite.launch(newAdmobInterstitialAd.mainActivity);
            }
            return this;
        }

        public Builder adId(String adId) {
            newAdmobInterstitialAd.adId = adId;
            return this;
        }

        public AdmobInterstitialAd build() {
            return newAdmobInterstitialAd;
        }
    }

    public void show() {
        MobileAds.initialize(MyApplicationContext.getAppContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                if (isShow) {
                    loadAd();
                }
            }
        });
    }

    public void loadAd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(MyApplicationContext.getAppContext(), adId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;

                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.d("MyTag", "on ad failed to show full screen content");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        mInterstitialAd = null;
                        Log.d("MyTag", "on ad was shown");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        Log.d("MyTag", "on ad dissmissed full screen content");
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                        Log.d("MyTag", "on ad impression");
                    }
                });

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(mainActivity);
                } else {
                    Log.d("MyTag", "show admob interstitial null");
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                mInterstitialAd = null;
                Log.d("MyTag", "on ad failder to load");
            }
        });
    }

}
