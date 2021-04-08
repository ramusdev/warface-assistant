package belev.org.warface_app;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    // public void executeAsync(Runnable runnable) {
        // executor.execute(runnable);
    // }

    public void executeAsync(final Callable callable, final StatisticsFragment.ActionAfterDone task) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("MyTag", "Some code inside runnable 1");
                try {
                    Log.d("MyTag", "Some code inside runnable 2");
                    Log.d("MyTag", Thread.currentThread().getName());
                    callable.call();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            task.actionSuccess();
                        }
                    });
                    Log.d("MyTag", "Some code inside runnable 3");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
