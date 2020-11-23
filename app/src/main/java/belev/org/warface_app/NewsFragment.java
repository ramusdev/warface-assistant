package belev.org.warface_app;

import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.Toast;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class NewsFragment extends ListFragment {
    public static final String URL_TO_HIT = "https://edgenews.ru/android/wardocwarface/news/news.json";
    android.widget.Adapter adapter;
    public static final String BUNDLE_TEXT = "bundle_text";
    public List<NewsModel> newsModelList = new ArrayList<>();
    InterstitialAd mInterstitialAd;

    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";
    private UnifiedNativeAd nativeAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framelist_maps, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);

        // View headerView = getLayoutInflater().inflate(R.layout.fragment_news_header, listView, false);
        View spaceView = new View(getContext());
        // listView.addHeaderView(spaceView);
        // listView.addFooterView(new View(getContext()));
        // listView.addHeaderView(headerView, null, false);
        // refreshAd(headerView, listView, headerView, spaceView);

        showAdd(view, listView, spaceView);

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

    public void refreshAd(final View view, final ListView listView, final View headerView, final View spaceView) {
        // AdLoader.Builder adLoader = new AdLoader.Builder(getContext(), ADMOB_AD_UNIT_ID);
        // final AdLoader adLoader = new AdLoader.Builder(getContext(), ADMOB_AD_UNIT_ID)
            // .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            // @Override
            // public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {

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

                System.out.println("Async load ad ---------------------------------------->");

                // if (adLoader.isLoading()) {
                    // Ads still loading
                // } else {
                    // ListView listView = (ListView) view.findViewById(android.R.id.list);
                // }

            }

        });

        AdLoader adLoader = builder.withAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                listView.removeHeaderView(spaceView);
                listView.addHeaderView(new View(getContext()));
                listView.addHeaderView(headerView, null, false);
            }
        }).build();

        // adLoader.loadAd(new AdRequest.Builder().build());
        adLoader.loadAds(new AdRequest.Builder().build(), 5);

        // adLoader.loadAd(new AdRequest.Builder().build());

        // AdLoader adLoader = builder.withAdListener(new AdListener() {
            // @Override
            // public void onAdFailedToLoad(int errorCode) {
                // Toast.makeText(getContext(), "Failed to load native ad: " + errorCode, Toast.LENGTH_SHORT).show();
            // }
        // }).build();

        // builder.loadAd(new AdRequest.Builder().build());
        // ListView listView = (ListView) view.findViewById(android.R.id.list);

        // if (adLoader.isLoading()) {
        // } else {
            // listView.addHeaderView(headerView, null, false);
        // }

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

        Intent myIntent = new Intent(getActivity(), NewsWebActivity.class);
        NewsModel currentNews = newsModelList.get(position-1);
        myIntent.putExtra("BUNDLE_TEXT", currentNews.getText());
        getActivity().startActivity(myIntent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new JSONTask().execute(URL_TO_HIT);
    }

    public class JSONTask extends AsyncTask<String, String, List<NewsModel> > {

        @Override
        protected List doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("movies");

                //List<NewsModel> newsModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    NewsModel newsModel = gson.fromJson(finalObject.toString(), NewsModel.class);
                    newsModelList.add(newsModel);
                }
                return newsModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null)
                        reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<NewsModel> result) {
            super.onPostExecute(result);
            if(result != null) {
                adapter = new NewsAdapter(getActivity(), result);
                setListAdapter((ListAdapter) adapter);
            } else {
                Toast.makeText(getActivity(), "Error Json Reader", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showInterstitial() {
        if(mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            // Toast.makeText(this, "Add did not loaded", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
        super.onDestroy();
        // disposable.dispose();
    }
}