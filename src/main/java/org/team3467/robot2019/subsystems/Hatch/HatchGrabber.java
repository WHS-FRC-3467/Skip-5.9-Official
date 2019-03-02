package org.team3467.robot2019.subsystems.Hatch;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HatchGrabber extends Subsystem {

    public static final double DEPLOY_TIME = 1.0;
    
    private static final double GRAB_SPEED = -1.0;
    private static final double RELEASE_SPEED = 1.0;
    private static final double DEPLOY_SPEED = -0.7;
    private static final double RETRACT_SPEED = 0.7;

    private WPI_TalonSRX m_grabTalon;
    private WPI_TalonSRX m_deployTalon;
    private boolean      m_grabberDeployed;

    // Static subsystem reference
	private static HatchGrabber hGInstance = new HatchGrabber();

	public static HatchGrabber getInstance() {
		return HatchGrabber.hGInstance;
	}
	
	//HatchGrabber class constructor
	protected HatchGrabber()
    {
        
        m_grabberDeployed = false;

        m_grabTalon = new  WPI_TalonSRX(RobotGlobal.HATCH_GRABBER);
        m_deployTalon = new WPI_TalonSRX(RobotGlobal.HATCH_ACTUATOR);

        m_grabTalon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        m_grabTalon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

        m_deployTalon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

        m_grabTalon.setNeutralMode(NeutralMode.Brake);
        m_deployTalon.setNeutralMode(NeutralMode.Brake);
    }

    protected void initDefaultCommand() {

    }

    // TODO: Confirm proper limit switch directions to use here
    public boolean grabHatch()
    {
        m_grabTalon.set(GRAB_SPEED);
        if ( m_grabTalon.getSensorCollection().isRevLimitSwitchClosed())
        {
            SmartDashboard.putBoolean("HatchGrabbed", true);
            return true;
        } else {
            return false;
        }
    }

    public boolean releaseHatch()
    {
        m_grabTalon.set(RELEASE_SPEED);
        if ( m_grabTalon.getSensorCollection().isFwdLimitSwitchClosed())
        {
            SmartDashboard.putBoolean("HatchGrabbed", false);
            return true;
        } else {
            return false;
        }
    }

    public void stopGrabber()
    {
        m_grabTalon.set(0.0);
    }

    public void driveDeployment(double speed)
    {
        m_deployTalon.set(speed);
    }
    
    public void deployGrabber()
    {
        m_deployTalon.set(DEPLOY_SPEED);
    }

    public boolean stowGrabber()
    {
        m_deployTalon.set(RETRACT_SPEED);
        if (m_deployTalon.getSensorCollection().isFwdLimitSwitchClosed())
        {
            m_grabberDeployed = false;
            return true;
        }
        else
            return false;
    }

    public void stopDeploy()
    {
        m_deployTalon.set(0.0);
    }

    public boolean isGrabberDeployed()
    {
        boolean hit = m_deployTalon.getSensorCollection().isFwdLimitSwitchClosed();
        SmartDashboard.putBoolean("Hatch Stowed", hit);

        return m_grabberDeployed;
    }

    public void setGrabberDeployed()
    {
        m_grabberDeployed = true;
    }

}