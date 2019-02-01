package frc.Mechanisms.Cargo;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotGlobal;

public class CargoController extends Subsystem {

    private final SpeedController Controller_CargoIntakeRollers = new SpeedControllerGroup(new Talon(RobotGlobal.CARGO_INTAKE_ROLLERS));
    private final SpeedController Controller_CargoTusk = new SpeedControllerGroup(new Talon(RobotGlobal.CARGO_TUSK));

    private boolean ROLLER_STATE;
    private boolean TUSK_STATE;




    public CargoController() {
        ROLLER_STATE  = false;
        TUSK_STATE = false;
    }


    @Override
    protected void initDefaultCommand() {

    }


    public void enableRollers(int direction, double speed) {
        Controller_CargoIntakeRollers.set(speed * (double)direction);
        ROLLER_STATE = true;
    }

    public void enableTusks(int direction, double speed) {
        Controller_CargoTusk.set(speed * (double)direction);
        TUSK_STATE = true;

    }

    public void disableRollers() {
        Controller_CargoIntakeRollers.set(0.0);
        ROLLER_STATE = false;

    }

    public void disableTusk() {
        Controller_CargoTusk.set(0.0);
        TUSK_STATE = false;

    }


    public void disableAll() {
        disableRollers();
        disableTusk();
    }

    public boolean getRollerState() {
        return ROLLER_STATE;
    }
    public boolean getTuskState() {
        return TUSK_STATE;
    }

}