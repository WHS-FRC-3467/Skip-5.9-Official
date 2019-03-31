package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Add your docs here.
 */
public class StowGrabber extends Command {

    private int counter;
    
    public StowGrabber() {
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
        Robot.sub_hatchgrabber.moveHGAToPosition(HatchGrabber.eHGAPosition.STOW, (counter == 0));
    }

    protected boolean isFinished() {
        return Robot.sub_hatchgrabber.isHGAOnTarget(HatchGrabber.eHGAPosition.STOW);
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }

}
