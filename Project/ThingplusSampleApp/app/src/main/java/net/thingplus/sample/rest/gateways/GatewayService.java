/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.rest.gateways;

import static net.thingplus.sample.rest.gateways.Gateway.*;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;
import retrofit.http.QueryMap;

public interface GatewayService {

    @GET(GATEWAY_URL)
    Call<List<Gateway>> getGateways(@Header(HEAD_AUTH) String oauthToken);

    @GET(GATEWAY_URL)
    Call<List<Gateway>> getGateways(@Header(HEAD_AUTH) String oauthToken, @QueryMap Map<String, String> queries);

    @GET(GATEWAY_URL + GATEWAY_PATH_ID)
    Call<Gateway> getGateway(@Header(HEAD_AUTH) String oauthToken, @Path(GATEWAY_PATH) String id);

    @GET(GATEWAY_URL + GATEWAY_PATH_ID + GATEWAY_STATUS)
    Call<Gateway> getGatewayWithStatus(@Header(HEAD_AUTH) String oauthToken, @Path(GATEWAY_PATH) String id);
}
