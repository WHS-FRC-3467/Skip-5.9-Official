package org.team3467.robot2019.subsystems.Cargo;

import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoLift extends Subsystem {

    @SuppressWarnings("unused")

    private final SpeedController Controller_CargoLift = new SpeedControllerGroup(new Talon(RobotGlobal.CARGO_LIFT));

    @Override
    protected void initDefaultCommand() {

    }

    //TODO uncomplete: cargolift
    

}