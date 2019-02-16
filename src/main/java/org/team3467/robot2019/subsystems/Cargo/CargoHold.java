package org.team3467.robot2019.subsystems.Cargo;

import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoHold extends Subsystem {

    @SuppressWarnings("unused")
    private Victor m_CargoHold = new Victor(RobotGlobal.CARGO_HOLD);

    //TODO uncomplete: cargohold

    @Override
    protected void initDefaultCommand() {
            
    }

}