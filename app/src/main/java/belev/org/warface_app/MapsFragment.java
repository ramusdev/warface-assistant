package belev.org.warface_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.ListFragment;

public class MapsFragment extends ListFragment {
    private Context mContext;

    private static final String BUNDLE_LINK = "bundle_link";
    private static final String BUNDLE_MAPS = "bundle_maps";
    InterstitialAd mInterstitialAd;
    private Integer achivmentid;
    private Integer mapsid;
    LayoutInflater inf;

    String[] name;
    String[] about;
    String[] type;
    TypedArray icons;

    android.widget.Adapter adapter;
    private List<Maps> rowItems;

    public static int[] name_arr = { R.array.spec_name, R.array.domin_name, R.array.podriv_name, R.array.komanda_name, R.array.mysorubka_name, R.array.unicht_name, R.array.zahvat_name, R.array.shturm_name, R.array.vizivan_name, R.array.blic_name, R.array.king_name };
    public static int[] about_arr = { R.array.spec_about, R.array.domin_about, R.array.podriv_about, R.array.komanda_about, R.array.mysorubka_about, R.array.unicht_about, R.array.zahvat_about, R.array.shturm_about, R.array.vizivan_about, R.array.blic_about, R.array.king_about };
    public static int[] type_arr = { R.array.spec_type, R.array.domin_type, R.array.podriv_type, R.array.komanda_type, R.array.mysorubka_type, R.array.unicht_type, R.array.zahvat_type, R.array.shturm_type, R.array.vizivan_type, R.array.blic_type, R.array.king_type };
    public static int[] icon_arr = { R.array.spec_icon, R.array.domin_icon, R.array.podriv_icon, R.array.komanda_icon, R.array.mysorubka_icon, R.array.unicht_icon, R.array.zahvat_icon, R.array.shturm_icon, R.array.vizivan_icon, R.array.blic_icon, R.array.king_icon };
    public static int[] link_arr = { R.array.spec_link, R.array.domin_link, R.array.podriv_link, R.array.komanda_link, R.array.mysorubka_link, R.array.unicht_link, R.array.zahvat_link, R.array.shturm_link, R.array.vizivan_link, R.array.blic_link, R.array.king_link };

    private List<Maps> myAbout = new ArrayList<Maps>();
    public int classid;
    MainActivity mainActivity;
    boolean isLoadedAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null && getArguments().containsKey(BUNDLE_MAPS)) {
            mapsid = getArguments().getInt(BUNDLE_MAPS);
        }

        View view = inflater.inflate(R.layout.framelist_maps, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);
        View spaceView = new View(getContext());
        mainActivity = (MainActivity) getActivity();

        NativeAdPopulationSync nativeAdPopulationSync = new NativeAdPopulationSync(2);
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
        String[] maps_array = getResources().getStringArray(link_arr[mapsid]);

        int positionShift = isLoadedAd ? position - 2 : position - 1;
        if (mainActivity.isOnline()) {
            Intent myIntent = new Intent(getActivity(), MapsWebActivity.class);
            myIntent.putExtra("BUNDLE_LINK", maps_array[positionShift]);
            getActivity().startActivity(myIntent);

        } else {
            Intent myIntent = new Intent(getActivity(), ConnectionActivity.class);
            getActivity().startActivity(myIntent);
        }
    }


    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        name = getResources().getStringArray(name_arr[mapsid]);
        about = getResources().getStringArray(about_arr[mapsid]);
        type = getResources().getStringArray(type_arr[mapsid]);
        //icons = getResources().obtainTypedArray(icon_arr[0]);
        TypedArray intarray = getResources().obtainTypedArray(icon_arr[mapsid]);

        rowItems = new ArrayList<Maps>();

        for (int i = 0; i < name.length; i++ ) {

            //Drawable drawable = icons.getDrawable(i);
            rowItems.add(new Maps(name[i], about[i], type[i], intarray.getResourceId(i, -1)));
        }

        adapter = new MapsAdapter(getActivity(), rowItems);
        setListAdapter((ListAdapter) adapter);

    }
}
