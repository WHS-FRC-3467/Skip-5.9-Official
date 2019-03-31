/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FRC Team 3467                                           */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoHold;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class IntakeCargo extends Command {

    private double m_currentLevel;

    public IntakeCargo() {
        requires(Robot.sub_cargohold);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        m_currentLevel = CargoHold.CARGO_HOLD_PICKUP_CURRENT;
        Robot.sub_cargohold.cargoIsHeld(false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
        // Returns True if current is met or exceeded
        if (Robot.sub_cargohold.intakeCargo(m_currentLevel, false))
        {
            m_currentLevel = CargoHold.CARGO_HOLD_STALL_CURRENT;
            Robot.sub_cargohold.cargoIsHeld(true);
        }
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
