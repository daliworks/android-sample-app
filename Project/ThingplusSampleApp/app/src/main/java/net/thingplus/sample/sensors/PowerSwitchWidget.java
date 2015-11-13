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

public class PowerSwitchWidget extends ActuatorWidget {
    public static final String WIDGET_TYPE = "powerswitch";
    private static final String CMD = "Last Command: ";
    private static final String KEY_CMD = "cmd";

    public PowerSwitchWidget(String name, Object value, long updatedTime) {
        super(name, value, updatedTime);
    }

    @Override
    public String getSensorType() {
        return WIDGET_TYPE;
    }

    @Override
    public int getSensorIcon() {
        return R.drawable.icon_powerswitch;
    }

    @Override
    public String getSensorValue() {
        StringBuilder sb = new StringBuilder();
        Map<String, String> value = (Map) mValue;

        sb.append(CMD);
        sb.append(LINE_SEPARATOR);
        sb.append(value.get(KEY_CMD).toUpperCase());

        return sb.toString();
    }
}
