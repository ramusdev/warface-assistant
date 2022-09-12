package belev.org.warface_app;

import android.app.Application;
import android.util.Log;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.work.Configuration;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;


public class MyApplication extends Application {

    private static AppOpenManager appOpenManager;

    @Override
    public void onCreate() {
        super.onCreate();

        MyApplicationContext myApplicationContext = new MyApplicationContext(this);

        MobileAds.initialize(MyApplicationContext.getAppContext(), new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                appOpenManager = new AppOpenManager(MyApplication.this);
            }
        });

        // MobileAds.enableDebugErrorIndicator(false);
        // appOpenManager = new AppOpenManager(this);
        // appOpenManager = new YandexAppMediation(this);

        // Work manager
        Log.d("MyTag", "my application start class --->");

        Configuration configuration = new Configuration.Builder().build();
        WorkManager.initialize(MyApplicationContext.getAppContext(), configuration);

        WorkManager workManager = WorkManager.getInstance(this);
        ListenableFuture<List<WorkInfo>> statuses = workManager.getWorkInfosByTag("task_worker6");

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
