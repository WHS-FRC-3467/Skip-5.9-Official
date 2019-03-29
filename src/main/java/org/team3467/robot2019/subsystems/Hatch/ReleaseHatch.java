package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ReleaseHatch extends Command {

    private boolean m_motionStopped;
    
    public ReleaseHatch() {
//        requires(Robot.sub_hatchgrabber);
    }

    protected void initialize() {
        m_motionStopped = false;
    }

    protected void execute() {
        m_motionStopped = Robot.sub_hatchgrabber.releaseHatch();
    }

    protected boolean isFinished() {
        return m_motionStopped;
    }

    protected void end() {
    }
    
    protected void interrupted() {
        end();
    }

}