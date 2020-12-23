package belev.org.warface_app;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import java.util.List;
import belev.org.warface_app.data.DataContract;
import belev.org.warface_app.data.DataDbHelper;

public class UpdateNewsAsync extends AsyncTask {

    private DataDbHelper dbHelper;
    List<News> newsArray;

    public UpdateNewsAsync(DataDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        parseNews();
        insertNewsToDatabase();

        return null;
    }

    public void parseNews() {
        NewsParser newsParser = new NewsParser();
        newsParser.pars();
        newsArray = newsParser.getNewsArray();
    }

    public void insertNewsToDatabase() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        for (News news : newsArray) {
            if (! isNewsExists(news, sqLiteDatabase)) {
                NewsValuesAdapter newValuesAdapter = new NewsValuesAdapter(news);
                ContentValues contentValues = newValuesAdapter.convert();
                long newRowId = sqLiteDatabase.insert(DataContract.NewsEntry.TABLE_NAME, null, contentValues);
            }
        }
    }

    public boolean isNewsExists(News news, SQLiteDatabase sqLiteDatabase) {
        String selection = DataContract.NewsEntry.COLUMN_TITLE + " = ?";
        String[] selectionArgs = { news.getTitle() };

        Cursor cursor = sqLiteDatabase.query(DataContract.NewsEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            Log.e("CustomLogTag", "Entry exists in database");
            cursor.close();
            return true;
        } else {
            Log.e("CustomLogTag", "Entry do not exists in database");
            cursor.close();
            return false;
        }
    }
}
