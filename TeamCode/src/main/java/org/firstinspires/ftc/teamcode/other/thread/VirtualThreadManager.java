package org.firstinspires.ftc.teamcode.other.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * this is a container class to store and run virtual threads on a single thread
 * @deprecated USE TASK RUNNER INSTEAD
 */
@Deprecated
public class VirtualThreadManager implements Runnable{
	private List<VirtualThread> threads = new ArrayList<>();
	private Thread thread;


	public void init(){
		thread = new Thread(this);
	}

	public void add(VirtualThread thread){
		threads.add(thread);
	}

	public void run(){
		while (!thread.isInterrupted()) {
			for (VirtualThread t : threads)
				t.run();
		}
	}

	public void stop(){
		thread.interrupt();
		for(VirtualThread t: threads)
			t.stop();
	}

	public void start(){
		for (VirtualThread t : threads)
			t.start();
		thread.start();
	}
}
