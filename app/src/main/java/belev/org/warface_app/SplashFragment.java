package belev.org.warface_app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

public class SplashFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_splash, container, false);
        final MainActivity mainActivity = (MainActivity) getActivity();

        final AfterLoadFunction afterLoadFunction = new AfterLoadFunction() {
            @Override
            public void run() {
                FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.containerView, new NewsFragment());
                fragmentTransaction.commitAllowingStateLoss();

                mainActivity.toolbar.setVisibility(View.VISIBLE);

                View decorView = mainActivity.getWindow().getDecorView();
                final int flags = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(flags);
            }
        };

        // Update news
        mainActivity.updateNewsIfNotExists();

        // Load ads
        NativeAdLoader nativeAdLoader = new NativeAdLoader(mainActivity, 1, afterLoadFunction);
        nativeAdLoader.loadAds();
        nativeAdLoader.execute();

        return view;
    }

}

interface AfterLoadFunction {
    void run();
}