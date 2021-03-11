package belev.org.warface_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.RequiresApi;
import belev.org.warface_app.data.DataContract;
import belev.org.warface_app.data.DataDbHelper;

public class NotificationShower extends AsyncTask {

    private Context context;
    private List<News> newsArray;
    DataDbHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(15));
        } catch (InterruptedException e) {
            Log.e("CustomLogTag", "Exception");
        }

        loadNotShowedNews();
        showNotificationIfExists();
        changeNewsToShowed();

        return null;
    }

    public NotificationShower(Context context) {
        this.context = context;
        newsArray = new ArrayList<News>();
        dbHelper = new DataDbHelper(context);
    }

    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void show() {
        loadNotShowedNews();
        showNotificationIfExists();
        changeNewsToShowed();
    }
    */

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notificationShow(News news) {

        String channelId = "channel_id_100";
        String channelDescription = "channel main";
        String name = "news_channel_name";
        String text = news.getPreviewText();
        int notifyId = 100;
        int importance = NotificationManager.IMPORTANCE_LOW;

        Intent intent = new Intent(context, NewsWebActivity.class);
        intent.putExtra("BUNDLE_TEXT", news.getText());

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationChannel notificationChannel = new NotificationChannel(channelId, name, importance);
        notificationChannel.setDescription(channelDescription);
        notificationChannel.enableLights(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);

        SpannableString title = new SpannableString(news.getTitle());
        title.setSpan(new StyleSpan(Typeface.BOLD), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.orange)), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        Notification notification = new Notification.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_warface_icon)
                // .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setChannelId(channelId)
                .setPriority(Notification.PRIORITY_MAX)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                // .setStyle(new Notification
                // .BigTextStyle()
                // .bigText(text))
                .setContentTitle(title)
                .setContentText(text)
                .build();

        notificationManager.notify(notifyId, notification);
    }

    public void loadNotShowedNews() {
        DataDbHelper dbHelper = new DataDbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = DataContract.NewsEntry.COLUMN_NOTIFIED + " = ?";
        String[] selectionArgs = { "0" };
        String order = DataContract.NewsEntry.COLUMN_DATE + " DESC";
        Cursor cursor = sqLiteDatabase.query(DataContract.NewsEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                order
        );

        while (cursor.moveToNext()) {
            News news = new News();

            news.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_TITLE)));
            news.setPreviewText(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_PREVIEWTEXT)));
            news.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_TEXT)));
            news.setLink(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_LINK)));
            news.setImage(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_IMAGE)));
            news.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.NewsEntry.COLUMN_DATE)));

            newsArray.add(news);
        }

        cursor.close();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotificationIfExists() {
        if (newsArray.size() > 0) {
            notificationShow(newsArray.get(0));
        }
    }

    public void changeNewsToShowed() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DataContract.NewsEntry.COLUMN_NOTIFIED, 1);
        String selection = DataContract.NewsEntry.COLUMN_NOTIFIED + " = ?";
        String[] selectionArgs = { "0" };

        sqLiteDatabase.update(DataContract.NewsEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }
}
