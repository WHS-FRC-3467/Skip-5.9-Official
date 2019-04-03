/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveHatchDeployment extends Command {
  public DriveHatchDeployment() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.sub_hatchgrabber);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    SmartDashboard.putString("Hatch Cmd", "DriveHatchDeployment");
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    double forwardSpeed = OI.getOperatorRightTrigger();
    double backwardSpeed = OI.getOperatorLeftTrigger();

    if(forwardSpeed > 0.0)
    {
        Robot.sub_hatchgrabber.driveManual(forwardSpeed);
    }
    else if (backwardSpeed > 0.0)
    {
      Robot.sub_hatchgrabber.driveManual(-backwardSpeed);
    }
    else
    {
      Robot.sub_hatchgrabber.driveManual(0.0);
    }

    Robot.sub_hatchgrabber.reportStats();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.sub_hatchgrabber.driveManual(0.0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
