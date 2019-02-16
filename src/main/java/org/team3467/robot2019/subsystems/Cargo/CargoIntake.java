package org.team3467.robot2019.subsystems.Cargo;

import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoIntake extends Subsystem {

    @SuppressWarnings("unused")
    private final SpeedController Controller_CargoIntakeArm = new SpeedControllerGroup(new Talon(RobotGlobal.CARGO_INTAKE_ARM_1), new Talon(RobotGlobal.CARGO_INTAKE_ARM_2));
    @SuppressWarnings("unused")
    private final SpeedController Controller_CargoIntakeRoller = new SpeedControllerGroup(new Victor(RobotGlobal.CARGO_INTAKE_ROLLER));

    @SuppressWarnings("unused")
    private boolean state;
   

        


    public CargoIntake() {
        state  = false;


    }


    @Override
    protected void initDefaultCommand() {

    }


}