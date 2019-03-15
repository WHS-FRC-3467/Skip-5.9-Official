package org.team3467.robot2019.robot;

import org.team3467.robot2019.robot.Control.ButtonBox;
import org.team3467.robot2019.robot.Control.ButtonBoxButton;
import org.team3467.robot2019.robot.Control.TestMe;
import org.team3467.robot2019.robot.Control.TestMe2;
import org.team3467.robot2019.robot.Control.XBoxControllerDPad;
import org.team3467.robot2019.robot.Control.XboxController;
import org.team3467.robot2019.robot.Control.XboxControllerButton;
import org.team3467.robot2019.subsystems.AutoSequence.PrepareToIntakeCargo;
import org.team3467.robot2019.subsystems.AutoSequence.StowCargo;
import org.team3467.robot2019.subsystems.CargoHold.DriveCargoHoldRollers;
import org.team3467.robot2019.subsystems.CargoHold.IntakeCargo;
import org.team3467.robot2019.subsystems.CargoHold.ReleaseCargo;
import org.team3467.robot2019.subsystems.CargoHold.StopCargoHold;
import org.team3467.robot2019.subsystems.CargoIntake.CargoIntake;
import org.team3467.robot2019.subsystems.CargoIntake.DriveCargoIntakeArm;
import org.team3467.robot2019.subsystems.CargoIntake.DriveCargoIntakeRoller;
import org.team3467.robot2019.subsystems.CargoIntake.MoveCargoIntakeArm;
import org.team3467.robot2019.subsystems.CargoIntake.UpdateIntakeStats;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift;
import org.team3467.robot2019.subsystems.CargoLift.LiftManually;
import org.team3467.robot2019.subsystems.CargoLift.MoveCargoLift;
import org.team3467.robot2019.subsystems.CargoLift.UpdateLiftStats;
import org.team3467.robot2019.subsystems.Drivetrain.DriveBot;
import org.team3467.robot2019.subsystems.Hatch.DeployGrabber;
import org.team3467.robot2019.subsystems.Hatch.DriveHatchDeployment;
import org.team3467.robot2019.subsystems.Hatch.GrabHatch;
import org.team3467.robot2019.subsystems.Hatch.ReleaseHatch;
import org.team3467.robot2019.subsystems.Hatch.StowGrabber;
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
        operatorController = new XboxController(2);
        buttonBox = new ButtonBox(1);
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
        
         //
        // Cargo Hold
        //
        // Pressing down the Left Stick will turn the Cargo Hold Roller ON
        // PRESS "Back" Button to turn it OFF!
        //
        new XboxControllerButton(operatorController, XboxController.Button.kStickLeft).whenPressed(new DriveCargoHoldRollers());
                
        //
        // Cargo Lift
        //
        // Press and hold the "Start" button to drive the Cargo Lift manually using the Left (down) and Right (up) Triggers
        new XboxControllerButton(operatorController, XboxController.Button.kStart).whileHeld(new LiftManually());

        // Go directly to Cargo setpoints using Left Bumper and DPad
        new XboxControllerButton(operatorController, XboxController.Button.kBumperLeft)
            .whenPressed(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.INTAKE));
        new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadDown)
            .whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.CARGO_SHIP));
        new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadLeft)
            .whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L1));
        new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadRight)
            .whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L2));
        new XBoxControllerDPad(operatorController, XboxController.DPad.kDPadUp)
            .whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L3));
        
        //
        // Cargo Intake Arm
        //
        // The "X" button will Stow the Arm
        new XboxControllerButton(operatorController, XboxController.Button.kX)
            .whenPressed(new MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition.RETRACTED));

        // The "Y" button will move the Arm to Collect position
        new XboxControllerButton(operatorController, XboxController.Button.kY)
            .whenPressed(new MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition.INTAKE));

		// The "B" button will move the Arm to Lift and Crawl position
        new XboxControllerButton(operatorController, XboxController.Button.kB)
            .whenPressed(new MoveCargoIntakeArm(CargoIntake.eCargoIntakeArmPosition.RETRACTED));

        // Press and hold the "A" button to drive the Intake Arm angle with the Right Stick X-Axis
        new XboxControllerButton(operatorController, XboxController.Button.kA).whileHeld(new DriveCargoIntakeArm());


        //
        // Cargo Intake Roller
        //
        // Pressing down the Right Stick will turn Cargo Intake Roller control on
        // PRESS "A" Button to turn it OFF!
        //
         new XboxControllerButton(operatorController, XboxController.Button.kStickRight).whenPressed(new DriveCargoIntakeRoller());


        //
        // BUTTON BOX BUTTONS
        //
        
        //  Button 1 = PrepareToIntakeCargo
            //Function: Intakes cargo from floor
            //Assumes: All appendages are in starting configuaration
            //Step 1: 4 Bar raises up
            //Step 2: Intake arm moves to floor intake setpoint
            //Step 3: 4 Bar moves down to intake setpoint
            //Step 4: Cargo Intake turns on
            //Step 4: Cargo Hold turns on
            //Step 5: Wait for current spike on Cargo Hold then initialize Cargo Hold low speed
            //Step 6; Cargo Intake turns off
            //  END

        //  Button 2 = StowCargo
            //Function: Brings all cargo appendages into the robot
            //Assumes: Robot is in "Collect Cargo" configuration
            //Step 1: 4 Bar raises up
            //Step 2: Cargo Intake returns to Home position
            //Step 3: 4 Bar moves down to Home/Intake setpoint (depending on Cargo held)
            //  END

        //  Button 3 = Spit Cargo
            //Function: Cargo Hold motor reverses to release ball
            //Assumes: We have a piece of Cargo in a scoring position
            //Step 1: Cargo Hold motor spins in reverse direction
        
        //  Button 4 = Collect Hatch
            //Function: Lowers Hatch Grabberto HP height
            //Assumes: Hatch Grabber is in the upright/stowed position
            //Step 1: Hatch Arm lowers to Level 1 height (bottoms out)
            //Step 2: If Limit Switch is pressed then pulse driver gamepad once
        
        //  Button 5 = Release Hatch
            //Function: Fingers that hold the Hatch are moved inward
            //Assumes: We have a Hatch in a scoring position
            //Hatch Finger Servo moves position
        //  Button 6 = Stow Hatch
            //Function: Raises Hatch Grabber to upright/stowed position
            //Assumes: Hatch Grabber is in the HP height
            //Step 1: Raises Hatch Arm to stowed/starting position
        //  Button 7 = Lift to Cargo 1
            //Function: Raises Cargo Hold to rocket level 1
            //Assumes: We have a piece of Cargo in Cargo Hold and are lined up. The Cargo Hold is in the stowed position.
            //Step 1: Lift the Cargo Hold to Rocket 1
            //Step 2: Stow Cargo Intake
            //Step 3: Spit Cargo
            //Step 4: Deploy Cargo Intake to prepare for another Cargo
            //Step 5: Stow Cargo Hold
        //  Button 8 = Lift to Cargo 2
            //Function: Raises Cargo Hold to level 2
            //Assumes: We have a piece of Cargo in Cargo Hold and are lined up. Cargo Hold is in the stowed position.
            //Step 1: Lift the Cargo Hold to Rocket level 2
            //Step 2: Stow Cargo Intake
            //Step 3: Spit Cargo
            //Step 4: Deploy Cargo Intake to prepare for another Cargo
            //Step 5: Stow Cargo Hold
        //  Button 9 = Lift to Cargo 3
            //Function: Raises Cargo Hold to rocket level 3
            //Assumes: We have a piece of Cargo in Cargo Hold and are lined up. Cargo Hold is in the stowed position.
            //Step 1: Lift the Cargo Hold to Rocket level 3
            //Step 2: Stow Cargo Intake
            //Step 3: Spit Cargo
            //Step 4: Deploy Cargo Intake to prepare for another Cargo
            //Step 5: Stow Cargo Hold
        //  Button 10 = Lift to Ship
            //Function: Raises Cargo Hold to Cargo Bay level
            //Assumes: We have a piece of Cargo in Cargo Hold and are lined up. Cargo Hold is in the stowed position.
            //Step 1: Lift the Cargo Hold to Rocket Cargo Bay level
            //Step 2: Stow Cargo Intake
            //Step 3: Spit Cargo
            //Step 4: Deploy Cargo Intake to prepare for another Cargo
            //Step 5: Stow Cargo Hold
        //  Button 11 = Lift to Hatch 1
            //Function: Raises Hatch Handler to Rocket level 1
            //Assumes: We have a Hatch in Hatch Handler and are lined up. The Hatch Handler is in the collect position.
            //Step 1: Lift the Hatch Handler to Rocket level 1
            //Step 2: Release Hatch
            //Step 3: Stow Hatch Handler
        //  Button 12 = Lift to Hatch 2
            //Function: Raises Hatch Handler to Rocket level 2
            //Assumes: We have a Hatch in Hatch Handler and are lined up. The Hatch Handler is in the collect position.
            //Step 1: Lift the Hatch Handler to Rocket level 2
            //Step 2: Release Hatch
            //Step 3: Stow Hatch Handler
        //  Button 13 = Lift to Hatch 3
            //Function: Raises Hatch Handler to Rocket level 3
            //Assumes: We have a Hatch in the Hatch Handler and are lined up. Hatch Handler is in the collect position.
            //Step 1: Lift the Hatch Handler to Rocket level 3
            //Step 2: Release Hatch
            //Step 3: Stow Hatch Handler
        //  Button 14 = Lift to Ship Hatch
            //Function: Raises Hatch Handler to Cargo Bay level
            //Assumes: We have a Hatch in the Hatch Handler and are lined up. Hatch Handler is in the collect position.
            //Step 1: Lift the Hatch Handler to Rocket Cargo Bay level
            //Step 2: Release Hatch
            //Step 3: Stow Hatch Handler
        //  Button 15 = Queue Climber
            //Function: Moves Cargo Intake to vertical: ready to climb
            //Assumes: All Cargo Appendages are in the Stowed position
            //Step 1: 4 Bar moves up
            //Step 2: Cargo Intake moves to setpoint clear of 4 bar
            //Step 3: 4 Bar returns to Home setpoint
            //Step 4: Cargo Intake rotates back into a vertical position
        //  Button 16 = Climb to Hab 2
            //Function: Climbs to Hab 2
            //Assumes: Lined up against the Hab platform prepared to climb
            //Step 1: Cargo Intake comes down onto Hab 2
            //Step 2: Cargo Intake pushes down to lift front of robot
            //Step 3: Cargo Intake crawls (Wheels on Cargo Intake and Drivebase are moving)
            //Step 4: Robot is on top of Hab 2
        //  Button 17 = Climb to Hab 3
            //Function: Climbs to Hab 3
            //Assumes: Lined up against the Hab platform prepared to climb (On level 1 in front of Hab 3)
            //Step 1: Cargo Intake comes down until touches Hab 3
            //Step 2: Continue to move Cargo Intake down while raising Polejack in back of robot
            //Step 3: Start Crawl mode and pull robot onto Hab 3
        //  Button 18 = Climb Retract
            //Function: Retract Polejack and return Cargo Intake
            //Assumes: Drivebase wheels are on Hab 3 and Polejack and Cargo Intake are fully deployed
            //Step 1: Retract both Polejack and Cargo Intake
        //  Button 19 = Climb Crawl
            //Function: Pull Drivebase onto Hab level 2 or 3
            //Assumes: Intake is touching Hab level and linec up
            //Step 1: Turn on Cargo Intake and slowly pull onto Hab level
        //  Button 20 = Auto Line Up
            //Function: Line up with reflective tape on level 1 at Cargo Ship and Rocket
            //Assumes: Robot is within range for LimeLight to obtain and track target
            //Step 1: 

        //  new ButtonBoxButton(buttonBox, ButtonBox.Button.k1).whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L1));
       //  new ButtonBoxButton(buttonBox, ButtonBox.Button.k2).whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L2));
       //  new ButtonBoxButton(buttonBox, ButtonBox.Button.k3).whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.L3));
       //  new ButtonBoxButton(buttonBox, ButtonBox.Button.k4).whenActive(new MoveCargoLift(FourBarLift.eFourBarLiftPosition.CARGO_SHIP));

        // new ButtonBoxButton(buttonBox, ButtonBox.Button.k11).whenPressed(new DriveCargoHoldRollers());

            new ButtonBoxButton(buttonBox, ButtonBox.Button.k1).whenPressed(new TestMe());
            new ButtonBoxButton(buttonBox, ButtonBox.Button.k2).whenPressed(new TestMe2());






        // Commands to drag onto the ShuffleBoard
         SmartDashboard.putData(new DriveBot(DriveBot.driveMode_Rocket, false));
         SmartDashboard.putData(new IntakeCargo());
         SmartDashboard.putData(new ReleaseCargo());
         SmartDashboard.putData(new StopCargoHold());
         SmartDashboard.putData(new DriveHatchDeployment());
         SmartDashboard.putData(new DriveCargoIntakeArm());
         SmartDashboard.putData(new UpdateIntakeStats());
         SmartDashboard.putData(new MoveCargoLift());
         SmartDashboard.putData(new UpdateLiftStats());

         SmartDashboard.putData(new PrepareToIntakeCargo());
         SmartDashboard.putData(new StowCargo());


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