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

public class NoiseWidget extends SensorWidget {
    public static final String WIDGET_TYPE = "noise";
    private static final String DECIMAL_FORMAT = "##.#";

    private String mUnit;

    public NoiseWidget(String name, Object value, long updatedTime) {
        super(name, value, updatedTime);
        mUnit = " dB";
    }

    @Override
    public String getSensorType() {
        return WIDGET_TYPE;
    }

    @Override
    public int getSensorIcon() {
        return R.drawable.icon_noise;
    }

    @Override
    public String getSensorValue() {
        DecimalFormat decimalFormat = new DecimalFormat(DECIMAL_FORMAT);
        return decimalFormat.format(Float.valueOf((String) mValue));
    }

    @Override
    public String getSensorUnit() {
        return mUnit;
    }

}
