package belev.org.warface_app;

import android.text.Html;
import android.text.Spanned;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticsViewModel extends ViewModel {

    private MutableLiveData<Spanned> textMutable;

    public StatisticsViewModel() {
        setTextAboutStatistcs();
    }

    public void setTextAboutStatistcs() {
        textMutable = new MutableLiveData<Spanned>();
        String text = MyApplicationContext.getAppContext().getResources().getString(R.string.statistics_text);
        textMutable.setValue(Html.fromHtml(text));
    }

    public MutableLiveData<Spanned> getText() {
        return textMutable;
    }

}