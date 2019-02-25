/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class RunTest extends Command {

    private int direction;

    public RunTest() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.sub_hatchgrabber);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        direction = 1;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (direction > 0)
        {
            if (Robot.sub_hatchgrabber.grabHatch())
                direction = direction * -1;
        }
        else if (direction < 0)
        {
            if (Robot.sub_hatchgrabber.releaseHatch())
                direction = direction * -1;
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
