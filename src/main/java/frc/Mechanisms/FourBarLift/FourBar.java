package frc.Mechanisms.FourBarLift;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotGlobal;

public class FourBar extends Subsystem {

    private SpeedController Controller_FourBar = new SpeedControllerGroup(new Talon(RobotGlobal.FOUR_BAR_LIFT));

    @Override
    protected void initDefaultCommand() {

    }

    //TODO Add four bar lift control methods
    

}