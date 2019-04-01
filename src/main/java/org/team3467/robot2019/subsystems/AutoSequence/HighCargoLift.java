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

import edu.wpi.first.wpilibj.command.Command;

public class HighCargoLift extends Command {

    // Command States
   private enum eCmdState {
       IntakeOut,
       LiftToTarget,
       IntakeVertical;
   }

   private eCmdState m_currentState;
   private boolean m_isFinished;
   private int m_counter;
   private ArmMonitor.eArmPositions m_armPos;
   private FourBarLift.eFourBarLiftPosition m_target4BarPos;

   //private double m_stateStartTime;

   public HighCargoLift(FourBarLift.eFourBarLiftPosition barPos) {

       requires(Robot.sub_cargointake);
       requires(Robot.sub_fourbarlift);

       m_target4BarPos = barPos;
   }

   protected void initialize()
   {

        m_isFinished = false;
        m_counter = 0;

        // Set default closed loop tolerances for this command sequence
        Robot.sub_cargointake.setTolerance(20);
        Robot.sub_fourbarlift.setTolerance(50);

        // Find out where the arms currently are
        m_armPos = ArmMonitor.getArmPositions();

        // Determine starting state
        switch (m_armPos)
        {
        case INTAKE_VERTICAL_LIFT_IN:
        default:
            m_currentState = eCmdState.IntakeOut;
            break;
            
        case INTAKE_IN_LIFT_IN:
        case INTAKE_OUT_LIFT_IN:
        case INTAKE_IN_LIFT_OUT:
        case INTAKE_VERTICAL_LIFT_OUT:
        case INTAKE_OUT_LIFT_OUT:
            m_currentState = eCmdState.LiftToTarget;
            break;
        }
    }

   protected void execute()
   {
        if (m_counter++ > 25) {
            m_counter = 0; // report stats when counter == 0 
        }

       switch (m_currentState)
       {
       case IntakeOut:

           // Start Intake Arm movement
           Robot.sub_cargointake.moveArmToPosition(CargoIntake.eCargoIntakeArmPosition.INTAKE, (m_counter == 0));
           
           // Check if Arm is in position...
           if (Robot.sub_cargointake.checkArmOnTarget(CargoIntake.eCargoIntakeArmPosition.INTAKE))
           {
               // .. and if so, start the Lift position
               m_currentState  = eCmdState.LiftToTarget;
           }
           break;
       
       case LiftToTarget:
           
           // Start Lift movement
           Robot.sub_fourbarlift.moveLiftToPosition(m_target4BarPos, (m_counter == 0));
           
           // Check if Lift is in position or if it has at least gone by the OOTW position...
           if (Robot.sub_fourbarlift.checkLiftOnTarget(m_target4BarPos) || 
                Robot.sub_fourbarlift.getLiftEncoderPosition() > FourBarLift.eFourBarLiftPosition.OUTOFTHEWAY.getSetpoint())
           {
               // .. and if so, start the Intake Arm position
               m_currentState  = eCmdState.IntakeVertical;
           }
           break;
       
       case IntakeVertical:

           // Start Intake Arm movement
           Robot.sub_cargointake.moveArmToPosition(CargoIntake.eCargoIntakeArmPosition.VERTICAL, (m_counter == 0));
           
           // Check if Arm is in position...
           if (Robot.sub_cargointake.checkArmOnTarget(CargoIntake.eCargoIntakeArmPosition.VERTICAL))
           {
               // .. and if so, we're done
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
           1) 4BarLift at target position and holding via MotionMagic
           3) CargoIntake VERTICAL
           4) CargoIntake rollers off
        */
   }

   protected void interrupted()
   {
       end();
   }

}
