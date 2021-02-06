package belev.org.warface_app;

import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NewsParser {

    // private final String URL_CONNECT = "https://ru.warface.com/rss.xml";
    private final String USER_AGENT = "Chrome/4.0.249.0 Safari/532.5";
    private final String REFERRER = "https://www.google.com";
    private final int COUNT_NEWS = 10;

    private List<News> newsArray;
    private Document document;
    private Context context;

    public NewsParser(Context context) {
        this.context = context;
        newsArray = new ArrayList<News>(COUNT_NEWS);
    }

    public List<News> pars() {
        try {
            String url = context.getResources().getString(R.string.news_link);
            document = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .get();
        } catch (IOException e) {
            System.out.printf("Error: ", e.getMessage());
        }

        Elements elements = document.select("item");

        for (Element element : elements) {
            News news = new News();
            byte[] bytes;
            String textToDecode;
            String textUncoded;

            // Title
            textToDecode = element.select("title").first().text();
            bytes = textToDecode.getBytes(Charset.forName("UTF-8"));
            textUncoded = new String(bytes);
            news.setTitle(textUncoded);

            // Preview text
            textToDecode = element.select("description").first().text();
            bytes = textToDecode.getBytes(Charset.forName("UTF-8"));
            textUncoded = new String(bytes);
            news.setPreviewText(textUncoded);

            // Text
            textToDecode = element.select("detail_text").first().text();
            bytes = textToDecode.getBytes(Charset.forName("UTF-8"));
            textUncoded = new String(bytes);
            news.setText(textUncoded);

            // Image
            textToDecode = element.select("images").first().text();
            bytes = textToDecode.getBytes(Charset.forName("UTF-8"));
            textUncoded = new String(bytes);
            news.setImage(textUncoded);

            // Link
            textToDecode = element.select("link").first().text();
            bytes = textToDecode.getBytes(Charset.forName("UTF-8"));
            textUncoded = new String(bytes);
            news.setLink(textUncoded);

            // Date
            textToDecode = element.select("pubDate").first().text();
            bytes = textToDecode.getBytes(Charset.forName("UTF-8"));
            textUncoded = new String(bytes);
            news.setDate(textUncoded);

            newsArray.add(news);
        }

        return newsArray;
    }
}
