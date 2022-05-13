package belev.org.warface_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.concurrent.Callable;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PeriodicWork extends Worker {

    private Context context;
    public static final String APP_PREFERENCES = "my_settings";
    public static final String APP_PREFERENCES_NAME = "name";
    public static final String APP_PREFERENCES_SERVER = "server";

    public PeriodicWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d("MyTag", "periodic work class -->");

        // UpdateNewsAsync updateNewsAsync = new UpdateNewsAsync(context);
        // updateNewsAsync.doInBackground();
        // ClearNewsAsync clearNewsAsync = new ClearNewsAsync(context);
        // clearNewsAsync.execute();
        // clearNewsAsync.doInBackground();
        // NotificationShower notificationShower = new NotificationShower(context);
        // notificationShower.execute();


        TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();

        Callable newsParser = new UpdateNewsCallable();
        taskRunner.executeAsync(newsParser);

        Callable clearMews = new ClearNewsCallable();
        taskRunner.executeAsync(clearMews);

        Callable notificationShower = new NotificationShowerCallable();
        taskRunner.executeAsync(notificationShower);

        SharedPreferences sharedPreferences = MyApplicationContext.getAppContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(APP_PREFERENCES_NAME)) {
            String name = sharedPreferences.getString(APP_PREFERENCES_NAME, "");
            String server = sharedPreferences.getString(APP_PREFERENCES_SERVER, "1");
            if (!name.isEmpty()) {
                // TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();
                Callable statisticsParser = new StatisticsParserCallable(name, server);
                taskRunner.executeAsync(statisticsParser);
            }
        }



        return Result.success();
    }
}
