package belev.org.warface_app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
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
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class NewsFragment extends ListFragment {
    public static final String URL_TO_HIT = "https://edgenews.ru/android/wardocwarface/news/news.json";
    android.widget.Adapter adapter;
    public static final String BUNDLE_TEXT = "bundle_text";
    public List<NewsModel> newsModelList = new ArrayList<>();
    InterstitialAd mInterstitialAd;
    // List<NewsModel> newsList;
    MainActivity mainActivity;
    boolean isLoadedAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framelist_maps, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        View spaceView = new View(getContext());
        mainActivity = (MainActivity) getActivity();
        // newsList = mainActivity.newsList;

        NativeAdPopulationSync nativeAdPopulationSync = new NativeAdPopulationSync(0);
        nativeAdPopulationSync.view = view;
        nativeAdPopulationSync.spaceView = spaceView;
        nativeAdPopulationSync.listView = listView;
        nativeAdPopulationSync.mainActivity = mainActivity;
        isLoadedAd = nativeAdPopulationSync.execute();

        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        int positionShift = isLoadedAd ? position - 2 : position - 1;
        if (mainActivity.isOnline()) {
            Intent myIntent = new Intent(getActivity(), NewsWebActivity.class);
            NewsModel currentNews = mainActivity.newsList.get(positionShift);
            myIntent.putExtra("BUNDLE_TEXT", currentNews.getText());
            getActivity().startActivity(myIntent);
        } else {
            Intent myIntent = new Intent(getActivity(), ConnectionActivity.class);
            getActivity().startActivity(myIntent);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TaskInterface task = new TaskInterface() {
            @Override
            public void makeTask(List<NewsModel> newsList) {
                adapter = new NewsAdapter(getActivity(), newsList);
                setListAdapter((ListAdapter) adapter);
            }
        };

        if (mainActivity.newsList.size() == 0) {
            NewsLoader newsLoader = new NewsLoader(mainActivity, task);
            newsLoader.execute();
        } else {
            // adapter = new NewsAdapter(getActivity(), newsList);
            // setListAdapter((ListAdapter) adapter);
            task.makeTask(mainActivity.newsList);
        }
    }

    /*
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
    */

    @Override
    public void onDestroy() {
        // if (nativeAd != null) {
            // nativeAd.destroy();
        // }
        super.onDestroy();
        // disposable.dispose();
    }

    interface TaskInterface {
        void makeTask(List<NewsModel> newsList);
    }
}