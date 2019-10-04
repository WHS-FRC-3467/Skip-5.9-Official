package org.team3467.robot2019.subsystems.Limelight;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Wrapper class for getting and setting Limelight NetworkTable values.
 * 
 */
public class Limelight {
	public static NetworkTableInstance table = null;

	/**
	 * Gets whether a target is detected by the Limelight.
	 * 
	 * @return true if a target is detected, false otherwise.
	 */
	public static boolean isTarget() {
		return getValue("tv").getDouble(0) == 1;
	}

	/**
	 * Horizontal offset from crosshair to target (-27 degrees to 27 degrees).
	 * 
	 * @return tx as reported by the Limelight.
	 */
	public static double getTx() {
		return getValue("tx").getDouble(-100.00);
	}

	/**
	 * Vertical offset from crosshair to target (-20.5 degrees to 20.5 degrees).
	 * 
	 * @return ty as reported by the Limelight.
	 */
	public static double getTy() {
		return getValue("ty").getDouble(0.00);
	}

	/**
	 * Area that the detected target takes up in total camera FOV (0% to 100%).
	 * 
	 * @return Area of target.
	 */
	public static double getTa() {
		return getValue("ta").getDouble(0.00);
	}

	/**
	 * Gets target skew or rotation (-90 degrees to 0 degrees).
	 * 
	 * @return Target skew.
	 */
	public static double getTs() {
		return getValue("ts").getDouble(0.00);
	}

	/**
	 * Gets target latency (ms).
	 * 
	 * @return Target latency.
	 */
	public static double getTl() {
		return getValue("tl").getDouble(0.00);
	}

    /*
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("<variablename>").setNumber(<value>);
    
    ledMode	Sets limelight’s LED state
        0	use the LED Mode set in the current pipeline
        1	force off
        2	force blink
        3	force on
    camMode	Sets limelight’s operation mode
        0	Vision processor
        1	Driver Camera (Increases exposure, disables vision processing)
    pipeline	Sets limelight’s current pipeline
        0 .. 9	Select pipeline 0..9
    stream	Sets limelight’s streaming mode
        0	Standard - Side-by-side streams if a webcam is attached to Limelight
        1	PiP Main - The secondary camera stream is placed in the lower-right corner of the primary camera stream
        2	PiP Secondary - The primary camera stream is placed in the lower-right corner of the secondary cam

    */
    
    /**
	 * Light modes for Limelight.
	 */
	public static enum LightMode {
		ePipeline, eOff, eBlink, eOn
	}

	/**
	 * Camera modes for Limelight.
	 */
	public static enum CameraMode {
		eVision, eDriver
	}

	/**
	 * Stream modes for Limelight.
	 */
	public static enum StreamMode {
		eStandard, ePIPMain, ePIPSecondary
	}

	/**
	 * Sets LED mode of Limelight.
	 * 
	 * @param mode
	 *            Light mode for Limelight.
	 */
	public static void setLedMode(LightMode mode) {
		getValue("ledMode").setNumber(mode.ordinal());
	}

	/**
	 * Sets camera mode for Limelight.
	 * 
	 * @param mode
	 *            Camera mode for Limelight.
	 */
	public static void setCameraMode(CameraMode mode) {
		getValue("camMode").setNumber(mode.ordinal());
	}

	/**
	 * Sets pipeline number (0-9 value).
	 * 
	 * @param number
	 *            Pipeline number (0-9).
	 */
	public static void setPipeline(int number) {
		getValue("pipeline").setNumber(number);
	}

   	/**
	 * Sets streaming mode for Limelight.
	 * 
	 * @param mode
	 *            Stream mode for Limelight.
	 */
	public static void setStreamMode(StreamMode mode) {
		getValue("stream").setNumber(mode.ordinal());
	}


	/**
	 * Sets Limelight to "Driver" mode.
	 */
	public static void setDriverMode() {
        setCameraMode(CameraMode.eDriver);
        setPipeline(0);
        setLedMode(LightMode.eOff);
	}

	/**
	 * Sets Limelight to "Vision" mode.
	 */
	public static void setVisionMode(int pipelinenumber) {
        setCameraMode(CameraMode.eVision);
        setPipeline(pipelinenumber);
        setLedMode(LightMode.ePipeline);
	}

	/**
	 * Helper method to get an entry from the Limelight NetworkTable.
	 * 
	 * @param key
	 *            Key for entry.
	 * @return NetworkTableEntry of given entry.
	 */
	private static NetworkTableEntry getValue(String key) {
		if (table == null) {
			table = NetworkTableInstance.getDefault();
		}

		return table.getTable("limelight").getEntry(key);
	}
}