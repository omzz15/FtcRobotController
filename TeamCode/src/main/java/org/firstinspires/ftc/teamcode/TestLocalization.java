package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.deprecated.arm.Arm;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.arm2.Arm2;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.duckspinner.DuckSpinner;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;
import org.firstinspires.ftc.teamcode.parts.movement.MoveToPosSettings;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTrackerHardware;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTrackerSettings;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

//@Disabled
@Autonomous(name = "Localization Test", group = "Test")
public class TestLocalization extends LinearOpMode {
    Movement move;
    Robot robot;
    Arm2 arm;
    Intake intake;
    DuckSpinner duckspinner;
    Boolean enableDelay = false;
    Position duckstart;
    Position spinnerPos;
    PositionTracker pt;
    MoveToPosSettings losePos;
    MoveToPosSettings wallLoosePos;
    MoveToPosSettings defaultPos;


    public void setAutoVar(){
        robot.autoBlue = true;
        duckstart = new Position(-43.5, 63, 90);
        spinnerPos = new Position(-59, 55, -90);
        pt.useLeftSlamra();
    }

    @Override
    public void runOpMode(){
        MoveToPosSettings mtps = new MoveToPosSettings(new double[]{1,1,10}, 5, 10000, 1);
        Position[] positions = {
            new Position(-43.5,63-23.5,90),
            new Position(-43.5-23.5,63-23.5,90),
        };


        robot = new Robot(this);
        new Drive(robot);
        PositionTrackerSettings ptsd = new PositionTrackerSettings();
        ptsd.useEncoders = true;
        ptsd.useVision = false;

        pt = new PositionTracker(robot, new PositionTrackerHardware(), ptsd);
        duckspinner = new DuckSpinner(robot);
        intake = new Intake(robot);
        move = new Movement(robot);
        arm = new Arm2(robot);
        pt.activateWallDistSensor();

        setAutoVar();

        pt.slamraFieldStart = duckstart;
        //pt.slamraRobotOffset = new Position(-4.5,-.5,90);

        defaultPos = ((MovementSettings) move.settings).defaultPosSettings;
        losePos = ((MovementSettings) move.settings).losePosSettings;
        wallLoosePos = ((MovementSettings) move.settings).wallLoosePosSettings;
        //new Position(9.5, 60, 90); old start
        enableDelay = false; // set to false to disable the testing delays

        Task autoTask = new Task();
        intake.isAutonomous = true;

        //autoTask.addStep(() -> {((Intake) robot.getPartByClass(Intake.class)).pause(false);});
        for (Position p : positions)
            move.addMoveToPositionToTask(autoTask, p, mtps, true);

        /*****************************************
         * Start main opmode running
         *****************************************/
        robot.init();
        pt.start();
        while (!isStarted()) {
            pt.slamraInfo();
            robot.sendTelemetry();
            sleep(50);
        }

        if(!isStopRequested()) {




            robot.taskManager.getMain().addSequentialTask(autoTask);

            intake.pause(true);
            intake.isAutonomous = true;
            waitForStart();
            robot.start();
            intake.pause(true);
        }
        while (opModeIsActive()) {
            robot.run();
            robot.sendTelemetry();
        }
        robot.stop();

    }
}