package belev.org.warface_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class BroadcastReceiverCustom extends BroadcastReceiver {

    public static final String APP_PREFERENCES = "my_settings";
    public static final String APP_PREFERENCES_NAME = "name";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("MyTag", "Inside broadcast receiver");

        UpdateNewsAsync updateNewsAsync = new UpdateNewsAsync(context);
        updateNewsAsync.execute();
        ClearNewsAsync clearNewsAsync = new ClearNewsAsync(context);
        clearNewsAsync.execute();
        NotificationShower notificationShower = new NotificationShower(context);
        notificationShower.execute();

        /*
        SharedPreferences sharedPreferences = MyApplicationContext.getAppContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(APP_PREFERENCES_NAME)) {
            String name = sharedPreferences.getString(APP_PREFERENCES_NAME, "");
            if (!name.isEmpty()) {
                TaskRunner taskRunner = new TaskRunner();
                taskRunner.executeAsync(new StatisticsParser(name));
            }
        }
        */

    }
}
