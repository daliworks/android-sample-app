/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample;

import static net.thingplus.sample.utils.Constants.LINE_SEPARATOR;
import static net.thingplus.sample.utils.Constants.ONE_SECOND;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;

import net.thingplus.sample.rest.gateways.Gateway;
import net.thingplus.sample.rest.gateways.Sensor;
import net.thingplus.sample.rest.gateways.ThingPlusApiWrapper;
import net.thingplus.sample.sensors.Sensors;
import net.thingplus.sample.sensors.util.SensorUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SensorListActivity extends AppCompatActivity {
    private static final int MAX_TRY_COUNT = 2;
    private static final String SENSOR_VALUE_KEY = "value";
    private static final String SENSOR_UPDATED_TIME_KEY = "time";
    private static final String SENSOR_LIST_URL = "https://www.thingplus.net/#/asset?resourceType=gateway&itemId=";

    private WidgetListAdapter mSensorListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ThingplusApiManager mManager;
    private Gateway mCurrGateway;
    private List<Gateway> mGateways;
    private List<Sensors> mWidgets;
    private List<Call<?>> mSensorCalls;

    private int mCurrGatewayPosition;
    private String mCurrErrorGatewayId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_list);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.sensorlist_icon);

        Button signOutBtn = (Button) findViewById(R.id.sign_out_btn);
        signOutBtn.setOnClickListener(new SignOutBtnListener());

        initWidgetListView();
        initSwipeRefreshLayout();
        initSpinner();
    }

    private void initWidgetListView() {
        mCurrGatewayPosition = 0;
        mSensorCalls = new ArrayList<Call<?>>();
        mWidgets = new ArrayList<Sensors>();
        mSensorListAdapter = new WidgetListAdapter(this, mWidgets);

        ListView widgetListView = (ListView) findViewById(R.id.widget_list_view);
        widgetListView.setAdapter(mSensorListAdapter);
        widgetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(SENSOR_LIST_URL + mCurrGateway.getId()));
                SensorListActivity.this.startActivity(intent);
            }
        });
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.widget_list_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(
                Color.parseColor(getString(R.string.thingplus_indigo)),
                Color.parseColor(getString(R.string.thingplus_yellow)));
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.parseColor(getString(R.string.thingplus_yellow)));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mGateways != null) {
                    updateSensorValues(mCurrGatewayPosition);
                }
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void initSpinner() {
        List<String> gatewayNames = new ArrayList<String>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gatewayNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.gateway_list);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mGateways != null) {
                    mCurrGatewayPosition = position;
                    updateSensorValues(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner.setAdapter(adapter);

        ProgressDialog progressDialog = ProgressDialog.show(this, null, getString(R.string.sensor_list_progress_dialog_text));
        progressDialog.setCancelable(false);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String oauthAccessToken = sharedPref.getString(getString(R.string.oauth_access_token_key), null);

        if (oauthAccessToken != null) {
            mManager = new ThingplusApiManager(new ThingPlusApiWrapper(oauthAccessToken));
            mManager.getGateways(new GatewayCallback(gatewayNames, progressDialog, adapter));
        } else {
            moveToSignInScreen();
        }
    }

    private void updateSensorValues(int position) {
        final Gateway gateway = mGateways.get(position);
        mCurrGateway = gateway;
        mManager.cancelSensorCall(mSensorCalls);
        mSensorCalls.clear();
        mWidgets.clear();
        mSensorListAdapter.notifyDataSetChanged();

        for (String sensorId : gateway.getSensors()) {
            mManager.getSensorWithValue(gateway.getId(), sensorId, new Callback<Sensor>() {
                @Override
                public void onResponse(Response<Sensor> response, Retrofit retrofit) {
                    if (!mCurrGateway.getId().equalsIgnoreCase(gateway.getId())) {
                        return;
                    }

                    if (response.body() != null) {
                        Sensor sensor = response.body();

                        if (sensor.getSeries() != null) {
                            Map<String, Object> series = (Map) sensor.getSeries();
                            Object sensorValue = series.get(SENSOR_VALUE_KEY);
                            long updatedTime = Long.valueOf(series.get(SENSOR_UPDATED_TIME_KEY).toString());

                            Sensors newWidget = SensorUtil.getSensorWidget(
                                    sensor.getType(),
                                    sensor.getName(),
                                    sensorValue,
                                    updatedTime);

                            if (newWidget != null) {
                                mWidgets.add(newWidget);
                                mSensorListAdapter.notifyDataSetChanged();
                            } else {
                                //TODO: If this sensor is not included in the SensorUtil, please implements it!
                                Log.i(getString(R.string.app_name), sensor.getCategory() + " || " + sensor.getType());
                            }
                        }

                    } else {
                        logOAuthTokenError(response.errorBody());
                        notifyOAuthAccessTokenError();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    if (mCurrErrorGatewayId == null ||
                            !mCurrErrorGatewayId.equalsIgnoreCase(gateway.getId())) {
                        notifyNetworkError(false);
                        mCurrErrorGatewayId = gateway.getId();
                    }
                }
            });
        }
    }

    private class GatewayCallback implements Callback<List<Gateway>> {
        private List<String> mGatewayNames;
        private ProgressDialog mProgressDialog;
        private ArrayAdapter<String> mSpinnerAdapter;
        private int mFailCount;

        public GatewayCallback(
                List<String> gatewayNames,
                ProgressDialog progressDialog,
                ArrayAdapter<String> spinnerAdapter) {
            mGatewayNames = gatewayNames;
            mProgressDialog = progressDialog;
            mSpinnerAdapter = spinnerAdapter;
            mFailCount = 0;
        }

        private GatewayCallback(
                List<String> gatewayNames,
                ProgressDialog progressDialog,
                ArrayAdapter<String> spinnerAdapter,
                int failCount) {
            mGatewayNames = gatewayNames;
            mProgressDialog = progressDialog;
            mSpinnerAdapter = spinnerAdapter;
            mFailCount = failCount;
        }

        @Override
        public void onResponse(Response<List<Gateway>> response, Retrofit retrofit) {
            if (response.body() != null) {
                mGateways = response.body();
                for (Gateway gateway : mGateways) {
                    if (gateway.getSensors() != null) {
                        mGatewayNames.add(gateway.getName() + " (" + gateway.getSensors().size() + ")");
                    } else {
                        mGatewayNames.add(gateway.getName());
                    }
                }
                mSpinnerAdapter.notifyDataSetChanged();
                dissmissDialog();
            } else {
                dissmissDialog();
                logOAuthTokenError(response.errorBody());
                notifyOAuthAccessTokenError();
            }
        }

        @Override
        public void onFailure(Throwable t) {
            if (mFailCount++ < MAX_TRY_COUNT) {
                mGatewayNames.clear();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mManager.getGateways(new GatewayCallback(
                                mGatewayNames,
                                mProgressDialog,
                                mSpinnerAdapter,
                                mFailCount + 1));
                    }
                }, ONE_SECOND);
            } else {
                notifyNetworkError(true);
                dissmissDialog();
            }
        }

        public void dissmissDialog() {
            mProgressDialog.dismiss();
        }
    }

    private void notifyNetworkError(final boolean hasToFinish) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(
                        SensorListActivity.this,
                        R.string.toast_msg_failed_to_get_data,
                        Toast.LENGTH_LONG).show();

                if (hasToFinish) {
                    moveToSignInScreen();
                }
            }
        });
    }

    private void notifyOAuthAccessTokenError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(
                        SensorListActivity.this,
                        R.string.toast_msg_access_token_error,
                        Toast.LENGTH_LONG).show();
                doSignOut();
            }
        });
    }

    private void logOAuthTokenError(ResponseBody body) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(body.charStream());
            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(LINE_SEPARATOR);
            }
            br.close();
        } catch (IOException e) {
            Log.e(getString(R.string.app_name), "Failed to read OAuth2 error message");
        }

        Log.e(getString(R.string.app_name), "OAuth2 error message" + LINE_SEPARATOR + sb.toString());
    }

    private class ThingplusApiManager {
        private ThingPlusApiWrapper mWrapper;

        public ThingplusApiManager(ThingPlusApiWrapper wrapper) {
            mWrapper = wrapper;
        }

        public void getGateways(final Callback<List<Gateway>> callback) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mWrapper.getGatewaysAsync(callback);
                }
            }).start();
        }

        public void getSensorWithValue(final String gatewayId, final String sensorId, final Callback<Sensor> callback) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mSensorCalls.add(mWrapper.getSensorWithValueAsync(gatewayId, sensorId, callback));
                }
            }).start();
        }

        public void cancelSensorCall(final List<Call<?>> callList) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    mWrapper.cancelCall(callList);
                }
            });
        }
    }

    private class WidgetListAdapter extends BaseAdapter {
        private Context mContext;
        private List<Sensors> mList;
        private LayoutInflater mInflater;

        public WidgetListAdapter(Context context, List<Sensors> list) {
            mContext = context;
            mList = list;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Sensors getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.widget_list_item, parent, false);
            }

            Sensors widget = mList.get(position);
            ImageView widgetIcon = (ImageView) convertView.findViewById(R.id.widget_list_sensor_icon);
            widgetIcon.setImageResource(widget.getSensorIcon());

            TextView widgetName = (TextView) convertView.findViewById(R.id.widget_list_sensor_name);
            widgetName.setText(widget.getSensorName());

            TextView widgetValue = (TextView) convertView.findViewById(R.id.widget_list_sensor_value);
            widgetValue.setText(widget.getSensorValue() + widget.getSensorUnit());

            TextView widgetUpdatedTime = (TextView) convertView.findViewById(R.id.widget_list_sensor_updated_time);
            widgetUpdatedTime.setText(widget.getSensorUpdatedTime());

            return convertView;
        }
    }

    private class SignOutBtnListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v.getId() != R.id.sign_out_btn) {
                return;
            }
            doSignOut();
        }
    }

    private void doSignOut() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.oauth_access_token_key), null);
        editor.apply();

        moveToSignInScreen();
    }

    private void moveToSignInScreen() {
        Intent intent = new Intent(SensorListActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
