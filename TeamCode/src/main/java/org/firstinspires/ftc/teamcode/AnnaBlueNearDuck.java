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
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

//@Disabled
@Autonomous(name = "Anna Blue NearDuck", group = "Test")
public class AnnaBlueNearDuck extends LinearOpMode {
    Movement move;
    Robot robot;
    Arm2 arm;
    Intake intake;
    DuckSpinner duckspinner;
    Boolean enableDelay = false;
    Vision vision;
    Position duckstart;
    Position spinnerPos;
    public void setAutoVar(){
        robot.autoBlue = true;
        duckstart = new Position(-41, 63, 90);
        spinnerPos = new Position(-58, 55, -90);
    }

    @Override
    public void runOpMode(){
        robot = new Robot(this);
        new Drive(robot);
        PositionTracker pt = new PositionTracker(robot);
        duckspinner = new DuckSpinner(robot);
        intake = new Intake(robot);
        move = new Movement(robot);
        arm = new Arm2(robot);
        vision = new Vision(robot);

        setAutoVar();
        Position lowDumpPos = new Position(4.6, 44.5, 57.5);
        Position midDumpPos = new Position(-0.2, 47, 72);
        Position highDumpPos = new Position(-21, 40, 112);
        midDumpPos = highDumpPos;
        lowDumpPos = highDumpPos;
        Position pipeLineUpOutsidePos = new Position(8, 39, 0);
        Position pipeLineUpInsidePos = new Position(38, 39, 0);
        Position startCheeseRunAt45Pos = new Position(48, 41, 45);
        Position deepInCheesePos = new Position(58, 52, 45);
        Position nearDuckDump = new Position(-24, 41, 125);
        Position againstDuckWallStart = new Position(-32,60,125);
        Position againstDuckWallFinal = new Position(-55,60,125);
        Position duckParkPosition = new Position(-59, 37, 0);
        Position duckParkMidpoint = new Position(-35, 56, 90);
        pt.slamraFieldStart = duckstart;
        pt.slamraRobotOffset = new Position(-4.5,-.5,90);

        //new Position(9.5, 60, 90); old start
        enableDelay = false; // set to false to disable the testing delays

        Task autoTask = new Task();
        intake.isAutonomous = false;

        //autoTask.addStep(() -> {((Intake) robot.getPartByClass(Intake.class)).pause(false);});


        /*****************************************
         * Start main opmode running
         *****************************************/
        robot.init();
        vision.start();
        pt.start();
        while (!opModeIsActive()) {
            vision.onRunLoop((short) 1);
            pt.slamraInfo();
            vision.duckPos();
            robot.sendTelemetry();
            sleep(50);
        }

        if (vision.duckPos == 3) {
            autoTask.addStep(() -> arm.autonomousPresets((short) 5));//dump high
            move.addMoveToPositionToTask(autoTask, highDumpPos, true); //moves to dump cargo
            autoTask.addStep(() -> arm.autonomousDump(0));
            autoTask.addDelay(500);
        } else if (vision.duckPos == 2) {
            autoTask.addStep(() -> arm.autonomousPresets((short) 4));//dump mid
            move.addMoveToPositionToTask(autoTask, midDumpPos, true); //moves to dump cargo
            autoTask.addStep(() -> arm.autonomousDump(1));
            autoTask.addDelay(1000);
        } else if (vision.duckPos == 1) {
            //autoTask.addDelay(500);
            autoTask.addStep(() -> arm.autonomousPresets((short) 3));//dump low
            move.addMoveToPositionToTask(autoTask, lowDumpPos, true); //moves to dump cargo
            //autoTask.addDelay(500);
            autoTask.addStep(() -> arm.autonomousDump(1));
            autoTask.addDelay(1000);
        }

        autoTask.addStep(() -> arm.autonomousPresets((short) 2));//cradle
        autoTask.addStep(() -> duckspinner.settings.runMode = 2);
        move.addMoveToPositionToTask(autoTask, spinnerPos, false);
        autoTask.addDelay(4500);
        autoTask.addStep(() -> duckspinner.settings.runMode = 1);

        //autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, againstDuckWallStart, true);
        autoTask.addStep(() -> arm.autonomousPresets((short) 1));//bottom
        autoTask.addStep(() -> { intake.runIntake(0.2f); }); //run intake to run
        autoTask.addDelay(500);
        //autoTask.addStep(() -> arm. .setArmPosition(30));
        autoTask.addStep(() -> intake.startIntake(.8f));
        //autoTask.addDelay(1000);
        move.addMoveToPositionToTask(autoTask, againstDuckWallFinal,
                ((MovementSettings) move.settings).finalPosSettings.withPower(.4), true);

       // autoTask.addStep(() -> { intake.runIntake(0.8f); }); //run intake to run
        //autoTask.addDelay(3000);

//        autoTask.addStep(() -> {intake.runIntake(0.8f);}, () -> arm.isBucketFull());//task to wait for bucket
        autoTask.addStep(() -> {
            intake.stopIntake();
            //move.stopMovementTask();
        });//stop movement and intake
        autoTask.addStep(() -> arm.autonomousPresets((short)2)); // cradle bucket after intake finds cheese
        autoTask.addDelay(500);

        autoTask.addStep(() -> arm.autonomousPresets((short)5));//dump high
        move.addMoveToPositionToTask(autoTask, highDumpPos, true);
        autoTask.addStep(() -> arm.autonomousDump(0));
        autoTask.addDelay(500);

        autoTask.addStep(() -> arm.autonomousPresets((short)2));//cradle
        move.addMoveToPositionToTask(autoTask, duckParkMidpoint, true);
        move.addMoveToPositionToTask(autoTask, duckParkPosition,
                ((MovementSettings) move.settings).finalPosSettings, true);
        autoTask.addStep(() -> intake.setIntakeServoPosition(.6));
        autoTask.addStep(() -> arm.autonomousPresets((short)1));//flatten sur la terre
        autoTask.addStep(() -> intake.isAutonomous = false);



        robot.taskManager.getMain().addSequentialTask(autoTask);

        intake.pause(true);
        intake.isAutonomous = true;
        waitForStart();
        robot.start();
        intake.pause(true);

        while (opModeIsActive()) {
            robot.run();
            robot.sendTelemetry();
        }
        robot.stop();

    }
}