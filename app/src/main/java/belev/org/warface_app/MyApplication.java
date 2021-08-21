package belev.org.warface_app;

import android.app.Application;
import android.util.Log;

import com.google.common.util.concurrent.ListenableFuture;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.work.Configuration;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


public class MyApplication extends Application {

    // private static AppOpenManager appOpenManager;
    private static YandexAppMediation appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();

        MyApplicationContext myApplicationContext = new MyApplicationContext(this);

        MobileAds.initialize(this, new InitializationListener() {
            @Override
            public void onInitializationCompleted() {

            }
        });

        MobileAds.enableDebugErrorIndicator(false);

        // appOpenManager = new AppOpenManager(this);
        appOpenManager = new YandexAppMediation(this);

        // Work manager
        Log.d("MyTag", "my application start class --->");
        Configuration configuration = new Configuration.Builder().build();
        WorkManager.initialize(MyApplicationContext.getAppContext(), configuration);

        WorkManager workManager = WorkManager.getInstance(this);
        ListenableFuture<List<WorkInfo>> statuses = workManager.getWorkInfosByTag("task_worker5");

        try {
            List<WorkInfo> workInfoList = statuses.get();

            if (workInfoList.size() <= 0) {
                PeriodicWorkCreator periodicWorkCreator = new PeriodicWorkCreator((Application) getApplicationContext());
                periodicWorkCreator.create();
            }
        } catch(ExecutionException | InterruptedException e) {
            Log.d("MyTag", e.getMessage());
        }

    }
}
