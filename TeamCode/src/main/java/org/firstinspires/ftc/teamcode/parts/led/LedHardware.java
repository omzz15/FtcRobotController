package org.firstinspires.ftc.teamcode.parts.led;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPartHardware;
import org.openftc.i2cdrivers.QwiicLEDStick;

public class LedHardware extends RobotPartHardware {

    QwiicLEDStick leds;

    @Override
    public void onInit(Robot robot) {
        leds = robot.hardwareMap.get(QwiicLEDStick.class, "statLed");
        leds.setBrightness(5);//max of 31
    }
}
