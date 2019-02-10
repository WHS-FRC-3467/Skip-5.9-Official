package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.Xbox.XboxController;

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