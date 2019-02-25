/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FRC Team 3467                                           */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoHold;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * CargoHold is the holder for Cargo. It lives on the end of the 4Bar Lift Arm.
 */
public class CargoHold extends Subsystem
{
    private static final double CARGO_HOLD_STALL_CURRENT = 25.0;
    
    private TalonSRX m_cargoHold = new TalonSRX(RobotGlobal.CARGO_HOLD);

    private double m_percentOutput;
    private double m_motorCurrent;
    private int m_closedLoopError;

    private static final int INTAKE_REPORTING_LOOP_COUNT = 20;
    private int loopCount = 0;

    public CargoHold()
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
        m_cargoHold.config_kP(0, 0.2, 10);
        m_cargoHold.config_kI(0, 0.0, 10);
        m_cargoHold.config_kD(0, 0.0, 10);
        m_cargoHold.config_kF(0, 0.0, 10);
        m_cargoHold.config_IntegralZone(0, 0, 10);
    }

    @Override
    public void initDefaultCommand()
    { 
        // Set the default command for a subsystem here.
        setDefaultCommand(new StopCargoHold());
    }

    /**
     * Inake Cargo while checking current draw
     */
    public void intakeCargo()
    {
        /* Current Closed Loop */
        m_cargoHold.set(ControlMode.Current, CARGO_HOLD_STALL_CURRENT);

        m_motorCurrent = m_cargoHold.getOutputCurrent();
        m_percentOutput = m_cargoHold.getMotorOutputPercent();
        m_closedLoopError = m_cargoHold.getClosedLoopError(0);

        if (loopCount++ > INTAKE_REPORTING_LOOP_COUNT)
        {
            reportCargoHoldStats();
            loopCount = 0;
        }
    }

    public void releaseCargo(double speed)
    {
        m_cargoHold.set(ControlMode.PercentOutput,speed);
    }

    public void stop()
    {
        m_cargoHold.set(ControlMode.PercentOutput,0.0);
    }

    private void reportCargoHoldStats()
    {
        SmartDashboard.putNumber("CargoHold Current Draw", m_motorCurrent);
        SmartDashboard.putNumber("CargoHold Percent Output", m_percentOutput);
        SmartDashboard.putNumber("CargoHold Closed Loop Error", m_closedLoopError);
    }


}
