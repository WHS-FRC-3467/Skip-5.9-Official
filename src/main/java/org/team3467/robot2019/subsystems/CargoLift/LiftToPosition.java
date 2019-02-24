package org.team3467.robot2019.subsystems.CargoLift;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift.eFourBarLiftPosition;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class LiftToPosition extends InstantCommand {

    private eFourBarLiftPosition position;

    @Override
    protected boolean isFinished() {
        return true;
    }

    public LiftToPosition(eFourBarLiftPosition position) {
        this.position = position;
        requires(Robot.sub_fourbarlift);
    }

    @Override
    protected void execute() {
        Robot.sub_fourbarlift.moveMagically(position);
    }


}