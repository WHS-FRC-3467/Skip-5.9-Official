package org.team3467.robot2019.subsystems.Gyro;

import org.team3467.robot2019.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Gyro extends Subsystem {

	PigeonIMU m_pidgey;
	PigeonIMU.GeneralStatus m_genStatus;
	double [] m_ypr = new double[3];
	double [] m_xyz_dps = new double [3];
	
	// Static subsystem reference
	private static Gyro imuInstance = new Gyro();

	public static Gyro getInstance() {
		return Gyro.imuInstance;
	}
	
	/* Constructor */
	protected Gyro() {
		
		TalonSRX imuTalon = null;
		m_pidgey = new PigeonIMU(imuTalon);
		
		m_genStatus = new PigeonIMU.GeneralStatus();

		/* reset heading, angle measurement wraps at plus/minus 23,040 degrees (64 rotations) */
		m_pidgey.setYaw(0.0, 10);
		
	}
	
    // Set the default command for a subsystem here.
    public void initDefaultCommand() {
        setDefaultCommand(new Gyro_ReportStatus());
    }
    
    /* Update all the data and display to Dashboard */
    public void displayGyroUpdate() {

		/* grab data from Pigeon */
		m_pidgey.getGeneralStatus(m_genStatus);
		m_pidgey.getRawGyro(m_xyz_dps);
		m_pidgey.getYawPitchRoll(m_ypr);
		
		SmartDashboard.putNumber("Current Angle",  m_ypr[0]);
		SmartDashboard.putBoolean("Is Angle Good?", (m_pidgey.getState() == PigeonIMU.PigeonState.Ready) ? true : false);
		SmartDashboard.putNumber("Current Angular Rate", m_xyz_dps[2]);

    }
    
    public boolean isAngleValid() {
    	return(m_pidgey.getState() == PigeonIMU.PigeonState.Ready);
    }
    
    public double getCurrentAngle() {
		m_pidgey.getYawPitchRoll(m_ypr);
		return (m_ypr[0]);
    }
    
    public double getcurrentAngularRate() {
		m_pidgey.getRawGyro(m_xyz_dps);
		return (m_xyz_dps[2]);
    }
    
    public void zeroAngle() {
    	m_pidgey.setYaw(0.0, 0);
    }
}

