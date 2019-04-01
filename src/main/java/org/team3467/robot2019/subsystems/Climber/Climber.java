/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Climber;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {

    public static final double HAB2_CLIMB_COUNT = 100.0;
    public static final double HAB3_CLIMB_COUNT = 275.0;
    
    public CANSparkMax m_sparkMax = new CANSparkMax(RobotGlobal.CLIMBER_STILT, MotorType.kBrushless);
    public CANEncoder m_sparkEncoder;

    // returns false when switch is CLOSED
    public DigitalInput m_limitSw = new DigitalInput(RobotGlobal.DIO_CLIMBER);

    // Static subsystem reference
	private static Climber cInstance = new Climber();

	public static Climber getInstance() {
		return Climber.cInstance;
	}
	
	protected Climber()
    {
        m_sparkMax.restoreFactoryDefaults();
        m_sparkMax.setIdleMode(CANSparkMax.IdleMode.kBrake);        

        m_sparkEncoder = m_sparkMax.getEncoder();
        zeroEncoder();
    }


    @Override
    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new DriveClimber());
        setDefaultCommand(new KeepIn(0.2));
    }

    public void drive(double speed)
    {
        m_sparkMax.set(speed);
    }

    public void moveOut(double speed)
    {
        m_sparkMax.set(speed);
    }

    public void moveIn(double speed)
    {
        speed = speed * -1.0;

        // Move in (relax cable) only if limit switch is not hit and encoder count is not negative
        if ((m_limitSw.get() == true) && (m_sparkEncoder.getPosition() > 0.0))
            m_sparkMax.set(speed);
        else
            stop();
    }
    
    public void stop()
    {
        m_sparkMax.set(0.0);
    }

    public void zeroEncoder()
    {
        m_sparkEncoder.setPosition(0.0);
    }

    public double getEncoderCount()
    {
        return m_sparkEncoder.getPosition();
    }

}
