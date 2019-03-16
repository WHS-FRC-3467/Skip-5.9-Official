/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoLift;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class HoldMagicallyInPlace extends Command {

    private int m_counter;

    public HoldMagicallyInPlace() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.sub_fourbarlift);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        m_counter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        
        if (m_counter++ > 25) {
            m_counter = 0; // report stats when counter == 0 
		}
        
        // If Lift is in "Home" position, then no need to run closed loop; just let it rest
        if (Robot.sub_fourbarlift.getLiftPosition() == FourBarLift.eFourBarLiftPosition.HOME)
            Robot.sub_fourbarlift.driveManual(0.0);
        else
            Robot.sub_fourbarlift.holdMagically((m_counter == 0));
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
