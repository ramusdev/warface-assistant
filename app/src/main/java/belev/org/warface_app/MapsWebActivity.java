package belev.org.warface_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import belev.org.warface_app.R;

public class MapsWebActivity extends AppCompatActivity {

    private static final String BUNDLE_LINK = "bundle_link";
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bar));
            window.setNavigationBarColor(this.getResources().getColor(R.color.bar));
        }

        //link = getArguments().getString(BUNDLE_LINK);

        Intent intent = getIntent();
        String link = intent.getStringExtra("BUNDLE_LINK");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.menu_locations));
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.setBackgroundColor(0);
        //webView.loadUrl("http://belev.org/android/wardocwarface/maps/spec_vulkan.html");
        webView.loadUrl(link);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
