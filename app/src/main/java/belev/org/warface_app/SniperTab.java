package belev.org.warface_app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import belev.org.warface_app.R;

public class SniperTab extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;
    private static final String BUNDLE_MAPS = "bundle_maps";
    private static final String BUNDLE_MAPS2 = "bundle_maps2";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View x =  inflater.inflate(R.layout.tab_maps, null);
        tabLayout = (TabLayout) x.findViewById(R.id.tabs);
        viewPager = (ViewPager) x.findViewById(R.id.viewpager);
        viewPager.setId(R.id.viewpager);

        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                    tabLayout.setupWithViewPager(viewPager);
                   }
        });

        return x;
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {

          final SniperFragment fragment = new SniperFragment();
          final Bundle arguments = new Bundle();

          switch (position){
              case 0 :
                      arguments.putInt(BUNDLE_MAPS, 0);
                      fragment.setArguments(arguments);
                      return fragment;
              case 1 :
                      arguments.putInt(BUNDLE_MAPS, 1);
                      fragment.setArguments(arguments);
                      return fragment;
              case 2 :
                      arguments.putInt(BUNDLE_MAPS, 2);
                      fragment.setArguments(arguments);
                      return fragment;
          }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            String warbaks = getResources().getString(R.string.menu_warbaks);
            String credits = getResources().getString(R.string.menu_credits);
            String box = getResources().getString(R.string.menu_box);

            switch (position){
                case 0 :
                    return warbaks;
                case 1 :
                    return credits;
                case 2 :
                    return box;
            }
            return null;
        }
    }
}
