package org.team3467.robot2019.subsystems.CargoHold;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCargoHoldRollers extends Command {

    private double m_currentLevel;

    public DriveCargoHoldRollers() {
        requires(Robot.sub_cargohold);
    }

    protected void initialize()
    {
        m_currentLevel = CargoHold.CARGO_HOLD_PICKUP_CURRENT;
        Robot.sub_cargohold.cargoIsHeld(false);
    }
 
    protected void execute() {

        double speed = (-1.0) * OI.getOperatorLeftY();

        if (speed > 0.1)
        {
            // Returns True if current is met or exceeded
            if (Robot.sub_cargohold.intakeCargo(m_currentLevel))
            {
                m_currentLevel = CargoHold.CARGO_HOLD_STALL_CURRENT;
                Robot.sub_cargohold.cargoIsHeld(true);
            }
        }
        else if (speed < -0.5)
        {
            Robot.sub_cargohold.releaseCargo(1.0);
            m_currentLevel = CargoHold.CARGO_HOLD_PICKUP_CURRENT;
            Robot.sub_cargohold.cargoIsHeld(false);
        }

    }
   
    protected boolean isFinished() {
        if (OI.getOperatorButtonBack())
            return true;
        else
            return false;
    }

    protected void end() {
        Robot.sub_cargohold.stop();
	}

	protected void interrupted() {
		end();
	}

}