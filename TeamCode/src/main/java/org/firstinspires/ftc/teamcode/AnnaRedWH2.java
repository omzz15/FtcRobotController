package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DigitalChannel;

import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;

@Autonomous(name = "Anna Red Warehouse 2", group = "Test")
public class AnnaRedWH2 extends AnnaBlueWH{
    @Override
    public void setAutoVar (){
        robot.autoBlue = false;
        fieldStartPos = new Position(15, -60, -90);
        Task checkButton = new Task();

        RevTouchSensor ts = robot.hardwareMap.get(RevTouchSensor.class, "WallSwitch");

        //DigitalChannel limitSwitch;

        //limitSwitch = robot.hardwareMap.get(DigitalChannel.class, "WallSwitch");
        //limitSwitch.setMode(DigitalChannel.Mode.INPUT);

        //checkButton.addStep(() -> (!limitSwitch.getState()));
        checkButton.addStep(() -> (ts.isPressed()));

        checkButton.addStep(() -> {
            ((Movement) robot.getPartByClass(Movement.class)).stopMovement();
        });

        robot.taskManager.getMain().addTask("checkButton",checkButton, true, false);
    }
}
