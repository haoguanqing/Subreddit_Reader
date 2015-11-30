package com.guanqing.subredditor.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.guanqing.subredditor.R;
import com.guanqing.subredditor.Util.ToastUtil;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.http.oauth.OAuthHelper;
import net.dean.jraw.models.Listing;
import net.dean.jraw.models.Submission;
import net.dean.jraw.paginators.Sorting;
import net.dean.jraw.paginators.SubredditPaginator;
import net.dean.jraw.paginators.TimePeriod;

import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private static final String REDIRECT_URL = "http://haoguanqing.github.io/Tests-Misc-for-Android/";

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private ImageView mLoginIcon;
    private View loginProcessView;
    private WebView webView;

    //JRAW login instances
    UserAgent mUserAgent;
    static RedditClient redditClient;

    private String username;
    private String password;
    static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupActionBar();

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);

        mPasswordView = (EditText) findViewById(R.id.password);

        Button mSignInButton = (Button) findViewById(R.id.sign_in_button);
        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mLoginIcon = (ImageView)findViewById(R.id.user_login_icon);
        loginProcessView = findViewById(R.id.login_process);
        webView = (WebView)findViewById(R.id.login_webview);

        username = "";
        password = "";

        mContext = this;
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            // Show the Up button in the action bar.
            if (getSupportActionBar()!=null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        username = mUsernameView.getText().toString();
        password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password.
        if (TextUtils.isEmpty(password)){
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid user name.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUserNameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_email));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            userLogin();
        }
    }

    private boolean isUserNameValid(String username) {
        boolean isValid = true;
        int len = username.length();
        if (len>20 || len<3){
            isValid = false;
        }
        //use regex to validate username
        Pattern pattern = Pattern.compile("[^[-_\\w]]");
        Matcher matcher = pattern.matcher(username);
        if(matcher.find()){
            isValid = false;
        }
        return isValid;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        loginProcessView.setVisibility(show ? View.VISIBLE : View.GONE);
        loginProcessView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loginProcessView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });

    }

    /**
     * performs user login
     */
    private void userLogin(){
        mUserAgent = UserAgent.of("Android", "com.guanqing.subredditor", "1.0", "besttth9");
        redditClient = new RedditClient(mUserAgent);
        final OAuthHelper helper = redditClient.getOAuthHelper();
        final net.dean.jraw.http.oauth.Credentials credentials =
                net.dean.jraw.http.oauth.Credentials.installedApp("p6NSlEAAL8HerQ", "http://haoguanqing.github.io/Tests-Misc-for-Android/");
        boolean permanent = true;
        String[] scopes = {"identity", "read"};

        URL authorizationUrl = helper.getAuthorizationUrl(credentials, permanent, scopes);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new jsInterface(), "HTMLOUT");

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.contains("code=")) {
                    new UserChallengeTask(helper, credentials).execute(url);
                    //webView.stopLoading();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("HGQ", "onPageFinished: " + url);

                if (url.startsWith("https://www.reddit.com/login")) {
                    String getButtonJS = "javascript:var els = document.getElementsByClassName('c-btn c-btn-primary c-pull-right');" +
                            "javascript:els[1].click();";
                    webView.loadUrl(
                            "javascript:document.getElementById('user_login').value = '" + username + "';" +
                                    "javascript:document.getElementById('passwd_login').value = '" + password + "';" +
                                    getButtonJS
                    );
                } else if (url.startsWith("https://www.reddit.com/api/v1/authorize")) {
                    webView.loadUrl("javascript:document.getElementsByClassName('fancybutton newbutton allow')[0].click();");
                } else if (url.contains("code=")) {
                    onBackPressed();
                }
            }
        });
        webView.loadUrl(authorizationUrl.toExternalForm());

        new AccountRetrieveTask().execute();
    }

    class jsInterface{
        public void showHTML(String html){
            Intent openNewActivity = new Intent("android.intent.action.showHTMLintent");
            startActivity(openNewActivity);
        }
    }


    private static final class UserChallengeTask extends AsyncTask<String, Void, OAuthData>{
        private OAuthHelper helper;
        private Credentials creds;

        public UserChallengeTask(OAuthHelper helper, Credentials creds){
            this.helper = helper;
            this.creds = creds;
        }

        @Override
        protected OAuthData doInBackground(String... params) {
            try{
                return helper.onUserChallenge(params[0], creds);
            }catch (OAuthException | NullPointerException e){
                ToastUtil.show(mContext, "Fail to authenticate");
                return null;
            }catch (NetworkException e){
                ToastUtil.show(mContext, "No network detected");
                return null;
            }
        }

        @Override
        protected void onPostExecute(OAuthData oAuthData) {
            if(oAuthData!=null){
                redditClient.authenticate(oAuthData);
                Log.e("HGQ", "oAuthData not null! Client authenticated! ");
            }
        }
    }


    private static final class AccountRetrieveTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... params) {
            try{
                SubredditPaginator frontPage = new SubredditPaginator(redditClient);
                // Adjust the request parameters
                frontPage.setLimit(50);                    // Default is 25 (Paginator.DEFAULT_LIMIT)
                frontPage.setTimePeriod(TimePeriod.MONTH); // Default is DAY (Paginator.DEFAULT_TIME_PERIOD)
                frontPage.setSorting(Sorting.TOP);         // Default is HOT (Paginator.DEFAULT_SORTING)
                // This Paginator is now set up to retrieve the highest-scoring links submitted within the past
                // month, 50 at a time

                // Since Paginator implements Iterator, you can use it just how you would expect to, using next() and hasNext()
                Listing<Submission> submissions = frontPage.next();
                for (Submission s : submissions) {
                    // Print some basic stats about the posts
                    Log.e("HGQ", "[/r/%s - %s karma] %s\n"+s.getSubredditName()+ s.getScore()+ s.getTitle());
                }

            }catch (Exception e){
                e.printStackTrace();
                Log.e("HGQ", "retrieve task failed");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
        }
    }
}

