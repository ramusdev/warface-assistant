package belev.org.warface_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class SettingsTabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 2;
    private static final String BUNDLE_MAPS = "bundle_maps";
    private static final String BUNDLE_MAPS2 = "bundle_maps2";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        /**
         *Inflate tab_layout and setup Views.
         */
            View x =  inflater.inflate(R.layout.tab_layout, null);
            tabLayout = (TabLayout) x.findViewById(R.id.tabs);
            viewPager = (ViewPager) x.findViewById(R.id.viewpager);
            viewPager.setId(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

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

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
          // final MapsFragment fragment = new MapsFragment();
          // final Bundle arguments = new Bundle();

          switch (position){
              case 0 :
                  // arguments.putInt(BUNDLE_MAPS, 0);
                  // fragment.setArguments(arguments);

                  RewardedFragment rewardedFragment = new RewardedFragment();
                  return rewardedFragment;
              case 1 :
                  // arguments.putInt(BUNDLE_MAPS, 1);
                  // fragment.setArguments(arguments);

                  SettingsFragment settingsFragment = new SettingsFragment();
                  return settingsFragment;
          }
            return null;
        }

        @Override
        public int getCount() {
            return int_items;
        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public String getPageTitle(int position) {

            String textSettings = getResources().getString(R.string.menu_statistics);
            String textRewarded = getResources().getString(R.string.rewarded_tab);

            switch (position){
                case 0 :
                    return textRewarded;
                case 1 :
                    return textSettings;
            }

            return null;
        }
    }

}
