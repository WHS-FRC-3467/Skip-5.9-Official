/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Climber;

//import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class Climber extends Subsystem {

    public static final double HAB2_CLIMB_COUNT = 1250.0;
    public static final double HAB3_CLIMB_COUNT = 3700.0;
    
    public CANSparkMax m_sparkMax = new CANSparkMax(RobotGlobal.CLIMBER_STILT, MotorType.kBrushless);
    
    Encoder m_Encoder = new Encoder(RobotGlobal.DIO_POLEJACK_ENCODER_C1, RobotGlobal.DIO_POLEJACK_ENCODER_C2, false, Encoder.EncodingType.k4X);

    // Static subsystem reference
	private static Climber cInstance = new Climber();

	public static Climber getInstance() {
		return Climber.cInstance;
	}
	
	protected Climber()
    {
        m_sparkMax.restoreFactoryDefaults();
        m_sparkMax.setIdleMode(CANSparkMax.IdleMode.kBrake);
        
        m_Encoder.setReverseDirection(true);

        //m_sparkEncoder = m_sparkMax.getEncoder();
        zeroEncoder();
    }


    @Override
    public void initDefaultCommand()
    {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new DriveClimber());
//        setDefaultCommand(new KeepIn(-0.9));
        setDefaultCommand(new WPI_PIDClimber(0.0));
    }

    public void drive(double speed)
    {
        m_sparkMax.set(speed);
    }

    public void moveOut(double speed)
    {
        m_sparkMax.set(speed);
    }

    public boolean moveIn(double speed)
    {
        boolean retVal = false;
        
        // Move in (relax cable) only if encoder count is not negative
        if (getEncoderCount() > 0)
        {
            m_sparkMax.set(speed);
            retVal = false;
        }
        else {
            stop();
            retVal = true;
        }
        return retVal;
    }
    
    public void stop()
    {
        m_sparkMax.set(0.0);
    }

    public void zeroEncoder()
    {
        //m_sparkEncoder.setPosition(0.0);
        m_Encoder.reset();
    }

    public double getEncoderCount()
    {
        //return m_sparkEncoder.getPosition();
        return (double)(m_Encoder.get());
    }

    public void reportClimberStats()
    {
        SmartDashboard.putNumber("Climber Encoder", getEncoderCount());

    }
}
