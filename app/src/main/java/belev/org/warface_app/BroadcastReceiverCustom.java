package belev.org.warface_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.concurrent.Callable;

public class BroadcastReceiverCustom extends BroadcastReceiver {

    public static final String APP_PREFERENCES = "my_settings";
    public static final String APP_PREFERENCES_NAME = "name";
    public static final String APP_PREFERENCES_SERVER = "server";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyTag", "Inside broadcast receiver");

        UpdateNewsAsync updateNewsAsync = new UpdateNewsAsync(context);
        updateNewsAsync.execute();
        ClearNewsAsync clearNewsAsync = new ClearNewsAsync(context);
        clearNewsAsync.execute();
        NotificationShower notificationShower = new NotificationShower(context);
        notificationShower.execute();

        SharedPreferences sharedPreferences = MyApplicationContext.getAppContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(APP_PREFERENCES_NAME)) {
            String name = sharedPreferences.getString(APP_PREFERENCES_NAME, "");
            String server = sharedPreferences.getString(APP_PREFERENCES_SERVER, "1");
            if (!name.isEmpty()) {
                TaskRunner<Integer> taskRunner = new TaskRunner<Integer>();
                Callable statisticsParser = new StatisticsParser(name, server);
                taskRunner.executeAsync(statisticsParser);
            }
        }
    }
}
