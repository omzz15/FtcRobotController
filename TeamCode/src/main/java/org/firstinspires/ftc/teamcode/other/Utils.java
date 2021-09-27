package org.firstinspires.ftc.teamcode.other;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.hardware.Gamepad;

import java.util.function.Function;

public class Utils {
	public static class AngleMath{
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
			return Math.atan2(X, Y)*(180 / Math.PI);
		}

		public static double[] getXYFromAngle(double angle)
		{
			// deg to rad
			angle /= (180 / Math.PI);

			//rad to X,Y
			double[] XY = new double[2];
			XY[0] = Math.sin(angle);
			XY[1] = Math.cos(angle);
			double total = Math.abs(XY[0]) + Math.abs(XY[1]);
			XY[0] /= total;
			XY[1] /= total;

			return XY;
		}
	}

	public enum GamepadNum{
		ONE,
		TWO;
	}
}

