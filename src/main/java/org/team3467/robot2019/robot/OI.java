package org.team3467.robot2019.robot;

import org.team3467.robot2019.robot.Xbox.XboxController;
import org.team3467.robot2019.robot.Xbox.XboxControllerButton;
import org.team3467.robot2019.subsystems.Cargo.IntakeCargo;
import org.team3467.robot2019.subsystems.Cargo.ReleaseCargo;
import org.team3467.robot2019.subsystems.Drivetrain.ToggleQuickTurns;
import org.team3467.robot2019.subsystems.Hatch.GrabHatch;
import org.team3467.robot2019.subsystems.Hatch.ReleaseHatch;

import edu.wpi.first.wpilibj.GenericHID.Hand;

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
        //new XboxControllerButton(driverController, XboxController.Button.kA).whenPressed(new GrabHatch());
        //new XboxControllerButton(driverController, XboxController.Button.kB).whenPressed(new ReleaseHatch());
        new XboxControllerButton(operatorController, XboxController.Button.kY).whileHeld(new IntakeCargo());
        new XboxControllerButton(operatorController, XboxController.Button.kX).whileHeld(new ReleaseCargo(1.0));
        new XboxControllerButton(driverController, XboxController.Button.kX).whenPressed(new ToggleQuickTurns());

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