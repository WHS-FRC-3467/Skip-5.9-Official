/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems;

import com.ctre.phoenix.ParamEnum;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/*
 * Class MagicTalonSRX
 * 
 * An extension of the TalonSRX class that adds functionality for managing control loops
 * 
 * Manages PIDF constants and updates SmartDashboard
 * with information useful for running a TalonSRX in closed-loop mode.
 * 
 * Displayed on SmartDashboard:
 * P:
 * I:
 * I*100:
 * D:
 * F:
 * Setpoint:
 * Current Position:
 * Tolerance:
 * Current Error:
 * Output:
 * Enabled:
 * 
 */

public class MagicTalonSRX extends TalonSRX {
	
	// Use this to slow down PID loops for initial tuning
	private static double PEAK_OUTPUT = 1.0;
	
	// ID of this Talon on SmartDashboard
	private String m_name;
	
	// Profile slot number (also known as "ordinal Id")
	private int m_slotNum = 0;
    
	// Tolerance is expressed in raw encoder units
	// Calculate Tolerance as a percentage of overall range of encoder motion
	private int m_tolerance = 0;
	
    // Default setpoint
    private double m_setpoint = 0.0;
    
	// Default PIDF constants - see CTRE documentation for tuning procedure
	private double m_P = 0.0;     // factor for "proportional" control
    private double m_I = 0.0;     // factor for "integral" control
    private double m_D = 0.0;     // factor for "derivative" control
    private double m_F = 0.0;     // factor for feedforward term

    private int m_iZone = 0;	// Integral Zone
    
    // Default cruise velocity and acceleration - see CTRE documentation for tuning procedure
    private int m_cruiseVel = 0;
    private int m_accel = 0;
    
    // Controls display to SmartDashboard - turn this off after system is tuned
	private boolean m_debugging = false;
	
	public MagicTalonSRX(String name, int deviceID, int slotNum, boolean debugging) {
		
		super(deviceID);
		
		m_name = name;
		m_slotNum = slotNum;
        m_debugging = debugging;
        
        // Set all configuration parameters to factory defaults
        configFactoryDefault();
        
        // Turn on Brake mode
		setNeutralMode(NeutralMode.Brake);

 		// Set Grayhill encoder as default feedback device
		//final int TICKS_PER_ROTATION = 256;
		configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);

