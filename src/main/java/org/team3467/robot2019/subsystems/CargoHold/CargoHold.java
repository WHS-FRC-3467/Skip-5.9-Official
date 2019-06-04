/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FRC Team 3467                                           */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoHold;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.robot.RobotGlobal;
import org.team3467.robot2019.subsystems.LED.LEDSerial;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * CargoHold is the holder for Cargo. It lives on the end of the 4Bar Lift Arm.
 */
public class CargoHold extends Subsystem
{
    // Public current levels for operation of the Cargo Hold
    public static final double CARGO_HOLD_PICKUP_CURRENT = 6.0;
    public static final double CARGO_HOLD_STALL_CURRENT = 5.0;
    
    private TalonSRX m_cargoHold = new TalonSRX(RobotGlobal.CARGO_HOLD);

    private double m_percentOutput;
    private double m_motorCurrent;
    private int m_closedLoopError;

    private static final int INTAKE_REPORTING_LOOP_COUNT = 20;
    private int loopCount = 0;
    private boolean m_haveCargo;

    // Static subsystem reference
	private static CargoHold cHInstance = new CargoHold();

	public static CargoHold getInstance() {
		return CargoHold.cHInstance;
	}
	
	//CargoHold class constructor
	protected CargoHold()
    {
        m_cargoHold.configFactoryDefault();

        /* Config the peak and nominal outputs ([-1, 1] represents [-100, 100]%) */
        m_cargoHold.configNominalOutputForward(0, 10);
        m_cargoHold.configNominalOutputReverse(0, 10);
        m_cargoHold.configPeakOutputForward(1, 10);
        m_cargoHold.configPeakOutputReverse(-1, 10);

        /**
         * Config the allowable closed-loop error, Closed-Loop output will be
         * neutral within this range. See Table here for units to use: 
         * https://github.com/CrossTheRoadElec/Phoenix-Documentation#what-are-the-units-of-my-sensor
         */
        m_cargoHold.configAllowableClosedloopError(0, 0, 10);

        /* Config closed loop gains for Primary closed loop (Current) */
        m_cargoHold.config_kP(0, 0.5, 10);
        m_cargoHold.config_kI(0, 0.0, 10);
        m_cargoHold.config_kD(0, 0.0, 10);
        m_cargoHold.config_kF(0, 0.0, 10);
        m_cargoHold.config_IntegralZone(0, 0, 10);

        // No Cargo Yet!
        m_haveCargo = false;
    }

    @Override
    protected void initDefaultCommand()
    { 
        // Set the default command for a subsystem here.
        setDefaultCommand(new HoldCargo());
    }

    /**
     * Intake Cargo while checking current draw
     * @param double currentLevel - max current draw (in Amps) allowed
     * @param boolean noStats - set to true if you do not want to print stats this time (speed optimization)
     * @return boolean - if currentLevel was met or exceeded
     */
    public boolean intakeCargo(double currentLevel, boolean noStats)
    {
        boolean retVal = false;
        
        /* Current Closed Loop */
        m_cargoHold.set(ControlMode.Current, currentLevel);

        m_motorCurrent = m_cargoHold.getOutputCurrent();
        // Return TRUE if currentLevel was met or exceeded on this pass
        // May be able to lower the multiplication factor here to see if it registers a "hold" more quickly
        // now that we start the rollers earlier in the command and hopefully avoid false positives from the roller
        // startup current surge.
        retVal = (m_motorCurrent >= (currentLevel*1.05));
    
        if (!noStats)
        {
            m_percentOutput = m_cargoHold.getMotorOutputPercent();
            m_closedLoopError = m_cargoHold.getClosedLoopError(0);
    
            if (loopCount++ > INTAKE_REPORTING_LOOP_COUNT)
            {
                reportCargoHoldStats();
                loopCount = 0;
            }
        }

        return retVal;
    }

    public void cargoIsHeld(boolean holdState)
    {
        m_haveCargo = holdState;
    }

    public boolean isCargoHeld()
    {
        return m_haveCargo;
    }

    public void releaseCargo(double speed)
    {
        m_cargoHold.set(ControlMode.PercentOutput,(-1.0) * speed);
        m_haveCargo = false;
    }

    public void stop()
    {
        m_cargoHold.set(ControlMode.PercentOutput,0.0);
        m_haveCargo = false;

        if (loopCount++ > INTAKE_REPORTING_LOOP_COUNT)
        {
            reportCargoHoldStats();
            loopCount = 0;
        }

    }

    private void reportCargoHoldStats()
    {

        if (m_haveCargo)
            // Light up the LED signal here to indicate "Cargo In Hand"
            Robot.sub_led.setLEDPattern(LEDSerial.P_CARGO_HOLDING);
        else
            // Light up the LED signal here to indicate "Normal" status
            Robot.sub_led.setLEDPattern(LEDSerial.DEFAULT_PATTERN);

        SmartDashboard.putNumber("CargoHold Current Draw", m_motorCurrent);
        SmartDashboard.putNumber("CargoHold Percent Output", m_percentOutput);
        SmartDashboard.putNumber("CargoHold Closed Loop Error", m_closedLoopError);
        SmartDashboard.putBoolean("CargoHold Holding?", m_haveCargo);
    }


}
