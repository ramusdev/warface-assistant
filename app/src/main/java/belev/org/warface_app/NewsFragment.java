package belev.org.warface_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
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

public class NewsFragment extends ListFragment {
    private final String URL_TO_HIT = "http://belev.org/android/wardocwarface/news/news.json";
    android.widget.Adapter adapter;
    private static final String BUNDLE_TEXT = "bundle_text";
    InterstitialAd mInterstitialAd;
    public List<NewsModel> newsModelList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        //return inflater.inflate(R.layout.fragment_news, container, false);

        // Admod Interstitial
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);

        View view = inflater.inflate(R.layout.framelist_maps, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);

        //NativeExpressAdView adView = (NativeExpressAdView) findViewById(R.id.adView);
        //adView.loadAd(new AdRequest.Builder().build());

        // Inflate header view
        //getActivity().getLayoutInflater()
        //ViewGroup headerView = (ViewGroup)view(R.layout.header, listView,false);
        //listView.addHeaderView(headerView);

        //View headerView = getActivity().getLayoutInflater().inflate(R.layout.header, null);
        //getListView().addHeaderView(headerView);

        //AdSize.FULL_WIDTH

        //NativeExpressAdView mAdmobNativeExpressAdview = new NativeExpressAdView(getActivity());;
        //mAdmobNativeExpressAdview.setAdUnitId(getString(R.string.ad_unit_id));
        //mAdmobNativeExpressAdview.setId(R.id.adView);
        //mAdmobNativeExpressAdview.setAdSize(AdSize.FLUID);
        //mAdmobNativeExpressAdview.setAdSize(new AdSize(320, 150));
        //listView.addHeaderView(mAdmobNativeExpressAdview);

        //TextView tv = new TextView(getActivity());
        //tv.setText("Hello");
        //listView.addHeaderView(tv);

        //NativeExpressAdView adView = (NativeExpressAdView) view.findViewById(R.id.adView);
        //adView.loadAd(new AdRequest.Builder().build());
        //listView.addHeaderView(adView);

        listView.addHeaderView(new View(getContext()));
        listView.addFooterView(new View(getContext()));

        //View header = LayoutInflater.from(getContext()).inflate(R.layout.header, null, false);
        //listView.addHeaderView(header);

        //listView.addFooterView(new View(getContext()));

        //NativeExpressAdView adView = (NativeExpressAdView) view.findViewById(R.id.adView);
        //adView.loadAd(new AdRequest.Builder().build());

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent myIntent = new Intent(getActivity(), NewsWebActivity.class);
        NewsModel currentNews = newsModelList.get(position-1);
        myIntent.putExtra("BUNDLE_TEXT", currentNews.getText());
        getActivity().startActivity(myIntent);

        ShowInterstitial();
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
                //NewsAdapter adapter = new NewsAdapter(getApplicationContext(), R.layout.row, result);
                //listView.setAdapter(adapter);

                adapter = new NewsAdapter(getActivity(), result);
                setListAdapter((ListAdapter) adapter);

            } else {
                Toast.makeText(getActivity(), "Ошибка разбора json", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ShowInterstitial() {
        if(mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Toast.makeText(this, "Add did not loaded", Toast.LENGTH_LONG).show();
        }
    }
}