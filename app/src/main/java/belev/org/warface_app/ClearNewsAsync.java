package belev.org.warface_app;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import belev.org.warface_app.data.DataContract;
import belev.org.warface_app.data.DataDbHelper;

public class ClearNewsAsync extends AsyncTask {

    private DataDbHelper dbHelper;

    public ClearNewsAsync(DataDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        clearNews();

        return null;
    }

    public void clearNews() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        String deleteQuery = "DELETE" +
                " FROM " + DataContract.NewsEntry.TABLE_NAME +
                " WHERE " + DataContract.NewsEntry._ID +
                " NOT IN (SELECT " + DataContract.NewsEntry._ID +
                " FROM " + DataContract.NewsEntry.TABLE_NAME +
                " ORDER BY " + DataContract.NewsEntry._ID +
                " DESC " +
                " LIMIT 20)";

        sqLiteDatabase.execSQL(deleteQuery);
    }
}
