/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Command;

public class RunTest extends Command {
  WPI_TalonSRX one_talon = Robot.m_hatch.getOne_Talon();
  int direction = 1;

  public RunTest() {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_hatch);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    one_talon.set(0.5*direction);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    boolean isClosedFwd = one_talon.getSensorCollection().isFwdLimitSwitchClosed();
    boolean isClosedRev = one_talon.getSensorCollection().isRevLimitSwitchClosed();
    if (isClosedFwd || isClosedRev)
      direction = direction*-1;

    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
