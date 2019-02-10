package frc.Subsystems.Cargo;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotGlobal;

public class CargoHold extends Subsystem {

    @SuppressWarnings("unused")
    private final SpeedController Controller_CargoHold = new SpeedControllerGroup(new Talon(RobotGlobal.CARGO_TUSK));

    //TODO uncomplete: cargohold

    @Override
    protected void initDefaultCommand() {
            
    }

}