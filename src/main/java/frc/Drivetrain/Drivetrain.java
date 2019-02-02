package frc.Drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.Drivetrain.commands.CPrimaryDrive;
import frc.robot.RobotGlobal;

public class Drivetrain extends Subsystem {

  // harvest grain and nut

  // TODO change these motor channels

  private final WPI_TalonSRX left_talon, right_talon;
  private final WPI_VictorSPX left_victor_1, left_victor_2, right_victor_1, right_victor_2;

  private final DifferentialDrive d_drive;

  public Drivetrain() {
        left_victor_1 = new WPI_VictorSPX(RobotGlobal.DRIVEBASE_VICTOR_1);
        left_victor_2 = new WPI_VictorSPX(RobotGlobal.DRIVEBASE_VICTOR_2);
        left_talon = new WPI_TalonSRX(RobotGlobal.DRIVEBASE_TALON_3);
        right_victor_1 = new WPI_VictorSPX(RobotGlobal.DRIVEBASE_VICTOR_4);
        right_victor_2 = new WPI_VictorSPX(RobotGlobal.DRIVEBASE_VICTOR_5);
        right_talon = new WPI_TalonSRX(RobotGlobal.DRIVEBASE_TALON_6);

        left_victor_1.follow(left_talon);
        left_victor_2.follow(left_talon);
        right_victor_1.follow(right_talon);
        right_victor_2.follow(right_talon);

        d_drive = new DifferentialDrive(left_talon, right_talon);
      }

    


    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new CPrimaryDrive());
    }

    
    public void drive(double left, double right) {
      d_drive.tankDrive(left, right);
    }

    public void driveCurvature(double speed, double rot, boolean quickTurn) {
      d_drive.curvatureDrive(speed, rot, quickTurn);
    }

    public void driveArcade(double speed, double rot) {
      d_drive.arcadeDrive(speed, rot);
      
    }


    public void stop() {
        drive(0, 0);
    }


        

}