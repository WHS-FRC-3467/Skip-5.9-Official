package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.command.InstantCommand;

public class ReleaseHatch extends InstantCommand {

    public ReleaseHatch() {
        requires(Robot.sub_hatchgrabber);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void execute() {
            if(!(Robot.sub_hatchgrabber.getHatcherGrabberState() == RobotGlobal.DIRECTION_REVERSE)) {
                Robot.sub_hatchgrabber.setHatchGrabber(0.2, RobotGlobal.DIRECTION_REVERSE);
            } else {
                Robot.sub_hatchgrabber.stopHatchGrabber();

            }
    }
    @Override
    protected void end() {
    }
    

}