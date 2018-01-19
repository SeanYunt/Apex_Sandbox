package com.scs.apex;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CumulativeMaxCurrent {

	public static final double MAX_CURRENT = 25.5;
	public static final long START_TIME = System.currentTimeMillis();
	private List<FakeDevice> deviceList = new ArrayList<FakeDevice>();

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CumulativeMaxCurrent program = new CumulativeMaxCurrent();

	}
	
	public CumulativeMaxCurrent() {
		
		deviceList.add(new FakeDevice("device1", true));
		deviceList.add(new FakeDevice("device2", true));
		deviceList.add(new FakeDevice("device3", false));
		deviceList.add(new FakeDevice("device4", true));
		setupThreads();
	}
	
	private void setupThreads() {
	   Runnable pollingCurrentThread = new Runnable() {
		   public void run() {
			   while(true) {
		        	try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				   for(int i=0;i<deviceList.size();i++) {
					   deviceList.get(i).setCurrentSpeed(getRandomInput());
					   System.out.println(System.currentTimeMillis()-START_TIME+": "+getTotalCurrentDraw());
				   }
			   }
		   }
	   };	
	   Runnable checkForExcessiveDrawThread = new Runnable() {
		   public void run() {
				//poll for excessive current draw
				while(true) {
		        	try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
					if(getTotalCurrentDraw() >= CumulativeMaxCurrent.MAX_CURRENT) {
						System.err.println(System.currentTimeMillis()-START_TIME+": amp draw of "+MAX_CURRENT+" exceeded!");
						Iterator<FakeDevice> it = deviceList.iterator();
						while(it.hasNext()) {
							FakeDevice device = it.next();
							if(device.isMissionCritial()) {
								continue;
							}
							System.err.println("reducing speed on non-critical  "+device.getDeviceName());
							System.err.println(device.getDeviceName() + " speed "+ device.getCurrentSpeed());
							System.err.println(device.getDeviceName() + " current "+ device.getCurrentAmpDraw());
							
							//reduce device speed until below max current draw
							while(getTotalCurrentDraw() >= CumulativeMaxCurrent.MAX_CURRENT) {
								device.setCurrentSpeed(device.getCurrentSpeed()*.90);
							}
							System.err.println(device.getDeviceName() + " speed "+ device.getCurrentSpeed());
							System.err.println(device.getDeviceName() + " current "+ device.getCurrentAmpDraw());

						}
					}
				}
		   }
	   };
	   
	   Thread thread1 = new Thread(pollingCurrentThread);
	   thread1.start();
	   Thread thread2 = new Thread(checkForExcessiveDrawThread);
	   thread2.start();
	}
	
	//simulate input from the RoboRio amp draw
	private double getRandomInput() {
		Random r = new Random();
		int maxPerThing = 100;
		double result = r.nextDouble()*maxPerThing;
		return result;
	}
	
	private double getTotalCurrentDraw() {
		double totalCurrent=0;
    	for(int i=0;i<deviceList.size();i++) {
    		totalCurrent += deviceList.get(i).getCurrentAmpDraw();
    	}
    	return totalCurrent;
	}

	
}
