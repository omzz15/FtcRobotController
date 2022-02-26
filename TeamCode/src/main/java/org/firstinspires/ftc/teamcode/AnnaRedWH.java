package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevTouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.teamcode.other.Position;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.parts.movement.Movement;

@Autonomous(name = "Anna Red Warehouse", group = "Test")
public class AnnaRedWH extends AnnaBlueWH{
    @Override
    public void setAutoVar (){
        robot.autoBlue = false;
        fieldStartPos = new Position(15, -60, -90);
        pt.useLeftSlamra();
    }
}
