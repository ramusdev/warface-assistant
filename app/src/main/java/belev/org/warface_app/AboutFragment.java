package belev.org.warface_app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView textView = (TextView) view.findViewById(R.id.about_textView);
        String text = getResources().getString(R.string.about_text);
        textView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        textView.setText(Html.fromHtml(text));

        return view;
    }
}