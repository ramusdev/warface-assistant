package belev.org.warface_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();

        NewsLoader newsLoader = new NewsLoader(mainActivity);
        newsLoader.execute();

        mainActivity.loadGroupAds();

        return view;
    }
}