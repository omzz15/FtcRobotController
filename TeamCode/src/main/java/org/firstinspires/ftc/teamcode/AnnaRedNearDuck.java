package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.other.Position;

@Autonomous(name = "Anna Red NearDuck", group = "Test")
public class AnnaRedNearDuck extends AnnaBlueNearDuck{
    @Override
    public void setAutoVar (){
        robot.autoBlue = false;
        // Manually reversed in this
        duckstart = new Position(-35.5, -63, -90);
        // Already reversed in movement tasks
        //spinnerPos = new Position(-58, 55, 0);
        spinnerPos = new Position(-54, 58, 0);
    }
}
