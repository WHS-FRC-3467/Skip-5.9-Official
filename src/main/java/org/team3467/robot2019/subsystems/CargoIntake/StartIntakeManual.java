package org.team3467.robot2019.subsystems.CargoIntake;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class StartIntakeManual extends Command {

    public StartIntakeManual() {
        requires(Robot.sub_cargointake);
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