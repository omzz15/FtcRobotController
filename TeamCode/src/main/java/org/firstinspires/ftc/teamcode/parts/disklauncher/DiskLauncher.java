package org.firstinspires.ftc.teamcode.parts.disklauncher;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.parts.arm2.Arm2Hardware;

public class DiskLauncher extends RobotPart<DiskLauncherHardware, DiskLauncherSettings> {
    boolean conveyerRunning;
    boolean launcherRunning;
    boolean conveyerWasPressed;
    boolean launcherWasPressed = false;

    public DiskLauncher(Robot robot) {
        super(robot, new DiskLauncherHardware(), new DiskLauncherSettings());
    }

    public DiskLauncher(Robot robot, DiskLauncherHardware hardware, DiskLauncherSettings settings) {
        super(robot, hardware, settings);
    }

    @Override
    public void onConstruct() {

    }

    @Override
    public void onInit() {
        hardware.launcherMotor.setPIDFCoefficients(DcMotorEx.RunMode.RUN_USING_ENCODER,settings.launcherMotorPID);
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
        if ( settings.launcherPresetSupplier.get()) {
            launcherWasPressed = true;
        } else if(launcherWasPressed) {
            launcherRunning = !launcherRunning;
            launcherWasPressed = false;
        }

        if (launcherRunning) {
            setRPM(3000);
        } else {
            setRPM(0);
        }

        hardware.flipServo.setPosition(settings.flipPresetSupplier.get());

        if (settings.conveyerPresetSupplier.get()) {
            conveyerWasPressed = true;
        } else if(conveyerWasPressed) {
            conveyerRunning = !conveyerRunning;
            conveyerWasPressed = false;
        }

        if (conveyerRunning) {
            hardware.intakeMotor.setPower(1);
        } else {
            hardware.intakeMotor.setPower(0);
        }
    }

    @Override
    public void onAddTelemetry() {
        robot.addTelemetry("spinner RPM", getRPM());
    }

    @Override
    public void onStop() {

    }

    public double getRPM() {
        return  hardware.launcherMotor.getVelocity() * settings.spinMultiplier;
    }

    public void setRPM(double RPM) {
        hardware.launcherMotor.setVelocity(RPM/settings.spinMultiplier);
    }
}
