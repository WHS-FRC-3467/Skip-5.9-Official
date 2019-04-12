package org.team3467.robot2019.subsystems.Hatch;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.Limelight.Limelight;
import org.team3467.robot2019.subsystems.Limelight.Limelight.LightMode;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ReleaseHatch extends Command {

    private boolean m_motionStopped;
    
    public ReleaseHatch() {
        requires(Robot.sub_hatchgrabber);
    }

    protected void initialize() {
        m_motionStopped = false;
        SmartDashboard.putString("Hatch Cmd", "ReleaseHatch");
        Limelight.setLedMode(LightMode.eBlink);
    }

    protected void execute() {
        m_motionStopped = Robot.sub_hatchgrabber.releaseHatch();
    }

    protected boolean isFinished() {
        return m_motionStopped;
    }

    protected void end() {
        Limelight.setLedMode(LightMode.eOff);
    }
    
    protected void interrupted() {
        end();
    }

}