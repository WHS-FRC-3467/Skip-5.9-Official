package org.team3467.robot2019.robot;

import org.team3467.robot2019.robot.Xbox.XBoxControllerDPad;
import org.team3467.robot2019.robot.Xbox.XboxController;
import org.team3467.robot2019.robot.Xbox.XboxControllerButton;
import org.team3467.robot2019.subsystems.CargoHold.DriveCargoHoldRollers;
import org.team3467.robot2019.subsystems.CargoHold.IntakeCargo;
import org.team3467.robot2019.subsystems.CargoHold.ReleaseCargo;
import org.team3467.robot2019.subsystems.CargoHold.StopCargoHold;
import org.team3467.robot2019.subsystems.CargoIntake.CargoIntake;
import org.team3467.robot2019.subsystems.CargoIntake.DriveCargoIntakeArm;
import org.team3467.robot2019.subsystems.CargoIntake.DriveCargoIntakeRoller;
import org.team3467.robot2019.subsystems.CargoIntake.MoveCargoIntakeArm;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift;
import org.team3467.robot2019.subsystems.CargoLift.LiftManually;
import org.team3467.robot2019.subsystems.CargoLift.MoveCargoLift;
import org.team3467.robot2019.subsystems.Drivetrain.DriveBot;
import org.team3467.robot2019.subsystems.Hatch.DeployGrabber;
import org.team3467.robot2019.subsystems.Hatch.DriveHatchDeployment;
import org.team3467.robot2019.subsystems.Hatch.GrabHatch;
import org.team3467.robot2019.subsystems.Hatch.ReleaseHatch;
import org.team3467.robot2019.subsystems.Hatch.StowGrabber;
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
		
		// DPad will change drive control mode
		new XBoxControllerDPad(driverController, XboxController.DPad.kDPadUp).whenActive(new DriveBot(DriveBot.driveMode_Tank, false));
		new XBoxControllerDPad(driverController, XboxController.DPad.kDPadDown).whenActive(new DriveBot(DriveBot.driveMode_Rocket, false));
		
		// The "A" Button will shift any drive mode to Precision mode
		new XboxControllerButton(driverController, XboxController.Button.kA).whenActive(new DriveBot(DriveBot.driveMode_Rocket, true));

		// The "X" button activates "turn in place" while held down
		new XboxControllerButton(driverController, XboxController.Button.kX).whileActive(new DriveBot(DriveBot.driveMode_RocketSpin, false));

		// The "B" button grabs the hatch cover
        new XboxControllerButton(driverController, XboxController.Button.kB).whenPressed(new GrabHatch());
        // The "Y" button releases the hatch cover
        new XboxControllerButton(driverController, XboxController.Button.kY).whenPressed(new ReleaseHatch());
        
        // The "LeftBumper" button retracts the hatch mechanism
        new XboxControllerButton(driverController, XboxController.Button.kBumperLeft).whenPressed(new StowGrabber());
		// The "RightBumper" button deploys the hatch mechanism
        new XboxControllerButton(driverController, XboxController.Button.kBumperRight).whenPressed(new DeployGrabber());

		/*
		 * 
		 * Operator Controller
		 * 
		 */
        // Cargo Hold
        // Pressing down the Left Stick will turn the Cargo Hold Roller ON
        //
        //  PRESS "Back" Button to turn it OFF!
        //
        new XboxControllerButton(operatorController, XboxController.Button.kStickLeft).whenPressed(new DriveCargoHoldRollers());
                
        // Cargo Lift
        new XboxControllerButton(operatorController, XboxController.Button.kStart).whileHeld(new LiftManually());

        new XboxControllerButton(operatorController, XboxController.Button.kBumperLeft).whenPressed(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.INTAKE));
        new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadDown).whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.CARGO_SHIP));
        new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadLeft).whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L1));
        new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadRight).whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L2));
        new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadUp).whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L3));
        
         // Cargo Intake Arm
		// The "X" button will Stow the Arm
        new XboxControllerButton(operatorController, XboxController.Button.kX)
            .whenPressed(new MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition.RETRACTED));

        // The "Y" button will move the Arm to Collect position
        new XboxControllerButton(operatorController, XboxController.Button.kY)
            .whenPressed(new MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition.INTAKE));

		// The "B" button will move the Arm to Lift and Crawl position
        new XboxControllerButton(operatorController, XboxController.Button.kB)
            .whenPressed(new MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition.CRAWL));

        // Cargo Intake Roller
        // Pressing down the Right Stick will turn Cargo Intake Roller control on
        //
        //  PRESS "A" Button to turn it OFF!
        //
         new XboxControllerButton(operatorController, XboxController.Button.kStickRight).whenPressed(new DriveCargoIntakeRoller());


        // Commands to drag onto the ShuffleBoard
         SmartDashboard.putData(new DriveBot(DriveBot.driveMode_Rocket, false));
         SmartDashboard.putData(new IntakeCargo());
         SmartDashboard.putData(new ReleaseCargo());
         SmartDashboard.putData(new StopCargoHold());
         SmartDashboard.putData(new DriveHatchDeployment());
         SmartDashboard.putData(new DriveCargoIntakeArm());


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

    public static double getOperatorLeftY() {
        return operatorController.getY(Hand.kLeft);
    }

    public static double getOperatorRightX() {
        return operatorController.getX(Hand.kRight);
    }

    public static double getOperatorRightY() {
        return operatorController.getY(Hand.kRight);
    }

    public static boolean getOperatorButtonA() {
        return operatorController.getAButtonPressed();
    }

    public static boolean getOperatorButtonBack() {
        return operatorController.getBackButtonPressed();
    }

    public static double getOperatorLeftTrigger() {
        return operatorController.getTriggerAxis(Hand.kRight);
    }

    public static double getOperatorRightTrigger() {
        return operatorController.getTriggerAxis(Hand.kLeft);
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