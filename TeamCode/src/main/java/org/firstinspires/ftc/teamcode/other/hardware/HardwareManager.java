package org.firstinspires.ftc.teamcode.other.hardware;

import java.util.Hashtable;

public class HardwareManager{
	private Hashtable<String, Hardware> hardware = new Hashtable<>();

	public void attachHardware(String key, Hardware hardware){
		this.hardware.put(key, hardware);
	}

	public void detachHardware(String key){
		this.hardware.remove(key);
	}

	public void home(){
		for (Hardware h: hardware.values()) {
			if(!h.homed)
				h.homed = h.home();
		}
	}

	public void run(){
		for (Hardware h: hardware.values()) {
			h.run();
		}
	}
}
