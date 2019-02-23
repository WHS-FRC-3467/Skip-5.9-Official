/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FRC Team 3467                                           */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoHold;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Stop running the Cargo Hold
 */
public class StopCargoHold extends InstantCommand {

  public StopCargoHold() {
    super();
    requires(Robot.sub_cargohold);
  }

  // Called once when the command executes
  @Override
  protected void initialize() {
    Robot.sub_cargohold.stop();
  }

}
