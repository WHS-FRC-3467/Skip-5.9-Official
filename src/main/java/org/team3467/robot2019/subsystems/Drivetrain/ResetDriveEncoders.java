package org.team3467.robot2019.subsystems.Drivetrain;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class ResetDriveEncoders extends InstantCommand {

    public ResetDriveEncoders() {
        requires(Robot.sub_drivetrain);
    }

    protected void execute() {
		Robot.sub_drivetrain.resetEncoders();
    }

}
