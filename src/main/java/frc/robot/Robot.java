
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.Drivetrain.Drivetrain;
import frc.Mechanisms.Cargo.CargoController;


public class Robot extends TimedRobot {


      public static Drivetrain sub_drivetrain;
      public static OI robot_oi;
      public static CargoController sub_cargocontroller;




  @Override
  public void robotInit() {
  
    sub_drivetrain = new Drivetrain();
    sub_cargocontroller = new CargoController();
    robot_oi = new OI();

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {    
  }

  @Override
  public void autonomousPeriodic() {
  }


  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }


  @Override
  public void testPeriodic() {
  }
}
