package frc.Mechanisms.Cargo;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotGlobal;

public class CargoController extends Subsystem {

    private final SpeedController Controller_CargoIntakeRollers = new SpeedControllerGroup(new Talon(RobotGlobal.CARGO_INTAKE_ROLLERS));
    private final SpeedController Controller_CargoTusk = new SpeedControllerGroup(new Talon(RobotGlobal.CARGO_TUSK));
    private final SpeedController Controller_CargoLift = new SpeedControllerGroup(new Spark(RobotGlobal.CARGO_LIFT_L), new Spark(RobotGlobal.CARGO_LIFT_R));

    private boolean roller_state;
    private boolean tusk_state;
    private boolean lift_state;




    public CargoController() {
        roller_state  = false;
        tusk_state = false;
        lift_state = false;

    }


    @Override
    protected void initDefaultCommand() {

    }


    public void enableRollers(int direction, double speed) {
        Controller_CargoIntakeRollers.set(speed * (double)direction);
        roller_state = true;
    }

    public void enableTusks(int direction, double speed) {
        Controller_CargoTusk.set(speed * (double) direction);
        tusk_state = true;

    }

    public void enableLift(int direction, double speed) {
        Controller_CargoLift.set(speed * (double)direction);
        lift_state = true;
    }

    public void disableRollers() {
        Controller_CargoIntakeRollers.stopMotor();
        roller_state = false;

    }

    public void disableTusk() {
        Controller_CargoTusk.stopMotor();
        tusk_state = false;

    }

    public void disableLift() {
        Controller_CargoLift.stopMotor();
        lift_state = true;
    }

    public void disableAll() {
        disableRollers();
        disableTusk();
    }

    public boolean getRollerState() {
        return roller_state;
    }

    public boolean getTuskState() {
        return tusk_state;
    }

    public boolean getLiftState() {
        return lift_state;
    }

}