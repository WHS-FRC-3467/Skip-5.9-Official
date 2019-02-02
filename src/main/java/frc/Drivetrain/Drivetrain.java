package frc.Drivetrain;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.Drivetrain.commands.CPrimaryDrive;
import frc.robot.RobotGlobal;

public class Drivetrain extends Subsystem {

  // harvest grain and nut 
  

  //TODO change these motor channels

      private final SpeedController LEFT_MOTOR_SPEED_GROUP = new SpeedControllerGroup(new Victor(RobotGlobal.DRIVEBASE_VICTOR_1) , new Victor(RobotGlobal.DRIVEBASE_VICTOR_2), new Talon(RobotGlobal.DRIVEBASE_TALON_3));
      private final SpeedController RIGHT_MOTOR_SPEED_GROUP = new SpeedControllerGroup(new Victor(RobotGlobal.DRIVEBASE_VICTOR_4) , new Victor(RobotGlobal.DRIVEBASE_VICTOR_5), new Talon(RobotGlobal.DRIVEBASE_TALON_6));

      private final DifferentialDrive D_DRIVE = new DifferentialDrive(LEFT_MOTOR_SPEED_GROUP, RIGHT_MOTOR_SPEED_GROUP);



    


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new CPrimaryDrive());
    }


    public Drivetrain() {
      super();
    }
    
    public void drive(double left, double right) {
      D_DRIVE.tankDrive(left, right);
    }

    public void driveCurvature(double speed, double rot, boolean quickTurn) {
      D_DRIVE.curvatureDrive(speed, rot, quickTurn);
    }

    public void driveArcade(double speed, double rot) {
      D_DRIVE.arcadeDrive(speed, rot);
    }


    public void stop() {
        drive(0, 0);
    }


        

}