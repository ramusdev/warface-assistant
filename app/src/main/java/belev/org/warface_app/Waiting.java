package belev.org.warface_app;

import android.os.AsyncTask;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class Waiting extends AsyncTask<Void, Void, Void> {

    private MainActivity mainActivity;
    private AfterLoadFunction afterLoadFunction;

    public Waiting(MainActivity mainActivity, AfterLoadFunction afterLoadFunction) {
        this.mainActivity = mainActivity;
        this.afterLoadFunction = afterLoadFunction;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Log.e("CustomLogTag", "start background");
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.e("CustomLogTag", "inside run");
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 10000);

        Log.e("CustomLogTag", "end background");
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        Log.e("CustomLogTag", "on post execute");

        if (mainActivity.isShowedMain.get() == false) {
            mainActivity.isShowedMain.set(true);
            afterLoadFunction.run();
        }
    }
}
