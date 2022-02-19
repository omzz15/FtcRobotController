package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.other.Position;

@Autonomous(name = "Anna Red Warehouse", group = "Test")
public class AnnaRedWH extends AnnaBlueWH{
    @Override
    public void setAutoVar (){
        robot.autoBlue = false;
        fieldStartPos = new Position(15, -60, -90);
    }
}
