package org.firstinspires.ftc.teamcode.base;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.part.RobotPart;
import org.firstinspires.ftc.teamcode.other.hardware.HardwareManager;
import org.firstinspires.ftc.teamcode.other.input.InputSupplier;
import org.firstinspires.ftc.teamcode.other.task.TaskManager;

import java.util.ArrayList;
import java.util.List;

public class Robot {
    public boolean isTeleOpMode;

    boolean useDashboard = true;
    boolean useTelemetry = true;

    public LinearOpMode opMode;
    public HardwareMap hardwareMap;
    public Gamepad gamepad1;
    public Gamepad gamepad2;
    private FtcDashboard dashboard;
    private Telemetry telemetry;
    private TelemetryPacket dashboardPacket;
    private List<RobotPart> parts = new ArrayList<>();
    public Canvas field;

    private boolean stopRequested = false;
    private InputSupplier stopSupplier = new InputSupplier((gamepad) -> (gamepad.back));

    //Managers
    public TaskManager taskManager = new TaskManager();
    public HardwareManager hardwareManager = new HardwareManager();

    ////////////////
    //constructors//
    ////////////////
    public Robot(LinearOpMode opMode, boolean isTeleOpMode){
        construct(opMode, isTeleOpMode);
    }

    void construct(LinearOpMode opMode, boolean isTeleOpMode){
        this.opMode = opMode;
        this.hardwareMap = opMode.hardwareMap;
        this.gamepad1 = opMode.gamepad1;
        this.gamepad2 = opMode.gamepad2;
        this.telemetry = opMode.telemetry;
        this.isTeleOpMode = isTeleOpMode;
    }

    //////////////////
    //init and start//
    //////////////////
    public void init(){
        if(useDashboard) dashboard = FtcDashboard.getInstance();
            startTelemetry();
        initParts();
    }

    public void start(){
        startParts();
        taskManager.start();
    }

    public void run() {
        taskManager.run();
        hardwareManager.run();
    }

    public void stop(){
        stopParts();
        taskManager.stop();
    }

    public boolean shouldStop(){
        return stopRequested || stopSupplier.getBoolean(gamepad1) || stopSupplier.getBoolean(gamepad2) || opMode.isStopRequested();
    }

    ////////////////
    //part methods//
    ////////////////
    //init and start
    public void initParts(@NonNull List<RobotPart> parts){
        for (RobotPart part: parts)
            part.init();
    }
    public void initParts(){
        initParts(parts);
    }

    public void startParts(@NonNull List<RobotPart> parts){
        for (RobotPart part: parts)
            part.start();
    }
    public void startParts(){
        startParts(parts);
    }

    public void stopParts(@NonNull List<RobotPart> parts){
        for (RobotPart part: parts)
            part.stop();
    }
    public void stopParts(){
        stopParts(parts);
    }

    //pause and unpause
    public void pauseParts(@NonNull List<RobotPart> parts){
        for(RobotPart part: parts)
            part.pause();
    }
    public void pauseParts(){
        pauseParts(parts);
    }

    public void unpauseParts(@NonNull List<RobotPart> parts){
        for(RobotPart part: parts)
            part.unpause();
    }
    public void unpauseParts(){
        unpauseParts(parts);
    }

    //other
    public void addPart(RobotPart part){
        parts.add(part);
    }

    public void removePart(RobotPart part){
        parts.remove(part);
    }

    public void removePart(int index){
        parts.remove(index);
    }

    public RobotPart getPartByClass(Class partClass){
        for(RobotPart part: parts)
            if (part.getClass().equals(partClass))
                return part;
        return null;
    }

    /////////////
    //telemetry//
    /////////////
    public void startTelemetry()
    {
        if(useDashboard)
        {
            dashboardPacket = new TelemetryPacket();
            field = dashboardPacket.fieldOverlay();
        }
    }

    public void addTelemetry(String cap, Object val)
    {
        if(useDashboard) dashboardPacket.put(cap, val);
        if(useTelemetry) telemetry.addData(cap, val);
    }

    public void sendTelemetry()
    {
        if(useDashboard) dashboard.sendTelemetryPacket(dashboardPacket);
        if(useTelemetry) telemetry.update();
    }
}
