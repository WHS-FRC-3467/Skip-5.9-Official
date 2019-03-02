
package org.team3467.robot2019.subsystems.CargoLift;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *  
 */
public class UpdateLiftStats extends InstantCommand {

	public UpdateLiftStats() {
		requires(Robot.sub_fourbarlift);
		this.setInterruptible(true);
	}
	
	protected void execute() {
		Robot.sub_fourbarlift.updateTalonStats();
	}

}
