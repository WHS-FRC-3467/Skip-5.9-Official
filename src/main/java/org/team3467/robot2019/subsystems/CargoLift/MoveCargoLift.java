package org.team3467.robot2019.subsystems.CargoLift;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift.eFourBarLiftPosition;

import edu.wpi.first.wpilibj.command.Command;

public class MoveCargoLift extends Command {

    private eFourBarLiftPosition m_position;
    private int counter;

    public MoveCargoLift(eFourBarLiftPosition pos) {
        super();
        requires(Robot.sub_fourbarlift);

        m_position = pos;
    }
    public MoveCargoLift() {
        super();
        requires(Robot.sub_fourbarlift);

        m_position = FourBarLift.eFourBarLiftPosition.L2;
    }


    protected void initialize() {
        counter = 0;
    }

   @Override
    protected void execute() {

        //System.out.println("THE WIZARD IS MOVING YOUR MOTOR");
        if (counter++ > 25) {
            counter = 0; // report stats when counter == 0 
		}
        Robot.sub_fourbarlift.moveLiftToPosition(m_position, (counter == 0));

    }

    @Override
    protected boolean isFinished() {
        return Robot.sub_fourbarlift.isLiftOnTarget(m_position);
    }

    protected void end() {
        Robot.sub_fourbarlift.driveManual(0.0);
    }
    
    protected void interrupted() {
        end();
    }
}