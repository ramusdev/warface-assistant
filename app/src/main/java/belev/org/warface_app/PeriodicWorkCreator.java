package belev.org.warface_app;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class PeriodicWorkCreator {

    private WorkManager workManager;
    private Application application;

    public PeriodicWorkCreator(Application application) {
        this.application = application;
    }

    public void create() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(PeriodicWork.class, 10, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag("task_worker6")
                .build();

        workManager = WorkManager.getInstance(application);
        workManager.enqueueUniquePeriodicWork("work_manager_name6", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);
    }
}
