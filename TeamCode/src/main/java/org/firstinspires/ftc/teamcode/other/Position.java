package org.firstinspires.ftc.teamcode.other;

public class Position
{
	public double X,Y,R;

	public Position(double X, double Y, double R){
		this.X = X;
		this.Y = Y;
		this.R = R;
	}

	public Position(double[] vals){
		try{
			this.X = vals[0];
			this.Y = vals[1];
			this.R = vals[2];
		}
		catch (Exception e){}
	}

	public Position(){
		X = 0;
		Y = 0;
		R = 0;
	}

	public double[] toArray()
	{
		try{ return new double[]{X,Y,R}; }
		catch (Exception e){ return null; }
	}

	public Position switchXY(){
		return new Position(Y, X, R);
	}

	public Position invert(){
		return new Position(-Y, -X, -R);
	}

	public Position invertX(){
		return new Position(-X, Y, R);
	}

	public Position invertY(){
		return new Position(X, -Y, R);
	}

	public Position invertR(){
		return new Position(X, Y, -R);
	}

	public void add(Position pos2){
		X += pos2.X;
		Y += pos2.Y;
		R += pos2.R;
	}

	public void subtract(Position pos2){
		X -= pos2.X;
		Y -= pos2.Y;
		R -= pos2.R;
	}

	@Override
	public String toString(){
		return "X: " + X + ", Y: " + Y + ", R: " + R;
	}

	/*
	Pose2d toPose2d(boolean convertToMeters){
		if(convertToMeters) return new Pose2d(X * Constants.mPerInch, Y * Constants.mPerInch, new Rotation2d(R));
		return new Pose2d(X, Y, new Rotation2d(R));
	}

	 */
}
