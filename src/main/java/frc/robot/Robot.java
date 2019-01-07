package frc.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.subsystems.pneumatics.Pneumatics;


public class Robot extends IterativeRobot {

    public Drivetrain r_drivetrain;
    public Pneumatics r_pneumatics;
  
    public OI r_oi;

  @Override
  public void robotInit() {

      r_drivetrain = new Drivetrain();
      r_pneumatics = new Pneumatics();

      r_oi = new OI();
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
