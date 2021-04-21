package belev.org.warface_app;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRunner<T> {

    private final ExecutorService executorMain = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface TaskRunnerCallback<T> {
        void execute(T taskAfterDone);
    }

    public void executeAsync(final Callable<T> callable, final TaskRunnerCallback<T> callback) {

        executorMain.execute(new Runnable() {
            @Override
            public void run() {
                T result = null;
                try {
                    result = callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final T finalResult = result;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.execute(finalResult);
                    }
                });
            }
        });
    }
}
