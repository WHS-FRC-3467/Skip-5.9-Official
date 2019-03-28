/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoIntake;

import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.LED.LEDSerial;

import edu.wpi.first.wpilibj.command.Command;

public class MoveCargoIntakeArm extends Command {

    private CargoIntake.eCargoIntakeArmPosition m_position;
    private int counter;

    public MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition pos) {
        super();
        requires(Robot.sub_cargointake);

        m_position = pos;
    }

    @Override
    protected void initialize() {
        counter = 0;

        if(m_position == CargoIntake.eCargoIntakeArmPosition.INTAKE) {
            Robot.sub_led.setLEDPattern(LEDSerial.P_CARGO_IN);
        }
    }

    @Override
    protected void execute() {
        if (counter++ > 25) {
            counter = 0; // report stats when counter == 0 
		}
        Robot.sub_cargointake.moveArmToPosition(m_position, (counter == 0));
            
    }

    @Override
    protected boolean isFinished() {
        return Robot.sub_cargointake.isArmOnTarget(m_position);
    }

    @Override
    protected void end() {
        Robot.sub_cargointake.driveArmManually(0.0);
            Robot.sub_led.setLEDPattern(LEDSerial.DEFAULT_PATTERN);
    }

    @Override
    protected void interrupted() {
        end();
    }
}
