package org.team3467.robot2019.subsystems.Drivetrain;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveBot extends Command {
	
	/**
	 * Input adjustment switches
	 */
	// Don't adjust the sticks at all
	static final boolean NO_STICK_ADJUSTMENT = false;
	// square the inputs (while preserving the sign) to increase fine control while permitting full power
	static final boolean SQUARE_INPUTS = true;
	// Limit the slew rate (rate of control signal change)
	static final boolean SLEW_LIMITING = true;
	// Set up "deadband" around center (off) stick position
	static final boolean USE_DEADBAND = true;
	
	// Deadband limit
	static final double deadband_limit = 0.02;
	
	// Scale factor for reducing inputs during Precision Mode
	static final double precision_scaleFactor = 0.5;
	
	// Drive interface mode
	public static final int driveMode_Tank = 0;
	public static final int driveMode_Rocket = 1;
	public static final int driveMode_RocketSpin = 2;
	
	private static final String[] driveModeNames = {
			"Tank",
			"Rocket League",
			"Rocket League Spin"
	};

	// Default drive mode to Rocket drive
	int m_driveMode = driveMode_Rocket;

	// Precision mode scales control inputs to allow for finer control
	// Off by default
	boolean m_precision = false;
	
	// Save last inputs (for slew rate limiting)
	double m_lastCtrlLeftX = 0.0;
	double m_lastCtrlLeftY = 0.0;
	double m_lastCtrlRightX = 0.0;
	double m_lastCtrlRightY = 0.0;
	double m_lastCtrlLeftTrig = 0.0;
	double m_lastCtrlRightTrig = 0.0;
	
	/**
	 * Constructor
	 * @param driveMode - (int) drive control mode
	 * @param precisionMode - (boolean) Scale inputs down for finer precision
	 */
	public DriveBot(int driveMode, boolean precisionMode) {
		requires(Robot.sub_drivetrain);
		this.setInterruptible(true);
	
		m_precision = precisionMode;
		if (m_precision == false) {
			// Only change driveMode if not in Precision mode
			// (i.e. Precision mode inherits the current driveMode)
			m_driveMode = driveMode;
		}
	}
	
	protected void initialize() {

		// Don't set or display drive mode until this command is actually underway
		// Otherwise, all the instances of this command instantiated in OI will change the driveMode prematurely
		Robot.sub_drivetrain.setDriveControlMode(m_driveMode);
		SmartDashboard.putString("Drive Mode", driveModeNames[m_driveMode]);
		SmartDashboard.putString("Talon Mode", Robot.sub_drivetrain.getTalonControlMode());
	}

	protected void execute() {

		// driveBase.reportEncoders();
		
		switch (m_driveMode) {
		
		default:
		case driveMode_Tank:
			Robot.sub_drivetrain.driveTank(getControllerLeftStickY(), getControllerRightStickY());
			break;
		
		case driveMode_Rocket:
		case driveMode_RocketSpin:
			double speed = 0.0;
			double backSpeed = getControllerLeftTrigger();
			double fwdSpeed = getControllerRightTrigger();
			if (backSpeed != 0.0 && fwdSpeed != 0.0)
				speed = 0.0;
			else if (fwdSpeed > 0.0)
				speed = fwdSpeed;
			else if (backSpeed > 0.0)
				speed = -1.0 * backSpeed;
			
			Robot.sub_drivetrain.drive(speed, getControllerLeftStickX(), (m_driveMode == driveMode_RocketSpin));

			Robot.sub_drivetrain.reportEncoders();
			break;
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
	}

	protected void interrupted() {
		end();
	}
	

	private double getControllerLeftStickX() {

		return (m_lastCtrlLeftX = adjustStick(OI.getDriverLeftX(), m_lastCtrlLeftX, 0.20)); 

	}

	private double getControllerLeftStickY() {

		return (m_lastCtrlLeftY = adjustStick(OI.getDriverLeftY() * (-1.0), m_lastCtrlLeftY, 0.20)); 

	}

		private double getControllerRightStickY() {

		return (m_lastCtrlRightY = adjustStick(OI.getDriverRightY() * (-1.0), m_lastCtrlRightY, 0.20)); 

	}
	
	private double getControllerLeftTrigger() {

		return (m_lastCtrlLeftTrig = adjustStick(OI.getDriverLeftTrigger(), m_lastCtrlLeftTrig, 0.20)); 

	}

	private double getControllerRightTrigger() {

		return (m_lastCtrlRightTrig = adjustStick(OI.getDriverRightTrigger(), m_lastCtrlRightTrig, 0.20)); 

	}
	
	private double adjustStick(double input, double lastVal, double changeLimit) {
		
		double val = input;
		double change;
		
		if (NO_STICK_ADJUSTMENT) {
			return input;
		}

		/*
		 *  Deadband limit
		 */
		if (USE_DEADBAND) {
			if ((val > (-1.0 * deadband_limit)) && (val < deadband_limit)) {
				// Just return from here
				return(0.0);
			}
		}

        /*
         *  Square the inputs (while preserving the sign) to increase
		 *  fine control while permitting full power
         */
		if (SQUARE_INPUTS) {
	        if (val > 0.0)
	            val = (val * val);
	        else
	            val = -(val * val);
		}
        
		/*
         *  Slew rate limiter - limit rate of change
         */
        if (SLEW_LIMITING) {
    		change = val - lastVal;
    		
    		if (change > changeLimit)
    			change = changeLimit;
    		else if (change < -changeLimit)
    			change = -changeLimit;
    		
    		val = lastVal += change;
        }

        /*
         * Precision mode scaling - do this last
         */
        if (m_precision) {
        	val = val * precision_scaleFactor;
        }
        
        return val;
		
	}
}