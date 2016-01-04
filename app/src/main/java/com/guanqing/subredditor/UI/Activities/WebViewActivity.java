package com.guanqing.subredditor.UI.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.guanqing.subredditor.R;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WebViewActivity extends AppCompatActivity {

    public static final String INTENT_URL_KEY = "WebViewActivity.INTENT_URL_KEY";
    public static final String INTENT_TITLE_KEY = "WebViewActivity.INTENT_TITLE_KEY";
    public static final String INTENT_SUBREDDIT_NAME_KEY = "WebViewActivity.INTENT_SUBREDDIT_NAME_KEY";
    private String url;
    private String submissionTitle;
    private String mainTitle;

    // UI reference
    @Bind(R.id.webview_web) WebView webView;
    @Bind(R.id.fab_web) FloatingActionButton fab;
    @BindString(R.string.title_activity_webview) String title_activity_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        url = "";
        submissionTitle = "";
        mainTitle = title_activity_webview;

        if (getIntent()!=null){
            url = getIntent().getStringExtra(INTENT_URL_KEY);
            submissionTitle = getIntent().getStringExtra(INTENT_TITLE_KEY);
            mainTitle = getIntent().getStringExtra(INTENT_SUBREDDIT_NAME_KEY);
            webView.loadUrl(url);
        }

        setupActionBar();
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            if (getSupportActionBar()!=null) {
                ActionBar actionBar = getSupportActionBar();
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle("/r/" + mainTitle);
                actionBar.setSubtitle(submissionTitle);
            }
        }
    }

    @OnClick(R.id.fab_web)
    protected void shareUrl(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, submissionTitle + " (" + url + ")");
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share"));
    }
}
