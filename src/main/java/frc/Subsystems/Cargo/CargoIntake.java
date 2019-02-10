package frc.Subsystems.Cargo;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotGlobal;

public class CargoIntake extends Subsystem {

    @SuppressWarnings("unused")
    private final SpeedController Controller_CargoIntake = new SpeedControllerGroup(new Talon(RobotGlobal.CARGO_INTAKE_ROLLERS));
    
    @SuppressWarnings("unused")
    private boolean state;
   

        


    public CargoIntake() {
        state  = false;


    }


    @Override
    protected void initDefaultCommand() {

    }


}