package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class OI {
    
    public static XboxController driverController;
    public static XboxController operatorController;

    public OI() {
        init();
    }

    public void init() {
        driverController = new XboxController(0);
        driverController = new XboxController(1);

    }


    public void bindControllerCommands() {

        //TODO add command bindings


    }

}