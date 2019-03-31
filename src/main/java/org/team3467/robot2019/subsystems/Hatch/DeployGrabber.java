package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.Hatch.HatchGrabber.eHGAPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Add your docs here.
 */
public class DeployGrabber extends Command {

    private int counter;

    public DeployGrabber() {
        super();
        requires(Robot.sub_hatchgrabber);
    }

    protected void initialize() {
        counter = 0;
    }

    protected void execute() {
        if (counter++ > 25) {
            counter = 0; // report stats when counter == 0 
		}
        Robot.sub_hatchgrabber.moveHGAToPosition(eHGAPosition.PLACE, (counter == 0));
    }

    protected boolean isFinished() {
        return Robot.sub_hatchgrabber.isHGAOnTarget(eHGAPosition.PLACE);
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
