package belev.org.warface_app;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class JobSchedulerCreator {

    MainActivity mainActivity;

    public JobSchedulerCreator(MainActivity mainActivity) {
        mainActivity = mainActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void create() {

        ComponentName componentName = new ComponentName(mainActivity.getApplicationContext(), JobService.class);

        JobInfo builder = new JobInfo.Builder(0, componentName)
            .setMinimumLatency(1 * 1000)
            .setOverrideDeadline(3 * 1000)
            .build();

        JobScheduler jobScheduler = mainActivity.getApplicationContext().getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder);

    }
}
