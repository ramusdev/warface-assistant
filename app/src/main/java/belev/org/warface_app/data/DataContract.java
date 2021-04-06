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
        public static final String COLUMN_PVEKILL = "pve_kill";
        public static final String COLUMN_PVEFRIENDLYKILLS = "pve_friendly_kills";
        public static final String COLUMN_PVEKILLS = "pve_kills";
        public static final String COLUMN_PVEDEATH = "pve_death";
        public static final String COLUMN_PVE = "pve";
        public static final String COLUMN_PLAYTIME = "playtime";
        public static final String COLUMN_PLAYTIMEH = "playtime_h";
        public static final String COLUMN_PLAYTIMEM = "playtime_m";
        public static final String COLUMN_FAVORITPVP = "favoritPVP";
        public static final String COLUMN_FAVORITPVE = "favoritPVE";
        public static final String COLUMN_PVEWINS = "pve_wins";
        public static final String COLUMN_PVPWINS = "pvp_wins";
        public static final String COLUMN_PVPLOST = "pvp_lost";
        public static final String COLUMN_PVELOST = "pve_lost";
        public static final String COLUMN_PVEALL = "pve_all";
        public static final String COLUMN_PVPALL = "pvp_all";
        public static final String COLUMN_PVPWL = "pvpwl";
    }

}
