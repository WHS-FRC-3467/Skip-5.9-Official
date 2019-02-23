package org.team3467.robot2019.robot;

import org.team3467.robot2019.robot.Xbox.XBoxControllerDPad;
import org.team3467.robot2019.robot.Xbox.XboxController;
import org.team3467.robot2019.robot.Xbox.XboxControllerButton;
import org.team3467.robot2019.subsystems.Drivetrain.DriveBot;
import org.team3467.robot2019.subsystems.Limelight.Limelight;
import org.team3467.robot2019.subsystems.Limelight.Limelight.LightMode;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

    private static XboxController driverController;
    private static XboxController operatorController;

    private ShuffleboardTab tab_sandstorm = Shuffleboard.getTab("Sandstorm Period");
    private ShuffleboardTab tab_teleop = Shuffleboard.getTab("Teleop Period");

    private NetworkTableEntry shuffle_led = tab_teleop.add("Toggle LED", false).getEntry();

    public OI() {
        init();
    }

    public void init() {
        driverController = new XboxController(0);
        operatorController = new XboxController(1);
        bindControllerCommands();
    }

    @SuppressWarnings("resource")
    public void bindControllerCommands() {


		/*
		 *
		 * Drive Controller
		 * 
		 */
		// Bind any Drive Controller buttons here
		
		// DPad will change drive control mode
		new XBoxControllerDPad(driverController, XboxController.DPad.kDPadUp).whenActive(new DriveBot(DriveBot.driveMode_Tank, false));
		new XBoxControllerDPad(driverController, XboxController.DPad.kDPadDown).whenActive(new DriveBot(DriveBot.driveMode_Rocket, false));
		
		// The "A" Button will shift any drive mode to Precision mode
		new XboxControllerButton(driverController, XboxController.Button.kA).whenActive(new DriveBot(DriveBot.driveMode_Rocket, true));

		// The "X" button activates "turn in place" while held down
		new XboxControllerButton(driverController, XboxController.Button.kX).whileActive(new DriveBot(DriveBot.driveMode_RocketSpin, false));


		/*
		 * 
		 * Operator Controller
		 * 
		 */
		// Bind any Operator Controller buttons here
		
       //new XboxControllerButton(operatorController, XboxController.Button.kStickLeft).whenPressed(new CargoIntakeManual());
       //new XboxControllerButton(operatorController, XboxController.Button.kX).whileHeld(new CargoIntakeManual());

       //new XboxControllerButton(operatorController, XboxController.Button.kA).whenPressed(new GrabHatch());
        //new XboxControllerButton(operatorController, XboxController.Button.kB).whenPressed(new ReleaseHatch());
       // new XboxControllerButton(operatorController, XboxController.Button.kY).whileHeld(new IntakeCargo());
        //new XboxControllerButton(operatorController, XboxController.Button.kX).whileHeld(new ReleaseCargo(1.0));
        //new XboxControllerButton(operatorController, XboxController.Button.kX).whenPressed(new ToggleQuickTurns());
        //new XboxControllerButton(operatorController, XboxController.Button.kX).whileHeld(new Elevate());
       // new XboxControllerButton(operatorController, XboxController.Button.kX).whenPressed(new MoveCargoIntake());

    }

        //Easier access to xbox controller

    public static XboxController getDriverController() {
        return driverController;
    }
    public static XboxController getOperatorController() {
        return  operatorController;
    }

    public static double getDriverLeftX() {
        return driverController.getX(Hand.kLeft);
    }

    public static double getDriverLeftY() {
        return driverController.getY(Hand.kLeft);
    }

    public static double getDriverRightY() {
        return driverController.getY(Hand.kRight);
    }

    public static double getDriverLeftTrigger() {
        return driverController.getTriggerAxis(Hand.kRight);
    }

    public static double getDriverRightTrigger() {
        return driverController.getTriggerAxis(Hand.kLeft);
    }

    public static double getOperatorLeftTrigger() {
        return operatorController.getTriggerAxis(Hand.kRight);
    }

    public static double getOperatorRightTrigger() {
        return operatorController.getTriggerAxis(Hand.kLeft);
    }


    public void log() {

            SmartDashboard.putNumber("FOUR_BAR_LIFT_ENCODER", Robot.sub_fourbarlift.getEncoder());
            SmartDashboard.putNumber("CARGO_INTAKE_ENCODER", Robot.sub_cargointake.getArmEncoderPosition());


            SmartDashboard.putString("CARGO_INTAKE_ARM_ACTIVE_POS", Robot.sub_cargointake.getArmActivePosition().getSetpointName());

            SmartDashboard.putNumber("CARGO_INTAKE_ARM_STANDBY_POS", Robot.sub_cargointake.getArmEncoderPosition());


      }

      public void shuffleboardUpdate() {
        boolean buttonvalue = Robot.robot_oi.shuffle_led.getBoolean(false);


        if(buttonvalue) {
            Limelight.setLedMode(LightMode.eOn);
        } else {
            Limelight.setLedMode(LightMode.eOff);
        }
      }





}