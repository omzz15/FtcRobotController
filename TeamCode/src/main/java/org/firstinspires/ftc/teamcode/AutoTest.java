package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.arm2.Arm2;
import org.firstinspires.ftc.teamcode.parts.drive.Drive;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;
import org.firstinspires.ftc.teamcode.parts.movement.MovementSettings;
import org.firstinspires.ftc.teamcode.parts.positiontracker.PositionTracker;
import org.firstinspires.ftc.teamcode.parts.vision.Vision;

import java.util.ArrayList;
import java.util.List;

@TeleOp(name = "test auto", group = "Test")
public class AutoTest extends LinearOpMode {
	Position[] positions = {
		new Position(10, 0 , 0),
		new Position(10, 10, 90),
		new Position(0, 10, 180),
		new Position(0,0,270)
	};
	int delay = 100;

	@Override
	public void runOpMode(){
		Robot robot = new Robot(this);

		new Drive(robot);
		new PositionTracker(robot);
		Movement move = new Movement(robot);
		new Vision(robot);

		for(Position p : positions){
			robot.taskManager.getMain().addSequentialTask(move.addMoveToPositionToTask(new Task(), p, ((MovementSettings) move.settings).finalPosSettings));
		}

		robot.init();

		waitForStart();

		robot.start();

		while(opModeIsActive()){
			robot.run();
			if(robot.taskManager.getMain().sequentialTasksDone())
				robot.addTelemetry("task done!", "");
			robot.sendTelemetry();
		}

		robot.stop();
	}
}
