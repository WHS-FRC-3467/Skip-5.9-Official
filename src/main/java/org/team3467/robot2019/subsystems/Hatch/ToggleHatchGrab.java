/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;


/**
 * Add your docs here.
 */
public class ToggleHatchGrab extends InstantCommand {


  boolean isActive = false;


  /**
   * Add your docs here.
   */
  public ToggleHatchGrab() {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.sub_hatchgrabber);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    System.out.println("HATCH MOVING AT TOGGLE");
    if(isActive) {
      new GrabHatch().execute();
      isActive = false;
      System.out.println("   > GRABBING");

    } else {
      isActive = true;
      new ReleaseHatch().execute();
      System.out.println("   > RELEASING");

    }
  }

  @Override
  protected boolean isFinished() {
    return true;
  }


}
