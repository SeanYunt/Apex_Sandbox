package com.scs.apex;

public class FakeDevice {
	
	private double maxAllowedSpeed = 100;
	private String deviceName = "";
	private double currentSpeed = 0.0;
	private double currentAmpDraw = 0.0;
	private boolean isMissionCritial;
	
	public FakeDevice(String string, boolean b) {
		deviceName = string;
		isMissionCritial = b;
		// TODO Auto-generated constructor stub
	}

	public double getCurrentSpeed() {
		return currentSpeed;
	}
	
	public void setCurrentSpeed(double currentSpeed) {
		if(currentSpeed >= maxAllowedSpeed) {
			this.currentSpeed = maxAllowedSpeed;
		} else {
			this.currentSpeed = currentSpeed;
		}
		setCurrentAmpDraw(this.currentSpeed*.1);
	}
	
	public double getCurrentAmpDraw() {
		return currentAmpDraw;
	}
	
	public void setCurrentAmpDraw(double currentAmpDraw) {
		this.currentAmpDraw = currentAmpDraw;
	}

	public boolean isMissionCritial() {
		return isMissionCritial;
	}

	public void setMissionCritial(boolean isMissionCritial) {
		this.isMissionCritial = isMissionCritial;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public double getMaxAllowedSpeed() {
		return maxAllowedSpeed;
	}

	public void setMaxAllowedSpeed(double maxAllowedSpeed) {
		this.maxAllowedSpeed = maxAllowedSpeed;
	}
	

}
