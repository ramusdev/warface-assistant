package belev.org.warface_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

public class NativeAd {

    private UnifiedNativeAd unifiedNativeAd;

    public NativeAd() {

    }

    public NativeAd(UnifiedNativeAd unifiedNativeAd) {
        this.unifiedNativeAd = unifiedNativeAd;
    }

    public String getString() {
        return "This is string native ad";
    }

    public UnifiedNativeAd getUnifiedNativeAd() {
        return unifiedNativeAd;
    }

}
