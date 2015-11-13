/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.rest.oauth;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface OAuthService {
    public static final String OAUTH_TOKEN_URL = "oauth2/token/";
    public static final String OAUTH_TOKEN_CODE_KEY = "code";
    public static final String OAUTH_TOKEN_CLIENT_ID_KEY = "client_id";
    public static final String OAUTH_TOKEN_CLIENT_SERCRET_KEY = "client_secret";
    public static final String OAUTH_TOKEN_REDIRECT_URI_KEY = "redirect_uri";
    public static final String OAUTH_TOKEN_GRANT_TYPE_KEY = "grant_type";

    @FormUrlEncoded
    @POST(OAUTH_TOKEN_URL)
    Call<TokenResponse> getOAuthAccessToken(
            @Field(OAUTH_TOKEN_CODE_KEY) String code,
            @Field(OAUTH_TOKEN_CLIENT_ID_KEY) String clientId,
            @Field(OAUTH_TOKEN_CLIENT_SERCRET_KEY) String clientSecret,
            @Field(OAUTH_TOKEN_REDIRECT_URI_KEY) String redirectUri,
            @Field(OAUTH_TOKEN_GRANT_TYPE_KEY) String grantType);
}
