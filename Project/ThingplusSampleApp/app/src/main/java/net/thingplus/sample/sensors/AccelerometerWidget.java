/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.sensors;

import net.thingplus.sample.R;

import java.util.Map;

public class AccelerometerWidget extends SensorWidget {
    public static final String WIDGET_TYPE = "accelerometer";

    private static final String UNIT_X = "X:";
    private static final String UNIT_Y = "Y:";
    private static final String UNIT_Z = "Z:";
    private static final String KEY_X = "x";
    private static final String KEY_Y = "y";
    private static final String KEY_Z = "z";

    private String mUnit;

    public AccelerometerWidget(String name, Object value, long updatedTime) {
        super(name, value, updatedTime);
        mUnit = "";
    }

    @Override
    public String getSensorType() {
        return WIDGET_TYPE;
    }

    @Override
    public int getSensorIcon() {
        return R.drawable.icon_accelerometer;
    }

    @Override
    public String getSensorValue() {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> value = (Map) mValue;

        sb.append(UNIT_X);
        sb.append(value.get(KEY_X));
        sb.append(LINE_SEPARATOR);
        sb.append(UNIT_Y);
        sb.append(value.get(KEY_Y));
        sb.append(LINE_SEPARATOR);
        sb.append(UNIT_Z);
        sb.append(value.get(KEY_Z));

        return sb.toString();
    }

    @Override
    public String getSensorUnit() {
        return mUnit;
    }
}
