package belev.org.warface_app;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import belev.org.warface_app.data.DataContract;
import belev.org.warface_app.data.DataDbHelper;

import static android.content.ContentValues.TAG;

public class NewsFragment extends ListFragment {
    public static final String URL_TO_HIT = "https://edgenews.ru/android/wardocwarface/news/news.json";
    android.widget.Adapter adapter;
    public static final String BUNDLE_TEXT = "bundle_text";
    // public List<NewsModel> newsModelList = new ArrayList<>();
    public List<News> newsArray = new ArrayList<News>();
    MainActivity mainActivity;
    boolean isLoadedAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.framelist_maps, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        View spaceView = new View(getContext());
        mainActivity = (MainActivity) getActivity();

        NativeAdPopulationSync nativeAdPopulationSync = new NativeAdPopulationSync(0);
        nativeAdPopulationSync.view = view;
        nativeAdPopulationSync.spaceView = spaceView;
        nativeAdPopulationSync.listView = listView;
        nativeAdPopulationSync.mainActivity = mainActivity;
        isLoadedAd = nativeAdPopulationSync.execute();

        // Load news
        NewsLoader newsLoader = new NewsLoader(mainActivity.getApplicationContext());
        newsArray = newsLoader.load();

        return view;
    }

    /*
    public void loadNews() {
        DataDbHelper dbHelper = new DataDbHelper(mainActivity.getApplicationContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = DataContract.NewsEntry.COLUMN_TITLE + " = ?";
        String[] selectionArgs = { "Title 10" };
        Cursor cursor = sqLiteDatabase.query(DataContract.NewsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            News news = new News();

            news.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_TITLE)));
            news.setPreviewText(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_PREVIEWTEXT)));
            news.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_TEXT)));
            news.setLink(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_LINK)));
            news.setImage(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_IMAGE)));
            news.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_DATE)));

            newsArray.add(news);
        }

        cursor.close();

        for (News news : newsArray) {
            Log.e("CustomLogTag", news.getTitle());
        }
    }
    */

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        int positionShift = isLoadedAd ? position - 2 : position - 1;
        if (mainActivity.isOnline()) {
            Intent myIntent = new Intent(getActivity(), NewsWebActivity.class);
            News currentNews = newsArray.get(positionShift);
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

        adapter = new NewsAdapter(getActivity(), newsArray);
        setListAdapter((ListAdapter) adapter);

        /*
        TaskInterface task = new TaskInterface() {
            @Override
            public void makeTask(List<News> newsArray) {
                adapter = new NewsAdapter(getActivity(), newsArray);
                setListAdapter((ListAdapter) adapter);
            }
        };

        if (mainActivity.newsList.size() == 0) {
            NewsLoader newsLoader = new NewsLoader(mainActivity, task);
            newsLoader.execute();
        } else {
            // adapter = new NewsAdapter(getActivity(), newsList);
            // setListAdapter((ListAdapter) adapter);
            task.makeTask(newsArray);
        }
        */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*
    interface TaskInterface {
        void makeTask(List<News> newsList);
    }
    */
}