package belev.org.warface_app.data;

import android.provider.BaseColumns;

public final class DataContract {

    private DataContract() {

    }

    public static final class NewsEntry implements BaseColumns {
        public final static String TABLE_NAME = "news";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_PREVIEWTEXT = "previewtext";
        public static final String COLUMN_TEXT = "text";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_LINK = "link";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_NOTIFIED = "notified";
    }

    public static final class StatisticsEntry implements BaseColumns {
        public final static String TABLE_NAME = "statistics";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USERID = "userid";
        public static final String COLUMN_NICKNAME = "nickname";
        public static final String COLUMN_EXPERIENCE = "experience";
        public static final String COLUMN_RANKID = "rankid";
        public static final String COLUMN_CLANID = "clanid";
        public static final String COLUMN_CLANNAME = "clanname";
        public static final String COLUMN_KILL = "kill";
        public static final String COLUMN_FRIENDLYKILLS = "friendlykills";
        public static final String COLUMN_KILLS = "kills";
        public static final String COLUMN_DEATH = "death";
        public static final String COLUMN_PVP = "pvp";
    }

}
