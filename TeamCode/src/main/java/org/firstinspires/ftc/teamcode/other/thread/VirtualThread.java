package org.firstinspires.ftc.teamcode.other.thread;

/**
 * use this to make a class that acts as a thread without needing a new thread
 * @deprecated USE TASKS INSTEAD
 */
@Deprecated
public interface VirtualThread {
	void run();

	void start();

	void stop();
}
