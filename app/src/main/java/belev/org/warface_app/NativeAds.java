package belev.org.warface_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import androidx.fragment.app.Fragment;

public class NativeAds extends Fragment {

    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private UnifiedNativeAd nativeAd;
    private TextView videoStatus;
    private Button refresh;
    private CheckBox startVideoAdsMuted;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final View view = layoutInflater.inflate(R.layout.fragment_nativeads, viewGroup, false);

        refresh = view.findViewById(R.id.btn_refresh);
        startVideoAdsMuted = view.findViewById(R.id.cb_start_muted);
        videoStatus = view.findViewById(R.id.tv_video_status);

        // Bundle customBundle = this.getArguments();
        // String jsonString = customBundle.getString("myObject");

        // Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        // Type nativeAdType = new TypeToken<ArrayList<UnifiedNativeAd>>(){}.getType();
        // List<UnifiedNativeAd> nativeAds = gson.fromJson(jsonString, nativeAdType);
        // NativeAd nativeAd = gson.fromJson(jsonString, NativeAd.class);
        // System.out.println("------------------------------------------------------------------>");
        // System.out.println(nativeAd.getUnifiedNativeAd().toString());

        // ((MainActivity) getActivity()).callFromFragment();

        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.valueOut = 1;
        mainActivity.valueIn = 2;

        System.out.println("--------------------------------------------------->");
        System.out.println(mainActivity.valueOut);
        System.out.println(mainActivity.valueIn);
        // System.out.println(mainActivity.mNativeAds.get(1).getResponseInfo());

        UnifiedNativeAd unifiedNativeAd = mainActivity.mNativeAds.get(1);

        FrameLayout frameLayout = view.findViewById(R.id.fl_adplaceholder);
        UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
        populateUnifiedNativeAdView(unifiedNativeAd, adView);
        frameLayout.removeAllViews();
        frameLayout.addView(adView);




        // FrameLayout frameLayout = view.findViewById(R.id.fl_adplaceholder);
        // frameLayout.removeAllViews();
        // frameLayout.addView(nativeAd.getUnifiedNativeAdView());

        // String str = nativeAd.getString();
        // Toast.makeText().show();

        /*
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View unusedView) {
                refreshAd(view);
            }
        });
        refreshAd(view);
        */

        return view;
    }

    public void refreshAd(final View view) {
        AdLoader.Builder builder = new AdLoader.Builder(getContext(), ADMOB_AD_UNIT_ID);

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }

                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout = view.findViewById(R.id.fl_adplaceholder);
                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);
            }

        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Toast.makeText(getContext(), "Failed to load native ad: " + errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view.
        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline and mediaContent are guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
            videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    videoStatus.setText("Video status: Video playback has ended.");
                    super.onVideoEnd();
                }
            });
        } else {
            videoStatus.setText("Video status: Ad does not contain a video asset.");
        }
    }

    @Override
    public void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
    }

}
