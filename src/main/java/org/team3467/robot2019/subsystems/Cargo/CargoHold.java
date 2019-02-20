/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FRC Team 3467                                           */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Cargo;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * CargoHold is the holder for Cargo. It lives on the end of the 4Bar Lift Arm.
 */
public class CargoHold extends Subsystem
{
  private WPI_TalonSRX m_CargoHold = new WPI_TalonSRX(RobotGlobal.CARGO_HOLD);

  private static final double CARGO_INTAKE_SPEED = 0.7;
  private static final double CARGO_HOLD_STALL_CURRENT = 6.0; //TODO: Determine correct value here


  public double motorCurrent;


  @Override
  public void initDefaultCommand()
  { 
    // Set the default command for a subsystem here.
    setDefaultCommand(new StopCargoHold());
  }

  /**
   * Inake Cargo while checking current draw
   * @return boolean - True if itake was stopped due to high current, False otherwise
   */
  public boolean intakeCargo()
  {
    boolean noIntake = checkCargoStall();

    // Motor is stalled or close to it; stop it
    // Otherwise, run motor to collect and hold cargo
    m_CargoHold.set(noIntake ? 0.0 : -CARGO_INTAKE_SPEED);
    
    return noIntake;
  }

  public void releaseCargo(double speed)
  {
    m_CargoHold.set(speed);
  }

  public void stop()
  {
    m_CargoHold.set(0.0);
  }

  public boolean checkCargoStall()
  {
     motorCurrent = Robot.robot_pdp.getCurrent(RobotGlobal.PDP_CARGO_HOLD_CHAN);
    SmartDashboard.putNumber("CargoHold Current Draw", motorCurrent);
    return (motorCurrent > CARGO_HOLD_STALL_CURRENT);
  }


}
