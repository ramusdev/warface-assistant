package belev.org.warface_app;

import android.os.Build;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import androidx.annotation.RequiresApi;

public class News {

    private String title;
    private String previewText;
    private String text;
    private String date;
    private String link;
    private String image;

    public News() {

    }

    public News(String title, String previewText, String text, String date, String link, String image) {
        this.title = title;
        this.previewText = previewText;
        this.text = text;
        this.date = date;
        this.link = link;
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPreviewText(String previewText) {
        this.previewText = previewText;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getPreviewText() {
        return previewText;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatFromDatabaseToView(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Locale locale = new Locale("ru");
        DateTimeFormatter formatterTo = DateTimeFormatter.ofPattern("dd MMMM yyyy", locale);
        String dateTo = dateTime.format(formatterTo);

        return dateTo;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatFromParseToDatabase(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        DateTimeFormatter formatterTo = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTo = dateTime.format(formatterTo);

        return dateTo;
    }

    @Override
    public String toString() {
        return "News (title: " + title +
                " date: " + date +
                " link: " + link +
                " image: " + image +
                " preview text: " + previewText +
                " text: " + text;
    }
}
