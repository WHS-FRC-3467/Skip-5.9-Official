package org.team3467.robot2019.subsystems.Drivetrain;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveBot extends Command {


    public static final int CURVATURE_MODE = 0;

    int current_drive_mode = 0;

    public DriveBot() {
        requires(Robot.sub_drivetrain);
        this.setInterruptible(true);

    }


    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    @SuppressWarnings("static-access")
    protected void execute() {
        
            //only drive with drivebase if crawl mode is disabled
        if(!Robot.sub_drivetrain.crawlModeEnabled) {

        switch(current_drive_mode) {

            case CURVATURE_MODE:

                if(Robot.robot_oi.getDriverLeftTrigger() == 0 && Robot.robot_oi.getDriverRightTrigger()  > 0) {
                    Robot.sub_drivetrain.driveCurvature(Robot.robot_oi.getDriverRightTrigger(), -Robot.robot_oi.getDriverLeftX(), Robot.sub_drivetrain.quickTurnsEnabled);

                } else {
                    Robot.sub_drivetrain.driveCurvature(-Robot.robot_oi.getDriverLeftTrigger(), -Robot.robot_oi.getDriverLeftX(), Robot.sub_drivetrain.quickTurnsEnabled);

                }


                break;
        }
        

        }

    }

}