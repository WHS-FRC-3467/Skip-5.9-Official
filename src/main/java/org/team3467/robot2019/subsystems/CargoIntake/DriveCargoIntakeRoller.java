package org.team3467.robot2019.subsystems.CargoIntake;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCargoIntakeRoller extends Command {

    double m_commandedSpeed;

    public DriveCargoIntakeRoller() {
        // Don't require subsystem, so roller can turn while arm is moving
        //requires(Robot.sub_cargointake);
        m_commandedSpeed = 0;
    }

    public DriveCargoIntakeRoller(double speed) {
        // Don't require subsystem, so roller can turn while arm is moving
        //requires(Robot.sub_cargointake);
        m_commandedSpeed = speed;
    }

    protected void initialize()
    {
    }
 
    protected void execute() {

        double speed;
        
        if (m_commandedSpeed == 0.0)
            speed = (-0.3) * OI.getOperatorRightY();
        else
            speed = m_commandedSpeed;

        if ((speed > 0.1) || (speed < -0.5))
        {
            Robot.sub_cargointake.driveRollerManually(speed);
        }
        else
        {
            Robot.sub_cargointake.driveRollerManually(CargoIntake.CARGO_INTAKE_ROLLER_SPEED);
        }
    }
   
    protected boolean isFinished() {

        if (OI.getOperatorButtonA())
            return true;
        else
            return false;
    }

    protected void end() {
        Robot.sub_cargointake.driveRollerManually(0.0);
	}

	protected void interrupted() {
		end();
	}

}