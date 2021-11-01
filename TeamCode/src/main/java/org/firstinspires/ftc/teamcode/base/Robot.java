package org.firstinspires.ftc.teamcode.base;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.base.basethreaded.RobotThreadedPart;
import org.firstinspires.ftc.teamcode.base.basethreaded.RobotThreadedPartSettings;
import org.firstinspires.ftc.teamcode.other.inputsupplier.InputSupplier;

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
    public void init(short runMode){
        initParts(runMode);
        if(useDashboard) dashboard = FtcDashboard.getInstance();
        startTelemetry();
    }

    public void init(){
        init((short)1);
    }

    public void start(){
        startThreads();
    }


    ////////////////
    //part methods//
    ////////////////
    //init
    void initParts(List<RobotPart> parts, short runMode){
        for(RobotPart part: parts)
            if(part.settings.usePart)part.init(runMode);
    }

    public void initParts(short runMode){
        initParts(parts, runMode);
    }

    //thread
    void startThreads(List<RobotPart> parts){
        for(RobotPart part: parts)
            if(part instanceof RobotThreadedPart && part.settings.canUse() && ((RobotThreadedPartSettings) part.settings).canRunThread())
                ((RobotThreadedPart) part).startThread();
    }

    public void startThreads(){
        startThreads(parts);
    }

    void stopThreads(List<RobotPart> parts){
        for(RobotPart part: parts)
            if(part instanceof RobotThreadedPart && part.settings.canUse() && ((RobotThreadedPartSettings) part.settings).canRunThread())
                ((RobotThreadedPart) part).startThread();
    }

    public void stopThreads(){
        stopThreads(parts);
    }

    //run
    void runParts(List<RobotPart> parts){
        for(RobotPart part: parts)
            if(part.settings.canUse())part.runPart();
    }

    public void runParts(){
        runParts(parts);
    }

    void addAllTelemetry(List<RobotPart> parts){
        for(RobotPart part: parts)
            if(part.settings.addTelemetry())part.addTelemetry();
    }

    public void addAllTelemetry(){
        addAllTelemetry(parts);
    }

    //stop
    void stopParts(List<RobotPart> parts){
        for(RobotPart part: parts)
            part.stop();
    }

    public void stopParts(){
        stopParts(parts);
    }

    //other
    public RobotPart getPartByClass(Class partClass){
        for(RobotPart part: parts){
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
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    ////////
    //stop//
    ////////
    public boolean stop() { return emergencyStop || stopSupplier.getBoolean(gamepad1) || stopSupplier.getBoolean(gamepad2) || opMode.isStopRequested(); }
}
