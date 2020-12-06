package belev.org.warface_app;

import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import androidx.fragment.app.FragmentTransaction;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class NativeAdLoader {

    private final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private MainActivity mainActivity;
    private int numberOfAdToLoad;
    private AdLoader adLoader;
    private boolean isAlive = true;

    public NativeAdLoader(MainActivity mainActivity, int numberOfAdToLoad) {
        this.mainActivity = mainActivity;
        this.numberOfAdToLoad = numberOfAdToLoad;
    }

    public void execute() {
        final AdLoader.Builder builder = new AdLoader.Builder(mainActivity.getApplicationContext(), ADMOB_AD_UNIT_ID);

        // final AdLoader adLoader = null;
        // final AdLoader finalAdLoader = adLoader;
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        mainActivity.mNativeAds.add(unifiedNativeAd);

                        // if (adLoader.isLoading()) {
                        // } else {
                            // System.out.println("AdLoader -------------------------------->");
                            // FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            // transaction.replace(R.id.containerView, new NewsFragment());
                            // transaction.commit();

                            // toolbar.setVisibility(View.VISIBLE);

                            // View decorView = getWindow().getDecorView();
                            // final int flags = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    // | SYSTEM_UI_FLAG_LAYOUT_STABLE;
                            // decorView.setSystemUiVisibility(flags);
                        // }
                    }
                }).withAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                isAlive = false;
                System.out.println("Ad end load ----------------------------------------->");
            }
        }).build();

        // for (int i = 0; i < numberOfAdsToLoad; ++i) {
        adLoader.loadAd(new AdRequest.Builder().build());
        // }
    }
}
