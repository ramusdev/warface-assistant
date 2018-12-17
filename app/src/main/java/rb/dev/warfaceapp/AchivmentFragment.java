package rb.dev.warfaceapp;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.List;

public class AchivmentFragment extends ListFragment {

	private static final String BUNDLE_ACHIVMENT = "bundle_achivment";
	private Integer achivmentid;
	
    String[] name;
    String[] about;
    TypedArray icons;
	//int[] intarray;

    Adapter adapter;
    private List<Achivment> rowItems;

	public static int[] name_arr = { R.array.mark_name, R.array.badge_name, R.array.strip_name };

	public static int[] about_arr = {R.array.mark_about, R.array.badge_about, R.array.strip_about };

	public static int[] icon_arr = { R.array.mark_icon, R.array.badge_icon, R.array.strip_icon };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
		 if (getArguments() != null && getArguments().containsKey(BUNDLE_ACHIVMENT)) {
			 achivmentid = getArguments().getInt(BUNDLE_ACHIVMENT);
		 }
        
        return inflater.inflate(R.layout.framelist, null, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
		int[] covers = new int[]{
				R.drawable.badge_2,
				R.drawable.badge_3,
				R.drawable.badge_4,
				R.drawable.badge_5,
				R.drawable.badge_6,
				R.drawable.badge_7,
				R.drawable.badge_8,
				R.drawable.badge_9,
				R.drawable.badge_10,
				R.drawable.badge_11,
				R.drawable.badge_12,
				R.drawable.badge_13,
				R.drawable.badge_14,
				R.drawable.badge_15,
				R.drawable.badge_16,
		};

        super.onActivityCreated(savedInstanceState);
        
		name = getResources().getStringArray(name_arr[achivmentid]);
		about = getResources().getStringArray(about_arr[achivmentid]);
		//icons = getResources().obtainTypedArray(icon_arr[achivmentid]);
		TypedArray intarray = getResources().obtainTypedArray(icon_arr[achivmentid]);

        rowItems = new ArrayList<Achivment>();

		for(int i = 0; i < name.length; i++ ) {

			//Log.v (TAG, "Res Id " + i + " is " + Integer.toHexString(intarray[i]));
			//intarray = icons.getInteger(0,0);
			//int imageResource = getResources().getIdentifier(intarray[i], null, getContext().getPackageName());
			//Drawable drawable = icons.getDrawable(i);
			//int idres = icons.getIndex(i);
			//int int2 = Integer.decode("R.drawable.badge_16");
			//intarray.getResourceId(i, -1);
			rowItems.add(new Achivment(name[i], about[i], achivmentid, intarray.getResourceId(i, -1)));
		}

		adapter = new rb.dev.warfaceapp.Adapter(getActivity(), rowItems);
		setListAdapter((ListAdapter) adapter);

    }
}