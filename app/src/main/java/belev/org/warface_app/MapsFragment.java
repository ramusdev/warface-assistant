package belev.org.warface_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class MapsFragment extends ListFragment {
    private Context mContext;

    private static final String BUNDLE_LINK = "bundle_link";
    private static final String BUNDLE_MAPS = "bundle_maps";
    InterstitialAd mInterstitialAd;
    private Integer achivmentid;
    private Integer mapsid;
    LayoutInflater inf;

    String[] name;
    String[] about;
    String[] type;
    TypedArray icons;

    android.widget.Adapter adapter;
    private List<Maps> rowItems;

    public static int[] name_arr = { R.array.spec_name, R.array.domin_name, R.array.podriv_name, R.array.komanda_name, R.array.mysorubka_name, R.array.unicht_name, R.array.zahvat_name, R.array.shturm_name, R.array.vizivan_name, R.array.blic_name, R.array.king_name };
    public static int[] about_arr = { R.array.spec_about, R.array.domin_about, R.array.podriv_about, R.array.komanda_about, R.array.mysorubka_about, R.array.unicht_about, R.array.zahvat_about, R.array.shturm_about, R.array.vizivan_about, R.array.blic_about, R.array.king_about };
    public static int[] type_arr = { R.array.spec_type, R.array.domin_type, R.array.podriv_type, R.array.komanda_type, R.array.mysorubka_type, R.array.unicht_type, R.array.zahvat_type, R.array.shturm_type, R.array.vizivan_type, R.array.blic_type, R.array.king_type };
    public static int[] icon_arr = { R.array.spec_icon, R.array.domin_icon, R.array.podriv_icon, R.array.komanda_icon, R.array.mysorubka_icon, R.array.unicht_icon, R.array.zahvat_icon, R.array.shturm_icon, R.array.vizivan_icon, R.array.blic_icon, R.array.king_icon };
    public static int[] link_arr = { R.array.spec_link, R.array.domin_link, R.array.podriv_link, R.array.komanda_link, R.array.mysorubka_link, R.array.unicht_link, R.array.zahvat_link, R.array.shturm_link, R.array.vizivan_link, R.array.blic_link, R.array.king_link };

    private List<Maps> myAbout = new ArrayList<Maps>();
    public int classid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey(BUNDLE_MAPS)) {
            mapsid = getArguments().getInt(BUNDLE_MAPS);
        }

        View view = inflater.inflate(R.layout.framelist_maps, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        View spaceView = new View(getContext());
        MainActivity mainActivity = (MainActivity) getActivity();

        // showAdd(view, listView, spaceView);
        NativeAdPopulation nativeAdPopulation = new NativeAdPopulation();
        nativeAdPopulation.view = view;
        nativeAdPopulation.spaceView = spaceView;
        nativeAdPopulation.listView = listView;
        nativeAdPopulation.mainActivity = mainActivity;
        nativeAdPopulation.execute();

        return view;
    }

    public void showAdd(View view, ListView listView, View spaceView) {
        MainActivity mainActivity = (MainActivity) getActivity();
        UnifiedNativeAd unifiedNativeAd = mainActivity.mNativeAds.get(0);

        View headerView = getLayoutInflater().inflate(R.layout.fragment_news_header, listView, false);
        FrameLayout frameLayout = headerView.findViewById(R.id.fl_adplaceholder);
        UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);
        populateUnifiedNativeAdView(unifiedNativeAd, adView);
        frameLayout.removeAllViews();
        frameLayout.addView(adView);

        // listView.removeHeaderView(spaceView);
        // listView.addHeaderView(new View(getContext()));
        listView.addHeaderView(headerView, null, false);
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
            // videoStatus.setText(String.format(Locale.getDefault(),
            // "Video status: Ad contains a %.2f:1 video asset.",
            // vc.getAspectRatio()));

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                    // videoStatus.setText("Video status: Video playback has ended.");
                    super.onVideoEnd();
                }
            });
        } else {
            // videoStatus.setText("Video status: Ad does not contain a video asset.");
        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String[] maps_array = getResources().getStringArray(link_arr[mapsid]);

        if (isOnline()) {
            Intent myIntent = new Intent(getActivity(), MapsWebActivity.class);
            myIntent.putExtra("BUNDLE_LINK", maps_array[position-1]);
            getActivity().startActivity(myIntent);

        } else {
            Intent myIntent = new Intent(getActivity(), ConnectionActivity.class);
            getActivity().startActivity(myIntent);
        }
    }


    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        name = getResources().getStringArray(name_arr[mapsid]);
        about = getResources().getStringArray(about_arr[mapsid]);
        type = getResources().getStringArray(type_arr[mapsid]);
        //icons = getResources().obtainTypedArray(icon_arr[0]);
        TypedArray intarray = getResources().obtainTypedArray(icon_arr[mapsid]);

        rowItems = new ArrayList<Maps>();

        for (int i = 0; i < name.length; i++ ) {

            //Drawable drawable = icons.getDrawable(i);
            rowItems.add(new Maps(name[i], about[i], type[i], intarray.getResourceId(i, -1)));
        }

        adapter = new MapsAdapter(getActivity(), rowItems);
        setListAdapter((ListAdapter) adapter);

    }

    /*
    private void ShowInterstitial() {
        if(mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Toast.makeText(this, "Add did not loaded", Toast.LENGTH_LONG).show();
        }
    }
    */

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
