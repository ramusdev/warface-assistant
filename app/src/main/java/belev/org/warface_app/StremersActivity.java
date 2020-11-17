package belev.org.warface_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class StremersActivity extends AppCompatActivity {

    public static String[] stremers_arr = { "https://edgenews.ru/android/wardocwarface/stremers/elez.json",
                                            "https://edgenews.ru/android/wardocwarface/stremers/warface.json",
                                            "https://edgenews.ru/android/wardocwarface/stremers/mcserega.json",
                                            "https://edgenews.ru/android/wardocwarface/stremers/razortv.json",
                                            "https://edgenews.ru/android/wardocwarface/stremers/monter.json",
                                            "https://edgenews.ru/android/wardocwarface/stremers/donic.json",
                                            "https://edgenews.ru/android/wardocwarface/stremers/dmitriykr.json" };

    private static final String BUNDLE_STREMERS = "bundle_stremers";
    private Integer stremersID;
    android.widget.Adapter adapter;
    public List<NewsModel> newsModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bar));
            window.setNavigationBarColor(this.getResources().getColor(R.color.bar));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.menu_vloger));
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        ListView listView = (ListView)findViewById(R.id.listView_news);
        listView.addHeaderView(new View(getApplicationContext()));
        listView.addFooterView(new View(getApplicationContext()));

        Intent intent = getIntent();
        stremersID = intent.getIntExtra(BUNDLE_STREMERS, 0);

        new JSONTask().execute(stremers_arr[stremersID]);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isOnline()) {
                    NewsModel currentNews = newsModelList.get(position-1);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentNews.getLink()));
                    startActivity(intent);

                } else {
                    Intent intent = new Intent(getApplicationContext(), ConnectionActivity.class);
                    startActivity(intent);
                }
            }
        });

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
                StremersAdapter adapter = new StremersAdapter(getApplicationContext(), result);
                ListView listView = (ListView)findViewById(R.id.listView_news);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(), "Ошибка разбора json", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}