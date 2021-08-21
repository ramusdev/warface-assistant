package belev.org.warface_app;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
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

public class AdditionalFragment extends ListFragment {
    private Context mContext;
    private static final String BUNDLE_LINK = "bundle_link";
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

    public final int[] name_arr = { R.array.additional_name };
    public final int[] about_arr = { R.array.additional_about };
    public final int[] type_arr = { R.array.additional_type };
    public final int[] icon_arr = { R.array.additional_icon };
    public final int[] link_arr = { R.array.additional_link };

    private List<Maps> myAbout = new ArrayList<Maps>();
    MainActivity mainActivity;
    boolean isLoadedAd;
    int mapsId = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.framelist_maps, container, false);
        ListView listView = (ListView) view.findViewById(android.R.id.list);

        mainActivity = (MainActivity) getActivity();
        listView.addHeaderView(new View(mainActivity.getApplicationContext()));
        listView.addFooterView(new View(mainActivity.getApplicationContext()));


        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String[] maps_array = getResources().getStringArray(link_arr[mapsId]);

        int positionShift = isLoadedAd ? position - 2 : position - 1;
        if (mainActivity.isOnline()) {
            Intent myIntent = new Intent(getActivity(), RiflemanWebActivity.class);
            myIntent.putExtra("BUNDLE_LINK", maps_array[positionShift]);
            myIntent.putExtra("BUNDLE_TITLE", getResources().getString(R.string.menu_additional));
            getActivity().startActivity(myIntent);
        } else {
            Intent myIntent = new Intent(getActivity(), ConnectionActivity.class);
            getActivity().startActivity(myIntent);
        }
    }

    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        name = getResources().getStringArray(name_arr[mapsId]);
        about = getResources().getStringArray(about_arr[mapsId]);
        type = getResources().getStringArray(type_arr[mapsId]);
        TypedArray intarray = getResources().obtainTypedArray(icon_arr[mapsId]);

        rowItems = new ArrayList<Maps>();

        for (int i = 0; i < name.length; i++ ) {

            //Drawable drawable = icons.getDrawable(i);
            rowItems.add(new Maps(name[i], about[i], type[i], intarray.getResourceId(i, -1)));
        }

        adapter = new RiflemanAdapter(getActivity(), rowItems);
        setListAdapter((ListAdapter) adapter);

    }
}
