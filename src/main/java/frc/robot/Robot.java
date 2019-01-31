
package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.Drivetrain.Drivetrain;


public class Robot extends TimedRobot {


      public static Drivetrain sub_drivetrain;




  @Override
  public void robotInit() {
  
    sub_drivetrain = new Drivetrain();
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
