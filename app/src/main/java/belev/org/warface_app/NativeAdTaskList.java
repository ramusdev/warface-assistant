package belev.org.warface_app;

import android.content.Context;
import android.os.AsyncTask;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NativeAdTaskList extends AsyncTask<Void, Integer, String> {

    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private AdLoader adLoader;
    public List<UnifiedNativeAd> mNativeAds = new ArrayList<UnifiedNativeAd>();
    public Context context;

    public NativeAdTaskList() {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            System.out.println("Before ----------------------------------------------------->");
            Thread.sleep(5000);
            // TimeUnit.SECONDS.sleep(5);
            System.out.println("After ------------------------------------------------------->");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "All Done!";
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }
}
