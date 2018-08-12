package belev.org.warface_app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import belev.org.warface_app.R;

public class StartFragment extends Fragment {

    Uri link;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_start, container, false);
        TextView textView = (TextView) view.findViewById(R.id.start_textView);
        String text = getResources().getString(R.string.start_text);
        textView.setText(Html.fromHtml(text));

        /*
        ImageView imageView = (ImageView) view.findViewById(R.id.start_imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    link = Uri.parse("https://www.youtube.com/watch?v=V788Wa7CeBE");
                    Intent intent = new Intent(Intent.ACTION_VIEW, link);
                    startActivity(intent);
                } else {
                    Intent myIntent = new Intent(getActivity(), ConnectionActivity.class);
                    getActivity().startActivity(myIntent);
                }
            }
        });
        */

        return view;
    }

}
