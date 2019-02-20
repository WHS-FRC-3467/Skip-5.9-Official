
package org.team3467.robot2019.robot;

import org.team3467.robot2019.subsystems.Cargo.CargoHold;
import org.team3467.robot2019.subsystems.Cargo.CargoIntake;
import org.team3467.robot2019.subsystems.Drivetrain.Drivetrain;
import org.team3467.robot2019.subsystems.Hatch.HatchGrabber;
import org.team3467.robot2019.subsystems.Lift.FourBarLift;
import org.team3467.robot2019.subsystems.Limelight.Limelight;
import org.team3467.robot2019.subsystems.Gyro.Gyro;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  public static OI robot_oi;

      public static Drivetrain sub_drivetrain;
      public static CargoHold sub_cargohold;
      public static CargoIntake sub_cargointake;
      //public static CargoLift sub_cargolift;
      public static HatchGrabber sub_hatchgrabber;
      //public static Gyro sub_gyro;
      public static FourBarLift sub_fourbarlift;

      
      public static PowerDistributionPanel robot_pdp;

    

  @Override
  public void robotInit() {
  
    robot_pdp = new PowerDistributionPanel();

    sub_drivetrain = new Drivetrain();
    sub_cargohold = new CargoHold();
    sub_cargointake = new CargoIntake();
    sub_hatchgrabber = new HatchGrabber();
    sub_fourbarlift = new FourBarLift();
    robot_oi = new OI();


  }

  @Override
  public void robotPeriodic() {
    robot_oi.shuffleboardUpdate();

  }

  @Override
  public void autonomousInit() {
    Shuffleboard.selectTab("Sandstorm Period");
    robot_oi.log();

  }
@Override
public void teleopInit() {
  Shuffleboard.selectTab("Teleop Period");


}

  @Override
  public void autonomousPeriodic() {
        Scheduler.getInstance().run();

  }


  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    robot_oi.log();
    //SmartDashboard.putNumber("CargoHold Current Draw", Robot.robot_pdp.getCurrent(RobotGlobal.PDP_CARGO_HOLD_CHAN));


  }


  @Override
  public void testPeriodic() {
  }

  
}
