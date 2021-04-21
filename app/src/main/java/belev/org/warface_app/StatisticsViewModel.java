package belev.org.warface_app;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.ViewModel;
import belev.org.warface_app.data.DataContract;
import belev.org.warface_app.data.DataDbHelper;

public class StatisticsViewModel extends ViewModel {

    public StatisticsViewModel() {

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
        statisticsUser.setClanid(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_CLANID)));
        statisticsUser.setRankid(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_RANKID)));
        statisticsUser.setClanname(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_CLANNAME)));
        statisticsUser.setKill(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_KILL)));
        statisticsUser.setFriendlykills(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_FRIENDLYKILLS)));
        statisticsUser.setKills(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_KILLS)));
        statisticsUser.setPvp(cursor.getDouble(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVP)));
        statisticsUser.setDeath(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_DEATH)));
        statisticsUser.setPvekill(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVEKILL)));
        statisticsUser.setPvefriendlykills(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVEFRIENDLYKILLS)));
        statisticsUser.setPvefriendlykills(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVEKILLS)));
        statisticsUser.setPvedeath(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVEDEATH)));
        statisticsUser.setPve(cursor.getDouble(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVE)));
        statisticsUser.setPlaytime(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PLAYTIME)));
        statisticsUser.setPlaytimeh(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PLAYTIMEH)));
        statisticsUser.setPlaytimem(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PLAYTIMEM)));
        statisticsUser.setFavoritPVP(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_FAVORITPVP)));
        statisticsUser.setFavoritPVE(cursor.getString(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_FAVORITPVE)));
        statisticsUser.setPvewins(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVEWINS)));
        statisticsUser.setPvpwins(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVPWINS)));
        statisticsUser.setPvplost(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVPLOST)));
        statisticsUser.setPvelost(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVELOST)));
        statisticsUser.setPveall(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVEALL)));
        statisticsUser.setPvpall(cursor.getInt(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVPALL)));
        statisticsUser.setPvpwl(cursor.getDouble(cursor.getColumnIndexOrThrow(DataContract.StatisticsEntry.COLUMN_PVPWL)));

        cursor.close();

        return statisticsUser;
    }
}