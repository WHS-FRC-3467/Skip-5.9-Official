package org.team3467.robot2019.subsystems.CargoLift;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.command.Command;

public class LiftManually extends Command {

    static final double SPEED_REDUCTION = 0.5;

    public LiftManually() {
        requires(Robot.sub_fourbarlift);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void execute() {

        System.out.println("MANUAL OPERATION");

        double fSpeed = OI.getOperatorRightTrigger() * SPEED_REDUCTION;
        double rSpeed = OI.getOperatorLeftTrigger() * SPEED_REDUCTION;

        if(fSpeed > 0) {
            Robot.sub_fourbarlift.driveManual(fSpeed, RobotGlobal.DIRECTION_NORMAL);

        } else {
            Robot.sub_fourbarlift.driveManual(rSpeed, RobotGlobal.DIRECTION_REVERSE);

        }

    }

}