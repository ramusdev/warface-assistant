package belev.org.warface_app;

import android.content.Context;
import android.os.AsyncTask;

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

public class NewsLoader extends AsyncTask<Void, Void, List<NewsModel>> {

    public final String NEWS_URL = "https://edgenews.ru/android/wardocwarface/news/news.json";
    public List<NewsModel> newsModelList = new ArrayList<NewsModel>();
    private MainActivity mainActivity;
    private NewsFragment.TaskInterface task;

    public NewsLoader(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public NewsLoader(MainActivity mainActivity, NewsFragment.TaskInterface task) {
        this.mainActivity = mainActivity;
        this.task = task;
    }

    @Override
    protected List doInBackground(Void... voids) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(NEWS_URL);
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
    protected void onPostExecute(final List<News> result) {
        super.onPostExecute(result);
        mainActivity.newsList = result;

        if (task != null) {
            task.makeTask(result);
        }
    }
}
