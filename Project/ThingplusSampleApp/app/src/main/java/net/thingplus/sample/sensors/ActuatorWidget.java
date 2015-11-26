/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.sensors;

import net.thingplus.sample.R;

public class ActuatorWidget implements Sensors {
    public static final String WIDGET_TYPE = "actuator";

    private String mName;
    protected Object mValue;
    private long mUpdatedTime;
    private String mUpdatedTimeUnit;

    public ActuatorWidget(String name, Object value, long updatedTime) {
        mName = name;
        mValue = value;
        mUpdatedTime = updatedTime;
        mUpdatedTimeUnit = SECONDS_AGO;
    }

    @Override
    public String getSensorType() {
        return WIDGET_TYPE;
    }

    @Override
    public int getSensorIcon() {
        return R.drawable.icon_defaultactuator;
    }

    @Override
    public String getSensorName() {
        return mName;
    }

    @Override
    public String getSensorValue() {
        return NOT_IMPLEMENTED;
    }

    @Override
    public String getSensorUnit() {
        return " ";
    }

    @Override
    public String getSensorUpdatedTime() {
        if (mUpdatedTime != 0) {
            long time = (System.currentTimeMillis() - mUpdatedTime) / ONE_SECONDE;
            if (time > ONE_MINUTE) {
                time /= ONE_MINUTE;
                mUpdatedTimeUnit = MINUTES_AGO;
            }
            return String.valueOf(time + mUpdatedTimeUnit);
        } else {
            return "";
        }
    }

}
