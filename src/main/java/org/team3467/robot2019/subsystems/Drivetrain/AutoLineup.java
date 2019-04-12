/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Drivetrain;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.Limelight.Limelight;
import org.team3467.robot2019.subsystems.Limelight.Limelight.CameraMode;
import org.team3467.robot2019.subsystems.Limelight.Limelight.LightMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;

public class AutoLineup extends Command {

    boolean targetIsFound = false;
    double predictedDriveCommand = 0.0;
    double predictedSteerCommand = 0.0;

    public AutoLineup() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.sub_drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Limelight.setPipeline(1);
        Limelight.setLedMode(LightMode.eOn);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        Update_Limelight_Tracking();


                // was a target found and locked? if so, drive to it with the drive constants
          if (targetIsFound)
          {
                Robot.sub_drivetrain.driveArcade(-predictedDriveCommand,-predictedSteerCommand);
          }
          else
          {
            Robot.sub_drivetrain.driveArcade(0.0, 0.0);
          }
        

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Limelight.setPipeline(0);
        Limelight.setLedMode(LightMode.eOff);
            
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }


    public void Update_Limelight_Tracking()
  {
        // These numbers must be tuned for your Robot!  Be careful!
        final double STEER_K = 0.15;                    // how hard to turn toward the target
        final double DRIVE_K = 0.26;                    // how hard to drive fwd toward the target
        final double DESIRED_TARGET_AREA = 5.0;        // Area of the target when the robot reaches the wall
        final double MAX_DRIVE = 0.37;                   // Simple speed limit so we don't drive too fast

        double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
        double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0);
        double ta = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta").getDouble(0);
        if (tv < 1.0)
        {
          targetIsFound = false;
          predictedDriveCommand = 0.0;
          predictedSteerCommand = 0.0;
          return;
        }

        targetIsFound = true;

        // Start with proportional steering
        double steer_cmd = tx * STEER_K;
        predictedSteerCommand = steer_cmd;

        // try to drive forward until the target area reaches our desired area
        double drive_cmd = (DESIRED_TARGET_AREA - ta) * DRIVE_K;

        // don't let the robot drive too fast into the goal
        if (drive_cmd > MAX_DRIVE)
        {
          drive_cmd = MAX_DRIVE;
        }
        predictedDriveCommand= drive_cmd;
  }
}
