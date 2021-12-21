package org.firstinspires.ftc.teamcode.other;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.base.Robot;

public class Utils {
    public static class Constants{
        public static final float mmPerInch        = 25.4f;
        public static final float mPerInch         = 0.0254f;
        public static final float mmTargetHeight   = 6 * mmPerInch;          // the height of the center of the target image above the floor
        public static final float halfField        = 72 * mmPerInch;
        public static final float halfTile         = 12 * mmPerInch;
        public static final float oneAndHalfTile   = 36 * mmPerInch;

        public static float[] inchesToMM(float[] arr){
            float[] out = new float[arr.length];
            for (int i = 0; i < arr.length; i++){
                out[i] = arr[i] * mmPerInch;
            }
            return out;
        }
    }

    public static class Math {
        public static double findAngleError(double currentAngle, double targetAngle)
        {
            targetAngle = scaleAngle(targetAngle);
            double angleError = currentAngle - targetAngle;
            if (angleError > 180) {
                angleError -= 360;
            } else if (angleError < -180) {
                angleError += 360;
            }
            return -angleError;
        }

        public static double scaleAngle(double angle)// scales an angle to fit in -180 to 180
        {
            if (angle > 180) { return angle - 360; }
            if (angle < -180) { return angle + 360; }
            return angle;
        }

        public static double getAngleFromXY(double X, double Y)
        {
            return java.lang.Math.atan2(X, Y)*(180 / java.lang.Math.PI);
        }

        public static double[] getXYFromAngle(double angle)
        {
            // deg to rad
            angle /= (180 / java.lang.Math.PI);

            //rad to X,Y
            double[] XY = new double[2];
            XY[0] = java.lang.Math.sin(angle);
            XY[1] = java.lang.Math.cos(angle);
            double total = java.lang.Math.abs(XY[0]) + java.lang.Math.abs(XY[1]);
            XY[0] /= total;
            XY[1] /= total;

            return XY;
        }

        public static int capInt(int val, int min, int max){
            return (val < min) ? min : (val > max) ? max : val;
        }

        public static double capDouble(double val, double min, double max){
            return (val < min) ? min : (val > max) ? max : val;
        }

        public static int getSign(double val){
            return val < 0 ? -1 : 1;
        }

        public static int getSign(float val){
            return val < 0 ? -1 : 1;
        }

        public static int getSign(int val){
            return val < 0 ? -1 : 1;
        }
    }

    public enum GamepadNum{
        ONE,
        TWO;

        public Gamepad getGamepad(Robot robot){
            if(this == GamepadNum.ONE)
                return robot.gamepad1;
            else if(this == GamepadNum.TWO)
                return robot.gamepad2;
            return null;
        }
    }
}
