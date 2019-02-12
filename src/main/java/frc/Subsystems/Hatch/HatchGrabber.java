package frc.Subsystems.Hatch;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotGlobal;

public class HatchGrabber extends Subsystem {

    private int hatchGrabberState;

    private boolean hatchActuatorState;

    private SpeedController Controller_HatchGrabber = new SpeedControllerGroup(new WPI_VictorSPX(RobotGlobal.HATCH_GRABBER));
    @SuppressWarnings("unused")
    private SpeedController Controller_HatchActuator = new SpeedControllerGroup(new WPI_VictorSPX(RobotGlobal.HATCH_ACTUATOR));


    public HatchGrabber() {
        hatchGrabberState = 0;
        hatchActuatorState = false;

    }

    @Override
    protected void initDefaultCommand() {

    }

    public void setHatchGrabber(double speed, int direction) {
            Controller_HatchGrabber.set(speed * (double)direction);
            hatchGrabberState = direction;
        }

    public void stopHatchGrabber() {
            Controller_HatchGrabber.stopMotor();
            hatchGrabberState = 0;
    }

    public void setHatchActuator(double speed, int direction) {
        Controller_HatchActuator.set(speed * (double)direction);
            hatchActuatorState = true;
    }
    
    public void stopHatchActuator() {
        Controller_HatchActuator.stopMotor();
        hatchActuatorState = false;
    }

    public int getHatcherGrabberState() {
        return hatchGrabberState;
    }

    public boolean getHatchActuatorState() {
        return hatchActuatorState;
    }

}