/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.CargoHold;

import org.team3467.robot2019.robot.OI;
import org.team3467.robot2019.robot.Robot;

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
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {
        /* 
        Each time thru:
        If isCargoHeld() - Only checks flag
            Rumble?
            intakeCargo() - runs motor and returns true if it finds cargo THE FIRST TIME
        else
            calls stop() - turns off motor and clears flag 
        */
        if (Robot.sub_cargohold.isCargoHeld())
        {
            // Optional: Rumble the Driver Control to indicate "Cargo In Hand"
            // OI.setDriverRumble(this.timeSinceInitialized() < RUMBLE_TIME);

            Robot.sub_cargohold.intakeCargo(CargoHold.CARGO_HOLD_STALL_CURRENT, false);
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
    }
        

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
    }
}
