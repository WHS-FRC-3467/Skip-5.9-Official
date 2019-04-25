/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Climber;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

    public class DriveClimber extends Command {

    public DriveClimber()
    {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.sub_climber);

    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
            System.out.println("moving the pole jack at : " + OI.getOperatorLeftY());
        double speed = OI.getOperatorLeftY();
        double encoderCnt = Robot.sub_climber.getEncoderCount();
        boolean limitSwitch = Robot.sub_climber.m_limitSw.get();

        if (speed > 0.2 )
        {
            Robot.sub_climber.drive(speed * 0.5);
        } else if (speed < -0.2 && limitSwitch == true && encoderCnt > 0.0) {
            Robot.sub_climber.drive(speed * 0.5);
        }
        else
            Robot.sub_climber.drive(0.0);

        SmartDashboard.putNumber("Climber Speed", speed);
        SmartDashboard.putNumber("Climber Encoder", encoderCnt);
        SmartDashboard.putBoolean("Climber Limit", limitSwitch);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
        return false;
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
