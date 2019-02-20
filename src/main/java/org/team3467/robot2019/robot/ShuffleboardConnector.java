package org.team3467.robot2019.robot;

import org.team3467.robot2019.subsystems.Limelight.Limelight;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

public class ShuffleboardConnector {
	public static NetworkTableInstance table = null;

  

    public static boolean getBooleanValue(String table, String key) {
       return Limelight.table.getTable(key).getEntry(key).getBoolean(false);
    }
    public static double getDoubleValue(String table, String key) {
        return Limelight.table.getTable(key).getEntry(key).getDouble(-9999.0);
     }
     public static String getStringValue(String table, String key) {
        return Limelight.table.getTable(key).getEntry(key).getString("N/A");
     }
    public static void setValue(String table, String key, String value) {
        getValue(key).setString(value);
    }
    public static void setValue(String table, String key, double value) {
        getValue(key).setDouble(value);
    }
    public static void setValue(String table, String key, boolean value) {
        getValue(key).setBoolean(value);
    }


    private static NetworkTableEntry getValue(String key) {
		if (table == null) {
			table = NetworkTableInstance.getDefault();
		}

		return table.getTable("shuffleboard").getEntry(key);
	}
    
}