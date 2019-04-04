/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class UpdateHatchStats extends InstantCommand {

	public UpdateHatchStats() {
		requires(Robot.sub_hatchgrabber);
		this.setInterruptible(true);
	}
	
	protected void execute() {
        Robot.sub_hatchgrabber.updateTalonStats();
        Robot.sub_hatchgrabber.setArmSetpointFromDashboard();
	}

}

