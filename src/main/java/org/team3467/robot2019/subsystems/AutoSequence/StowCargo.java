/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.AutoSequence;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.CargoIntake.CargoIntake;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift.eFourBarLiftPosition;

import edu.wpi.first.wpilibj.command.Command;

public class StowCargo extends Command
{

    /*
    * Steps to Follow:
    *
    * Turn OFF Intake
    * Lift Up (OUTOFTHEWAY)
        * Poll position until there
    * Intake In (RETRACT)
        * Poll position until there
    * If Cargo held:
        *Lift to Intake position (INTAKE)
      else
        *Lift to Home position (HOME)
    * Exit command
    */

    // Command States
	public enum eCmdState {
        IntakeOff(0.0),
        LiftUp(0.0),
        IntakeIn(0.0),
        StowLift(0.0);
        
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
    private eFourBarLiftPosition m_liftEndPosition;

    //private double m_stateStartTime;

    public StowCargo()
    {
        requires(Robot.sub_cargointake);
        requires(Robot.sub_cargohold);
        requires(Robot.sub_fourbarlift);

    }

    protected void initialize()
    {

        m_currentState = eCmdState.IntakeOff;
        m_isFinished = false;

        // Set default closed loop tolerances for this command sequence
        Robot.sub_cargointake.setTolerance(20);
        Robot.sub_fourbarlift.setTolerance(50);

        if (Robot.sub_cargohold.isCargoHeld())
            m_liftEndPosition = FourBarLift.eFourBarLiftPosition.INTAKE;
        else
            m_liftEndPosition = FourBarLift.eFourBarLiftPosition.HOME;
        
        //m_stateStartTime = this.timeSinceInitialized();
    }

    protected void execute()
    {
        switch (m_currentState)
        {
        case IntakeOff:
            
            // Stop the Intake rollers
            Robot.sub_cargointake.driveRollerManually(0.0);
            
            m_currentState = eCmdState.LiftUp;
            break;

        case LiftUp:    
            
            // Start Lift movement
            Robot.sub_fourbarlift.moveLiftToPosition(FourBarLift.eFourBarLiftPosition.OUTOFTHEWAY, false);
            
            // Check if Lift is in position...
            if (Robot.sub_fourbarlift.checkLiftOnTarget(FourBarLift.eFourBarLiftPosition.OUTOFTHEWAY))
            {
                // .. and if so, start the Intake Arm position
                m_currentState  = eCmdState.IntakeIn;
            }
            break;
        
        case IntakeIn:

            // Start Intake Arm movement
            Robot.sub_cargointake.moveArmToPosition(CargoIntake.eCargoIntakeArmPosition.RETRACTED, false);
            
            // Check if Arm is in position...
            if (Robot.sub_cargointake.checkArmOnTarget(CargoIntake.eCargoIntakeArmPosition.RETRACTED))
            {
                // .. and if so, start the Lift position
                m_currentState  = eCmdState.StowLift;
            }
            break;
        
        case StowLift:

            // Start Lift movement
            Robot.sub_fourbarlift.moveLiftToPosition(m_liftEndPosition, false);
            
            // Check if Lift is in position...
            if (Robot.sub_fourbarlift.checkLiftOnTarget(m_liftEndPosition))
            {
                // .. and if so, then we are finished
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
        /*
            This command will end with:
            1) CargoIntake rollers off
            2) CargoIntake RETRACTED and holding via MotionMagic
            3) If CargoHold is holding a Cargo:
                    4BarLift at INTAKE position and holding via MotionMagic
             else
                    4BarLift at HOME position and holding via MotionMagic
         */
    }

    protected void interrupted()
    {
        end();
    }
}
