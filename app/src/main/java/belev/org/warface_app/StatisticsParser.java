package belev.org.warface_app;

import android.util.Log;
import java.io.IOException;
import java.net.URISyntaxException;

public class StatisticsParser implements Runnable {

    @Override
    public void run() {

        try {
            String stringJson = getUserByApi("Элез");
        }
        catch (URISyntaxException | IOException e){
            // e.printStackTrace();
            Log.d("MyTag", "Error: from statistics parser");
        }

    }

    public String getUserByApi(String name) throws URISyntaxException, IOException {

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                .build();


        /*
        URIBuilder uriBuilder = new URIBuilder("http://api.warface.ru/user/stat");

        ArrayList<NameValuePair> queryParameter;
        queryParameter = new ArrayList<NameValuePair>();

        byte[] nameBytes = name.getBytes();
        String nameUtf = new String(nameBytes, StandardCharsets.UTF_8);
        queryParameter.add(new BasicNameValuePair("name", nameUtf));

        uriBuilder.addParameters(queryParameter);

        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
        // httpGet.addHeader("Accept-charset:", "utf-8");

        HttpResponse httpResponse = httpClient.execute(httpGet);
        HttpEntity httpEntity = httpResponse.getEntity();

        return EntityUtils.toString(httpEntity);
        */

        return "s";
    }
}
