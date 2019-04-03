package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GrabHatch extends Command {

    private boolean m_hatchInHand;
    private int m_counter;
    
    public GrabHatch() {
        requires(Robot.sub_hatchgrabber);
    }

    protected void initialize() {
        m_hatchInHand = false;
        m_counter = 0;
        SmartDashboard.putString("Hatch Cmd", "GrabHatch");
    }

    protected void execute() {
        if (m_counter++ > 25) {
            m_counter = 0; // report stats when counter == 0 
		}

        Robot.sub_hatchgrabber.moveHGAToPosition(HatchGrabber.eHGAPosition.PLACE, (m_counter == 0));

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