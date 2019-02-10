package frc.Subsystems.Hatch;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotGlobal;

public class HatchGrabber extends Subsystem {

    private boolean hatch_state;

    private SpeedController Controller_HatchGrabber = new SpeedControllerGroup(new Victor(RobotGlobal.HATCH_GRABBER));
    @SuppressWarnings("unused")
    private SpeedController Controller_HatchActuator = new SpeedControllerGroup(new Victor(RobotGlobal.HATCH_ACTUATOR));


    public HatchGrabber() {
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