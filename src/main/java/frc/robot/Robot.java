
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import frc.Subsystems.Cargo.CargoHold;
import frc.Subsystems.Cargo.CargoIntake;
import frc.Subsystems.Cargo.CargoLift;
import frc.Subsystems.Drivetrain.Drivetrain;
import frc.Subsystems.Hatch.HatchGrabber;



public class Robot extends TimedRobot {


      public static OI robot_oi;

      //public static Drivetrain sub_drivetrain;
      public static CargoHold sub_cargohold;
      //public static CargoIntake sub_cargointake;
      //public static CargoLift sub_cargolift;
      public static HatchGrabber sub_hatchgrabber;




  @Override
  public void robotInit() {
  
    //sub_drivetrain = new Drivetrain();
    sub_cargohold = new CargoHold();
    //sub_cargointake = new CargoIntake();
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
