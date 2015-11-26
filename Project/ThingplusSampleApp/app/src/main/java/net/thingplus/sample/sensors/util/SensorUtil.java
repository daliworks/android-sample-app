/*
 * Copyright (c) 2015, Daliworks. All rights reserved.
 *
 * Reproduction and/or distribution in source and binary forms
 * without the written consent of Daliworks, Inc. is prohibited.
 *
 */

package net.thingplus.sample.sensors.util;

import net.thingplus.sample.sensors.AccelerometerWidget;
import net.thingplus.sample.sensors.BuzzerWidget;
import net.thingplus.sample.sensors.HumidityWidget;
import net.thingplus.sample.sensors.LcdWidget;
import net.thingplus.sample.sensors.LedWidget;
import net.thingplus.sample.sensors.LightWidget;
import net.thingplus.sample.sensors.NoiseWidget;
import net.thingplus.sample.sensors.OnOffWidget;
import net.thingplus.sample.sensors.PowerSwitchWidget;
import net.thingplus.sample.sensors.RgbLedWidget;
import net.thingplus.sample.sensors.RotaryAngleWidget;
import net.thingplus.sample.sensors.Sensors;
import net.thingplus.sample.sensors.TemperatureWidget;

public class SensorUtil {
    private enum SensorTypeList {
        HUMIDITY(HumidityWidget.WIDGET_TYPE),
        LIGHT(LightWidget.WIDGET_TYPE),
        TEMPERATURE(TemperatureWidget.WIDGET_TYPE),
        ON_OFF(OnOffWidget.WIDGET_TYPE),
        POWER_SWITCH(PowerSwitchWidget.WIDGET_TYPE),
        NOISE(NoiseWidget.WIDGET_TYPE),
        ROTARY_ANGLE(RotaryAngleWidget.WIDGET_TYPE),
        BUZZER(BuzzerWidget.WIDGET_TYPE),
        LCD(LcdWidget.WIDGET_TYPE),
        LED(LedWidget.WIDGET_TYPE),
        ACCELEROMETER(AccelerometerWidget.WIDGET_TYPE),
        RGB_LED(RgbLedWidget.WIDGET_TYPE);

        private String mSensorType = null;

        SensorTypeList(String sensorType) {
            mSensorType = sensorType;
        }

        public String getSensorType() {
            return mSensorType;
        }
    }

    public static Sensors getSensorWidget(String sensorType, String name, Object value, long updatedTime) {
        SensorTypeList currSensorType = null;

        for (SensorTypeList sensor : SensorTypeList.values()) {
            if (sensorType.equalsIgnoreCase(sensor.getSensorType())) {
                currSensorType = sensor;
            }
        }

        if (currSensorType == null) {
            return null;
        }

        switch (currSensorType) {
            case HUMIDITY:
                return new HumidityWidget(name, value, updatedTime);

            case LIGHT:
                return new LightWidget(name, value, updatedTime);

            case TEMPERATURE:
                return new TemperatureWidget(name, value, updatedTime);

            case ON_OFF:
                return new OnOffWidget(name, value, updatedTime);

            case POWER_SWITCH:
                return new PowerSwitchWidget(name, value, updatedTime);

            case NOISE:
                return new NoiseWidget(name, value, updatedTime);

            case ROTARY_ANGLE:
                return new RotaryAngleWidget(name, value, updatedTime);

            case BUZZER:
                return new BuzzerWidget(name, value, updatedTime);

            case LCD:
                return new LcdWidget(name, value, updatedTime);

            case LED:
                return new LedWidget(name, value, updatedTime);

            case ACCELEROMETER:
                return new AccelerometerWidget(name, value, updatedTime);

            case RGB_LED:
                return new RgbLedWidget(name, value, updatedTime);
        }

        return null;
    }
}
