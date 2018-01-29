package belev.org.warface_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import belev.org.warface_app.R;

import java.util.ArrayList;
import java.util.List;

public class StremersFragment extends ListFragment {
    private Context mContext;

    private static final String BUNDLE_STREMERS = "bundle_stremers";
    private static final String BUNDLE_MAPS = "bundle_maps";
    private Integer achivmentid;
    private Integer mapsid;
    LayoutInflater inf;

    String[] name;
    String[] about;
    String[] type;
    TypedArray icons;

    android.widget.Adapter adapter;
    private List<Maps> rowItems;
    InterstitialAd mInterstitialAd;

    public static int[] name_arr = { R.array.stremers_name, R.array.stremers_name };
    public static int[] about_arr = { R.array.stremers_about, R.array.stremers_about };
    public static int[] type_arr = { R.array.stremers_type, R.array.stremers_type };
    public static int[] icon_arr = { R.array.stremers_icon, R.array.stremers_icon };
    private List<Maps> myAbout = new ArrayList<Maps>();
    public int classid;
    //private InterstitialAd interstitial;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Admod Interstitial
        //mInterstitialAd = new InterstitialAd(getContext());
        //mInterstitialAd.setAdUnitId(getString(R.string.interstitial_full_screen));
        //AdRequest adRequest = new AdRequest.Builder().build();
        //mInterstitialAd.loadAd(adRequest);

        if (getArguments() != null && getArguments().containsKey(BUNDLE_MAPS)) {
            mapsid = getArguments().getInt(BUNDLE_MAPS);
        }

        View view = inflater.inflate(R.layout.framelist_maps, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        listView.addHeaderView(new View(getContext()));
        listView.addFooterView(new View(getContext()));

        return view;
        //return inflater.inflate(R.layout.framelist_maps, null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        //String[] maps_array = getResources().getStringArray(link_arr[mapsid]);

        if (isOnline()) {

            //ShowInterstitial();

            Intent myIntent = new Intent(getActivity(), StremersActivity.class);
            myIntent.putExtra(BUNDLE_STREMERS, position-1);
            getActivity().startActivity(myIntent);

        } else {
            Intent myIntent = new Intent(getActivity(), ConnectionActivity.class);
            getActivity().startActivity(myIntent);
        }
    }


    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        name = getResources().getStringArray(name_arr[1]);
        about = getResources().getStringArray(about_arr[1]);
        type = getResources().getStringArray(type_arr[1]);
        //icons = getResources().obtainTypedArray(icon_arr[0]);
        TypedArray intarray = getResources().obtainTypedArray(icon_arr[1]);

        rowItems = new ArrayList<Maps>();

        for (int i = 0; i < name.length; i++ ) {

            //Drawable drawable = icons.getDrawable(i);
            rowItems.add(new Maps(name[i], about[i], type[i], intarray.getResourceId(i, -1)));
        }

        adapter = new MapsAdapter(getActivity(), rowItems);
        setListAdapter((ListAdapter) adapter);

    }

    private void ShowInterstitial() {
        if(mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //Toast.makeText(this, "Add did not loaded", Toast.LENGTH_LONG).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
