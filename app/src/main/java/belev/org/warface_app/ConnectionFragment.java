package belev.org.warface_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import belev.org.warface_app.R;

public class ConnectionFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_connection, container, false);
        //TextView textView = (TextView) view.findViewById(R.id.start_textView);
        //String text = getResources().getString(R.string.start_text);
        //textView.setText(Html.fromHtml(text));

        return view;
    }
}
