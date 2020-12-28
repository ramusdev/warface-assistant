package belev.org.warface_app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class CronService extends Service {
    public static final String LOG_TAG = "CustomLogTag";

    public CronService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.e(LOG_TAG, "onBind method");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate(Intent intent, int startId) {
        Log.e(LOG_TAG, "onCreate method");
    }
}