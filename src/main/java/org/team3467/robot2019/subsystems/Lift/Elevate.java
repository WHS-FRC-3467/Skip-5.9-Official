package org.team3467.robot2019.subsystems.Lift;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.command.Command;

public class Elevate extends Command {

    public Elevate() {
        requires(Robot.sub_fourbarlift);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {

        double speed = OI.getDriverRightTrigger();

        Robot.sub_fourbarlift.driveManual(speed, RobotGlobal.DIRECTION_NORMAL);
    }

}