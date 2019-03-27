/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Climber;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class KeepIn extends InstantCommand {
  /**
   * Add your docs here.
   */
  private static double m_power;

  public KeepIn(double power) {
    super();
    // Use requires() here to declare subsystem dependencies
    requires(Robot.sub_climber);

    m_power = power;
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
      Robot.sub_climber.keepIn(m_power);
      SmartDashboard.putNumber("Climber Encoder", Robot.sub_climber.getEncoderCount());
  }

}
