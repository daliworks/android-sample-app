/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.rest.oauth;

import java.io.IOException;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class OAuthWrapper {
    private static final String BASE_URL = "https://api.thingplus.net/v1/";
    private static final String OAUTH_TOKEN_GRANT_TYPE = "authorization_code";

    private Retrofit mRetrofit;

    public OAuthWrapper() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     *
     * Get OAuth2 AccessToken synchronously with Authorization Code Grant type
     *
     * @param code Authoriztion code from before step.
     * @param clientId The client ID you received from thingplus when you registered your application
     * @param clientSecret The client secret you received from thingplus when you registered your application
     * @param redirectUri The redirect URL when you registered your application
     * @return TokenResponse object contains information on AccessToken
     * @throws IOException Failed to get AccessToken because of a network problem
     */
    public Response<TokenResponse> getAccessTokenSync(String code, String clientId, String clientSecret, String redirectUri) throws IOException {
        Call<TokenResponse> call = mRetrofit.create(OAuthService.class).getOAuthAccessToken(
                code, clientId, clientSecret, redirectUri, OAUTH_TOKEN_GRANT_TYPE);
        Response<TokenResponse> response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            throw new IOException("Failed to access to the Internet");
        }
        return response;
    }

    /**
     * Get OAuth2 AccessToken asynchronously with Authorization Code Grant type
     *
     * @param code Authoriztion code from before step.
     * @param clientId The client ID you received from thingplus when you registered your application
     * @param clientSecret The client secret you received from thingplus when you registered your application
     * @param redirectUri The redirect URL when you registered your application
     * @param callback Callback object of {@link retrofit.Callback}
     */
    public Call<?> getAccessTokenAsync(String code, String clientId, String clientSecret, String redirectUri, Callback callback) {
        Call<TokenResponse> call = mRetrofit.create(OAuthService.class).getOAuthAccessToken(
                code, clientId, clientSecret, redirectUri, OAUTH_TOKEN_GRANT_TYPE);
        call.enqueue(callback);
        return call;
    }

}
