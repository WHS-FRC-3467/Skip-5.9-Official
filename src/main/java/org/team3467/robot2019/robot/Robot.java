
package org.team3467.robot2019.robot;

import org.team3467.robot2019.subsystems.Hatch.HatchGrabber;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import org.team3467.robot2019.subsystems.Cargo.CargoHold;
import org.team3467.robot2019.subsystems.Cargo.CargoIntake;
import org.team3467.robot2019.subsystems.Cargo.CargoLift;
import org.team3467.robot2019.subsystems.Drivetrain.Drivetrain;
import org.team3467.robot2019.subsystems.Hatch.HatchGrabber;



public class Robot extends TimedRobot {


      public static OI robot_oi;

      public static Drivetrain sub_drivetrain;
      public static CargoHold sub_cargohold;
      public static CargoIntake sub_cargointake;
      //public static CargoLift sub_cargolift;
      public static HatchGrabber sub_hatchgrabber;




  @Override
  public void robotInit() {
  
    sub_drivetrain = new Drivetrain();
    sub_cargohold = new CargoHold();
    sub_cargointake = new CargoIntake();
   // sub_cargolift = new CargoLift();
    sub_hatchgrabber = new HatchGrabber();

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
