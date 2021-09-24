package org.firstinspires.ftc.teamcode;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    List<RobotPart> parts = new ArrayList<>();

    void runAllForTeleOp(){
        for(RobotPart part: parts)
            part.runForTeleOp();
    }
}
