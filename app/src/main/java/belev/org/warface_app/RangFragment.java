package belev.org.warface_app;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import java.util.ArrayList;
import java.util.List;
import androidx.fragment.app.ListFragment;

public class RangFragment extends ListFragment {

    private static final String BUNDLE_ACHIVMENT = "bundle_achivment";
    private Integer achivmentid;

    String[] name;
    String[] about;
    TypedArray icons;

    android.widget.Adapter adapter;
    private List<Achivment> rowItems;

    public static int[] name_arr = { R.array.rang_name,  R.array.rang_name };

    public static int[] about_arr = { R.array.rang_about, R.array.rang_about };

    public static int[] icon_arr = { R.array.rang_icon, R.array.rang_icon };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        achivmentid = 1;
        return inflater.inflate(R.layout.framelist, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        name = getResources().getStringArray(name_arr[achivmentid]);
        about = getResources().getStringArray(about_arr[achivmentid]);
        //icons = getResources().obtainTypedArray(icon_arr[achivmentid]);
        TypedArray intarray = getResources().obtainTypedArray(icon_arr[achivmentid]);

        rowItems = new ArrayList<Achivment>();

        for(int i = 0; i < name.length; i++ ) {

            //Drawable drawable = icons.getDrawable(i);
            rowItems.add(new Achivment(name[i], about[i], achivmentid, intarray.getResourceId(i, -1)));
        }

        adapter = new Adapter(getActivity(), rowItems);
        setListAdapter((ListAdapter) adapter);

    }
}