package org.team3467.robot2019.subsystems.Hatch;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team3467.robot2019.robot.RobotGlobal;
import org.team3467.robot2019.subsystems.MagicTalonSRX;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HatchGrabber extends Subsystem {

    public enum eHGAPosition {
        START(0, "START"),
        STOW(10, "STOW"),
        PLACE(1400, "PLACE");

        private final int setpoint;
        private final String name;

        private eHGAPosition(int position, String name) {
                this.setpoint = position;
                this.name = name;
        }

        public int getSetpoint() {
            return this.setpoint;
        }

        public String getName() {
            return this.name;
        }
    }
    
    // Last "commanded" HatchGrabberArm position
    private eHGAPosition m_moveToPosition;
    
    // Actual encoder position - updated everytime the arm is moved
    // Save this because we want the default command to run MotionMagic at the current position,
    // which will avoid sudden movements upon re-enabling the robot.
    private int m_actualEncoderPosition;
    private boolean m_wasRecentlyDisabled;

    private double m_P = 1.5;
    private double m_I = 0.0;
    private double m_D = 0.03;
    private double m_F = 0.0;

    private int m_cruiseVelocity = 1400;
    private int m_acceleration = 1300;
    private int m_tolerance = 10;
    private int m_slot = 0;

    private Servo m_releaseServo;
    private MagicTalonSRX m_grabberArm;

    // Static subsystem reference
	private static HatchGrabber hGInstance = new HatchGrabber();

	public static HatchGrabber getInstance() {
		return HatchGrabber.hGInstance;
	}
	
	//HatchGrabber class constructor
	protected HatchGrabber()
    {
        m_releaseServo = new Servo(RobotGlobal.RELEASE_SERVO);
        m_grabberArm = new MagicTalonSRX("HGA", RobotGlobal.HATCH_GRABBER, 0, true);

        m_grabberArm.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

        // Run in brake mode
        m_grabberArm.setNeutralMode(NeutralMode.Brake);

        // Flip Motor Directions?
        m_grabberArm.setInverted(false);
            
        // Configure to use CTRE MagEncoder (built into Versaplanetary Encoder Slice)
        m_grabberArm.configFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        // Flip sensors so they count positive in the positive control direction?
        m_grabberArm.setSensorPhase(true);

        // Configure PIDF constants
        m_grabberArm.configPIDF(m_slot, m_P, m_I, m_D, m_F);
        
        // Configure motion constants
        m_grabberArm.configMotion(m_cruiseVelocity, m_acceleration, m_tolerance);

        // Stop all motion (for now)
        m_grabberArm.set(ControlMode.PercentOutput, 0.0);

        // Zero the encoder
        zeroHGAEncoder();
        
        // Assuming this is Starting Position; if not, then need to change it
        m_moveToPosition = eHGAPosition.START;
        updatePosition();
        m_wasRecentlyDisabled = false;
    }

    protected void initDefaultCommand() {
        /*
            Continue MotionMagic with setpoint at current position
        */
        setDefaultCommand(new HoldMagicallyInPlace());
    }

    /*
     * Manual Control
     */
    public void driveManual(double speed) {
        m_grabberArm.set(ControlMode.PercentOutput, speed);
        updatePosition();
    }

    /*
     * Closed Loop Motion Control
     */
    public void moveHGAToPosition(eHGAPosition position, boolean reportStats)
    {
        m_moveToPosition = position;
        m_grabberArm.runMotionMagic(position.getSetpoint());
        updatePosition();

        // Now that HGA has been re-commanded to a position, turn off flag
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
            m_grabberArm.runMotionMagic(m_actualEncoderPosition);
        } else {
            m_grabberArm.runMotionMagic(m_moveToPosition.getSetpoint());
        }
        updatePosition();

        if (reportStats)
        {
            reportStats();
        }
    }

    public void moveMagic (int setpoint)
    {
        m_actualEncoderPosition = setpoint;
        m_grabberArm.runMotionMagic(m_actualEncoderPosition);
    }

    // Signal that robot has been disabled, so lift may move from known position
    public void signalDisabled() {
        m_wasRecentlyDisabled = true;
    }

    public boolean isHGAOnTarget(eHGAPosition m_position) {
        
        // We only want to stop the HGA PID loop from running when
        // we reach the START state; otherwise, keep it going to hold arm position
        if (m_position == eHGAPosition.START)
        {
            return (checkHGAOnTarget(m_position));
        }
        else
            return false;
    }

    public boolean checkHGAOnTarget(eHGAPosition m_position) {
        
        // Get current target and determine how far we are from it (error)
        int target = (int) m_grabberArm.getClosedLoopTarget();
        int error = target - m_grabberArm.getSelectedSensorPosition();
        int allowable = m_grabberArm.getTolerance();
        
        return (Math.abs(error) <= allowable);
    } 

    /*
     * Setter / Getter Methods
     */
    
    public void updatePosition() {
        m_actualEncoderPosition = m_grabberArm.getSelectedSensorPosition();
    } 

    public void zeroHGAEncoder() {
        m_grabberArm.setSelectedSensorPosition(0,0,0);
        updatePosition();
    }

    public eHGAPosition getHGAPosition() {
        return m_moveToPosition;
    }

    public int getHGAEncoderPosition() {
        return m_actualEncoderPosition;
    }

    public void setArmSetpointFromDashboard() {
        m_grabberArm.updateSetpointFromDashboard();
    }
    
    public void setTolerance(int tol) {
        m_grabberArm.setTolerance(tol);
    }

  
    /*
     * SmartDashbord Update Methods
     */
    public void reportStats() {

        SmartDashboard.putString("HGA Position", getHGAPosition().getName());
        reportEncoder();
        reportTalonStats();

    }
    
    public void reportEncoder() {
        SmartDashboard.putNumber("HGA Encoder", m_grabberArm.getSelectedSensorPosition(0));
    }
 
     public void reportTalonStats() {
        m_grabberArm.reportMotionToDashboard();
    }
    
    public void updateTalonStats() {
        m_grabberArm.updateStats();
    }


    public void setServoPosition(double pos) {
        m_releaseServo.set(pos);
        SmartDashboard.putNumber("Hatch Servo", m_releaseServo.getPosition());
    }

    public boolean grabHatch()
    {
        m_releaseServo.set(0.9);
        SmartDashboard.putNumber("Hatch Servo", m_releaseServo.getPosition());

/*
        if ( m_grabberArm.getSensorCollection().isFwdLimitSwitchClosed())
        {
            SmartDashboard.putBoolean("HatchGrabbed", true);
            return true;
        } else {
            return false;
        }
    */
        return false;
}

    public boolean releaseHatch()
    {
        m_releaseServo.set(0.1);
        SmartDashboard.putNumber("Hatch Servo", m_releaseServo.getPosition());
        
    /*
        if ( !m_grabberArm.getSensorCollection().isFwdLimitSwitchClosed())
        {
            SmartDashboard.putBoolean("HatchGrabbed", false);
            return true;
        } else {
            return false;
        }
    */
        return false;

    }

    
}