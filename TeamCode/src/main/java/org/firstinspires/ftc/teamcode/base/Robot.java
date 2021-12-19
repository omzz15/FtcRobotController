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
import org.firstinspires.ftc.teamcode.other.thread.VirtualThreadManager;

import java.util.ArrayList;
import java.util.List;

public class Robot {
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
    //public VirtualThreadManager VTM = new VirtualThreadManager();
    public TaskManager taskManager = new TaskManager();
    public HardwareManager hardwareManager = new HardwareManager();

    ////////////////
    //constructors//
    ////////////////
    public Robot(LinearOpMode opMode){
        construct(opMode);
    }

    void construct(LinearOpMode opMode){
        this.opMode = opMode;
        this.hardwareMap = opMode.hardwareMap;
        this.gamepad1 = opMode.gamepad1;
        this.gamepad2 = opMode.gamepad2;
        this.telemetry = opMode.telemetry;
    }

    //////////////////
    //init and start//
    //////////////////
    public void init(){
        if(useDashboard) dashboard = FtcDashboard.getInstance();
            startTelemetry();
        //VTM.init();
        initParts();
    }

    public void start(){
        startParts();
        //VTM.start();
    }

    public void run(){
        runParts();
        //VTM.run();
        taskManager.run();
        hardwareManager.run();
        addAllTelemetry();
    }

    public void stop(){
        //VTM.stop();
        stopParts();
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


    //run and telemetry
    public void runParts(@NonNull List<RobotPart> parts){
        for(RobotPart part: parts)
            part.runPart();
    }
    public void runParts(){
        runParts(parts);
    }

    public void addAllTelemetry(@NonNull List<RobotPart> parts){
        for(RobotPart part: parts)
            part.addTelemetry();
    }
    public void addAllTelemetry(){
        addAllTelemetry(parts);
    }

    //pause and unpause
    public void pauseParts(@NonNull List<RobotPart> parts, boolean keepRunMode){
        for(RobotPart part: parts)
            part.pause(keepRunMode);
    }
    public void pauseParts(){
        pauseParts(parts, false);
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
