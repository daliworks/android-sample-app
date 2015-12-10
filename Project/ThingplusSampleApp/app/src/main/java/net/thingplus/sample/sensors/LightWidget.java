/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.sensors;

import net.thingplus.sample.R;

import java.text.DecimalFormat;

public class LightWidget extends SensorWidget {
    public static final String WIDGET_TYPE = "light";
    private static final String DECIMAL_FORMAT = "##.#";

    private String mUnit;

    public LightWidget(String name, Object value, long updatedTime) {
        super(name, value, updatedTime);
        mUnit = " lux";
    }

    @Override
    public String getSensorType() {
        return WIDGET_TYPE;
    }

    @Override
    public int getSensorIcon() {
        return R.drawable.icon_light;
    }

    @Override
    public String getSensorValue() {
        if (mValue != null) {
            DecimalFormat df = new DecimalFormat(DECIMAL_FORMAT);
            return df.format(Double.valueOf((String) mValue));
        }
        return "";
    }

    @Override
    public String getSensorUnit() {
        return mUnit;
    }

}
