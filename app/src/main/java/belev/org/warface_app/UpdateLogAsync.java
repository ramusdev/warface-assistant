package belev.org.warface_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import belev.org.warface_app.data.DataContract;
import belev.org.warface_app.data.DataDbHelper;

public class UpdateLogAsync extends AsyncTask {

    private Context context;
    List<News> newsArray;

    public UpdateLogAsync(Context context) {
        this.context = context;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        updateLogToDatabase();

        return null;
    }

    public void notAsyncExecute() {
        updateLogToDatabase();
    }

    private void updateLogToDatabase() {
        DataDbHelper dbHelper = new DataDbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatedDateTime = simpleDateFormat.format(calendar.getTime());

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.NewsEntry.COLUMN_TITLE, "Log title");
        contentValues.put(DataContract.NewsEntry.COLUMN_DATE, formatedDateTime);
        contentValues.put(DataContract.NewsEntry.COLUMN_IMAGE, "Log image");
        contentValues.put(DataContract.NewsEntry.COLUMN_LINK, "Log link");
        contentValues.put(DataContract.NewsEntry.COLUMN_PREVIEWTEXT, "Log preview text");
        contentValues.put(DataContract.NewsEntry.COLUMN_TEXT, "Log text");
        contentValues.put(DataContract.NewsEntry.COLUMN_NOTIFIED, 0);

        sqLiteDatabase.insert(DataContract.NewsEntry.TABLE_NAME, null, contentValues);
        // sqLiteDatabase.close();
    }
}
