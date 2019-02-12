package frc.Subsystems.Hatch;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.robot.Robot;
import frc.robot.RobotGlobal;

public class GrabHatch extends InstantCommand {

    public GrabHatch() {
        requires(Robot.sub_hatchgrabber);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void execute() {
        if(!(Robot.sub_hatchgrabber.getHatcherGrabberState() == RobotGlobal.DIRECTION_NORMAL)) {
            Robot.sub_hatchgrabber.setHatchGrabber(0.2, RobotGlobal.DIRECTION_NORMAL);
            } else {
                Robot.sub_hatchgrabber.stopHatchGrabber();

            }
    }
    @Override
    protected void end() {
      //  Robot.sub_hatchgrabber.disable();
    }
    

}