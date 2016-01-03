package com.guanqing.subredditor.UI.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guanqing.subredditor.Events.FinishLoginActivityEvent;
import com.guanqing.subredditor.R;
import com.guanqing.subredditor.UI.UI.CircleTransformation;
import com.guanqing.subredditor.Util.ImageUtil;
import com.guanqing.subredditor.Util.SharedPrefUtil;
import com.guanqing.subredditor.Util.ToastUtil;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkException;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.http.oauth.Credentials;
import net.dean.jraw.http.oauth.OAuthData;
import net.dean.jraw.http.oauth.OAuthException;
import net.dean.jraw.http.oauth.OAuthHelper;

import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by Guanqing on 2015/12/4.
 */
public class WelcomeDialog extends DialogFragment{


    private static RedditClient redditClient;
    private UserAgent mUserAgent;
    private WebView webView;

    @Bind(R.id.ivAvatar) ImageView ivUserAvatar;
    @Bind(R.id.tvWelcomeText) TextView tvWelcomeText;

    private static Context mContext;
    private String username;
    private  String password;
    private boolean loggedIn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        mContext = getActivity();
        //default username and password if user does not login
        username = "besttth8";
        password = "besttth3";

        loggedIn = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        //error check
        if (getDialog() == null) {return;}
        //set fade in/out animation for the dialog
        getDialog().getWindow().setWindowAnimations(
                R.style.dialog_animation_fade);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(mContext,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_welcome, container, false);
        ButterKnife.bind(this, view);

        setupProfileIcon();
        login();
        return view;
    }

    private void setupProfileIcon() {
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.global_menu_avatar_size_large);
        if(loggedIn) {
            Uri uri = SharedPrefUtil.getAvatarFilePath();
            Drawable avatar = ImageUtil.getAvatarImage(mContext, uri);
            Glide.with(mContext)
                    .load(avatar)
                    .placeholder(R.drawable.img_circle_placeholder)
                    .override(avatarSize, avatarSize)
                    .error(R.drawable.error)
                    .transform(new CircleTransformation(mContext))
                    .into(ivUserAvatar);
            String username = SharedPrefUtil.getUserName();
            tvWelcomeText.setText("welcome back, "+ username);
        }else{
            Glide.with(mContext)
                    .load(R.drawable.avatar_blue)
                    .placeholder(R.drawable.img_circle_placeholder)
                    .override(avatarSize, avatarSize)
                    .transform(new CircleTransformation(mContext))
                    .into(ivUserAvatar);
            tvWelcomeText.setText("welcome, stranger");
        }
    }

    private void login(){
        if(loggedIn){
            username = SharedPrefUtil.getUserName();
            password = SharedPrefUtil.getPassword();
        }
        //login to user account
        userLogin();
    }

    /**
     * performs user login
     */
    private void userLogin(){
        final String CLIENT_ID = getString(R.string.client_id);
        final String REDIRECT_URL = getString(R.string.redirect_url);

        mUserAgent = UserAgent.of("Android", "com.guanqing.subredditor", "1.0", "besttth9");
        redditClient = new RedditClient(mUserAgent);
        final OAuthHelper helper = redditClient.getOAuthHelper();
        final net.dean.jraw.http.oauth.Credentials credentials =
                net.dean.jraw.http.oauth.Credentials.installedApp(CLIENT_ID, REDIRECT_URL);
        boolean permanent = true;
        String[] scopes = {"identity", "read"};

        URL authorizationUrl = helper.getAuthorizationUrl(credentials, permanent, scopes);
        webView = new WebView(getActivity());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new jsInterface(), "HTMLOUT");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.contains("code=")) {
                    new UserChallengeTask(helper, credentials).execute(url);
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
                }
            }
        });
        webView.loadUrl(authorizationUrl.toExternalForm());
    }

    class jsInterface{
        @JavascriptInterface
        public void showHTML(String html){
            Intent openNewActivity = new Intent("android.intent.action.showHTMLintent");
            startActivity(openNewActivity);
        }
    }

    /**
     * use the given code(credentials) to authenticate the reddit client
     */
    private static final class UserChallengeTask extends AsyncTask<String, Void, OAuthData> {
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
                ToastUtil.show("Fail to authenticate");
                return null;
            }catch (NetworkException e){
                ToastUtil.show("No network detected");
                return null;
            }
        }

        @Override
        protected void onPostExecute(OAuthData oAuthData) {
            if(oAuthData!=null){
                redditClient.authenticate(oAuthData);
                EventBus.getDefault().post(new FinishLoginActivityEvent(redditClient));
            }
        }
    }

    public void onEventMainThread(FinishLoginActivityEvent event){
        EventBus.getDefault().unregister(this);
        dismiss();
    }
}
