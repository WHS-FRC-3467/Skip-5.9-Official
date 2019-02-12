package frc.Subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveBot extends Command {


    public static final int CURVATURE_MODE = 0;

    int current_drive_mode = 0;

    public DriveBot() {
        //requires(Robot.sub_drivetrain);
        this.setInterruptible(true);

    }


    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    @SuppressWarnings("static-access")
    protected void execute() {
        
        switch(current_drive_mode) {

            case CURVATURE_MODE:

                if(Robot.robot_oi.getDriverLeftTrigger() == 0 && Robot.robot_oi.getDriverRightTrigger()  > 0) {
                    //Robot.sub_drivetrain.driveCurvature(-Robot.robot_oi.getDriverRightTrigger(), Robot.robot_oi.getDriverLeftX(), false);

                } else {
                    //Robot.sub_drivetrain.driveCurvature(Robot.robot_oi.getDriverLeftTrigger(), -Robot.robot_oi.getDriverLeftX(), false);

                }


                break;
        }
        
    }

}