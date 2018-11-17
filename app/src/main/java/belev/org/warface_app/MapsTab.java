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

/**
 * Created by Ratan on 7/27/2015.
 */
public class MapsTab extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 11;
    private static final String BUNDLE_MAPS = "bundle_maps";
    private static final String BUNDLE_MAPS2 = "bundle_maps2";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        /**
         *Inflate tab_layout and setup Views.
         */
            View x =  inflater.inflate(R.layout.tab_maps, null);
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
        public Fragment getItem(int position)
        {

          final MapsFragment fragment = new MapsFragment();
          final Bundle arguments = new Bundle();

        //Fragment webFragment = new DemoWebFragment();
        //FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        //transaction.add(R.id.containerView, webFragment).commit();

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
              case 3 :
                      arguments.putInt(BUNDLE_MAPS, 3);
                      fragment.setArguments(arguments);
                      return fragment;
              case 4 :
                      arguments.putInt(BUNDLE_MAPS, 4);
                      fragment.setArguments(arguments);
                      return fragment;
              case 5 :
                      arguments.putInt(BUNDLE_MAPS, 5);
                      fragment.setArguments(arguments);
                      return fragment;
              case 6 :
                      arguments.putInt(BUNDLE_MAPS, 6);
                      fragment.setArguments(arguments);
                      return fragment;
              case 7 :
                      arguments.putInt(BUNDLE_MAPS, 7);
                      fragment.setArguments(arguments);
                      return fragment;
              case 8 :
                      arguments.putInt(BUNDLE_MAPS, 8);
                      fragment.setArguments(arguments);
                      return fragment;
              case 9 :
                      arguments.putInt(BUNDLE_MAPS, 9);
                      fragment.setArguments(arguments);
                      return fragment;
              case 10 :
                      arguments.putInt(BUNDLE_MAPS, 10);
                      fragment.setArguments(arguments);
                      return fragment;
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
        public CharSequence getPageTitle(int position) {

            String spec = getResources().getString(R.string.menu_spec);
            String domin = getResources().getString(R.string.menu_domin);
            String podriv = getResources().getString(R.string.menu_podriv);
            String comand = getResources().getString(R.string.menu_comand);
            String mysorubka = getResources().getString(R.string.menu_mysorubka);
            String unicht = getResources().getString(R.string.menu_unicht);
            String zahvat = getResources().getString(R.string.menu_zahvat);
            String shturm = getResources().getString(R.string.menu_shturm);
            String viziv = getResources().getString(R.string.menu_viziv);
            String bliz = getResources().getString(R.string.menu_bliz);
            String king = getResources().getString(R.string.menu_king);

            switch (position){
                case 0 :
                    return spec;
                case 1 :
                    return domin;
                case 2 :
                    return podriv;
                case 3 :
                    return comand;
                case 4 :
                    return mysorubka;
                case 5 :
                    return unicht;
                case 6 :
                    return zahvat;
                case 7 :
                    return shturm;
                case 8 :
                    return viziv;
                case 9 :
                    return bliz;
                case 10 :
                    return king;
            }
                return null;
        }
    }

}
