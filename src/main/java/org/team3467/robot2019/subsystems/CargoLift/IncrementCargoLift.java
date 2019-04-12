/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoLift;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Add your docs here.
 */
public class IncrementCargoLift extends Command {
  /**
   * Add your docs here.
   */


   private int delta;
  private boolean isIncrementedUpward;
    private int increment;

  public IncrementCargoLift(boolean incrementUpward, int increment) {
    super();
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.isIncrementedUpward = incrementUpward;
    this.increment = increment;
    requires(Robot.sub_fourbarlift);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {

    if(isIncrementedUpward) delta = Robot.sub_fourbarlift.getLiftEncoderPosition() + increment;
    else delta = Robot.sub_fourbarlift.getLiftEncoderPosition() - increment;

    System.out.println("Incrementing FBL with delta: " + delta);

  }

  @Override
  protected boolean isFinished() {
      int tolerance = 30;

      if(Math.abs((Robot.sub_fourbarlift.getLiftEncoderPosition() - delta)) <= tolerance) {
        System.out.println("increment position found. stopping incrementer");
        return true;
      } else {
        System.out.println("target is " + delta);
        System.out.println("current is " + Robot.sub_fourbarlift.getLiftEncoderPosition());

        return false;
      }
  }

  @Override
  protected void execute() {
    System.out.println("moving now");
    Robot.sub_fourbarlift.moveLiftToExactPosition(delta);
    
  }
}
