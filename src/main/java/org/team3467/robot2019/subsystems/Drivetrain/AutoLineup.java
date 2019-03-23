/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Drivetrain;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.Limelight.Limelight;

import edu.wpi.first.wpilibj.command.Command;

public class AutoLineup extends Command {


  double lineup_tolerance = 0.0;




  public AutoLineup() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.sub_drivetrain);
    
    
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {

  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {

    //step 1: get the most updated limelight/lidar data

    double target_error = Limelight.getTx()+0.2;
    System.out.println("Limelight Error: " + target_error);



    //step 2: calculate the power needed to get perfectly inline
    

    //step 3: move

    Robot.sub_drivetrain.drive(-0.5, (target_error*-0.02), true);

    

    

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
