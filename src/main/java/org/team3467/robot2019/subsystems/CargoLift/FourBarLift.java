package org.team3467.robot2019.subsystems.CargoLift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team3467.robot2019.robot.RobotGlobal;
import org.team3467.robot2019.subsystems.MagicTalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FourBarLift extends Subsystem {

    //TODO implement encoder values here
    public enum eFourBarLiftPosition {
        L1(0, "ROCKET LEVEL ONE"),
        L2(1, "ROCKET LEVEL TWO"),
        L3(2, "ROCKET LEVEL THREE"),
        CARGO_SHIP(3,  "CARGO SHIP"),
        INTAKE(4, "INTAKE");

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
    
    MagicTalonSRX m_liftMotor = new MagicTalonSRX("FBL",RobotGlobal.CARGO_LIFT,0);
    
    private eFourBarLiftPosition moveToPosition;
    
    private double m_P = 0.0;
    private double m_I = 0.0;
    private double m_D = 0.0;
    private double m_F = 0.0;
    private double m_iZone = 0;

    private int m_cruiseVelocity = 0;
    private int m_acceleration = 0;
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
            
        // Flip sensors so they count positive in the positive control direction?
        m_liftMotor.setSensorPhase(false);

        // Configure PIDF constants
        m_liftMotor.configPIDF(m_slot, m_P, m_I, m_D, m_F);
        
        // Configure motion constants
        m_liftMotor.configMotion(m_cruiseVelocity, m_acceleration, m_tolerance);

        // Stop all motion (for now)
        m_liftMotor.set(ControlMode.PercentOutput, 0.0);
            
        // Zero the encoder
        zeroLiftEncoder();
        
        // Assuming this is Starting Position; if not, then need to change it
        setLiftPosition(eFourBarLiftPosition.INTAKE);

    }
    
    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new LiftManually());
    }

    /*
     * Manual Motor Control
     */
    public void driveManual(double speed) {
        m_liftMotor.set(ControlMode.PercentOutput, speed);
    }

    /*
     * Closed Loop Motion Control
     */
    public void moveLiftToPosition(eFourBarLiftPosition position, boolean reportStats)
    {
        moveToPosition = position;
        moveMagically(position.getSetpoint());

        if (reportStats)
        {
            SmartDashboard.putString("FBL Position", getLiftPosition().getName());
            reportEncoder();
            reportTalonStats();
        }
    }

    public void moveMagically (int setPoint) {

        m_liftMotor.runMotionMagic(setPoint);
    }
    
    public void moveMagically () {

        m_liftMotor.runMotionMagic();
    }
    
    public boolean isLiftOnTarget(eFourBarLiftPosition m_position) {
        
        // We only want to stop the Cargo Lift PID loop from running when
        // we reach the INTAKE state; otherwise, keep it going to hold arm position
        if (m_position == eFourBarLiftPosition.INTAKE)
        {
            int error = m_liftMotor.getClosedLoopError(0);
            int allowable = m_liftMotor.getTolerance();
            
            return (((error >= 0 && error <= allowable) ||
                (error < 0 && error >= (-1.0) * allowable))
                );
        } 
        else
        {
            return false;
        }
    }
    
    /*
     * Setter / Getter Methods
     */
    
    public int getLiftEncoder() {
        return m_liftMotor.getSelectedSensorPosition(0);
    }
    public void zeroLiftEncoder() {
        m_liftMotor.setSelectedSensorPosition(0,0,0);
    }

    public eFourBarLiftPosition getLiftPosition() {
        return moveToPosition;
    }

    public void setLiftPosition(eFourBarLiftPosition pos) {
        moveToPosition = pos;
    }

    public void setArmSetpointFromDashboard() {
        m_liftMotor.updateSetpointFromDashboard();
    }
    
  
    /*
     * SmartDashbord Update Methods
     */

     public void reportTalonStats() {
        m_liftMotor.reportMotionToDashboard();
    }
    
    public void updateTalonStats() {
        m_liftMotor.updateStats();
    }
    
    public void reportEncoder() {
        SmartDashboard.putNumber("FBL Encoder", getLiftEncoder());
    }
 
}