package belev.org.warface_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

public class NewsFragment extends ListFragment {
    public static final String URL_TO_HIT = "https://edgenews.ru/android/wardocwarface/news/news.json";
    android.widget.Adapter adapter;
    public static final String BUNDLE_TEXT = "bundle_text";
    public List<NewsModel> newsModelList = new ArrayList<>();
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