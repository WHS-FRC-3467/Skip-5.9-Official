package org.team3467.robot2019.subsystems.CargoIntake;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StartIntakeManual extends Command {

    public StartIntakeManual() {
        
        // Do not require CargoIntake in this command. This will allow PrepareToIntakeCargo() to keep going even if the rollers need to be started manually.
        // HOWEVER: You must then check for cargo even if the 4Bar is still moving into position (or is stuck), otherwise Cargo Hold motor will
        // stall at excessive current.
        // Once PrepareToIntakeCargo() is finished, rollers will turn off.

        //requires(Robot.sub_cargointake);
    }

    @Override
    protected void execute() {
        Robot.sub_cargointake.driveRollerManually(0.75);
    }


    @Override
    protected boolean isFinished() {
        return false;
    }

}