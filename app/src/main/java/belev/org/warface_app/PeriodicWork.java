package belev.org.warface_app;

import android.content.Context;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PeriodicWork extends Worker {

    private Context context;

    public PeriodicWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        // UpdateNewsAsync updateNewsAsync = new UpdateNewsAsync(context);
        // updateNewsAsync.execute();
        // ClearNewsAsync clearNewsAsync = new ClearNewsAsync(context);
        // clearNewsAsync.execute();

        return Result.success();
    }
}
