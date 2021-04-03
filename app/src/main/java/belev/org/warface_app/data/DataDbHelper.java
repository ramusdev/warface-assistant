package belev.org.warface_app.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "warface.db";
    private static final int DATABASE_VERSION = 2;

    public DataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + DataContract.NewsEntry.TABLE_NAME + " ("
                + DataContract.NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataContract.NewsEntry.COLUMN_TITLE + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_PREVIEWTEXT + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_TEXT + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_LINK + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_IMAGE + " TEXT NOT NULL, "
                + DataContract.NewsEntry.COLUMN_NOTIFIED + " INTEGER NOT NULL DEFAULT 0);";

        String SQL_CREATE_STATISTICS_TABLE = "CREATE TABLE " + DataContract.StatisticsEntry.TABLE_NAME + " ("
                + DataContract.StatisticsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DataContract.StatisticsEntry.COLUMN_USERID + " TEXT NOT NULL, "
                + DataContract.StatisticsEntry.COLUMN_NICKNAME + " TEXT NOT NULL, "
                + DataContract.StatisticsEntry.COLUMN_EXPERIENCE + " INTEGER NOT NULL, "
                + DataContract.StatisticsEntry.COLUMN_RANKID + " INTEGER NOT NULL, "
                + DataContract.StatisticsEntry.COLUMN_CLANID + " INTEGER NOT NULL, "
                + DataContract.StatisticsEntry.COLUMN_CLANNAME + " TEXT NOT NULL, "
                + DataContract.StatisticsEntry.COLUMN_KILL + " INTEGER NOT NULL, "
                + DataContract.StatisticsEntry.COLUMN_FRIENDLYKILLS + " INTEGER NOT NULL, "
                + DataContract.StatisticsEntry.COLUMN_KILLS + " INTEGER NOT NULL, "
                + DataContract.StatisticsEntry.COLUMN_DEATH + " INTEGER NOT NULL, "
                + DataContract.StatisticsEntry.COLUMN_PVP + " TEXT NOT NULL);";

        sqLiteDatabase.execSQL(SQL_CREATE_NEWS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_STATISTICS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w("SQLite", "Update datebase ");

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataContract.NewsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
