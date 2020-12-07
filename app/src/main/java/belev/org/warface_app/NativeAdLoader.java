package belev.org.warface_app;

import android.os.AsyncTask;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import java.util.ArrayList;
import java.util.List;

public class NativeAdLoader extends AsyncTask<Void, Void, Void> {

    // Test ad
    // private final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";

    // Current ad
    private final String ADMOB_AD_UNIT_ID = "ca-app-pub-4140002463111288/5408922979";

    private MainActivity mainActivity;
    private int numberAdsToLoad;
    private AfterLoadFunction afterLoadFunction;
    private List<NativeAdLoader.NativeAd> nativeAdsList;

    public NativeAdLoader(MainActivity mainActivity, int numberAdsToLoad, AfterLoadFunction afterLoadFunction) {
        this.mainActivity = mainActivity;
        this.numberAdsToLoad = numberAdsToLoad;
        this.afterLoadFunction = afterLoadFunction;
    }

    public void loadAds() {
        nativeAdsList = new ArrayList<NativeAdLoader.NativeAd>();

        for (int i = 0; i < numberAdsToLoad; ++i) {
            NativeAd nativeAd = new NativeAd();
            nativeAd.load();
            nativeAdsList.add(nativeAd);
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {
        boolean isThreadAlive;
        do {
            isThreadAlive = false;
            for (int i = 0; i < numberAdsToLoad; ++i) {
                boolean alive = nativeAdsList.get(i).getIsAlive();
                if (alive) {
                    isThreadAlive = true;
                }
            }
            System.out.println("Is loaded --------------------------------------------->");
        } while (isThreadAlive);

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        afterLoadFunction.execute();
    }

    class NativeAd {

        private boolean isAlive = true;
        private AdLoader adLoader;

        public NativeAd() {
        }

        public boolean getIsAlive() {
            return isAlive;
        }

        public void load() {
            final AdLoader.Builder builder = new AdLoader.Builder(mainActivity.getApplicationContext(), ADMOB_AD_UNIT_ID);

            adLoader = builder.forUnifiedNativeAd(
                    new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            mainActivity.mNativeAds.add(unifiedNativeAd);
                        }
                    }).withAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                            isAlive = false;
                         }
                    }).build();

            adLoader.loadAd(new AdRequest.Builder().build());
        }
    }
}
