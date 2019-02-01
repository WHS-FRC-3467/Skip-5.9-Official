package frc.Drivetrain.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CPrimaryDrive extends Command {


    public static final int mode_drive_with_xbox_controller = 0;

    int current_drive_mode = 0;

    public CPrimaryDrive() {
        requires(Robot.sub_drivetrain);
        this.setInterruptible(true);

    }


    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {
        
        switch(current_drive_mode) {

            case mode_drive_with_xbox_controller:

            Robot.sub_drivetrain.driveArcade(Robot.robot_oi.driverController.getTriggerAxis(Hand.kRight), Robot.robot_oi.driverController.getX(Hand.kLeft));

                break;
        }
        
    }

}