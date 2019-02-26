package org.team3467.robot2019.subsystems.Drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team3467.robot2019.robot.RobotGlobal;
import org.team3467.robot2019.subsystems.Drivetrain.DriveBot;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drivetrain extends Subsystem {

	// Motor controller objects and RobotDrive object
	private final WPI_TalonSRX left_talon, right_talon;
	private final WPI_VictorSPX left_victor_1, left_victor_2, right_victor_1, right_victor_2;
	
	private final DifferentialDrive m_drive;
	private ControlMode 			m_talonControlMode;
	private int						m_driveMode;

	// Static subsystem reference
	private static Drivetrain dTInstance = new Drivetrain();

	public static Drivetrain getInstance() {
		return Drivetrain.dTInstance;
	}
	
	//Drivetrain class constructor
	public Drivetrain() {

		// Three motors per side -> three speed controllers per side
        left_victor_1 = new WPI_VictorSPX(RobotGlobal.DRIVEBASE_VICTOR_L1);
        left_victor_2 = new WPI_VictorSPX(RobotGlobal.DRIVEBASE_VICTOR_L2);
        left_talon = new WPI_TalonSRX(RobotGlobal.DRIVEBASE_TALON_L);
        right_victor_1 = new WPI_VictorSPX(RobotGlobal.DRIVEBASE_VICTOR_R1);
        right_victor_2 = new WPI_VictorSPX(RobotGlobal.DRIVEBASE_VICTOR_R2);
        right_talon = new WPI_TalonSRX(RobotGlobal.DRIVEBASE_TALON_R);

		// Slave the extra Talons on each side
        left_victor_1.follow(left_talon);
        left_victor_2.follow(left_talon);
        right_victor_1.follow(right_talon);
        right_victor_2.follow(right_talon);
		
		// Flip any sensors?
		left_talon.setSensorPhase(true);
		
		// Invert all motors (until we figure out why controls are backward)
 		left_talon.setInverted(false);
		left_victor_1.setInverted(false);
		left_victor_2.setInverted(false);
		right_talon.setInverted(false);
		right_victor_1.setInverted(false);
		right_victor_2.setInverted(false);

		// Turn off Brake mode
		setTalonBrakes(false);
		
		// Set default control Modes for Master Talons
		setControlMode(ControlMode.PercentOutput);
		
 		// Set encoders as feedback device
		left_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		right_talon.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
		
		// Instantiate DifferentialDrive
		m_drive = new DifferentialDrive(left_talon, right_talon);
		
		// DifferentialDrive Parameters
		m_drive.setSafetyEnabled(false);
		m_drive.setExpiration(1.0);
		m_drive.setMaxOutput(1.0);


	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new DriveBot(DriveBot.driveMode_Rocket, false));
    }
    
	public WPI_TalonSRX getLeftTalon() {
		return left_talon;
	}
	
	public WPI_TalonSRX getRightTalon() {
		return right_talon;
	}
	
    
	//Use standard Tank Drive method
	public void driveTank (double leftSpeed, double rightSpeed) {
		m_drive.tankDrive(leftSpeed, rightSpeed, false);
	}

	// Use single-stick Arcade Drive method
	public void driveArcade(double move, double rotate) {
		m_drive.arcadeDrive(move, rotate, false);
	}

	// Use DifferentialDrive curvatureDrive() method
	public void drive(double outputMagnitude, double curve, boolean spin) {
		m_drive.curvatureDrive(outputMagnitude, curve, spin);
	}

	/**
	 * @param controlMode Set the control mode of the left and
	 * right master WPI_TalonSRXs
	 */
	public void setControlMode(ControlMode controlMode) {
		left_talon.set(controlMode, 0.0);
		right_talon.set(controlMode, 0.0);
		
		// Save control mode so we will know if we have to set it back later
		m_talonControlMode = controlMode;
	}
	
	/**
	 * @return The current WPI_TalonSRX control mode
	 */
	public String getTalonControlMode() {
		if (m_talonControlMode == ControlMode.PercentOutput) {
			return "PercentVbus";
		}
		else if(m_talonControlMode == ControlMode.Position) {
			return "Position";
		}
		else
			return "Problem";
	}
	
	/**
	 * Sets the drive control mode
	 * @param driveMode - values defined in DriveBot
	 */
	public void setDriveControlMode(int driveMode) {
		m_driveMode = driveMode;
	}
	
	/**
	 * Gets the drive control mode
	 * @return driveMode - values defined in DriveBot
	 */
	public int getDriveControlMode() {
		return m_driveMode;
	}
	
	
	/**
	 * Sets the brake mode for ALL WPI_TalonSRXs
	 * @param setBrake Enable brake mode?
	 */
	public void setTalonBrakes(boolean setBrake) {

		NeutralMode nm = setBrake ? NeutralMode.Brake : NeutralMode.Coast;
		
		left_talon.setNeutralMode(nm);
		left_victor_1.setNeutralMode(nm);
		left_victor_2.setNeutralMode(nm);
		right_talon.setNeutralMode(nm);
		right_victor_1.setNeutralMode(nm);
		right_victor_2.setNeutralMode(nm);
		
		SmartDashboard.putBoolean("Talon Brakes", setBrake);
	}
	
	
 	// @return Average of the encoder values from the left and right encoders
	public double getDistance() {
		return (left_talon.getSelectedSensorPosition(0) + 
//				(right_talon.getSelectedSensorPosition(0) * -1.0))/2;
				right_talon.getSelectedSensorPosition(0) ) / 2;
	}

	public void reportEncoders() {
		SmartDashboard.putNumber("Left Encoder", left_talon.getSelectedSensorPosition(0));
//		SmartDashboard.putNumber("Right Encoder", right_talon.getSelectedSensorPosition(0) * -1.0);			
		SmartDashboard.putNumber("Right Encoder", right_talon.getSelectedSensorPosition(0));			
	}

	public void resetEncoders() {
		left_talon.setSelectedSensorPosition(0, 0, 0);
		right_talon.setSelectedSensorPosition(0, 0, 0);
	}

}