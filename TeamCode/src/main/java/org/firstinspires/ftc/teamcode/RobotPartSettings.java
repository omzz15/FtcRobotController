package org.firstinspires.ftc.teamcode;

public abstract class RobotPartSettings {
    boolean usePart = true;
    boolean initialized = false;
    boolean runForTeleOp = true;
    boolean sendTelemetry = true;
    boolean stop = false;

    boolean runForTeleOp(){
        return usePart && initialized && runForTeleOp && !stop;
    }
}
