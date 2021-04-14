package belev.org.warface_app;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRunner<T> {

    private final ExecutorService executorCallable = Executors.newFixedThreadPool(1);
    private final ExecutorService executorMain = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    // public void executeAsync(Runnable runnable) {
        // executor.execute(runnable);
    // }

    public interface TaskRunnerCallback<T> {
        void execute(T taskAfterDone);
    }

    public void executeAsync(final Callable<T> callable, final TaskRunnerCallback<T> callback) {

        Log.d("MyTag", "Task runner");
        Log.d("MyTag", Thread.currentThread().getName());

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

                // Future<R> a = executorCallable.submit(callable);
            }
        });

        /*
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Log.d("MyTag", "Some code inside runnable 1");
                try {
                    Log.d("MyTag", "Some code inside runnable 2");
                    // Log.d("MyTag", Thread.currentThread().getName());
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
        */
    }
}
