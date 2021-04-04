package belev.org.warface_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.ViewModel;
import belev.org.warface_app.data.DataContract;
import belev.org.warface_app.data.DataDbHelper;

public class StatisticsDetailsViewModel extends ViewModel {

    public StatisticsDetailsViewModel() {

    }

    public StatisticsUser loadUser() {
        DataDbHelper dbHelper = new DataDbHelper(MyApplicationContext.getAppContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        // String selection = DataContract.NewsEntry.COLUMN_TITLE + " = ?";
        // String[] selectionArgs = { "Title 10" };
        // String order = DataContract.NewsEntry.COLUMN_DATE + " DESC";
        Cursor cursor = sqLiteDatabase.query(DataContract.StatisticsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToLast();

        StatisticsUser statisticsUser = new StatisticsUser();

        statisticsUser.setUserid(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_USERID)));
        statisticsUser.setNickname(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_NICKNAME)));
        statisticsUser.setExperience(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_EXPERIENCE)));
        statisticsUser.setClanname(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_CLANNAME)));

        cursor.close();

        return statisticsUser;
    }
}