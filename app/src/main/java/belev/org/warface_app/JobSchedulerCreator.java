package belev.org.warface_app;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import androidx.annotation.RequiresApi;

public class JobSchedulerCreator {

    MainActivity mainActivity;

    public JobSchedulerCreator(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void create() {

        ComponentName componentName = new ComponentName(mainActivity.getApplicationContext(), JobServiceCreator.class);

        JobInfo builder = new JobInfo.Builder(10, componentName)
                .setMinimumLatency(1)
                .setOverrideDeadline(15 * 60 * 1000)
                .build();

        JobScheduler jobScheduler = mainActivity.getApplicationContext().getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder);
    }
}
