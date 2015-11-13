/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;

import net.thingplus.sample.rest.oauth.OAuthWrapper;
import net.thingplus.sample.rest.oauth.TokenResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;

import retrofit.Response;

import static net.thingplus.sample.utils.Constants.LINE_SEPARATOR;
import static net.thingplus.sample.utils.Constants.ONE_SECOND;
import static net.thingplus.sample.utils.Constants.QUERY_ACCEPT_OAUTH_CODE;
import static net.thingplus.sample.utils.Constants.QUERY_DENY_OAUTH_CODE;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mInitScreen;
    private RelativeLayout mSignInScreen;
    private ProgressDialog mProgressDialog;

    private String mClientId;
    ;
    private String mClientSecret;
    private String mRedirectUri;
    private String mOauthCodeRequestUrl;
    private String mOAuthAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signInBtn = (Button) findViewById(R.id.sign_in_btn);
        signInBtn.setOnClickListener(new SignInBtnListener());

        mInitScreen = (RelativeLayout) findViewById(R.id.init_screen);
        mSignInScreen = (RelativeLayout) findViewById(R.id.sign_in_screen);
        mSignInScreen.setVisibility(View.GONE);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mOAuthAccessToken = sharedPref.getString(getString(R.string.oauth_access_token_key), null);

        // TODO: Replace your own Client ID, Client Secret and Redirect Uri on strings.xml
        mClientId = getString(R.string.oauth_client_id);
        mClientSecret = getString(R.string.oauth_client_secret);
        mRedirectUri = getString(R.string.oauth_redirect_uri);
        mOauthCodeRequestUrl = String.format(
                getString(R.string.oauth_code_request_url),
                getString(R.string.oauth_request_type),
                mClientId,
                mRedirectUri);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                crossfade();
            }
        }, ONE_SECOND);
    }

    private class SignInBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() != R.id.sign_in_btn) {
                return;
            }

            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setContentView(R.layout.webview_layout);
            RelativeLayout webViewParent = (RelativeLayout) dialog.findViewById(R.id.webview_parent);
            final WebView webView = new WebView(getApplicationContext());
            webView.setLayoutParams(webViewParent.getLayoutParams());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(mOauthCodeRequestUrl);
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    boolean isCompleted = false;
                    URI uri = URI.create(url);
                    String query = uri.getQuery();

                    if (query != null) {
                        if (query.contains(QUERY_ACCEPT_OAUTH_CODE)) {
                            mProgressDialog = ProgressDialog.show(
                                    MainActivity.this, null, getString(R.string.sign_in_progress_dialog_text));
                            mProgressDialog.setCancelable(false);
                            OAuthTokenThread th = new OAuthTokenThread(query.substring(QUERY_ACCEPT_OAUTH_CODE.length()));
                            th.start();
                            isCompleted = true;
                        } else if (query.contains(QUERY_DENY_OAUTH_CODE)) {
                            isCompleted = true;
                        }

                        if (isCompleted) {
                            dialog.dismiss();
                        }
                    }
                }
            });

            webViewParent.addView(webView);
            dialog.setCancelable(true);
            dialog.setTitle(R.string.sign_in_dialog_title);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    removeCookie(webView);
                }
            });
            dialog.show();
        }
    }

    private class OAuthTokenThread extends Thread {
        private String mCode;

        public OAuthTokenThread(String code) {
            mCode = code;
        }

        @Override
        public void run() {
            OAuthWrapper manager = new OAuthWrapper();
            Response<TokenResponse> response = null;
            String accessToken = null;
            boolean isComplete = false;

            try {
                response = manager.getAccessTokenSync(mCode, mClientId, mClientSecret, mRedirectUri);
            } catch (IOException e) {
                Log.e(getString(R.string.app_name), e.getMessage());
            }

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }

            if (response != null) {
                if (response.body() != null) {
                    accessToken = response.body().toString();
                    isComplete = true;
                } else {
                    ResponseBody body = response.errorBody();
                    StringBuilder errorMsg = new StringBuilder();
                    try {
                        BufferedReader br = new BufferedReader(body.charStream());
                        String line;
                        while ((line = br.readLine()) != null) {
                            errorMsg.append(line + LINE_SEPARATOR);
                        }
                        br.close();
                    } catch (IOException e) {
                        Log.e(getString(R.string.app_name), getString(R.string.toast_msg_oauth_access_token_error_read_fail));
                    }
                    Log.e(getString(R.string.app_name), errorMsg.toString());
                }


                if (isComplete) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.oauth_access_token_key), accessToken);
                    editor.apply();
                    moveToSensorListScreen();
                } else {
                    Toast.makeText(MainActivity.this, R.string.toast_msg_oauth_access_token_request_fail, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void removeCookie(WebView webView) {
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
        cookieSyncManager.sync();

        webView.removeAllViews();
        webView.destroy();
        webView = null;
    }

    private void crossfade() {
        int shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mSignInScreen.setAlpha(0f);
        mSignInScreen.setVisibility(View.VISIBLE);
        mSignInScreen.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);

        mInitScreen.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mInitScreen.setVisibility(View.GONE);
                    }
                });

        if (mOAuthAccessToken != null) {
            moveToSensorListScreen();
        }
    }

    private void moveToSensorListScreen() {
        Intent intent = new Intent(MainActivity.this, SensorListActivity.class);
        startActivity(intent);
        finish();
    }
}
