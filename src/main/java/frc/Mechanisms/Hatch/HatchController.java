package frc.Mechanisms.Hatch;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotGlobal;

public class HatchController extends Subsystem {

    private boolean hatch_state;

    private SpeedController Controller_HatchGrabber = new SpeedControllerGroup(new Talon(RobotGlobal.HATCH_HATCH_GRABBER));
    private SpeedController Controller_HatchLateral = new SpeedControllerGroup(new Talon(RobotGlobal.HATCH_LATERAL));
    private SpeedController Controller_HatchActuator = new SpeedControllerGroup(new Talon(RobotGlobal.HATCH_ACTUATOR));


    public HatchController() {
        hatch_state = false;
    }

    @Override
    protected void initDefaultCommand() {

    }

    public void enableHatchGrabber(int direction, double speed) {
            Controller_HatchGrabber.set(speed * (double)direction);
    }

    public void disableHatchGrabber() {
            Controller_HatchGrabber.stopMotor();
    }

    public boolean getHatchGrabberState() {
        return hatch_state;
    }

}