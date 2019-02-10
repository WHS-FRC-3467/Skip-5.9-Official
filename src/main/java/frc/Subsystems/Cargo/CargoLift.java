package frc.Subsystems.Cargo;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotGlobal;

public class CargoLift extends Subsystem {

    @SuppressWarnings("unused")

    //TODO this isnt right...
    private final SpeedController Controller_CargoLift = new SpeedControllerGroup(new Spark(RobotGlobal.CARGO_LIFT_L), new Spark(RobotGlobal.CARGO_LIFT_R));

    @Override
    protected void initDefaultCommand() {

    }

    //TODO uncomplete: cargolift
    

}