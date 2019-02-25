/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FRC Team 3467                                           */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoHold;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ReleaseCargo extends Command
{

  private double m_releaseSpeed = 1.0;  // Default release speed

  public ReleaseCargo()
  {
    requires(Robot.sub_cargohold);
  }

  public ReleaseCargo(double speed)
  {
    requires(Robot.sub_cargohold);
    m_releaseSpeed = speed;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize()
  {
 
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {
    Robot.sub_cargohold.releaseCargo(m_releaseSpeed);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished()
  {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end()
  {
    Robot.sub_cargohold.stop();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted()
  {
    end();
  }
}
