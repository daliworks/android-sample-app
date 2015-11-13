/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.rest.oauth;

import com.google.gson.annotations.SerializedName;

public class TokenResponse {
    private static final String ACCESS_TOKEN = "access_token";
    private static final String TOKEN_TYPE = "token_type";

    @SerializedName(ACCESS_TOKEN)
    private String mAccessToken;

    @SerializedName(TOKEN_TYPE)
    private String mTokenType;

    public String getAccessToken() {
        return mAccessToken;
    }

    public String getTokenType() {
        return mTokenType;
    }

    @Override
    public String toString() {
        return mTokenType + " " + mAccessToken;
    }

}