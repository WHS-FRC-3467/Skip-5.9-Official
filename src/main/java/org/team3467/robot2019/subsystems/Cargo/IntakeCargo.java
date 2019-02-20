/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FRC Team 3467                                           */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Cargo;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class IntakeCargo extends Command {
  
  private boolean m_intakeStopped = false;
  Timer m_timer = new Timer();

  public IntakeCargo() {
    requires(Robot.sub_cargohold);
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
 
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {
    SmartDashboard.putNumber("CargoHold Current Draw", Robot.sub_cargohold.motorCurrent);
    // If intake is stopped due to high current, give it a rest for a bit
    if (m_intakeStopped == true)
    {
      // Let the timer run for 2 seconds before resuming intake
      if (m_timer.get() > 2.0)
      {
        m_intakeStopped = false;
        m_timer.stop();
      }
    }
    else
    {
      m_intakeStopped = Robot.sub_cargohold.intakeCargo();

      if (m_intakeStopped == true)
      {
        // Intake has stopped; reset timer and start it up
        m_timer.reset();
        m_timer.start();
      }
    }
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
