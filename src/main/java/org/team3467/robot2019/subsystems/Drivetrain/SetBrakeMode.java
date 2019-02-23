package org.team3467.robot2019.subsystems.Drivetrain;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class SetBrakeMode extends InstantCommand {

	boolean brakesOn;
	
    public SetBrakeMode(boolean setBrake) {
        requires(Robot.sub_drivetrain);
        brakesOn = setBrake;
    }

    protected void execute() {
    	Robot.sub_drivetrain.setTalonBrakes(brakesOn);
    }

}
