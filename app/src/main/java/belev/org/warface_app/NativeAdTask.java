package belev.org.warface_app;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import java.util.ArrayList;
import java.util.List;

public class NativeAdTask extends AsyncTask<Void, Integer, String> {

    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private AdLoader adLoader;
    public List<UnifiedNativeAd> mNativeAds = new ArrayList<UnifiedNativeAd>();
    public Context context;

    public NativeAdTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            AdLoader.Builder builder = new AdLoader.Builder(context, ADMOB_AD_UNIT_ID);

            // Thread.sleep(50);
            adLoader = builder.forUnifiedNativeAd(
                    new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                        @Override
                        public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                            mNativeAds.add(unifiedNativeAd);
                            System.out.println("Async load ad ---------------------------------------->");

                            if (adLoader.isLoading()) {
                            } else {
                                // System.out.println("! adLoader.isLoading() ---------------------------------------->");
                            }

                        }
                    }).build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // adLoader.loadAds(new AdRequest.Builder().build(), 5);

        return "All Done!";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Log.d("On post execute","On post execute -------------------------------->");

        adLoader.loadAd(new AdRequest.Builder().build());
        // adLoader.loadAds(new AdRequest.Builder().build(), 5);

    }
}
