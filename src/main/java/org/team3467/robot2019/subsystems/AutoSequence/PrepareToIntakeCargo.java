/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.AutoSequence;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.CargoIntake.CargoIntake;
import org.team3467.robot2019.subsystems.CargoHold.CargoHold;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift;

import edu.wpi.first.wpilibj.command.Command;

public class PrepareToIntakeCargo extends Command
{

    /*
    * Steps to Follow:
    *
    * Lift Up (OUTOFTHEWAY)
        * Poll position until there
    * Intake Out (INTAKE)
        * Poll position until there
    * Lift to Intake position (INTAKE)
    * Turn ON Hold
    * Turn ON Intake
    * When Cargo Hold reports Cargo is being held:
        * Turn off Intake
        * Lower Hold current limit
    * Exit command
    */

        // Command States
	public enum eCmdState {
        LiftUp(0.0),
        IntakeOut(0.0),
        LiftToIntake(0.0),
        WaitForCapture(0.0);
        
		private final double time;
		
		private eCmdState(double time) {
			this.time = time;
		}
		
		public double getTime() {
			return this.time;
		}
	}

    private eCmdState m_currentState;
    private boolean m_isFinished;
    //private double m_stateStartTime;

    private double m_cargoHoldCurrent;

    public PrepareToIntakeCargo()
    {
        requires(Robot.sub_cargointake);
        requires(Robot.sub_cargohold);
        requires(Robot.sub_fourbarlift);

    }

    protected void initialize()
    {

        m_currentState = eCmdState.LiftUp;
        m_isFinished = false;
        m_cargoHoldCurrent = CargoHold.CARGO_HOLD_PICKUP_CURRENT;

        // Set default closed loop tolerances for this command sequence
        Robot.sub_cargointake.setTolerance(20);
        Robot.sub_fourbarlift.setTolerance(50);

        //m_stateStartTime = this.timeSinceInitialized();
    }

    protected void execute()
    {

        switch (m_currentState)
        {
        case LiftUp:
            
            // Start Lift movement
            Robot.sub_fourbarlift.moveLiftToPosition(FourBarLift.eFourBarLiftPosition.OUTOFTHEWAY, false);
            
            // Check if Lift is in position...
            if (Robot.sub_fourbarlift.checkLiftOnTarget(FourBarLift.eFourBarLiftPosition.OUTOFTHEWAY))
            {
                // .. and if so, start the Intake Arm position
                m_currentState  = eCmdState.IntakeOut;
            }
            break;
        
        case IntakeOut:

            // Start Intake Arm movement
            Robot.sub_cargointake.moveArmToPosition(CargoIntake.eCargoIntakeArmPosition.INTAKE, false);
            
            // Check if Arm is in position...
            if (Robot.sub_cargointake.checkArmOnTarget(CargoIntake.eCargoIntakeArmPosition.INTAKE))
            {
                // .. and if so, start the Lift position
                m_currentState  = eCmdState.LiftToIntake;
            }
            break;
        
        case LiftToIntake:

            // Start Lift movement
            Robot.sub_fourbarlift.moveLiftToPosition(FourBarLift.eFourBarLiftPosition.INTAKE, false);
            
            // Check if Lift is in position...
            if (Robot.sub_fourbarlift.checkLiftOnTarget(FourBarLift.eFourBarLiftPosition.INTAKE))
            {
                // .. and if so, start the Intake rollers
                m_currentState  = eCmdState.WaitForCapture;
            }
            break;
        
        case WaitForCapture:

            Robot.sub_cargointake.driveRollerManually(CargoIntake.CARGO_INTAKE_ROLLER_SPEED);
    
            if (Robot.sub_cargohold.intakeCargo(m_cargoHoldCurrent))
            {
                m_cargoHoldCurrent = CargoHold.CARGO_HOLD_STALL_CURRENT;
                Robot.sub_cargohold.cargoIsHeld(true);

                // cargo detected - we're done
                m_isFinished = true;
            }
            break;
        
        default:
            break;
        }
    }

    protected boolean isFinished()
    {
        return m_isFinished;
    }

    protected void end()
    {
        // Stop the Intake rollers
        Robot.sub_cargointake.driveRollerManually(0.0);

        /*
            This command will end with:
            1) CargoHold holding a Cargo (unless interrupted() is called)
            2) 4BarLift at INTAKE position and holding via MotionMagic
            3) CargoIntake deployed to INTAKE position and holding via MotionMagic
            4) CargoIntake rollers off
         */
    }

    protected void interrupted()
    {
        // NOTE: If this command is interrupted, we may not have a Cargo in the Hold yet
        end();
    }
}