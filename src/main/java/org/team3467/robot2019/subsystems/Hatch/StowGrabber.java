package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Add your docs here.
 */
public class StowGrabber extends Command {

    private boolean m_motionStopped;
    
    public StowGrabber() {
    // Use requires() here to declare subsystem dependencies
        requires(Robot.sub_hatchgrabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        m_motionStopped = Robot.sub_hatchgrabber.isGrabberDeployed();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (m_motionStopped)
            Robot.sub_hatchgrabber.setGrabberDeployed();
        else
            m_motionStopped = Robot.sub_hatchgrabber.releaseHatch();

    }

    protected boolean isFinished() {
        return m_motionStopped;
    }
    
    // Called once after timeout
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }

}
