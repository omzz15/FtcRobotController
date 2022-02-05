package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.deprecated.arm.Arm;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.duckspinner.DuckSpinner;
import org.firstinspires.ftc.teamcode.parts.duckspinner.DuckSpinnerHardware;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;
import org.firstinspires.ftc.teamcode.parts.intake.IntakeHardware;
import org.firstinspires.ftc.teamcode.parts.movement.MoveToPosSettings;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;
@Disabled
@TeleOp(name = "NearDuck tasks 2", group = "Test")
public class NearDuckAutoTestTask extends LinearOpMode {
    Movement move;
    Robot robot;
    Arm arm;
    Intake intake;
    DuckSpinner duckspinner;
    Boolean enableDelay = false;
    Vision vision;

    @Override
    public void runOpMode(){
        robot = new Robot(this);
        new Drive(robot);
        PositionTracker pt = new PositionTracker(robot);
        duckspinner = new DuckSpinner(robot);
        intake = new Intake(robot);
        move = new Movement(robot);
        arm = new Arm(robot);
        vision = new Vision(robot);

        Position lowDumpPos = new Position(4.6, 44.5, 57.5);
        Position midDumpPos = new Position(-0.2, 47, 72);
        Position highDumpPos = new Position(-4.5, 41, 72);
        Position pipeLineUpOutsidePos = new Position(8, 39, 0);
        Position pipeLineUpInsidePos = new Position(38, 39, 0);
        Position startCheeseRunAt45Pos = new Position(48, 41, 45);
        Position deepInCheesePos = new Position(58, 52, 45);
        Position duckstart = new Position(-35, 63, 90);
        Position spinnerPos = new Position(-57, 55, 0);
        Position nearDuckDump = new Position(-24, 41, 125);
        Position againstDuckWallStart = new Position(-37,58,125);
        Position againstDuckWallFinal = new Position(-55,58,125);
        Position duckParkPosition = new Position(-59, 37, 0);
        Position duckParkMidpoint = new Position(-35, 56, 90);
        pt.slamraFieldStart = duckstart;
                //new Position(9.5, 60, 90); old start
        enableDelay = false; // set to false to disable the testing delays

        Task autoTask = new Task();

        autoTask.addStep(() -> arm.setToAPresetPosition((short)2));//dump high
        move.addMoveToPositionToTask(autoTask, nearDuckDump, true); //moves to dump cargo
        autoTask.addStep(() -> arm.setToAPresetPosition((short)4));//cradle

        autoTask.addStep(() -> duckspinner.settings.runMode = 2);
        move.addMoveToPositionToTask(autoTask, spinnerPos, false);
        autoTask.addDelay(4500);
        autoTask.addStep(() -> duckspinner.settings.runMode = 1);

        //autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, againstDuckWallStart, true);
        autoTask.addStep(() -> { intake.runIntake(0.2f); }); //run intake to run
        autoTask.addDelay(500);
        autoTask.addStep(() -> arm.setArmPosition(30));
        autoTask.addStep(() -> intake.startIntake(.8f));
        //autoTask.addDelay(1000);
        move.addMoveToPositionToTask(autoTask, againstDuckWallFinal,
                ((MovementSettings) move.settings).finalPosSettings.withPower(.6), true);

       // autoTask.addStep(() -> { intake.runIntake(0.8f); }); //run intake to run
        //autoTask.addDelay(3000);

//        autoTask.addStep(() -> {intake.runIntake(0.8f);}, () -> arm.isBucketFull());//task to wait for bucket
        autoTask.addStep(() -> {
            intake.stopIntake();
            //move.stopMovementTask();
        });//stop movement and intake
        autoTask.addStep(() -> arm.setToAPresetPosition((short)4)); // cradle bucket after intake finds cheese
        autoTask.addDelay(500);

        autoTask.addStep(() -> arm.setToAPresetPosition((short)2));//dump high
        move.addMoveToPositionToTask(autoTask, nearDuckDump, true);
        autoTask.addDelay(500);

        autoTask.addStep(() -> arm.setToAPresetPosition((short)4));//cradle
        move.addMoveToPositionToTask(autoTask, duckParkMidpoint, true);
        move.addMoveToPositionToTask(autoTask, duckParkPosition,
                ((MovementSettings) move.settings).finalPosSettings, true);
        autoTask.addStep(() -> intake.setIntakeServoPosition(.6));
        autoTask.addStep(() -> arm.setToAPresetPosition((short)1));//flatten sur la terre
        autoTask.addStep(() -> intake.isAutonomous = false);



        robot.taskManager.getMain().addSequentialTask(autoTask);


       /* autoTask.addStep(() -> arm.setToAPresetPosition((short)4));//cradle
        move.addMoveToPositionToTask(autoTask, spinner, false);
        autoTask.addStep(() -> duckspinner.settings.runMode = 2);
        autoTask.addDelay(3500);
        autoTask.addStep(() -> duckspinner.settings.runMode = 1);
        autoTask.addStep(() -> arm.setToAPresetPosition((short)2));//dump high
        move.addMoveToPositionToTask(autoTask, nearDuckDump, true); //moves to dump cargo
        move.addMoveToPositionToTask(autoTask, againstDuckWallStart, true);
        autoTask.addStep(() -> { intake.runIntake(0.8f); }); //run intake to run
        autoTask.addDelay(500);
        move.addMoveToPositionToTask(autoTask, againstDuckWallFinal,
                ((MovementSettings) move.settings).finalPosSettings.withPower(.4), false);
        autoTask.addStep(() -> {intake.runIntake(0.8f);}, () -> arm.isBucketFull());//task to wait for bucket
        autoTask.addStep(() -> {
            intake.stopIntake();
            //move.stopMovementTask();
        });//stop movement and intake
        autoTask.addStep(() -> arm.setToAPresetPosition((short)4)); // cradle bucket after intake finds cheese
        autoTask.addDelay(500);
        autoTask.addStep(() -> arm.setToAPresetPosition((short)2));//dump high
        move.addMoveToPositionToTask(autoTask, nearDuckDump, true);
        move.addMoveToPositionToTask(autoTask, duckParkPosition, true);

        robot.taskManager.getMain().addSequentialTask(autoTask);
*/

        /*****************************************
         * Start main opmode running
         *****************************************/
        robot.init();
        
        vision.start();
        while(!opModeIsActive()){
            vision.onRunLoop((short)1);
            vision.duckPos();
            robot.sendTelemetry();
            sleep(500);
        }
        intake.isAutonomous = true;

        waitForStart();
        robot.start();
        intake.pause(true);

        while(opModeIsActive()){
            robot.run();
            robot.sendTelemetry();
        }
        robot.stop();
    }
}