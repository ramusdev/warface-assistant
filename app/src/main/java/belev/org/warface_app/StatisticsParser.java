package belev.org.warface_app;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import belev.org.warface_app.data.DataContract;
import belev.org.warface_app.data.DataDbHelper;

public class StatisticsParser implements Runnable {

    public String stringJson;
    public String name;

    public StatisticsParser(String name) {
        this.name = name;
    }

    @Override
    public void run() {

        try {
            stringJson = getUserByApi(name);
            // Log.d("MyTag", stringJson);
        }
        catch (URISyntaxException | IOException | ParseException e){
            Log.d("MyTag", "Error: from statistics parser");
        }

        clearDatabase();
        insertUserToDatabase(stringJson);
    }

    public void clearDatabase() {
        DataDbHelper dbHelper = new DataDbHelper(MyApplicationContext.getAppContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        sqLiteDatabase.delete(DataContract.StatisticsEntry.TABLE_NAME, null, null);
    }

    public void insertUserToDatabase(String stringJson) {
        Gson gson = new Gson();
        StatisticsUser statisticsUser = gson.fromJson(stringJson, StatisticsUser.class);

        DataDbHelper dbHelper = new DataDbHelper(MyApplicationContext.getAppContext());
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.StatisticsEntry.COLUMN_USERID, statisticsUser.getUserid());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_NICKNAME, statisticsUser.getNickname());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_EXPERIENCE, statisticsUser.getExperience());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_RANKID, statisticsUser.getRankid());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_CLANID, statisticsUser.getClanid());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_CLANNAME, statisticsUser.getClanname());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_KILL, statisticsUser.getKill());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_FRIENDLYKILLS, statisticsUser.getFriendlykills());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_KILLS, statisticsUser.getKills());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVP, statisticsUser.getPvp());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_DEATH, statisticsUser.getDeath());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVEKILL, statisticsUser.getPvekill());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVEFRIENDLYKILLS, statisticsUser.getPvefriendlykills());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVEKILLS, statisticsUser.getPvekills());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVEDEATH, statisticsUser.getPvedeath());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVE, statisticsUser.getPve());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PLAYTIME, statisticsUser.getPlaytime());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PLAYTIMEH, statisticsUser.getPlaytimeh());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PLAYTIMEM, statisticsUser.getPlaytimem());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_FAVORITPVP, statisticsUser.getFavoritPVP());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_FAVORITPVE, statisticsUser.getFavoritPVE());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVEWINS, statisticsUser.getPvewins());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVPWINS, statisticsUser.getPvpwins());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVPLOST, statisticsUser.getPvplost());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVELOST, statisticsUser.getPvelost());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVEALL, statisticsUser.getPveall());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVPALL, statisticsUser.getPvpall());
        contentValues.put(DataContract.StatisticsEntry.COLUMN_PVPWL, statisticsUser.getPvpwl());

        long newRowId = sqLiteDatabase.insert(DataContract.StatisticsEntry.TABLE_NAME, null, contentValues);
    }

    public String getUserByApi(String name) throws URISyntaxException, IOException, ParseException {

        CloseableHttpClient closeableHttpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().build())
                .build();

        URIBuilder uriBuilder = new URIBuilder("http://api.warface.ru/user/stat");

        ArrayList<NameValuePair> queryParameter;
        queryParameter = new ArrayList<NameValuePair>();

        byte[] nameBytes = name.getBytes();
        String nameUtf = new String(nameBytes, StandardCharsets.UTF_8);
        queryParameter.add(new BasicNameValuePair("name", nameUtf));

        uriBuilder.addParameters(queryParameter);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        CloseableHttpResponse closeableHttpResponse = closeableHttpClient.execute(httpGet);
        HttpEntity httpEntity = closeableHttpResponse.getEntity();
        return EntityUtils.toString(httpEntity);
    }
}