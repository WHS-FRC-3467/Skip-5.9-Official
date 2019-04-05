package org.team3467.robot2019.robot;

import org.team3467.robot2019.robot.Control.ButtonBox;
import org.team3467.robot2019.robot.Control.ButtonBoxButton;
import org.team3467.robot2019.robot.Control.ComboBoxButton;
import org.team3467.robot2019.robot.Control.LoneBoxButton;
import org.team3467.robot2019.robot.Control.XBoxControllerDPad;
import org.team3467.robot2019.robot.Control.XboxController;
import org.team3467.robot2019.robot.Control.XboxControllerButton;
import org.team3467.robot2019.subsystems.AutoSequence.HighCargoLift;
import org.team3467.robot2019.subsystems.AutoSequence.LowCargoLift;
import org.team3467.robot2019.subsystems.AutoSequence.PrepareToIntakeCargo;
import org.team3467.robot2019.subsystems.AutoSequence.QueueForClimb;
import org.team3467.robot2019.subsystems.AutoSequence.QuickCargoLift;
import org.team3467.robot2019.subsystems.AutoSequence.StowCargo;
import org.team3467.robot2019.subsystems.CargoHold.DriveCargoHoldRollers;
import org.team3467.robot2019.subsystems.CargoHold.HoldCargo;
import org.team3467.robot2019.subsystems.CargoHold.IntakeCargo;
import org.team3467.robot2019.subsystems.CargoHold.ReleaseCargo;
import org.team3467.robot2019.subsystems.CargoIntake.CargoIntake;
import org.team3467.robot2019.subsystems.CargoIntake.DriveCargoIntakeArm;
import org.team3467.robot2019.subsystems.CargoIntake.DriveCargoIntakeRoller;
import org.team3467.robot2019.subsystems.CargoIntake.MoveCargoIntakeArm;
import org.team3467.robot2019.subsystems.CargoIntake.StartIntakeManual;
import org.team3467.robot2019.subsystems.CargoLift.DriveFourBarLift;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift;
import org.team3467.robot2019.subsystems.CargoLift.MoveCargoLift;
import org.team3467.robot2019.subsystems.Climber.Climber;
import org.team3467.robot2019.subsystems.Climber.DriveClimber;
import org.team3467.robot2019.subsystems.Climber.KeepIn;
import org.team3467.robot2019.subsystems.Climber.PIDClimber;
import org.team3467.robot2019.subsystems.Drivetrain.AutoLineup;
import org.team3467.robot2019.subsystems.Drivetrain.DriveBot;
import org.team3467.robot2019.subsystems.Hatch.DeployGrabber;
import org.team3467.robot2019.subsystems.Hatch.DriveHatchDeployment;
import org.team3467.robot2019.subsystems.Hatch.GrabHatch;
import org.team3467.robot2019.subsystems.Hatch.ReleaseHatch;
import org.team3467.robot2019.subsystems.Hatch.StowGrabber;
import org.team3467.robot2019.subsystems.Hatch.ZeroHatchEncoder;
import org.team3467.robot2019.subsystems.Limelight.Limelight;
import org.team3467.robot2019.subsystems.Limelight.Limelight.CameraMode;
import org.team3467.robot2019.subsystems.Limelight.Limelight.LightMode;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

    private static XboxController driverController;
    private static XboxController operatorController;
    //private static XboxController autoseqController;
	private static ButtonBox buttonBox;

    //private ShuffleboardTab tab_sandstorm = Shuffleboard.getTab("Sandstorm Period");
    //private ShuffleboardTab tab_teleop = Shuffleboard.getTab("Teleop Period");

    //private NetworkTableEntry shuffle_led = tab_teleop.add("Toggle LED", false).getEntry();
    //private NetworkTableEntry shuffle_camMode = tab_teleop.add("Toggle Vision", false).getEntry();
    //private NetworkTableEntry shuffle_stream = tab_teleop.add("Camera Stream Mode", false).getEntry();

    public OI() {
        init();
    }

    public void init() {
        driverController = new XboxController(0);
        operatorController = new XboxController(1);
        //autoseqController = new XboxController(3);

        buttonBox = new ButtonBox(2);
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

        // The "B" button releases the hatch cover when pressed, then resets the hatch grabber when released
        new XboxControllerButton(driverController, XboxController.Button.kB).whenPressed(new ReleaseHatch());
        new XboxControllerButton(driverController, XboxController.Button.kB).whenReleased(new GrabHatch());
        
        // *** These are on Button Box now
        // The "LeftBumper" button retracts the hatch mechanism
        //new XboxControllerButton(driverController, XboxController.Button.kBumperLeft).whenPressed(new StowGrabber());
		// The "RightBumper" button deploys the hatch mechanism
        //new XboxControllerButton(driverController, XboxController.Button.kBumperRight).whenPressed(new DeployGrabber());

        // Do Auto Lineup and move toward hatch or cargo markings using LimeLight tracking
        new XBoxControllerDPad(driverController, XboxController.DPad.kDPadLeft).whileActive(new AutoLineup());

		/*
		 * 
		 * Operator Controller
		 * 
		 */
        
        //
        // Manual manipulation of Cargo Lift, Cargo Intake Arm, Pole Jack, and Hatch Grabber Arm
        //
        // Press and hold the "Start" button to drive the Cargo Lift manually using the Left (down) and Right (up) Triggers
        new XboxControllerButton(operatorController, XboxController.Button.kStart).whileHeld(new DriveFourBarLift());

        // Press and hold the "A" button to drive the Intake Arm angle with the Right Stick X-Axis
        new XboxControllerButton(operatorController, XboxController.Button.kA).whileHeld(new DriveCargoIntakeArm());

        // Press and hold the "Y" button to drive the Pole Jack with the Left Stick Y-Axis
        new XboxControllerButton(operatorController, XboxController.Button.kY).whileHeld(new DriveClimber());

        // Press and hold the "B" button to drive the Hatch Grabber with the Left Stick X-Axis
        new XboxControllerButton(operatorController, XboxController.Button.kB).whileHeld(new DriveHatchDeployment());

        // ******************************************************************************//
        // *****  All of the following are now controllable on the Button Box  **********//
        // ******************************************************************************//
        //
        // Cargo Lift Setpoints
        //
        // Go directly to Cargo setpoints using Left Bumper and DPad
        // new XboxControllerButton(operatorController, XboxController.Button.kBumperLeft)
        //    .whenPressed(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.INTAKE));
        // new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadDown)
        //    .whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.CARGO_SHIP));
        // new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadLeft)
        //    .whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L1));
        // new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadRight)
        //    .whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L2));
        // new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadUp)
        //    .whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L3));
        
        //
        // Cargo Hold
        //
        // Pressing down the Left Stick will turn the Cargo Hold Roller ON
        // PRESS "Back" Button to turn it OFF!
        //
        //new XboxControllerButton(operatorController, XboxController.Button.kStickLeft).whenPressed(new DriveCargoHoldRollers());
                
        //
        // Cargo Intake Arm
        //
        // The "X" button will Stow the Arm
        //new XboxControllerButton(operatorController, XboxController.Button.kX)
        //    .whenPressed(new MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition.RETRACTED));

        // The "Y" button will move the Arm to Collect position
        //new XboxControllerButton(operatorController, XboxController.Button.kY)
        //    .whenPressed(new MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition.INTAKE));

		// The "B" button will move the Arm to Lift and Crawl position
        //new XboxControllerButton(operatorController, XboxController.Button.kB)
        //    .whenPressed(new MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition.RETRACTED));


        //
        // Cargo Intake Roller
        //
        // Pressing down the Right Stick will turn Cargo Intake Roller control on
        // PRESS "A" Button to turn it OFF!
        //
        // new XboxControllerButton(operatorController, XboxController.Button.kStickRight).whenPressed(new DriveCargoIntakeRoller());


		
		/*
		 * 
		 * Button Box
		 * 
		 */

        //  Button 1 = PrepareToIntakeCargo
        //  Function: Set up arms and intake cargo from floor
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kCollectCargo).whenPressed(new PrepareToIntakeCargo());

        //  Button 2 = StowCargo (NOTE: This button is ONLY used in ComboButton commands)
        //  Function: Repositions all cargo appendages into the robot
        //      Target location of arms depends on other button used in Combo 
        new ComboBoxButton(buttonBox, ButtonBox.Button.kStowCargo, ButtonBox.Button.kReverseIntakeRoller).whenActive(new StowCargo());
        new ComboBoxButton(buttonBox, ButtonBox.Button.kStowCargo, ButtonBox.Button.kLiftCargo1).whenActive(new LowCargoLift(FourBarLift.eFourBarLiftPosition.L1));
        new ComboBoxButton(buttonBox, ButtonBox.Button.kStowCargo, ButtonBox.Button.kLiftCargo2).whenActive(new HighCargoLift(FourBarLift.eFourBarLiftPosition.L2));
        new ComboBoxButton(buttonBox, ButtonBox.Button.kStowCargo, ButtonBox.Button.kLiftCargo3).whenActive(new HighCargoLift(FourBarLift.eFourBarLiftPosition.L3));
        new ComboBoxButton(buttonBox, ButtonBox.Button.kStowCargo, ButtonBox.Button.kLiftCargoShip).whenActive(new HighCargoLift(FourBarLift.eFourBarLiftPosition.CARGO_SHIP));

        //  Button 3 = ReleaseCargo
        //  Function: Cargo Hold motor reverses to release ball
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kSpitCargo).whileHeld(new ReleaseCargo());
        
        //  Button 4 = DeployHatch
        //  Function: Lowers Hatch Grabber to Feeding Station height
      //  new ButtonBoxButton(buttonBox, ButtonBox.Button.kDeployHatch).whenPressed(new DeployGrabber());
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kDeployHatch).whileHeld(new StartIntakeManual());

        //  Button 5 = StowHatch
        //  Function: Raises Hatch Grabber to upright/stowed position
      //  new ButtonBoxButton(buttonBox, ButtonBox.Button.kStowHatch).whenPressed(new StowGrabber());
        
        //  Button 6 = ReleaseHatch
        //  Function: Release Hatch from grabber when pressed, reset to grab when button is released
      //  new ButtonBoxButton(buttonBox, ButtonBox.Button.kReleaseHatch).whenPressed(new ReleaseHatch());
      //  new ButtonBoxButton(buttonBox, ButtonBox.Button.kReleaseHatch).whenReleased(new GrabHatch());
        
        //  Button 7 = LiftCargo1
        //  Function: Raises Cargo Hold to Rocket Level 1
        new LoneBoxButton(buttonBox, ButtonBox.Button.kLiftCargo1, ButtonBox.Button.kStowCargo).whenActive(new QuickCargoLift(FourBarLift.eFourBarLiftPosition.L1));

        //  Button 8 = LiftCargo2
        //  Function: Raises Cargo Hold to Rocket Level 2
        new LoneBoxButton(buttonBox, ButtonBox.Button.kLiftCargo2, ButtonBox.Button.kStowCargo).whenActive( new QuickCargoLift(FourBarLift.eFourBarLiftPosition.L2));

        //  Button 9 = LiftCargo3
        //  Function: Raises Cargo Hold to Rocket Level 3
        new LoneBoxButton(buttonBox, ButtonBox.Button.kLiftCargo3, ButtonBox.Button.kStowCargo).whenActive( new HighCargoLift(FourBarLift.eFourBarLiftPosition.L3));

        //  Button 10 = LiftCargoShip
        //  Function: Raises Cargo Hold to Cargo Ship
        new LoneBoxButton(buttonBox, ButtonBox.Button.kLiftCargoShip, ButtonBox.Button.kStowCargo).whenActive( new QuickCargoLift(FourBarLift.eFourBarLiftPosition.CARGO_SHIP));

        //  Button 11 = LiftHatch1
        //  Function: Raises Hatch Handler to Rocket level 1 / Feeder Station / Cargo Ship
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kLiftHatch1).whenPressed(new LowCargoLift(FourBarLift.eFourBarLiftPosition.HATCH_1));
        
        //  Button 12 = LiftHatch2
        //  Function: Raises Hatch Handler to Rocket level 2
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kLiftHatch2).whenPressed(new LowCargoLift(FourBarLift.eFourBarLiftPosition.HATCH_2));
        
        //  Button 13 = LiftHatch3
        //  Function: Raises Hatch Handler to Rocket level 3
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kLiftHatch3).whenPressed(new LowCargoLift(FourBarLift.eFourBarLiftPosition.HATCH_3));
        
        //  Button 14 = Reverse Intake Roller
        //  Function: Runs Cargo Intake in reverse to eject unwanted Cargo
        new LoneBoxButton(buttonBox, ButtonBox.Button.kReverseIntakeRoller, ButtonBox.Button.kStowCargo).whileActive(new DriveCargoIntakeRoller(-1.0));
        
        //  Button 15 = QueueClimber
        //  Function: Moves Cargo Intake to vertical: ready to climb
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kQueueClimber).whenPressed(new QueueForClimb());
        
        //  Button 16 = ClimbHab2
        //  Function: Raises PoleJack to Hab 2 Level
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kClimbHab2).whenPressed(new PIDClimber(Climber.HAB2_CLIMB_COUNT));
        
        //  Button 17 = ClimbHab3
        //  Function: Raises PoleJack to Hab 3 Level
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kClimbHab3).whenPressed(new PIDClimber(Climber.HAB3_CLIMB_COUNT));
        
        //  Button 18 = ClimbRetract
        //  Function: Retract Polejack
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kClimbRetract).whenPressed(new KeepIn(0.9));
        
        //  Button 19 = ClimbCrawl
        //  Function: Lower Cargo Intake to CRAWL position and slowly pull onto Hab level
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kClimbCrawl).whenPressed(new MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition.CRAWL));
        
        //  Button 20 = AutoLineUp
        //  Function: Line up with reflective tape on level 1 at Cargo Ship and Rocket
        //  Assumes: Robot is within range for LimeLight to obtain and track target
        //  Turn on LEDs and switch to Vision mode while tracking; turn off LEDs and return to Driver mode when done 
        new ButtonBoxButton(buttonBox, ButtonBox.Button.kAutoLineUp).whenPressed(new IntakeCargo());


        //
        // Commands to drag onto the ShuffleBoard
        //
        // Most of these are also accesible on the Operator Contoller
        // (initiated by pressing a specific button)
        SmartDashboard.putData(new DriveFourBarLift());
        SmartDashboard.putData(new DriveCargoIntakeArm());
        SmartDashboard.putData(new DriveClimber());
        SmartDashboard.putData(new DriveHatchDeployment());
        SmartDashboard.putData(new ZeroHatchEncoder());

        //SmartDashboard.putData(new IntakeCargo());
        //SmartDashboard.putData(new ReleaseCargo());
        //SmartDashboard.putData(new StopCargoHold());
        //SmartDashboard.putData(new UpdateIntakeStats());
        //SmartDashboard.putData(new MoveCargoLift());
        //SmartDashboard.putData(new UpdateLiftStats());
        //SmartDashboard.putData(new UpdateHatchStats());
        
    }

    //
    // Easier access to XBox controllers
    //
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

    public static double getOperatorLeftX() {
        return operatorController.getX(Hand.kLeft);
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

    public static void setDriverRumble(boolean rumbleOn) {
        driverController.setRumble(GenericHID.RumbleType.kLeftRumble, rumbleOn ? 1 : 0);
        driverController.setRumble(GenericHID.RumbleType.kRightRumble, rumbleOn ? 1 : 0);
    }
    public static void setOperatorRumble(boolean rumbleOn) {
        operatorController.setRumble(GenericHID.RumbleType.kLeftRumble, rumbleOn ? 1 : 0);
        operatorController.setRumble(GenericHID.RumbleType.kRightRumble, rumbleOn ? 1 : 0);
    }

    public static void setOperatorRumble(double rumbleValue) {
        operatorController.setRumble(GenericHID.RumbleType.kLeftRumble, rumbleValue);
        operatorController.setRumble(GenericHID.RumbleType.kRightRumble, rumbleValue);
    }

    public static ButtonBox getButtonBox() {
        return buttonBox;
    }

    public void shuffleboardUpdate() {
    
        //boolean ledState = shuffle_led.getBoolean(false);
        boolean ledState = false;
        if(ledState) {
            Limelight.setLedMode(LightMode.eOn);
        } else {
            Limelight.setLedMode(LightMode.eOff);
        }

        //boolean camMode = shuffle_camMode.getBoolean(false);
        boolean camMode = false;
        if (camMode) {
            Limelight.setCameraMode(CameraMode.eVision);
        } else {
            Limelight.setCameraMode(CameraMode.eDriver);            
        }

        //double streamConfig = shuffle_stream.getNumber(0);
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("stream").setNumber(0);


        /*
        NetworkTableInstance.getDefault().getTable("limelight").getEntry("<variablename>").setNumber(<value>);
        
        ledMode	Sets limelight’s LED state
            0	use the LED Mode set in the current pipeline
            1	force off
            2	force blink
            3	force on
        camMode	Sets limelight’s operation mode
            0	Vision processor
            1	Driver Camera (Increases exposure, disables vision processing)
        pipeline	Sets limelight’s current pipeline
            0 .. 9	Select pipeline 0..9
        stream	Sets limelight’s streaming mode
            0	Standard - Side-by-side streams if a webcam is attached to Limelight
            1	PiP Main - The secondary camera stream is placed in the lower-right corner of the primary camera stream
            2	PiP Secondary - The primary camera stream is placed in the lower-right corner of the secondary cam

        */
    }





}