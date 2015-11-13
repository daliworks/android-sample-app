/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.sensors;

import net.thingplus.sample.R;

public class OnOffWidget extends SensorWidget {
    public static final String WIDGET_TYPE = "onoff";
    private static final String ON = "ON";
    private static final String ON_STATE = "1";
    private static final String OFF = "OFF";
    private static final String OFF_STATE ="0";

    private String mUnit;

    public OnOffWidget(String name, Object value, long updatedTime) {
        super(name, value, updatedTime);
        mUnit = " ";
    }

    @Override
    public String getSensorType() {
        return WIDGET_TYPE;
    }

    @Override
    public int getSensorIcon() {
        return R.drawable.icon_onoff;
    }

    @Override
    public String getSensorValue() {
        String value = (String) mValue;

        if (value.equalsIgnoreCase(ON_STATE)) {
            return ON;
        } else if (value.equalsIgnoreCase(OFF_STATE)) {
            return OFF;
        }
        return (String) mValue;
    }

    @Override
    public String getSensorUnit() {
        return mUnit;
    }

}
