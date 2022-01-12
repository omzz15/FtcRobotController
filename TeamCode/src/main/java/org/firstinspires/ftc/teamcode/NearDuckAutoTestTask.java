package org.firstinspires.ftc.teamcode;

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
import org.firstinspires.ftc.teamcode.parts.movement.MoveToPosSettings;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

@TeleOp(name = "NearDuck tasks 2", group = "Test")
public class NearDuckAutoTestTask extends LinearOpMode {
    Movement move;
    Robot robot;
    Arm arm;
    Intake intake;
    DuckSpinner duckspinner;
    Boolean enableDelay = false;

    @Override
    public void runOpMode(){
        robot = new Robot(this);
        new Drive(robot);
        PositionTracker pt = new PositionTracker(robot);
        duckspinner = new DuckSpinner(robot);
        intake = new Intake(robot);
        move = new Movement(robot);
        arm = new Arm(robot);
        new Vision(robot);

        Position lowDumpPos = new Position(4.6, 44.5, 57.5);
        Position midDumpPos = new Position(-0.2, 47, 72);
        Position highDumpPos = new Position(-4.5, 41, 72);
        Position pipeLineUpOutsidePos = new Position(8, 39, 0);
        Position pipeLineUpInsidePos = new Position(38, 39, 0);
        Position startCheeseRunAt45Pos = new Position(48, 41, 45);
        Position deepInCheesePos = new Position(58, 52, 45);
        Position duckstart = new Position(-35, 60, 90);
        Position spinner = new Position(-56, 56, 0);
        Position nearDuckDump = new Position(-22, 36, 125);
        pt.slamraFieldStart = duckstart;
                //new Position(9.5, 60, 90); old start
        enableDelay = false; // set to false to disable the testing delays

        Task autoTask = new Task();

        move.addMoveToPositionToTask(autoTask, spinner, false);
        autoTask.addStep(() -> duckspinner.settings.runMode = 2);
        autoTask.addDelay(5000);
        autoTask.addStep(() -> duckspinner.settings.runMode = 1);
        autoTask.addStep(() -> arm.setToAPresetPosition((short)2));//dump high
        move.addMoveToPositionToTask(autoTask, nearDuckDump, true);


        robot.taskManager.getMain().addSequentialTask(autoTask);

        /*****************************************
         * Start main opmode running
         *****************************************/
        robot.init();

        intake.pause(true);

        waitForStart();
        robot.start();

        while(opModeIsActive()){
            robot.run();
            robot.sendTelemetry();
        }
        robot.stop();
    }
}