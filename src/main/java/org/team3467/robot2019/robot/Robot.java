
package org.team3467.robot2019.robot;

import org.team3467.robot2019.subsystems.CargoHold.CargoHold;
import org.team3467.robot2019.subsystems.CargoIntake.CargoIntake;
import org.team3467.robot2019.subsystems.CargoLift.FourBarLift;
import org.team3467.robot2019.subsystems.Drivetrain.Drivetrain;
import org.team3467.robot2019.subsystems.Hatch.HatchGrabber;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;


public class Robot extends TimedRobot {

      public static Drivetrain sub_drivetrain;
      public static CargoHold sub_cargohold;
      public static CargoIntake sub_cargointake;
      public static HatchGrabber sub_hatchgrabber;
      public static FourBarLift sub_fourbarlift;
      //public static Gyro sub_gyro;
      //public static LEDSerial sub_led;
      
      public static OI robot_oi;
    

  @Override
  public void robotInit() {
  
    sub_drivetrain = Drivetrain.getInstance();
    sub_cargohold = CargoHold.getInstance();
    sub_cargointake = CargoIntake.getInstance();
    sub_hatchgrabber = HatchGrabber.getInstance();
    sub_fourbarlift = FourBarLift.getInstance();
    //sub_gyro = Gyro.getInstance();
    //sub_led = LEDSerial.getInstance();

    robot_oi = new OI();


  }

  @Override
  public void robotPeriodic() {
   //robot_oi.shuffleboardUpdate();
   
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
  //  robot_oi.log();
  //  SmartDashboard.putNumber("FBLEncoder", Robot.sub_fourbarlift.getEncoder());


  }


  @Override
  public void testPeriodic() {
  }

  
}
