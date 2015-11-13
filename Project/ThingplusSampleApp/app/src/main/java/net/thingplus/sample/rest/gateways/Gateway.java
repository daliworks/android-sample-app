/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.rest.gateways;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class Gateway {
    private static final String ID = "id";
    public static final String GATEWAYS = "gateways";
    public static final String GATEWAY_URL = GATEWAYS + "/";
    public static final String GATEWAY_PATH = GATEWAYS + ID;
    public static final String GATEWAY_PATH_ID = "{" + GATEWAY_PATH + "}/";
    public static final String HEAD_AUTH = "Authorization";

    public static final String GATEWAY_ID = ID;
    public static final String GATEWAY_NAME = "name";
    public static final String GATEWAY_CTIME = "ctime";
    public static final String GATEWAY_MTIME = "mtime";
    public static final String GATEWAY_SENSORS = "sensors";
    public static final String GATEWAY_DEVICES = "devices";
    public static final String GATEWAY_SITE = "_site";
    public static final String GATEWAY_SERVICE = "_service";
    public static final String GATEWAY_STATUS = "status";
    public static final String GATEWAY_REPORT_INTERVAL = "reportInterval";
    public static final String GATEWAY_TREE = "tree";
    public static final String GATEWAY_MODEL = "model";
    public static final String GATEWAY_DEVICE_MODELS = "deviceModels";
    public static final String GATEWAY_VIRTUAL = "virtual";
    public static final String GATEWAY_AUTO_CREATE_DISCOVERABLE = "autoCreateDiscoverable";
    public static final String GATEWAY_LOCATION = "location";

    private static final String VIRTUAL = "y";
    private static final String DISCOVERABLE = "y";

    @SerializedName(GATEWAY_ID)
    private String mID;

    @SerializedName(GATEWAY_NAME)
    private String mName;

    @SerializedName(GATEWAY_CTIME)
    private String mCtime;

    @SerializedName(GATEWAY_MTIME)
    private String mMtime;

    @SerializedName(GATEWAY_SENSORS)
    private List<String> mSensors;

    @SerializedName(GATEWAY_DEVICES)
    private List<String> mDevices;

    @SerializedName(GATEWAY_SITE)
    private String mSite;

    @SerializedName(GATEWAY_SERVICE)
    private String mService;

    @SerializedName(GATEWAY_STATUS)
    private Object mStatus;

    @SerializedName(GATEWAY_REPORT_INTERVAL)
    private String mReportInterval;

    @SerializedName(GATEWAY_TREE)
    private String mTree;

    @SerializedName(GATEWAY_MODEL)
    private int mModel;

    @SerializedName(GATEWAY_DEVICE_MODELS)
    private List<Map<String, String>> mDeviceModels;

    @SerializedName(GATEWAY_VIRTUAL)
    private String mVirtual;

    @SerializedName(GATEWAY_AUTO_CREATE_DISCOVERABLE)
    private String mAutoCreateDiscoverable;

    @SerializedName(GATEWAY_LOCATION)
    private Object mLocation;

    public String getId() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public String getCtime() {
        return mCtime;
    }

    public String getMtime() {
        return mMtime;
    }

    public List<String> getSensors() {
        return mSensors;
    }

    public List<String> getDevices() {
        return mDevices;
    }

    public String getSite() {
        return mSite;
    }

    public String getService() {
        return mService;
    }

    public Object getStatus() {
        return mStatus;
    }

    public String getReportInterval() {
        return mReportInterval;
    }

    public String getTree() {
        return mTree;
    }

    public int getModel() {
        return mModel;
    }

    public List<Map<String, String>> getDeviceModels() {
        return mDeviceModels;
    }

    public boolean isVirtual() {
        return mVirtual != null ? (mVirtual.equalsIgnoreCase(VIRTUAL) ? true : false) : false;
    }

    public boolean isAutoCreateDiscoverable() {
        return mAutoCreateDiscoverable != null ? (mAutoCreateDiscoverable.equalsIgnoreCase(DISCOVERABLE) ? true : false) : false;
    }

    public Object getLocation() {
        return mLocation;
    }
}
