/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoHold;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;
import org.team3467.robot2019.subsystems.LED.LEDSerial;

import edu.wpi.first.wpilibj.command.Command;

public class HoldCargo extends Command {

    static final private double RUMBLE_TIME = 2.0;

    public HoldCargo() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.sub_cargohold);
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
        Robot.sub_led.setLEDPattern(LEDSerial.P_CARGO_HOLDING);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        if (Robot.sub_cargohold.isCargoHeld())
        {
            OI.setDriverRumble(this.timeSinceInitialized() < RUMBLE_TIME);

            Robot.sub_cargohold.intakeCargo(CargoHold.CARGO_HOLD_STALL_CURRENT);
        }
        else
        {
            Robot.sub_cargohold.stop();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    protected boolean isFinished() {
    return false;
    }

    // Called once after isFinished returns true
    @Override
    protected void end() {
        Robot.sub_led.setLEDPattern(LEDSerial.DEFAULT_PATTERN);
    }
        

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
