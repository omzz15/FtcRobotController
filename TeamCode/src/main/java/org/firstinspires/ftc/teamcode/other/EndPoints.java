package org.firstinspires.ftc.teamcode.other;

public class EndPoints {
	double min;
	double max;

	public EndPoints(double min, double max){
		this.min = min;
		this.max = max;
	}

	public double cap(double val){
			return (val < min) ? min : (val > max) ? max : val;
	}

	public int capInt(int val){
		return  (int) cap(val);
	}

	//public double doubleRange(double val, EndPoints endPoints){

	//}
}
