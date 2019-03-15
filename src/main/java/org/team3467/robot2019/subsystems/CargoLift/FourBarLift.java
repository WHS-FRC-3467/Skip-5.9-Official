package org.team3467.robot2019.subsystems.CargoLift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import org.team3467.robot2019.robot.RobotGlobal;
import org.team3467.robot2019.subsystems.MagicTalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FourBarLift extends Subsystem {

    //TODO implement Hatch level encoder values here
    public enum eFourBarLiftPosition {
        HOME(10, "ZERO"),
        INTAKE(501, "INTAKE"),
        HATCH_1(4500, "HATCH LEVEL ONE"),
        L1(5547, "ROCKET LEVEL ONE"),
        OUTOFTHEWAY(6644,  "OUT OF THE WAY"),
        CARGO_SHIP(7828,  "CARGO SHIP"),
        HATCH_2(8000, "HATCH LEVEL TWO"),
        L2(11505, "ROCKET LEVEL TWO"),
        HATCH_3(13000, "HATCH LEVEL THREE"),
        L3(14512, "ROCKET LEVEL THREE");


        private final int setpoint;
        private final String name;

        private eFourBarLiftPosition(int position, String name) {
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
    
    // Last "commanded" Lift position
    private eFourBarLiftPosition m_moveToPosition;
    
    // Actual encoder position - updated everytime the lift is moved
    // Save this because we want the default command to run MotionMagic at the current position,
    // which will avoid sudden movements upon re-enabling the robot.
    private int m_actualEncoderPosition;

    MagicTalonSRX m_liftMotor = new MagicTalonSRX("FBL",RobotGlobal.CARGO_LIFT,0);
        
    private double m_P = 2.2;
    private double m_I = 0.0;
    private double m_D = 0.0;
    private double m_F = 0.0;
    //private double m_iZone = 0;

    private int m_cruiseVelocity = 1400;
    private int m_acceleration = 1300;
    private int m_tolerance = 10;
    private int m_slot = 0;

    
    // Static subsystem reference
    private static FourBarLift fBInstance = new FourBarLift();

    public static FourBarLift getInstance() {
        return FourBarLift.fBInstance;
    }

    //FourBarLift class constructor
    protected FourBarLift()
    {
        // Run in brake mode
        m_liftMotor.setNeutralMode(NeutralMode.Brake);

        // Flip Motor Directions?
        m_liftMotor.setInverted(false);
            
        // Configure to use CTRE MagEncoder (built into Versaplanetary Encoder Slice)
        m_liftMotor.configFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        // Flip sensors so they count positive in the positive control direction?
        m_liftMotor.setSensorPhase(true);

        // Configure PIDF constants
        m_liftMotor.configPIDF(m_slot, m_P, m_I, m_D, m_F);
        
        // Configure motion constants
        m_liftMotor.configMotion(m_cruiseVelocity, m_acceleration, m_tolerance);

        // Stop all motion (for now)
        m_liftMotor.set(ControlMode.PercentOutput, 0.0);
            
        // Zero the encoder
        zeroLiftEncoder();
        
        // Assuming this is Starting Position; if not, then need to change it
        m_moveToPosition = eFourBarLiftPosition.HOME;
        updatePosition();
    }
    
    @Override
    protected void initDefaultCommand() {
        // setDefaultCommand(new LiftManually());
        /*
            Continue MotionMagic with setpoint at current position
        */
        setDefaultCommand(new HoldMagicallyInPlace());
    }

    /*
     * Manual Lift Control
     */
    public void driveManual(double speed) {
        m_liftMotor.set(ControlMode.PercentOutput, speed);
        updatePosition();
    }

    /*
     * Closed Loop Motion Control
     */
    public void moveLiftToPosition(eFourBarLiftPosition position, boolean reportStats)
    {
        m_moveToPosition = position;
        m_liftMotor.runMotionMagic(position.getSetpoint());
        updatePosition();

        if (reportStats)
        {
            reportStats();
        }
    }

    // Use current position as setpoint
    public void holdMagically (boolean reportStats) {

        m_liftMotor.runMotionMagic(m_actualEncoderPosition);
        updatePosition();

        if (reportStats)
        {
            reportStats();
        }

    }
    
    public boolean isLiftOnTarget(eFourBarLiftPosition m_position) {
        
        // We only want to stop the Cargo Lift PID loop from running when
        // we reach the HOME state; otherwise, keep it going to hold arm position
        if (m_position == eFourBarLiftPosition.HOME)
        {
            return (checkLiftOnTarget(m_position));
        }
        else
            return false;
    }

    public boolean checkLiftOnTarget(eFourBarLiftPosition m_position) {
        
        // Get current target and determine how far we are from it (error)
        int target = (int) m_liftMotor.getClosedLoopTarget();
        int error = target - m_liftMotor.getSelectedSensorPosition();
        int allowable = m_liftMotor.getTolerance();
        
        return (Math.abs(error) <= allowable);
    } 

    /*
     * Setter / Getter Methods
     */
    
    public void updatePosition() {
        m_actualEncoderPosition = m_liftMotor.getSelectedSensorPosition();
    } 

    public void zeroLiftEncoder() {
        m_liftMotor.setSelectedSensorPosition(0,0,0);
        updatePosition();
    }

    public eFourBarLiftPosition getLiftPosition() {
        return m_moveToPosition;
    }

    public void setArmSetpointFromDashboard() {
        m_liftMotor.updateSetpointFromDashboard();
    }
    
    public void setTolerance(int tol) {
        m_liftMotor.setTolerance(tol);
    }

  
    /*
     * SmartDashbord Update Methods
     */
    public void reportStats() {

        SmartDashboard.putString("FBL Position", getLiftPosition().getName());
        reportEncoder();
        reportTalonStats();

    }
    
    public void reportEncoder() {
        SmartDashboard.putNumber("FBL Encoder", m_liftMotor.getSelectedSensorPosition(0));
    }
 
     public void reportTalonStats() {
        m_liftMotor.reportMotionToDashboard();
    }
    
    public void updateTalonStats() {
        m_liftMotor.updateStats();
    }
    
}