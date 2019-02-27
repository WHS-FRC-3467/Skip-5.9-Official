package org.team3467.robot2019.subsystems.CargoIntake;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCargoIntakeRoller extends Command {

    private static final double INTAKE_SPEED = 0.5;

    static boolean m_isRollerOn = false;
    boolean m_finished = false;

    public DriveCargoIntakeRoller() {
        requires(Robot.sub_cargointake);
    }

    protected void initialize()
    {
        m_finished = m_isRollerOn;
        m_isRollerOn = !(m_isRollerOn);  
    }
 
    protected void execute() {

        double speed = (-1.0) * OI.getOperatorRightY();

        if ((speed > 0.1) || (speed < -0.5))
        {
            Robot.sub_cargointake.driveRollerManually(speed);
        }
        else
        {
            Robot.sub_cargointake.driveRollerManually(INTAKE_SPEED);
        }

    }
   
    protected boolean isFinished() {
        return m_finished;
    }

    protected void end() {
        Robot.sub_cargointake.driveRollerManually(0.0);
	}

	protected void interrupted() {
		end();
	}

}