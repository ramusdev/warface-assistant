package belev.org.warface_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd;
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback;
import com.google.android.material.snackbar.Snackbar;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class RewardedFragment extends Fragment implements View.OnClickListener, OnUserEarnedRewardListener {

    public View view;
    // public static final String REWARDED_INTERSTITIAL_ID = "ca-app-pub-3940256099942544/5354046379";
    public static final String REWARDED_INTERSTITIAL_ID = "ca-app-pub-4140002463111288/8506892128";
    public RewardedInterstitialAd rewardedInterstitialAd;
    public static final String APP_PREFERENCES = "settings";
    public static final String APP_PREFERENCES_DATE = "date";
    public static final String APP_PREFERENCES_ISHOW = "isshow";

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Toolbar
        // Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        // toolbar.setTitle(getResources().getString(R.string.rewarded_tab));

        // View
        view = inflater.inflate(R.layout.rewarded_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String text = MyApplicationContext.getAppContext().getResources().getString(R.string.rewarded_text);
        final TextView textView = view.findViewById(R.id.main_text);
        textView.setText(Html.fromHtml(text));

        final Button button = view.findViewById(R.id.button_show);
        button.setOnClickListener(this);

        loadRewarded(false);
    }

    @Override
    public void onClick(View v) {
        Log.d("MyTag", "Button pressed");
        showRewarded();
    }

    public void loadRewarded(boolean isShow) {
        RewardedInterstitialAd.load(MyApplicationContext.getAppContext(), REWARDED_INTERSTITIAL_ID, new AdRequest.Builder().build(), new RewardedInterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedInterstitialAd rewardedInterstitialAd) {
                super.onAdLoaded(rewardedInterstitialAd);
                Log.d("MyTag", "Ad loaded");
                RewardedFragment.this.rewardedInterstitialAd = rewardedInterstitialAd;
                RewardedFragment.this.rewardedInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        Log.d("MyTag", "Failed");
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        super.onAdShowedFullScreenContent();
                        Log.d("MyTag", "Showed");
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        Log.d("MyTag", "Dismissed");
                        showMessage();
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                        Log.d("MyTag", "Impression");
                    }

                });

                if (isShow) {
                    showRewarded();
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Log.d("MyTag", "Ad failed to load");
            }
        });
    }

    public void showRewarded() {
        if (rewardedInterstitialAd != null) {
            rewardedInterstitialAd.show(getActivity(), RewardedFragment.this);
        } else {
            loadRewarded(true);
        }
    }

    public void showMessage() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String text = MyApplicationContext.getAppContext().getResources().getString(R.string.rewarded_message);
                Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(getResources().getColor(R.color.bar));
                snackbar.setTextColor(getResources().getColor(R.color.orange));
                snackbar.show();
            }
        }, 1000);
    }

    @Override
    public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
        Log.d("MyTag", "On user earned reward");
        SharedPreferences sharedPreferences = MyApplicationContext.getAppContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        // String currentVersion = sharedPreferences.getString(APP_PREFERENCES_DATE, "");

        long dateTime = (new Date()).getTime();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(APP_PREFERENCES_DATE, dateTime);
        editor.putBoolean(APP_PREFERENCES_ISHOW, false);
        editor.apply();
    }
}
