package org.firstinspires.ftc.teamcode.parts.led;

import android.graphics.Color;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;

public class Led extends RobotPart<LedHardware, LedSettings> {

    public Led(Robot robot, LedHardware hardware, LedSettings settings) {
        super(robot, hardware, settings);
    }

    public Led(Robot robot){
        super(robot, new LedHardware(), new LedSettings());
    }

    @Override
    public void onConstruct() {

    }

    @Override
    public void onInit() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onUnpause() {

    }

    @Override
    public void onRunLoop(short runMode) {
        if(runMode == 1){
            hardware.leds.setColor(1, settings.led1Supplier.get());
            hardware.leds.setColor(2, settings.led2Supplier.get());
            hardware.leds.setColor(3, settings.led3Supplier.get());
            hardware.leds.setColor(4, settings.led4Supplier.get());
            hardware.leds.setColor(5, settings.led5Supplier.get());
            hardware.leds.setColor(6, settings.led6Supplier.get());
            hardware.leds.setColor(7, settings.led7Supplier.get());
            hardware.leds.setColor(8, settings.led8Supplier.get());
            hardware.leds.setColor(9, settings.led9Supplier.get());
            hardware.leds.setColor(10, settings.led10Supplier.get());
            //hardware.leds.setColor(Color.WHITE);
        }
    }

    @Override
    public void onAddTelemetry() {

    }

    @Override
    public void onStop() {

    }
}
