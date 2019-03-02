package org.team3467.robot2019.subsystems.CargoIntake;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCargoIntakeRoller extends Command {

    private static final double INTAKE_SPEED = 0.6;

    public DriveCargoIntakeRoller() {
        // Don't require subsystem, so roller can turn while arm is moving
        //requires(Robot.sub_cargointake);
    }

    protected void initialize()
    {
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