/*----------------------------------------------------------------------------*/
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.AutoSequence;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.CargoIntake.CargoIntake;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift;

/**
 * Monitors and reports on the position of the 4Bar Lift Arm and the Cargo Intake Arm
 */
public class ArmMonitor {

    public enum eArmPositions
    { 
        INTAKE_IN_LIFT_IN,
        INTAKE_VERTICAL_LIFT_IN,
        INTAKE_OUT_LIFT_IN,
        INTAKE_IN_LIFT_OUT,
        INTAKE_VERTICAL_LIFT_OUT,
        INTAKE_OUT_LIFT_OUT;
    }
        
    public static eArmPositions getArmPositions()
    {
        int intakeArmPosition = Robot.sub_cargointake.getArmEncoderPosition();
        int liftArmPosition = Robot.sub_fourbarlift.getLiftEncoderPosition();

        // Determine if 4Bar Lift is "Out" far enough to avoid interference with Intake Arm
        boolean liftIsOut = (liftArmPosition >= (FourBarLift.eFourBarLiftPosition.OUTOFTHEWAY.getSetpoint() - 50));

        if (intakeArmPosition < (CargoIntake.eCargoIntakeArmPosition.RETRACTED.getSetpoint() + 100))
        {
            // INTAKE is IN
            return (liftIsOut ? eArmPositions.INTAKE_IN_LIFT_OUT : eArmPositions.INTAKE_IN_LIFT_IN);
        }
        else if (intakeArmPosition > CargoIntake.eCargoIntakeArmPosition.OUTSIDE.getSetpoint())
        {
            // INTAKE is OUT
            return (liftIsOut ? eArmPositions.INTAKE_OUT_LIFT_OUT : eArmPositions.INTAKE_OUT_LIFT_IN);
        }
        else
        {
            // INTAKE is VERTICAL
            return (liftIsOut ? eArmPositions.INTAKE_VERTICAL_LIFT_OUT : eArmPositions.INTAKE_VERTICAL_LIFT_IN);
        }

    }

}
