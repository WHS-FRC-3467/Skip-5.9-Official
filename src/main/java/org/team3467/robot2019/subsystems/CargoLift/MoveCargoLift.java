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

    protected void initialize() {
        counter = 0;
    }

//
//  TODO: Add logic to make sure Cargo Lift is out of the way when moving the Cargo Intake Arm.
//
//
    @Override
    protected void execute() {

        //System.out.println("THE WIZARD IS MOVING YOUR MOTOR");
        if (counter++ > 25) {
            counter = 0; // report stats when counter == 0 
		}
       // Robot.sub_fourbarlift.moveArmToPosition(m_position, (counter == 0));

    }

    @Override
    protected boolean isFinished() {
        //return Robot.sub_fourbarlift.isArmOnTarget(m_position);
        return true;
    }

    protected void end() {
        //Robot.sub_fourbarlift.driveArmManually(0.0);
    }
    
    protected void interrupted() {
        end();
    }
}