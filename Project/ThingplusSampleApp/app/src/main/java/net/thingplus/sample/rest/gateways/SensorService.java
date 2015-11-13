/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.rest.gateways;

import static net.thingplus.sample.rest.gateways.Gateway.*;
import static net.thingplus.sample.rest.gateways.Sensor.*;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;

public interface SensorService {

    @GET(GATEWAY_URL + GATEWAY_PATH_ID + SENSORS_URL)
    Call<List<Sensor>> getSensors(@Header(HEAD_AUTH) String oauthToken, @Path(GATEWAY_PATH) String gatewayId);

    @GET(GATEWAY_URL + GATEWAY_PATH_ID + SENSORS_URL + SENSOR_PATH_ID)
    Call<Sensor> getSensor(@Header(HEAD_AUTH) String oauthToken, @Path(GATEWAY_PATH) String gatewayId, @Path(SENSORS_PATH) String sensorId);

    @GET(GATEWAY_URL + GATEWAY_PATH_ID + SENSORS_URL + SENSOR_PATH_ID + SENSOR_STATUS)
    Call<Sensor> getSensorWithStatus(@Header(HEAD_AUTH) String oauthToken, @Path(GATEWAY_PATH) String gatewayId, @Path(SENSORS_PATH) String sensorId);

    @GET(GATEWAY_URL + GATEWAY_PATH_ID + SENSORS_URL + SENSOR_PATH_ID + SENSOR_SERIES)
    Call<Sensor> getSensorWithValue(@Header(HEAD_AUTH) String oauthToken, @Path(GATEWAY_PATH) String gatewayId, @Path(SENSORS_PATH) String sensorId);
}
