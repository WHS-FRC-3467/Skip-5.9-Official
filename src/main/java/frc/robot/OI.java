package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.Subsystems.Hatch.GrabHatch;
import frc.Subsystems.Hatch.ReleaseHatch;
import frc.robot.Xbox.XboxController;
import frc.robot.Xbox.XboxControllerButton;

public class OI {
    

    private static XboxController driverController;
    private static XboxController operatorController;

    public OI() {
        init();
    }

    public void init() {
        driverController = new XboxController(0);
        operatorController = new XboxController(1);
            bindControllerCommands();
    }


    public void bindControllerCommands() {
        new XboxControllerButton(driverController, XboxController.Button.kA).whenPressed(new GrabHatch());
        new XboxControllerButton(driverController, XboxController.Button.kB).whenPressed(new ReleaseHatch());

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

    public static double getDriverLeftTrigger() {
        return driverController.getTriggerAxis(Hand.kRight);
    }
    public static double getDriverRightTrigger() {
        return driverController.getTriggerAxis(Hand.kLeft);
    }








}