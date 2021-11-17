package org.firstinspires.ftc.teamcode.deprecated;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.base.part.RobotPartHardware;
import org.firstinspires.ftc.teamcode.parts.arm.Arm;

@Deprecated
public abstract class ThreadedRobotPart extends RobotPart implements Runnable {
    Thread thread;


    public ThreadedRobotPart(Robot robot, RobotPartHardware hardware, ThreadedRobotSettings settings){
        super(robot, hardware, settings);
    }

    @Override
    public void init() {
        super.init();
        if(((ThreadedRobotSettings) settings).makeThread){
            thread = new Thread(this);
            ((ThreadedRobotSettings) settings).threadMade = true;
        }
    }

    @Override
    public void run() {
        onThreadStart();
        while (!thread.isInterrupted()){
            threadLoop();
        }
        onThreadStop();
    }

    private void threadLoop(){
        if (((ThreadedRobotSettings) settings).threadRunMode == -2) {
            onThreadPause();
            ((ThreadedRobotSettings) settings).threadRunMode = 0;
        } else if (((ThreadedRobotSettings) settings).threadRunMode == -1){
            onThreadUnpause();
            ((ThreadedRobotSettings) settings).threadRunMode = 1;
        } else if (((ThreadedRobotSettings) settings).threadRunMode > 0) {
            onThreadRunLoop(((ThreadedRobotSettings) settings).threadRunMode);
        }
    }

    public void startThread(){
        if(((ThreadedRobotSettings) settings).threadMade) {
            thread.start();
            ((ThreadedRobotSettings) settings).threadOn = true;
        }
    }

    public void pauseThread(){
        ((ThreadedRobotSettings) settings).threadRunMode = -2;
    }

    public void unPauseThread(){
        ((ThreadedRobotSettings) settings).threadRunMode = -1;
    }

    public void stopThread(){
        if(((ThreadedRobotSettings) settings).threadMade) {
            thread.interrupt();
            ((ThreadedRobotSettings) settings).threadOn = false;
        }
    }


    ////////////////////
    //abstract methods//
    ////////////////////
    public abstract void onThreadStart();

    public abstract void onThreadPause();

    public abstract void onThreadUnpause();

    public abstract void onThreadStop();

    public abstract void onThreadRunLoop(short runMode);
}
