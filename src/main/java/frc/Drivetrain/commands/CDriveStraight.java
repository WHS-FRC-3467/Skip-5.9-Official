package frc.Drivetrain.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class CDriveStraight extends Command {

    private PIDController m_pid;

    private double KP = 2.0;
    private double KI = 0.0;
    private double KD = 0.0;

    private int P_SPEED = 0;

    private void buildController() {
            m_pid = new PIDController(KP, KI, KD, 
                new PIDSource(){
                PIDSourceType mSourceType = PIDSourceType.kDisplacement;


                    @Override
                    public void setPIDSourceType(PIDSourceType pidSource) {
                        mSourceType = pidSource;
                    }
                
                    @Override
                    public double pidGet() {
                        return 0;
                    }
                
                    @Override
                    public PIDSourceType getPIDSourceType() {
                        return mSourceType;
                    }


                }, new PIDOutput(){
                
                    @Override
                    public void pidWrite(double output) {
                        Robot.sub_drivetrain.drive(P_SPEED, P_SPEED);
                    }
                });
    }
    public CDriveStraight(double distance, double maxSpeed){
        requires(Robot.sub_drivetrain);
    }

    public CDriveStraight(double distance){
        requires(Robot.sub_drivetrain);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }


    @Override
    protected void execute() {
        
    }
}