package belev.org.warface_app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BroadcastReceiverCustom extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("CustomLogTag", "Inside receiver");
        UpdateNewsAsync updateNewsAsync = new UpdateNewsAsync(context);
        updateNewsAsync.execute();
        ClearNewsAsync clearNewsAsync = new ClearNewsAsync(context);
        clearNewsAsync.execute();
        NotificationShower notificationShower = new NotificationShower(context);
        notificationShower.execute();
    }
}
