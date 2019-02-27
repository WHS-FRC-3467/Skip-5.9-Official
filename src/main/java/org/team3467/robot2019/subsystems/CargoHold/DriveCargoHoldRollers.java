package org.team3467.robot2019.subsystems.CargoHold;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCargoHoldRollers extends Command {

    static boolean m_isRollerOn = false;
    boolean m_finished = false;

    public DriveCargoHoldRollers() {
        requires(Robot.sub_cargohold);
    }

    protected void initialize()
    {
        m_finished = m_isRollerOn;
        m_isRollerOn = !(m_isRollerOn);  
    }
 
    protected void execute() {

        double speed = (-1.0) * OI.getOperatorLeftY();

        if (speed > 0.1)
        {
            Robot.sub_cargohold.intakeCargo();
        }
        else if (speed < -0.5)
        {
            Robot.sub_cargohold.releaseCargo(1.0);
        }

    }
   
    protected boolean isFinished() {
        return m_finished;
    }

    protected void end() {
        Robot.sub_cargohold.stop();
	}

	protected void interrupted() {
		end();
	}

}