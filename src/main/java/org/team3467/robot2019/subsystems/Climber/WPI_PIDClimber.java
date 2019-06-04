
package org.team3467.robot2019.subsystems.Climber;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class WPI_PIDClimber extends Command {

	private static final double TOLERANCE = 2;
	
	private PIDController m_pid;
    private double m_setPoint = 0.0;
    private double m_maxSpeed = 0.2;  // TODO: Bump this up to speed up PID action
    private int m_statsCounter = 0;
    
	private double KP = 1.0;
	private double KI = 0.0;
	private double KD = 0.0;
	
    public WPI_PIDClimber(double setPoint) {
    	requires(Robot.sub_climber);
		
		m_setPoint = setPoint;
		
    }
    
    private void buildController() {
		
		m_pid = new PIDController(KP, KI, KD,
                new PIDSource() {
                    PIDSourceType m_sourceType = PIDSourceType.kDisplacement;

                    public double pidGet() {
                    	return Robot.sub_climber.getEncoderCount();
                    }

                    public void setPIDSourceType(PIDSourceType pidSource) {
                    	m_sourceType = pidSource;
                    }

                    public PIDSourceType getPIDSourceType() {
                        return m_sourceType;
                    }
                },
                new PIDOutput() {
                	
                	public void pidWrite(double d) {
                		// Drive with the magnitude returned by the PID calculation, 
            			Robot.sub_climber.drive(d);
                }});
		
        m_pid.setAbsoluteTolerance(TOLERANCE);
        m_pid.setOutputRange((m_maxSpeed * -1.0), m_maxSpeed);
    }

    // Called just before this Command runs the first time
    protected void initialize() {

		buildController();
		
        // Set the target setpoint
        m_pid.setSetpoint(m_setPoint);
        SmartDashboard.putNumber("Climber SetPoint", m_setPoint);

        // Reset and start the PID controller
        m_pid.reset();
        m_pid.enable();
        
        m_statsCounter = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
 
        if (m_statsCounter++ > 25) {
            m_statsCounter = 0; // report stats when counter == 0
            Robot.sub_climber.reportClimberStats();
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

    	double error = m_pid.getError();
   		return ((error >= 0 && error <= TOLERANCE) || (error < 0 && error >= (-1.0)*TOLERANCE));
    }

    // Called once after isFinished returns true
    protected void end() {
        
        m_pid.disable();
        Robot.sub_climber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
