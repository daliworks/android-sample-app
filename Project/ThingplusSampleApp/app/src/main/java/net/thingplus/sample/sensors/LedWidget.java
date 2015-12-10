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

public class LedWidget extends ActuatorWidget {
    public static final String WIDGET_TYPE = "led";
    private static final String CMD = "Last Command: ";
    private static final String KEY_CMD = "cmd";

    public LedWidget(String name, Object value, long updatedTime) {
        super(name, value, updatedTime);
    }

    @Override
    public String getSensorType() {
        return WIDGET_TYPE;
    }

    @Override
    public int getSensorIcon() {
        return R.drawable.icon_led;
    }

    @Override
    public String getSensorValue() {
        if (mValue != null) {
            StringBuilder sb = new StringBuilder();
            Map<String, Object> value = (Map) mValue;

            sb.append(CMD);
            sb.append(LINE_SEPARATOR);
            sb.append(value.get(KEY_CMD).toString());

            return sb.toString();
        }
        return "";
    }
}
