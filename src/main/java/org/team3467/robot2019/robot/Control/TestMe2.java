/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.robot.Control;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Add your docs here.
 */
public class TestMe2 extends InstantCommand {
  /**
   * Add your docs here.
   */
  public TestMe2() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    System.out.println("Test Successful!");

    Robot.sub_led.setLEDPattern(3);

  }
}
