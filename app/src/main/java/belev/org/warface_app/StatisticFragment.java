package belev.org.warface_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import belev.org.warface_app.R;

public class StatisticFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        //TextView textView = (TextView) view.findViewById(R.id.start_textView);
        //String text = getResources().getString(R.string.start_text);
        //textView.setText(Html.fromHtml(text));
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);
        searchView.setIconified(false);
        searchView.setFocusable(false);
        searchView.onActionViewExpanded();
        searchView.requestFocusFromTouch();

        return view;
    }
}
