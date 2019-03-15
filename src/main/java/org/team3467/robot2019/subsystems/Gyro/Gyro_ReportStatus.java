package org.team3467.robot2019.subsystems.Gyro;

import edu.wpi.first.wpilibj.command.Command;

public class Gyro_ReportStatus extends Command {

	private int counter;
	
	public Gyro_ReportStatus() {
		//requires(Robot.sub_gyro);
		this.setInterruptible(true);
	}
	
	protected void initialize() {
		counter = 0;
	}

	protected void execute() {
		if (counter < 50) {
			counter++;
		}
		else {
			//Robot.sub_gyro.displayGyroUpdate();
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
