/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Climber;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Keeps the Climber's polejack retracted as long as it is running
 */
public class KeepIn extends Command {

    private static double m_power;
    private static boolean m_finished;

    public KeepIn(double power) {
        super();
        // Use requires() here to declare subsystem dependencies
        requires(Robot.sub_climber);

        m_power = power;
    }

    // Called once when the command executes
    protected void initialize() {
        m_finished = false;
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        m_finished = Robot.sub_climber.moveIn(m_power);

        SmartDashboard.putNumber("Climber Encoder", Robot.sub_climber.getEncoderCount());
        SmartDashboard.putBoolean("Climber Limit", Robot.sub_climber.m_limitSw.get());
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return m_finished;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.sub_climber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
