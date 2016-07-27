package org.ladbury.smartpowerPkg;


public class Utility {
	public static final double DEVICE_AMP_TOLERANCE = 0.004; // tolerance within which two doubles are "equal"  in readings
	public static final double DEVICE_AMP_EQUAL = 0.004; // tolerance within which two doubles are "identical"  in readings
	public static final int DEVICE_WATT_TOLERANCE = 5;// tolerance within which two devices are "equal" (more precise because less noise)

	//
    // matching procedures, NB signs are ignored
    //
	public static boolean matches(double d1, double d2){ 
		return Math.abs(Math.abs(d1)-Math.abs(d2)) < DEVICE_AMP_TOLERANCE; 
	}

	public static boolean equal(double d1, double d2){ 
		return Math.abs(Math.abs(d1)-Math.abs(d2)) < DEVICE_AMP_EQUAL; 
	}

	public static boolean matches(int  i1, int i2){ 
		return Math.abs(Math.abs(i1)-Math.abs(i2)) < DEVICE_WATT_TOLERANCE; 
	}
}
