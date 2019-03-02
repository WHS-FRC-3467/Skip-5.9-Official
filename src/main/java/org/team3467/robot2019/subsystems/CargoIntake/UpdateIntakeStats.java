
package org.team3467.robot2019.subsystems.CargoIntake;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *  
 */
public class UpdateIntakeStats extends InstantCommand {

	public UpdateIntakeStats() {
		requires(Robot.sub_cargointake);
		this.setInterruptible(true);
	}
	
	protected void execute() {
		Robot.sub_cargointake.updateTalonStats();
	}

}
