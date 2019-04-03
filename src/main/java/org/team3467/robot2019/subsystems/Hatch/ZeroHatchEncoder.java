/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class ZeroHatchEncoder extends InstantCommand {
  /**
   * Add your docs here.
   */
  public ZeroHatchEncoder() {
    super();
    // Use requires() here to declare subsystem dependencies
    requires(Robot.sub_hatchgrabber);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
      Robot.sub_hatchgrabber.zeroHGAEncoder();
      SmartDashboard.putString("Hatch Cmd", "ZeroHatchEncoder");
  }

}
