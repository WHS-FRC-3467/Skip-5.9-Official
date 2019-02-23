/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoIntake;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class MoveCargoIntake extends InstantCommand {
  public MoveCargoIntake() {
    requires(Robot.sub_cargointake);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    System.out.println("THE WIZARD IS MOVING YOUR MOTOR");
    //Robot.sub_cargointake.moveMagically(Robot.sub_cargointake);

  }

  @Override
  protected boolean isFinished() {
    return true;
  }

  @Override
  protected void end() {
  }


  @Override
  protected void interrupted() {
  }
}
