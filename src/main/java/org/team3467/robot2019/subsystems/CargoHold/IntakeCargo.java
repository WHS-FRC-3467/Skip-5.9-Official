/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FRC Team 3467                                           */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoHold;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCargo extends Command {

    
    public IntakeCargo() {
        requires(Robot.sub_cargohold);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        Robot.sub_cargohold.intakeCargo();
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished()
    {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end()
    {
        Robot.sub_cargohold.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted()
    {
        end();
    }
}
