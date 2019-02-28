package org.team3467.robot2019.subsystems.CargoIntake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team3467.robot2019.robot.RobotGlobal;
import org.team3467.robot2019.subsystems.MagicTalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class CargoIntake extends Subsystem
{

    public enum eCargoIntakeArmPosition
    { 
        CRAWL(0000,"CRAWL"),
        INTAKE(0000, "INTAKE"),
        RETRACTED(0000, "RETRACTED");

        private int IntakeArmPosition;
        private String IntakeArmPositionName;

        private eCargoIntakeArmPosition(int pos, String name) {
                IntakeArmPosition = pos;
                IntakeArmPositionName = name;   
        }

        public int getSetpoint() {
            return IntakeArmPosition;
        }

        public String getSetpointName() {
            return IntakeArmPositionName;
        }
    }

    private eCargoIntakeArmPosition activeIntakeArmPosition;

    MagicTalonSRX   m_intakeArm = new MagicTalonSRX("Cargo Intake", RobotGlobal.CARGO_INTAKE_ARM_1, 0);
    TalonSRX        m_intakeArm_2 = new TalonSRX(RobotGlobal.CARGO_INTAKE_ARM_2);
    TalonSRX        m_intakeRoller = new TalonSRX(RobotGlobal.CARGO_INTAKE_ROLLER);

    private double m_P = 0.3;
    private double m_I = 0.0;
    private double m_D = 0.0;
    private double m_F = 0.0;
    private double m_iZone = 0;

    private int m_cruiseVelocity = 300;
    private int m_acceleration = 150;
    private int m_tolerance = 10;
    private int m_slot = 0;

    // Static subsystem reference
	private static CargoIntake cIInstance = new CargoIntake();

	public static CargoIntake getInstance() {
		return CargoIntake.cIInstance;
	}
	
	//CargoIntake class constructor
	protected CargoIntake()
    {
        m_intakeArm.setNeutralMode(NeutralMode.Brake);
        m_intakeArm_2.setNeutralMode(NeutralMode.Brake);
        m_intakeArm_2.follow(m_intakeArm);

        // Flip Motor Directions?
        m_intakeArm.setInverted(true);
        m_intakeArm_2.setInverted(true);
            
        // Flip sensors so they count positive in the positive control direction?
        m_intakeArm.setSensorPhase(true);

        // Configure PIDF constants
        m_intakeArm.configPIDF(m_slot, m_P, m_I, m_D, m_F);
        
        // Configure motion constants
        m_intakeArm.configMotion(m_cruiseVelocity, m_acceleration, m_tolerance);

        // Stop all motion (for now)
        driveArmManually(0.0);
        driveRollerManually(0.0);
            
        // Zero the encoder
        zeroArmEncoder();
        
        // Assuming this is Starting Position; if not, then need to change it
        setArmActivePosition(eCargoIntakeArmPosition.RETRACTED);
    }

    protected void initDefaultCommand()
    {
        //setDefaultCommand(new ReportStats());
        setDefaultCommand(new DriveCargoIntakeArm());
    }

    //#region Roller System

    //TODO make this an enum for crawl, intake speeds

    public void driveRollerManually(double speed) {
        m_intakeRoller.set(ControlMode.PercentOutput, speed);
     }

    //#endregion
   

    //#region Arm System
   
    /*
     * Manual Arm Control
     */
    public void driveArmManually(double speed) {
        m_intakeArm.set(ControlMode.PercentOutput, speed);
    }

    /*
     * Closed Loop Motion Control
     */
    public void moveArmToPosition(eCargoIntakeArmPosition position, boolean reportStats)
    {
        activeIntakeArmPosition = position;
        moveMagically(position.getSetpoint());

        if (reportStats)
        {
            SmartDashboard.putString("Cargo Intake Arm Position", getArmActivePosition().getSetpointName());
            reportEncoder();
            reportTalonStats();
        }
    }

    public void moveMagically (int setPoint) {

        m_intakeArm.runMotionMagic(setPoint);
    }
    
    public void moveMagically () {

        m_intakeArm.runMotionMagic();
    }
    
    public boolean isArmOnTarget(eCargoIntakeArmPosition m_position) {
        
        // We only want to stop the Cargo Arm PID loop from running when
        // we reach the RETRACTED state; otherwise, keep it going
        if (m_position == eCargoIntakeArmPosition.RETRACTED)
        {
            int error = m_intakeArm.getClosedLoopError(0);
            int allowable = m_intakeArm.getTolerance();
            
            return (((error >= 0 && error <= allowable) ||
                (error < 0 && error >= (-1.0) * allowable))
                );
        } 
        else
        {
            return false;
        }
    }
    
    public void setArmSetpointFromDashboard() {
        m_intakeArm.updateSetpointFromDashboard();
    }
    
    /*
     * Setter / Getter Methods
     */
    
    public void zeroArmEncoder() {
        m_intakeArm.setSelectedSensorPosition(0,0,0);
    }

    public int getArmEncoderPosition() {
        return m_intakeArm.getSelectedSensorPosition();
    }

    public eCargoIntakeArmPosition getArmActivePosition() {
            return activeIntakeArmPosition;
    }
    
    public void setArmActivePosition(eCargoIntakeArmPosition pos) {
        activeIntakeArmPosition = pos;
    }

    /*
     * SmartDashbord Update Methods
     */

     public void reportTalonStats() {
        m_intakeArm.reportMotionToDashboard();
    }
    
    public void updateTalonStats() {
        m_intakeArm.updateStats();
    }
    
    public void reportEncoder() {
        SmartDashboard.putNumber("Cargo Arm Encoder", getArmEncoderPosition());
    }
        
        
    //#endregion

}

    