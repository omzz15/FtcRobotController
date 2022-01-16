package org.firstinspires.ftc.teamcode.base.part;

import org.firstinspires.ftc.teamcode.base.Robot;
import org.firstinspires.ftc.teamcode.other.task.Task;
import org.firstinspires.ftc.teamcode.other.task.TaskRunner;

public class RobotPart {
    private String name;
    public Robot robot;
    public RobotPartHardware hardware;
    public RobotPartSettings settings;
    private TaskRunner taskRunner;

    public RobotPart(String name, Robot robot, RobotPartHardware hardware, RobotPartSettings settings){
        this.name = name;
        this.robot = robot;
        this.hardware = hardware;
        this.settings = settings;
        if(settings.makeTaskRunner){
            makeTaskRunner();
            if(settings.makeTeleOpCodeTask)addTeleOpToTaskRunner();
            if(settings.makeTelemetryTask)addTelemetryToTaskRunner();
        }
        onConstruct();
        robot.addPart(this);
    }

    public void makeTaskRunner(){
        taskRunner = new TaskRunner(name, robot.taskManager);
    }

    public TaskRunner getTaskRunner(){
        return taskRunner;
    }

    private void addTeleOpToTaskRunner(){
        Task t = new Task();
        t.addStep(() -> {teleOpCode();}, () -> (true));
        taskRunner.addTask("TeleOp Code", t, true, true);
    }

    private void addTelemetryToTaskRunner(){
        Task t = new Task();
        t.addStep(() -> {telemetry();}, () -> (true));
        taskRunner.addTask("Telemetry Code", t, true, true);
    }

    public void init(){
        if(hardware != null) hardware.onInit(robot);
        settings.onInit(robot);
        onInit();
        settings.initialized = true;
    }

    public void start(){
        if(settings.canStart()) {
            onStart();
            settings.started = true;
        }
    }

    public void pause(){
        if(taskRunner != null) taskRunner.stop();
        settings.running = false;
        onPause();
    }

    public void pauseTeleOp(){
        onTeleOpPause();
        taskRunner.getBackgroundTask("TeleOp Code").pause();
    }

    public void pauseTelemetry(){
        taskRunner.getBackgroundTask("Telemetry Code").pause();
    }

    public void unpause(){
        if(taskRunner != null) taskRunner.start();
        settings.running = true;
        onUnpause();
    }

    public void unpauseTeleOp(){
        onTeleOpUnpause();
        taskRunner.getTask("TeleOp Code").unpause();
    }

    public void unpauseTelemetry(){
        taskRunner.getBackgroundTask("Telemetry Code").unpause();
    }

    public void stop(){
        onStop();
        settings.started = false;
    }

    ////////////////////
    //abstract methods//
    ////////////////////
    public void onConstruct(){}

    public void onInit(){}

    public void onStart(){}

    public void onPause(){}

    public void onUnpause(){}

    public void onStop(){}

    public void teleOpCode(){}

    public void onTeleOpPause(){}

    public void onTeleOpUnpause(){}

    public void telemetry(){}
}
