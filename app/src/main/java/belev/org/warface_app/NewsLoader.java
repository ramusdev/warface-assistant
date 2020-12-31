package belev.org.warface_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import belev.org.warface_app.data.DataContract;
import belev.org.warface_app.data.DataDbHelper;

public class NewsLoader {

    private Context context;
    private List<News> newsArray;

    public NewsLoader(Context context) {
        this.context = context;
        newsArray = new ArrayList<News>();
    }

    public List<News> load() {

        DataDbHelper dbHelper = new DataDbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        String selection = DataContract.NewsEntry.COLUMN_TITLE + " = ?";
        String[] selectionArgs = { "Title 10" };
        String order = DataContract.NewsEntry.COLUMN_DATE + " DESC";
        Cursor cursor = sqLiteDatabase.query(DataContract.NewsEntry.TABLE_NAME,
                null,
                null,
                null,
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

            // Log.e("CustomLogTag", news.getDate());
            newsArray.add(news);
        }

        cursor.close();

        return newsArray;
    }
}
