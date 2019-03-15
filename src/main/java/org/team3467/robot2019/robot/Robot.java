package org.team3467.robot2019.robot;

import org.team3467.robot2019.subsystems.CargoHold.CargoHold;
import org.team3467.robot2019.subsystems.CargoIntake.CargoIntake;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift;
import org.team3467.robot2019.subsystems.Drivetrain.Drivetrain;
import org.team3467.robot2019.subsystems.FieldCamera.FieldCamera;
import org.team3467.robot2019.subsystems.Hatch.HatchGrabber;
import org.team3467.robot2019.subsystems.LED.LEDSerial;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

public class Robot extends TimedRobot {

    public static FieldCamera fieldCamera;

    public static Drivetrain sub_drivetrain;
    public static CargoHold sub_cargohold;
    public static CargoIntake sub_cargointake;
    public static HatchGrabber sub_hatchgrabber;
    public static FourBarLift sub_fourbarlift;
    //public static Gyro sub_gyro;
    public static LEDSerial sub_led;

    public static OI robot_oi;

    @Override
    public void robotInit() {

        // Start the FieldCamera
        fieldCamera = new FieldCamera();

        sub_drivetrain = Drivetrain.getInstance();
        sub_cargohold = CargoHold.getInstance();
        sub_cargointake = CargoIntake.getInstance();
        sub_hatchgrabber = HatchGrabber.getInstance();
        sub_fourbarlift = FourBarLift.getInstance();
        //sub_gyro = Gyro.getInstance();
        sub_led = LEDSerial.getInstance();

        robot_oi = new OI();

    }

    @Override
    public void robotPeriodic() {
        robot_oi.shuffleboardUpdate();
    }

    /**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
    
        // TODO: Think about what happens if we remove all currently running commands upon Disablement
        // ?: Will this be detrimental when the shift from Sandstorm -> Disabled -> Teleop happens?
        // This will be useful when testing or practicing and robot is Disabled while a command is running
        
        // Scheduler.getInstance().removeAll();
    }

	@Override
	public void disabledPeriodic() {

        // Keep the Intake Arm and 4Bar Lift positions updated even when Disabled
        // We set the target closed-loop setpoints to the current position when reEnabled
        // so as to minimize any movement of the arms 
        sub_cargointake.updatePosition();
        sub_fourbarlift.updatePosition();

        Scheduler.getInstance().run();
	}


    @Override
    public void autonomousInit() {
        Shuffleboard.selectTab("Sandstorm Period");
        robot_oi.shuffleboardUpdate();
    }

    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {

        // Remove any commands letover from prior runs
        Scheduler.getInstance().removeAll();

        Shuffleboard.selectTab("Teleop Period");
        robot_oi.shuffleboardUpdate();
    }

    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
        //SmartDashboard.putNumber("FBL Encoder2" , sub_fourbarlift.getLiftEncoder());
    }


    @Override
    public void testPeriodic() {
    }

}
