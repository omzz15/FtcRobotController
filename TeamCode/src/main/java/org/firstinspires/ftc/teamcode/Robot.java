package org.firstinspires.ftc.teamcode;

import static java.lang.Thread.currentThread;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.RobotPart;
import org.firstinspires.ftc.teamcode.base.RobotPartHardware;
import org.firstinspires.ftc.teamcode.base.RobotPartSettings;
import org.firstinspires.ftc.teamcode.drive.Drive;
import org.firstinspires.ftc.teamcode.drive.DriveHardware;
import org.firstinspires.ftc.teamcode.drive.DriveSettings;
import org.firstinspires.ftc.teamcode.other.InputSupplier;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTracker;
import org.firstinspires.ftc.teamcode.positiontracking.PositionTrackerSettings;

import java.util.ArrayList;
import java.util.List;

public class Robot{
    //user variables
    boolean useDashboard = true;
    boolean useTelemetry = true;
    InputSupplier stopSupplier = new InputSupplier(gamepad -> (gamepad.back));
    public boolean emergencyStop = false;

    //other variables
    public LinearOpMode opMode;
    public HardwareMap hardwareMap;
    public Gamepad gamepad1;
    public Gamepad gamepad2;
    FtcDashboard dashboard;
    Telemetry telemetry;
    TelemetryPacket dashboardPacket;
    public List<RobotPart> parts = new ArrayList<>();

    ////////////////
    //constructors//
    ////////////////
    Robot(LinearOpMode opMode){
        construct(opMode,null, null);
    }

    Robot(LinearOpMode opMode, List<RobotPartHardware> hardware, List<RobotPartSettings> settings){
        construct(opMode, hardware, settings);
    }

    void construct(LinearOpMode opMode, List<RobotPartHardware> hardware, List<RobotPartSettings> settings){
        this.opMode = opMode;
        this.hardwareMap = opMode.hardwareMap;
        this.gamepad1 = opMode.gamepad1;
        this.gamepad2 = opMode.gamepad2;
        this.telemetry = opMode.telemetry;

        if(settings != null && hardware != null) {
            new Drive(this, (DriveHardware) hardware.get(0),(DriveSettings) settings.get(0));
            new PositionTracker(this, (DriveHardware) hardware.get(1),(DriveSettings) settings.get(1));
        }
        else{
            new Drive(this);
            new PositionTracker(this);
        }
    }

    ////////
    //init//
    ////////
    void init(List<RobotPart> parts){
        initParts();
        if(useDashboard) dashboard = FtcDashboard.getInstance();
        startTelemetry();
    }

    void init(){
        init(parts);
    }

    ////////////////
    //part methods//
    ////////////////
    void initParts(List<RobotPart> parts){
        for(RobotPart part: parts)
            if(part.settings.usePart)part.init();
    }

    void initParts(){
        initParts(parts);
    }

    void startParts(){
        if(((PositionTrackerSettings) ((PositionTracker) getPartByClass(PositionTracker.class)).settings).useThread)
            ((PositionTracker) getPartByClass(PositionTracker.class)).startThread();
    }

    void runForTeleOp(List<RobotPart> parts){
        for(RobotPart part: parts)
            if(part.settings.runForTeleOp())part.runForTeleOp();
    }

    void runForTeleOp(){
        runForTeleOp(parts);
    }

    void addAllTelemetry(List<RobotPart> parts){
        for(RobotPart part: parts)
            if(part.settings.addTelemetry())part.addTelemetry();
    }

    void addAllTelemetry(){
        addAllTelemetry(parts);
    }

    public Object getPartByClass(Class partClass){
        for(Object part: parts){
            if(part.getClass().equals(partClass)) {
                return part;
            }
        }
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

    /////////
    //sleep//
    /////////
    public void delay(long ms){
        long last = System.currentTimeMillis();
        while(System.currentTimeMillis() - last < ms)
        {
            if(stop())break;
        }
    }

    public void sleep(long ms)
    {
        try {
            currentThread().sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    ////////
    //stop//
    ////////
    public boolean stop() { return emergencyStop || stopSupplier.getBoolean(gamepad1) || stopSupplier.getBoolean(gamepad2) || opMode.isStopRequested(); }
}
