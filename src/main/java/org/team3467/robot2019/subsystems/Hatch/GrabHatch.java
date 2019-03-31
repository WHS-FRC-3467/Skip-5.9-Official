package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class GrabHatch extends Command {

    private boolean m_hatchInHand;
    
    public GrabHatch() {
//        requires(Robot.sub_hatchgrabber);
    }

    protected void initialize() {
        m_hatchInHand = false;
    }

    protected void execute() {
        m_hatchInHand = Robot.sub_hatchgrabber.grabHatch();
    }

    protected boolean isFinished() {
        return m_hatchInHand;
    }

    protected void end() {
    }
    
    protected void interrupted() {
        end();
    }
}