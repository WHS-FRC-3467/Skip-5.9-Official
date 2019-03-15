/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoLift;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *  
 */
public class ReportStats extends Command {

	private int counter;
	
	public ReportStats() {
		requires(Robot.sub_fourbarlift);
	}
	
	protected void initialize() {
		counter = 0;
	}

	protected void execute() {

        if (counter++ > 25) {
            
            Robot.sub_fourbarlift.reportStats();
            counter = 0;
		}
	}

	protected boolean isFinished() {
		return false;
	}

	protected void end() {
		
	}

	protected void interrupted() {
		end();
	}
}