		/* Set relevant frame periods to be at least as fast as periodic rate */
		setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, 10);
		setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, 10);

		/* set the peak and nominal outputs, 1.0 means full */
		configNominalOutputForward(0, 0);
		configNominalOutputReverse(0, 0);
		configPeakOutputForward(PEAK_OUTPUT, 0);
		configPeakOutputReverse(-PEAK_OUTPUT, 0);

		/* config closed loop gains */
		configPIDF(m_slotNum, m_P, m_I, m_D, m_F);
		
		/* config motion parameters */
		configMotion(m_cruiseVel, m_accel, m_tolerance);

		/* zero the sensor (May not want to do this on startup?) */
		setSelectedSensorPosition(0, 0, 10);

	}
	
    public void configFeedbackSensor(FeedbackDevice fbd) {
        
        // Set alternative feedback device
		configSelectedFeedbackSensor(fbd, 0, 10);
    }

    public void runMotionMagic() {
		
		// Tell SmartDashboard to update m_setpoint
		updateSetpointFromDashboard(); 

		this.set(ControlMode.MotionMagic, m_setpoint);
		reportMotionToDashboard();
	}
	
	public void runMotionMagic(int setpoint) {
		
		this.set(ControlMode.MotionMagic, setpoint);
		m_setpoint = setpoint;
		writeMotionToDashboard();
		reportMotionToDashboard();
	}
	
	
    /**
    * Set the PID Controller gain parameters.
    * Set the proportional, integral, and differential coefficients.
    * @param p Proportional coefficient
    * @param i Integral coefficient
    * @param d Differential coefficient
    * @param f Feed forward coefficient
    */
    public synchronized void configPIDF(int slotnum, double p, double i, double d, double f) {
        m_P = p;
        m_I = i;
        m_D = d;
        m_F = f;
        m_slotNum = slotnum;
        
        selectProfileSlot(m_slotNum, 0);
        config_kP(m_slotNum, p, 0);
        config_kI(m_slotNum, i, 0);
        config_kD(m_slotNum, d, 0);
        config_kF(m_slotNum, f, 0);
        config_IntegralZone(0, m_iZone, 0);
        
        writePIDFToDashboard();
    }

	private void writePIDFToDashboard() {
		
		if (m_debugging) {
			SmartDashboard.putNumber(m_name + " P", configGetParameter(ParamEnum.eProfileParamSlot_P, m_slotNum, 0));
			SmartDashboard.putNumber(m_name + " I", configGetParameter(ParamEnum.eProfileParamSlot_I, m_slotNum, 0));
			SmartDashboard.putNumber(m_name + " D", configGetParameter(ParamEnum.eProfileParamSlot_D, m_slotNum, 0));
			SmartDashboard.putNumber(m_name + " F", configGetParameter(ParamEnum.eProfileParamSlot_F, m_slotNum, 0));
			SmartDashboard.putNumber(m_name + " I*100", (configGetParameter(ParamEnum.eProfileParamSlot_I, m_slotNum, 0))*100);
			SmartDashboard.putNumber(m_name + " Current Draw", getOutputCurrent());
		}
    }

    private void updatePIDFFromDashboard() {

    	// Assign
		double p = SmartDashboard.getNumber(m_name + " P", 0.0);
		double i = SmartDashboard.getNumber(m_name + " I", 0.0);
		double d = SmartDashboard.getNumber(m_name + " D", 0.0);
		double f = SmartDashboard.getNumber(m_name + " F", 0.0);
		configPIDF(0, p, i, d, f);
		
		writePIDFToDashboard();
	}
    
    public synchronized void configMotion(int cruiseV, int accel, int tolerance) {
		
		m_cruiseVel = cruiseV;
		m_accel = accel;
		m_tolerance = tolerance;
		
    	/* set acceleration and cruise velocity - see CTRE documentation for how to tune */
		configMotionCruiseVelocity(m_cruiseVel, 10);
		configMotionAcceleration(m_accel, 10);

        /* Set curve smoothing (0 - 8)*/
        configMotionSCurveStrength(1);

		/* Use the specified tolerance to set the allowable Closed-Loop error */
		configAllowableClosedloopError(m_slotNum, m_tolerance, 10);
    	
		writeMotionToDashboard();
    }
    
	private void writeMotionToDashboard() {

		// These are things that are set infrequently, and may be updated from SDB
		if (m_debugging) {

			SmartDashboard.putNumber(m_name + " Setpoint", m_setpoint);
	    	SmartDashboard.putNumber(m_name + " Tolerance", m_tolerance);
			SmartDashboard.putNumber(m_name + " Set Cruise Vel", m_cruiseVel);
	    	SmartDashboard.putNumber(m_name + " Set Accel", m_accel);
		}
    }
    
    public int getTolerance() {
    	return m_tolerance;
    }

    public void setTolerance(int allowable) {
        m_tolerance = allowable;
    }

	public void reportMotionToDashboard() {

		// These are things that we cannot change on SDB; just report their current values
		if (m_debugging) {
			SmartDashboard.putString(m_name + " ControlMode", getTalonControlMode());
	    	SmartDashboard.putNumber(m_name + " Position", getSelectedSensorPosition(0));
			SmartDashboard.putNumber(m_name + " Velocity", getSelectedSensorVelocity(0));
	    	SmartDashboard.putNumber(m_name + " MotorOutputPercent", getMotorOutputPercent());
			
		/* check if we are motion-magic-ing */
 			if (getControlMode() == ControlMode.MotionMagic) {
 
				SmartDashboard.putNumber(m_name + " ClosedLoopTarget", getClosedLoopTarget(0));
				SmartDashboard.putNumber(m_name + " ClosedLoopError", getClosedLoopError(0));

		/*
				// Print the Active Trajectory Point Motion Magic is servoing towards
	    		SmartDashboard.putNumber(m_name + " ActTrajVelocity", getActiveTrajectoryVelocity());
	    		SmartDashboard.putNumber(m_name + " ActTrajPosition", getActiveTrajectoryPosition());
	    		SmartDashboard.putNumber(m_name + " ActTrajHeading", getActiveTrajectoryHeading());
		*/
			}
		}
	}
	
    private void updateMotionFromDashboard() {

    	// Assign
		m_cruiseVel = (int) SmartDashboard.getNumber(m_name + " Set Cruise Vel", 0.0);
    	m_accel = (int) SmartDashboard.getNumber(m_name + " Set Accel", 0.0);
		m_tolerance = (int) SmartDashboard.getNumber(m_name + " Tolerance", 0.0);
		
    	// Update 
		configMotionCruiseVelocity(m_cruiseVel, 0);
		configMotionAcceleration(m_accel, 0);
		configAllowableClosedloopError(m_slotNum, m_tolerance, 0);

		writeMotionToDashboard();
    }
    
    public void updateSetpointFromDashboard() {
		m_setpoint = SmartDashboard.getNumber(m_name + " Setpoint", 0.0);
    }
    

	public void updateStats() {

		updatePIDFFromDashboard();
		updateMotionFromDashboard();
	}

    /**
	 * @return The current TalonSRX control mode
	 */
	public String getTalonControlMode() {
		
		ControlMode tcm = getControlMode();
		
		if (tcm == ControlMode.PercentOutput) {
			return "PercentOutput";
		}
		else if (tcm == ControlMode.Position) {
			return "Position";
		}
		else if (tcm == ControlMode.MotionMagic) {
			return "MotionMagic";
		}
		else if (tcm == ControlMode.Velocity) {
			return "Velocity";
		}
		else
			return "Problem";
	}
	
}