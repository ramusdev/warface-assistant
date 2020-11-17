package belev.org.warface_app;

import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class StartFragment extends Fragment {

    Uri link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_start, container, false);
        TextView textView = (TextView) view.findViewById(R.id.start_textView);
        String text = getResources().getString(R.string.start_text);
        textView.setText(Html.fromHtml(text));

        return view;
    }

}
