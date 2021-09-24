package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.ArrayList;
import java.util.List;

public class Robot{
    LinearOpMode opMode;
    List<RobotPart> parts = new ArrayList<>();

    Robot(LinearOpMode opMode){
        construct(opMode, null);
    }

    Robot(LinearOpMode opMode, List<RobotPartSettings> settings){
        construct(opMode, settings);
    }

    void construct(LinearOpMode opMode, List<RobotPartSettings> settings){
        this.opMode = opMode;
        if(settings != null) {
            new Drive(this, (DriveSettings) settings.get(0));
        }
        else{
            new Drive(this);
        }
    }

    void init(List<RobotPart> parts){
        for(RobotPart part: parts)
            if(part.settings.usePart)part.init();
    }

    void init(){
        init(parts);
    }

    void runForTeleOp(List<RobotPart> parts){
        for(RobotPart part: parts)
            if(part.settings.runForTeleOp())part.runForTeleOp();
    }

    void runForTeleOp(){
        runForTeleOp(parts);
    }
}
