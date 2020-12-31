package belev.org.warface_app;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobServiceCreator extends JobService {

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.e("CustomLogTag", "Out from jobService");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
