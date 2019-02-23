package org.team3467.robot2019.subsystems.CargoIntake;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveCargoIntakeRoller extends Command {

    public DriveCargoIntakeRoller() {
        requires(Robot.sub_cargointake);
    }

    protected void initialize()
    {
    }
 
    protected void execute() {

        double forwardSpeed = OI.getOperatorRightTrigger();
        double backwardSpeed = OI.getOperatorLeftTrigger();

        if(forwardSpeed > 0.0)
        {
            Robot.sub_cargointake.driveManualRoller(forwardSpeed);
        }
        else if (backwardSpeed > 0.0)
        {
            Robot.sub_cargointake.driveManualRoller(backwardSpeed * -1.0);
        }
        else
        {
            Robot.sub_cargointake.driveManualRoller(0.0);
        }

    }
   
    protected boolean isFinished() {
        return false;
    }

    protected void end() {
	}

	protected void interrupted() {
		end();
	}

}