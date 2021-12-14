package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@TeleOp(name = "TestAuto", group = "Test")
public class TestAuto extends LinearOpMode {
    @Override
    public void runOpMode(){
        Robot robot = new Robot(this);
        new PositionTracker(robot);
        new Movement(robot);

        robot.init();
        waitForStart();
        robot.start();
        List<Position> path = new ArrayList();
        path.add(new Position(5,5,90));

        Movement move = (Movement) robot.getPartByClass(Movement.class);
        Position currentPos = ((PositionTracker) robot.getPartByClass(PositionTracker.class)).currentPosition;
        MovementSettings ms = new MovementSettings();
        move.settings.runMode = 1;
        int step = 0;

        while (opModeIsActive())
        {
            robot.run();
            move.setMoveToPosition(path.get(step), ms.losePosSettings);
            //if(currentPos.inTolerance(path.get(step), ms.losePosSettings))
            robot.sendTelemetry();
        }
        robot.stop();
    }
}
