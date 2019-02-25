package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;

/**
 * Add your docs here.
 */
public class DeployGrabber extends TimedCommand {

    public DeployGrabber() {
        super(HatchGrabber.DEPLOY_TIME);
        requires(Robot.sub_hatchgrabber);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        Robot.sub_hatchgrabber.deployGrabber();
    }

    // Called once after timeout
    @Override
    protected void end() {
        Robot.sub_hatchgrabber.stopDeploy();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
