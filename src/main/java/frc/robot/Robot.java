
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.Drivetrain.Drivetrain;


public class Robot extends TimedRobot {


      public static Drivetrain sub_drivetrain;
      public static OI robot_oi;




  @Override
  public void robotInit() {
  
    sub_drivetrain = new Drivetrain();
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

  }


  @Override
  public void testPeriodic() {
  }
}
