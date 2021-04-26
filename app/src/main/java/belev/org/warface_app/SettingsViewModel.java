package belev.org.warface_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.Spanned;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<Spanned> textMutable;
    public static final String APP_PREFERENCES = "my_settings";
    public static final String APP_PREFERENCES_NAME = "name";
    public static final String APP_PREFERENCES_SERVER = "server";
    SharedPreferences sharedPreferences;

    public SettingsViewModel() {
        setTextAboutStatistcs();
        sharedPreferences = MyApplicationContext.getAppContext().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void setTextAboutStatistcs() {
        textMutable = new MutableLiveData<Spanned>();
        String text = MyApplicationContext.getAppContext().getResources().getString(R.string.statistics_text);
        textMutable.setValue(Html.fromHtml(text));
    }

    public void setPreferencesName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES_NAME, name);
        editor.apply();
    }

    public void setPreferencesServer(String server) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(APP_PREFERENCES_SERVER, server);
        editor.apply();
    }

    public MutableLiveData<Spanned> getText() {
        return textMutable;
    }

    public String getPreferencesName() {
        if (sharedPreferences.contains(APP_PREFERENCES_NAME)) {
            return sharedPreferences.getString(APP_PREFERENCES_NAME, "");
        }

        return "";
    }

    public String getPreferencesServer() {
        if (sharedPreferences.contains(APP_PREFERENCES_SERVER)) {
            return sharedPreferences.getString(APP_PREFERENCES_SERVER, "1");
        }

        return "1";
    }

}