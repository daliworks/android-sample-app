/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.rest.gateways;

import com.squareup.okhttp.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ThingPlusApiWrapper {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String BASE_URL = "https://api.thingplus.net/v1/";

    private String mOAuthAccessToken;
    private Retrofit mRetrofit;

    public ThingPlusApiWrapper(String oauthAccessToken) {
        mOAuthAccessToken = oauthAccessToken;
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get a list of {@link net.thingplus.sample.rest.gateways.Gateway} synchronously from a Service you are included in
     *
     * @return A list of {@link net.thingplus.sample.rest.gateways.Gateway}
     * @throws IOException Failed to get a list of {@link net.thingplus.sample.rest.gateways.Gateway} with diverse reasons, Please refer a message from the Exception
     */
    @SuppressWarnings("unchecked")
    public Response<List<Gateway>> getGateways() throws IOException {
        Call<List<Gateway>> call = mRetrofit.create(GatewayService.class).getGateways(mOAuthAccessToken);
        Object obj = execute(call);
        return (Response<List<Gateway>>) obj;
    }

    /**
     * Get a list of {@link net.thingplus.sample.rest.gateways.Gateway} asynchronously from a Service you are included in
     *
     * @param callback A {@link retrofit.Callback} object you receive a response
     * @return A {@link retrofit.Call} object to access and control a current process
     */
    public Call<?> getGatewaysAsync(Callback<List<Gateway>> callback) {
        Call<List<Gateway>> call = mRetrofit.create(GatewayService.class).getGateways(mOAuthAccessToken);
        call.enqueue(callback);
        return call;
    }

    /**
     * Get a list of {@link net.thingplus.sample.rest.gateways.Gateway} synchronously from a Service you are included in with Queries
     *
     * @param query "count": "100", "page": "1" etc.. Please refer <a href=http://support.thingplus.net/ko/rest-api/rest-api.html>Rest API document</a>
     * @return A list of {@link net.thingplus.sample.rest.gateways.Gateway}
     * @throws IOException Failed to get a list of {@link net.thingplus.sample.rest.gateways.Gateway} with diverse reasons, Please refer a message from the Exception
     */
    @SuppressWarnings("unchecked")
    public Response<List<Gateway>> getGatewaysWithQuery(Map<String, String> query) throws IOException {
        Call<List<Gateway>> call = mRetrofit.create(GatewayService.class).getGateways(mOAuthAccessToken, query);
        Object obj = execute(call);
        return (Response<List<Gateway>>) obj;
    }

    /**
     * Get a list of {@link net.thingplus.sample.rest.gateways.Gateway} asynchronously from a Service you are included in with Queries
     *
     * @param query "count": "100", "page": "1" etc.. Please refer <a href=http://support.thingplus.net/ko/rest-api/rest-api.html>Rest API document</a>
     * @param callback A {@link retrofit.Callback} object you receive a response
     * @return A {@link retrofit.Call} object to access and control a current process
     */
    public Call<?> getGatewaysWithQueryAsync(Map<String, String> query, Callback<List<Gateway>> callback) {
        Call<List<Gateway>> call = mRetrofit.create(GatewayService.class).getGateways(mOAuthAccessToken, query);
        call.enqueue(callback);
        return call;
    }

    /**
     * Get a {@link net.thingplus.sample.rest.gateways.Gateway} synchronously from a Service you are included in
     *
     * @param id A specific Gateway ID
     * @return A {@link net.thingplus.sample.rest.gateways.Gateway} object
     * @throws IOException Failed to get a {@link net.thingplus.sample.rest.gateways.Gateway} with diverse reasons, Please refer a message from the Exception
     */
    public Response<Gateway> getGateway(String id) throws IOException {
        Call<Gateway> call = mRetrofit.create(GatewayService.class).getGateway(mOAuthAccessToken, id);
        Object obj = execute(call);
        return (Response<Gateway>) obj;
    }

    /**
     * Get a {@link net.thingplus.sample.rest.gateways.Gateway} asynchronously from a Service you are included in
     *
     * @param id A specific Gateway ID
     * @param callback A {@link retrofit.Callback} object you receive a response
     * @return A {@link retrofit.Call} object to access and control a current process
     */
    public Call<?> getGatewayAsync(String id, Callback<Gateway> callback) {
        Call<Gateway> call = mRetrofit.create(GatewayService.class).getGateway(mOAuthAccessToken, id);
        call.enqueue(callback);
        return call;
    }

    /**
     * Get a {@link net.thingplus.sample.rest.gateways.Gateway} synchronously from a Service you are included in with additional Status information in a key,<b>"status"</b>
     *
     * @param id A specific Gateway ID
     * @return A {@link net.thingplus.sample.rest.gateways.Gateway} object
     * @throws IOException Failed to get a {@link net.thingplus.sample.rest.gateways.Gateway} with diverse reasons, Please refer a message from the Exception
     */
    public Response<Gateway> getGatewayWithStatus(String id) throws IOException {
        Call<Gateway> call = mRetrofit.create(GatewayService.class).getGatewayWithStatus(mOAuthAccessToken, id);
        Object obj = execute(call);
        return (Response<Gateway>) obj;
    }

    /**
     * Get a {@link net.thingplus.sample.rest.gateways.Gateway} asynchronously from a Service you are included in with additional Status information in a key,<b>"status"</b>
     *
     * @param id A specific Gateway ID
     * @param callback A {@link retrofit.Callback} object you receive a response
     * @return A {@link retrofit.Call} object to access and control a current process
     */
    public Call<?> getGatewayWithStatusAsync(String id, Callback<Gateway> callback) {
        Call<Gateway> call = mRetrofit.create(GatewayService.class).getGatewayWithStatus(mOAuthAccessToken, id);
        call.enqueue(callback);
        return call;
    }

    /**
     * Get a list of {@link net.thingplus.sample.rest.gateways.Sensor} synchronously from a gateway sensors are registered
     *
     * @param gatewayId A Gateway ID sensors are registered
     * @return A list of {@link net.thingplus.sample.rest.gateways.Sensor} object
     * @throws IOException Failed to get a list of {@link net.thingplus.sample.rest.gateways.Sensor} with diverse reasons, Please refer a message from the Exception
     */
    @SuppressWarnings("unchecked")
    public Response<List<Sensor>> getSensors(String gatewayId) throws IOException {
        Call<List<Sensor>> call = mRetrofit.create(SensorService.class).getSensors(mOAuthAccessToken, gatewayId);
        Object obj = execute(call);
        return (Response<List<Sensor>>) obj;
    }

    /**
     * Get a list of {@link net.thingplus.sample.rest.gateways.Sensor} asynchronously from a gateway sensors are registered
     *
     * @param gatewayId A Gateway sensors are registered
     * @param callback A {@link retrofit.Callback} object you receive a response
     * @return A {@link retrofit.Call} object to access and control a current process
     */
    public Call<?> getSensorsAsync(String gatewayId, Callback<List<Sensor>> callback) {
        Call<List<Sensor>> call = mRetrofit.create(SensorService.class).getSensors(mOAuthAccessToken, gatewayId);
        call.enqueue(callback);
        return call;
    }

    /**
     * Get a {@link net.thingplus.sample.rest.gateways.Sensor} synchronously from a gateway sensors are registered with a specific Sensor ID
     *
     * @param gatewayId A Gateway ID sensors are registered
     * @param sensorId A Sensor ID
     * @return A {@link net.thingplus.sample.rest.gateways.Sensor} object
     * @throws IOException Failed to get a {@link net.thingplus.sample.rest.gateways.Sensor} with diverse reasons, Please refer a message from the Exception
     */
    public Response<Sensor> getSensor(String gatewayId, String sensorId) throws IOException {
        Call<Sensor> call = mRetrofit.create(SensorService.class).getSensor(mOAuthAccessToken, gatewayId, sensorId);
        Object obj = execute(call);
        return (Response<Sensor>) obj;
    }

    /**
     * Get a {@link net.thingplus.sample.rest.gateways.Sensor} asynchronously from a gateway sensors are registered with a specific Sensor ID
     *
     * @param gatewayId A Gateway ID sensors are registered
     * @param sensorId A Sensor ID
     * @param callback A {@link retrofit.Callback} object you receive a response
     * @return A {@link retrofit.Call} object to access and control a current process
     */
    public Call<?> getSensorAsync(String gatewayId, String sensorId, Callback<Sensor> callback) {
        Call<Sensor> call = mRetrofit.create(SensorService.class).getSensor(mOAuthAccessToken, gatewayId, sensorId);
        call.enqueue(callback);
        return call;
    }

    /**
     * Get a {@link net.thingplus.sample.rest.gateways.Sensor} synchronously from a gateway sensors are registered with a specific Sensor ID with additional Status information in a key, "<b>status</b>"
     *
     * @param gatewayId A Gateway ID sensors are registered
     * @param sensorId A Sensor ID
     * @return A {@link net.thingplus.sample.rest.gateways.Sensor} object
     * @throws IOException Failed to get a {@link net.thingplus.sample.rest.gateways.Sensor} with diverse reasons, Please refer a message from the Exception
     */
    public Response<Sensor> getSensorWithStatus(String gatewayId, String sensorId) throws IOException {
        Call<Sensor> call = mRetrofit.create(SensorService.class).getSensorWithStatus(mOAuthAccessToken, gatewayId, sensorId);
        Object obj = execute(call);
        return (Response<Sensor>) obj;
    }

    /**
     * Get a {@link net.thingplus.sample.rest.gateways.Sensor} asynchronously from a gateway sensors are registered with a specific Sensor ID with additional Status information in a key, "<b>status</b>"
     *
     * @param gatewayId A Gateway ID sensors are registered
     * @param sensorId A Sensor ID
     * @param callback A {@link retrofit.Callback} object you receive a response
     * @return A {@link retrofit.Call} object to access and control a current process
     */
    public Call<?> getSensorWithStatusAsync(String gatewayId, String sensorId, Callback<Sensor> callback) {
        Call<Sensor> call = mRetrofit.create(SensorService.class).getSensorWithStatus(mOAuthAccessToken, gatewayId, sensorId);
        call.enqueue(callback);
        return call;
    }

    /**
     * Get a {@link net.thingplus.sample.rest.gateways.Sensor} synchronously from a gateway sensors are registered with a specific Sensor ID with additional Series information in a key, "<b>series</b>"
     *
     * @param gatewayId A Gateway ID sensors are registered
     * @param sensorId A Sensor ID
     * @return A {@link net.thingplus.sample.rest.gateways.Sensor} object
     * @throws IOException Failed to get a {@link net.thingplus.sample.rest.gateways.Sensor} with diverse reasons, Please refer a message from the Exception
     */
    public Response<Sensor> getSensorWithValue(String gatewayId, String sensorId) throws IOException {
        Call<Sensor> call = mRetrofit.create(SensorService.class).getSensorWithValue(mOAuthAccessToken, gatewayId, sensorId);
        Object obj = execute(call);
        return (Response<Sensor>) obj;
    }

    /**
     * Get a {@link net.thingplus.sample.rest.gateways.Sensor} asynchronously from a gateway sensors are registered with a specific Sensor ID with additional Series information in a key, "<b>series</b>"
     *
     * @param gatewayId A Gateway ID sensors are registered
     * @param sensorId A Sensor ID
     * @param callback A {@link retrofit.Callback} object you receive a response
     * @return A {@link retrofit.Call} object to access and control a current process
     */
    public Call<?> getSensorWithValueAsync(String gatewayId, String sensorId, Callback<Sensor> callback) {
        Call<Sensor> call = mRetrofit.create(SensorService.class).getSensorWithValue(mOAuthAccessToken, gatewayId, sensorId);
        call.enqueue(callback);
        return call;
    }

    /**
     * Try to cancel current processes
     *
     * @param callList a list of {@link retrofit.Call}
     */
    public void cancelCall(List<Call<?>> callList) {
        for (Call<?> call : callList) {
            call.cancel();
        }
    }

    private <T> Response<T> execute(Call<T> call) throws IOException {
        Response<T> response = null;

        try {
            response = call.execute();
        } catch (IOException e) {
            throw new IOException("Failed to access to the Internet");
        }

        if (response != null) {
            return response;
        }
        return null;
    }
}
