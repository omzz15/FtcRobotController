package org.firstinspires.ftc.teamcode.vision;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.basethreaded.RobotThreadedPart;
import org.firstinspires.ftc.teamcode.other.Utils;

public class Vision extends RobotThreadedPart {
	/////////////////////////
	//objects and variables//
	/////////////////////////
	OpenGLMatrix lastLocation = null;
	VuforiaLocalizer vuforia  = null;
	VuforiaTrackables targets = null ;
	int cameraMonitorViewId;
	WebcamName webcamName;
	boolean usingWebcam = false;

	public boolean targetVisible = false;

	////////////////
	//constructors//
	////////////////
	public Vision(Robot robot, VisionSettings settings) {
		super(robot, null, settings);
	}

	public Vision(Robot robot){
		super(robot, null, new VisionSettings());
	}


	/////////////////
	//init and stop//
	/////////////////
	@Override
	public void init() {
		super.init();
		initCameraFully();
		initVuforia();
	}

	void initCameraFully(){
		checkCameraType();
		getCMVId();
		if(usingWebcam)
			getWebcamName();
	}

	void getWebcamName(){
		webcamName = robot.hardwareMap.get(WebcamName.class, ((VisionSettings) settings).webcamName);
	}

	void checkCameraType()
	{
		try
		{
			robot.hardwareMap.get(WebcamName.class, ((VisionSettings) settings).webcamName);
			usingWebcam = true;
		}
		catch(Exception e)
		{
			usingWebcam = false;
		}
	}

	void getCMVId() //gets the camera Id to make initialization easier for vision objects
	{
		cameraMonitorViewId = robot.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", robot.hardwareMap.appContext.getPackageName());
	}

	@Override
	public void stop() {

	}


	/////////////
	//telemetry//
	/////////////
	@Override
	public void addTelemetry() {

	}


	////////////////
	//thread stuff//
	////////////////
	@Override
	public void onThreadInit() {
		startTargets();
	}

	@Override
	public void onThreadLoop() {
		runVuforia();
	}


	///////////
	//vuforia//
	///////////
	//construct
	void constructVuforia(){
		VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
		parameters.vuforiaLicenseKey = ((VisionSettings) settings).VUFORIA_KEY;
		parameters.useExtendedTracking = false;

		if(usingWebcam) {
			parameters.cameraName = webcamName;
		}
		else {
			parameters.cameraDirection = ((VisionSettings) settings).CAMERA_CHOICE;
		}

		vuforia = ClassFactory.getInstance().createVuforia(parameters);
	}

	//init
	void initVuforia(){
		constructVuforia();
		loadAsset("FreightFrenzy");
		identifyAllTargets();
		setCameraTransform(((VisionSettings) settings).cameraPosition, ((VisionSettings) settings).phoneRotation);
	}

	void loadAsset(String assetName) { targets = vuforia.loadTrackablesFromAsset(assetName); } // loads the vuforia assets from a file

	void identifyAllTargets(){
		identifyTarget(0, "Blue Storage",       -Utils.Constants.halfField,  Utils.Constants.oneAndHalfTile, Utils.Constants.mmTargetHeight, 90, 0, 90);
		identifyTarget(1, "Blue Alliance Wall",  Utils.Constants.halfTile,   Utils.Constants.halfField,      Utils.Constants.mmTargetHeight, 90, 0, 0);
		identifyTarget(2, "Red Storage",        -Utils.Constants.halfField, -Utils.Constants.oneAndHalfTile, Utils.Constants.mmTargetHeight, 90, 0, 90);
		identifyTarget(3, "Red Alliance Wall",   Utils.Constants.halfTile,  -Utils.Constants.halfField,      Utils.Constants.mmTargetHeight, 90, 0, 180);
	}

	void identifyTarget(int targetIndex, String targetName, float dx, float dy, float dz, float rx, float ry, float rz) {
		VuforiaTrackable aTarget = targets.get(targetIndex);
		aTarget.setName(targetName);
		aTarget.setLocation(OpenGLMatrix.translation(dx, dy, dz)
				.multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, rx, ry, rz)));
	}

	void setCameraTransform(float[] position, float[] rotation){
		OpenGLMatrix robotFromCamera = OpenGLMatrix
				.translation(position[1], position[0], position[2])
				.multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, rotation[1], rotation[2], rotation[0]));

		/**  Let all the trackable listeners know where the phone is.  */
		for (VuforiaTrackable trackable : targets) {
			if(usingWebcam)
				((VuforiaTrackableDefaultListener) trackable.getListener()).setCameraLocationOnRobot(webcamName, robotFromCamera);
			else
				((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, ((VisionSettings) settings).CAMERA_CHOICE);
		}
	}

	//start
	void startTargets(){
		targets.activate();
	}

	void runVuforia(){
		targetVisible = false;
		for (VuforiaTrackable trackable : targets) {
			if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
				targetVisible = true;

				// getUpdatedRobotLocation() will return null if no new information is available since
				// the last time that call was made, or if the trackable is not currently visible.
				OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
				if (robotLocationTransform != null) {
					lastLocation = robotLocationTransform;
				}
				break;
			}
		}
	}

}
