package org.firstinspires.ftc.teamcode.parts.arm;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.other.Utils;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.other.task.TaskManager;
import org.firstinspires.ftc.teamcode.parts.intake.Intake;

public class Arm extends RobotPart {

	public Arm(Robot robot, ArmHardware hardware, ArmSettings settings) {
		super("Arm", robot, hardware, settings);
	}

	public Arm(Robot robot){
		super("Arm", robot, new ArmHardware(), new ArmSettings());
	}


	//////////////////
	//bucket methods//
	//////////////////



	////////////
	//Dock arm//
	////////////
	private void addDockArmTask(){
		Task task = new Task();
		Task.Step step;
		Task.EndPoint end;

		//step 1 - stop intake and arm and move bucket and arm
		step = () -> {
			if(robot.isTeleOpMode) {
				robot.getPartByClass(Intake.class).pauseTeleOp();
				pauseTeleOp();
			}
			setBucketToPreset((short) 4);//set bucket to cradle
			setArmToPreset((short) 4);//set arm to cradle
		};
		task.addStep(step);

		//step 2 - wait for bucket
		end = () -> (bucketDoneMoving());
		task.addStep(end);

		//step 3 - drop arm
		step = () -> {
			setArmToPreset((short) 1);//set arm to flat
		};
		task.addStep(step);

		//step 4 - wait for arm to finish
		end = () -> (armDoneMoving());
		task.addStep(end);

		//step 5 - drop bucket
		step = () -> {
			setBucketToPreset((short) 1);//set bucket to flat
		};
		task.addStep(step);

		//step 6 - wait for bucket to finish
		end = () -> (bucketDoneMoving());
		task.addStep(end);

		//step 7 - reset state
		step = () -> {
			if(robot.isTeleOpMode) {
				robot.getPartByClass(Intake.class).unpauseTeleOp();
				unpauseTeleOp();
			}
		};
		task.addStep(step);

		getTaskRunner().addTask("Dock Arm", task, true,false);
	}

	private void startDockArmTask(){
		getTaskRunner().getBackgroundTask("Dock Arm").start();
	}

	boolean doneDockingArm(){
		return getTaskRunner().getBackgroundTask("Dock Arm").isDone();
	}


	/////////////////////
	//RobotPart Methods//
	/////////////////////
	@Override
	public void teleOpCode() {

	}

	
	/////////
	//other//
	/////////
	public enum ArmPosition{
		FLAT((short) 0),
		DUMP((short) 1),
		F_DUMP((short) 2),
		CRADLE((short) 3);

		short value;

		ArmPosition(short value){
			this.value = value;
		}
	}
}
