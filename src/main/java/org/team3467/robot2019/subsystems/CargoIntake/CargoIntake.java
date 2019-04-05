package org.team3467.robot2019.subsystems.CargoIntake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.RobotGlobal;
import org.team3467.robot2019.subsystems.MagicTalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class CargoIntake extends Subsystem
{

    public static final double CARGO_INTAKE_ROLLER_SPEED = 0.75;
    private static final double CRAWL_SPEED = 0.50;

    public enum eCargoIntakeArmPosition
    { 
        // Fully retracted
        RETRACTED(0, "RETRACTED"),
        // Standing vertical inside bumper perimeter
        VERTICAL(800,"VERTICAL"),   // TODO: Check this value
        // Safely outside the Lift "swing zone"
        OUTSIDE(1400, "OUTSIDE"),   // TODO: Check this value
        // Cargo Intake position
        INTAKE(1550, "INTAKE"),
        // HAB-top Crawling position
        CRAWL(2100,"CRAWL");

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

    // Last "commanded" arm position
    private eCargoIntakeArmPosition m_activeIntakeArmPosition;
    
    // Actual encoder position - updated everytime the arm is moved
    // Save this because we want the default command to run MotionMagic at the current position,
    // which will avoid sudden movements upon re-enabling the robot.
    private int m_actualEncoderPosition;
    private boolean m_wasRecentlyDisabled;

    MagicTalonSRX   m_intakeArm = new MagicTalonSRX("Cargo Intake", RobotGlobal.CARGO_INTAKE_ARM_1, 0, false);
    TalonSRX        m_intakeArm_2 = new TalonSRX(RobotGlobal.CARGO_INTAKE_ARM_2);
    TalonSRX        m_intakeRoller = new TalonSRX(RobotGlobal.CARGO_INTAKE_ROLLER);

    private double m_P = 1.0;
    private double m_I = 0.001;
    private double m_D = 0.1;
    private double m_F = 0.0;
    // private double m_iZone = 0;

    private int m_cruiseVelocity = 1000;
    private int m_acceleration = 500;
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
        // Set plain Talons to default config
        m_intakeArm_2.configFactoryDefault();
        m_intakeRoller.configFactoryDefault();
        
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
        m_activeIntakeArmPosition = eCargoIntakeArmPosition.RETRACTED;
        updatePosition();
        m_wasRecentlyDisabled = false;
    }

    protected void initDefaultCommand()
    {
        //setDefaultCommand(new ReportStats());
        //setDefaultCommand(new DriveCargoIntakeArm());

        /*
            Continue MotionMagic with setpoint at current position
        */
        setDefaultCommand(new HoldMagicallyInPlace());
    }

    //#region Roller System

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
        updatePosition();
    }

    /*
     * Closed Loop Motion Control
     */
    public void moveArmToPosition(eCargoIntakeArmPosition position, boolean reportStats)
    {
        m_activeIntakeArmPosition = position;
        m_intakeArm.runMotionMagic(position.getSetpoint());
        updatePosition();

        // Now that Lift has been re-commanded to a position, turn off flag
        m_wasRecentlyDisabled = false;

        if (reportStats)
        {
            reportStats();
        }
    }

    // Use current position as setpoint
    public void holdMagically (boolean reportStats) {

        // If robot was recently disabled and hasn't been re-commanded to a position, use actual encoder position
        if (m_wasRecentlyDisabled == true) {
            m_intakeArm.runMotionMagic(m_actualEncoderPosition);
        } else {
            m_intakeArm.runMotionMagic(m_activeIntakeArmPosition.getSetpoint());
        }
        updatePosition();

        if (reportStats)
        {
            reportStats();
        }
    }
    
    // Signal that robot has been disabled, so lift may move from known position
    public void signalDisabled() {
        m_wasRecentlyDisabled = true;
    }

    public boolean isArmOnTarget(eCargoIntakeArmPosition position) {
        
        if (OI.getOperatorButtonA())
        {
            // Pressing A Button stops PID control
            return true;
        }

        // We only want to stop the Cargo Arm PID loop from running when
        // we reach the RETRACTED state; otherwise, keep it going to hold arm position
        if (position == eCargoIntakeArmPosition.RETRACTED)
        {
            return (checkArmOnTarget(position));
        } 
        
        // When lowering arm to crawl on HAB, start rollers once the arm gets close
        // to the position; Keep PID going to hold position
        if (position == eCargoIntakeArmPosition.CRAWL)
        {
            //if (checkArmOnTarget(position))
            //{
                driveRollerManually(CRAWL_SPEED);
            //}
            //else
            //    driveRollerManually(0.0);
        } 
    
        return false;
    }
    
    public boolean checkArmOnTarget(eCargoIntakeArmPosition position) {
        
        // Get current target and determine how far we are from it (error)
        int target = (int) m_intakeArm.getClosedLoopTarget();
        int error = target - m_intakeArm.getSelectedSensorPosition();
        int allowable = m_intakeArm.getTolerance();
        
        return (Math.abs(error) <= allowable);
    } 

    /*
     * Setter / Getter Methods
     */
    
    public void updatePosition() {
        m_actualEncoderPosition = m_intakeArm.getSelectedSensorPosition();
    } 

    public void zeroArmEncoder() {
        m_intakeArm.setSelectedSensorPosition(0,0,0);
        updatePosition();
    }

    public int getArmEncoderPosition() {
        return m_actualEncoderPosition;
    }

    public eCargoIntakeArmPosition getArmActivePosition() {
        return m_activeIntakeArmPosition;
    }
    
    public void setArmSetpointFromDashboard() {
        m_intakeArm.updateSetpointFromDashboard();
    }
    
    public void setTolerance(int tol) {
        m_intakeArm.setTolerance(tol);
    }

    /*
     * SmartDashbord Update Methods
     */
    public void reportStats() {

        SmartDashboard.putString("Cargo Intake Arm Position", getArmActivePosition().getSetpointName());
        reportEncoder();
        reportTalonStats();

    }

    public void reportEncoder() {
        SmartDashboard.putNumber("Cargo Arm Encoder", m_intakeArm.getSelectedSensorPosition());
    }

    public void reportTalonStats() {
        m_intakeArm.reportMotionToDashboard();
    }
    
    public void updateTalonStats() {
        m_intakeArm.updateStats();
    }
    
        
        
    //#endregion

}

    