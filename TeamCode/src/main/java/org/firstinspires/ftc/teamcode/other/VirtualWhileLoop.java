package org.firstinspires.ftc.teamcode.other;

import org.firstinspires.ftc.robotcore.external.Func;

import java.util.function.Function;

public class VirtualWhileLoop {
	private Function<Void, Boolean> check;
	private Function<Void, Void> run;
	private boolean done = false;

	public VirtualWhileLoop(){

	}

	public void startLoop(Function<Void, Void> run, Function<Void, Boolean> check){
		this.run = run;
		this.check = check;
		done = false;
	}

	public void run(){
		if(!done && !check.apply(null))
			run.apply(null);
		else{
			done = true;
		}
	}

	public boolean isDone(){
		return done;
	}
}
