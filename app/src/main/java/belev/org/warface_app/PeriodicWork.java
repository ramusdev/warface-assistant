package belev.org.warface_app;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PeriodicWork extends Worker {

    private static final String LOG_TAG = "CustomLogTag";
    private Context context;

    public PeriodicWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        Log.e("CustomLogTag", "From do work");
        // UpdateNewsAsync updateNewsAsync = new UpdateNewsAsync(context);
        // updateNewsAsync.execute();
        // ClearNewsAsync clearNewsAsync = new ClearNewsAsync(context);
        // clearNewsAsync.execute();
        NotificationShower notificationShower = new NotificationShower(context);
        notificationShower.show();

        // UpdateLogAsync updateLogAsync = new UpdateLogAsync(context);
        // updateLogAsync.notAsyncExecute();
        // updateLogAsync.execute();

        return Result.success();
        // return null;
    }
}
