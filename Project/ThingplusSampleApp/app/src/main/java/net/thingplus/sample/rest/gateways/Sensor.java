/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.rest.gateways;

import com.google.gson.annotations.SerializedName;

public class Sensor {
    private static final String ID = "id";
    public static final String SENSORS = "sensors";
    public static final String SENSORS_URL = SENSORS + "/";
    public static final String SENSORS_PATH = SENSORS + ID;
    public static final String SENSOR_PATH_ID = "{" + SENSORS_PATH + "}/";

    public static final String SENSOR_ID = ID;
    public static final String SENSOR_NAME = "name";
    public static final String SENSOR_NETWORK = "network";
    public static final String SENSOR_DRIVER_NAME = "dirverName";
    public static final String SENSOR_MODEL = "model";
    public static final String SENSOR_SEQUENCE = "sequence";
    public static final String SENSOR_TYPE = "type";
    public static final String SENSOR_CATEGORY = "category";
    public static final String SENSOR_DEVICE_ID = "deviceId";
    public static final String SENSOR_VIRTUAL = "virtual";
    public static final String SENSOR_OWNER = "owner";
    public static final String SENSOR_MTIME = "mtime";
    public static final String SENSOR_CTIME = "ctime";
    public static final String SENSOR_SERIES = "series";
    public static final String SENSOR_STATUS = "status";

    private static final String VIRTUAL = "y";

    @SerializedName(SENSOR_ID)
    private String mId;

    @SerializedName(SENSOR_NAME)
    private String mName;

    @SerializedName(SENSOR_NETWORK)
    private String mNetwork;

    @SerializedName(SENSOR_DRIVER_NAME)
    private String mDriverName;

    @SerializedName(SENSOR_MODEL)
    private String mModel;

    @SerializedName(SENSOR_SEQUENCE)
    private String mSequence;

    @SerializedName(SENSOR_TYPE)
    private String mType;

    @SerializedName(SENSOR_CATEGORY)
    private String mCategory;

    @SerializedName(SENSOR_DEVICE_ID)
    private String mDeviceId;

    @SerializedName(SENSOR_VIRTUAL)
    private String mVirtual;

    @SerializedName(SENSOR_OWNER)
    private String mOwner;

    @SerializedName(SENSOR_MTIME)
    private String mMtime;

    @SerializedName(SENSOR_CTIME)
    private String mCtime;

    @SerializedName(SENSOR_SERIES)
    private Object mSeries;

    @SerializedName(SENSOR_STATUS)
    private Object mStatus;

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getNetwork() {
        return mNetwork;
    }

    public String getDriverName() {
        return mDriverName;
    }

    public String getModel() {
        return mModel;
    }

    public String getSequence() {
        return mSequence;
    }

    public String getType() {
        return mType;
    }

    public String getCategory() {
        return mCategory;
    }

    public String getDeviceId() {
        return mDeviceId;
    }

    public boolean isVirtual() {
        return mVirtual != null ? (mVirtual.equalsIgnoreCase(VIRTUAL) ? true : false) : false;
    }

    public String getOwner() {
        return mOwner;
    }

    public Object getSeries() {
        return mSeries;
    }

    public Object getStatus() {
        return mStatus;
    }
}
