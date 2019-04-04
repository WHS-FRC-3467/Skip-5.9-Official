/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.team3467.robot2019.subsystems.Climber;

import org.team3467.robot2019.robot.Robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.command.Command;

public class PIDClimber extends Command {

    private CANSparkMax m_motor;
    private CANPIDController m_pidController;
    private CANEncoder m_encoder;
    public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
    private double m_initSetpoint = 0.0;

    private boolean m_calibrating = false;

    public PIDClimber(double initSetpoint)
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.sub_climber);

        // initialize motor
        m_motor = Robot.sub_climber.m_sparkMax;
    
        /**
         * In order to use PID functionality for a controller, a CANPIDController object
         * is constructed by calling the getPIDController() method on an existing
         * CANSparkMax object
         */
        m_pidController = m_motor.getPIDController();
    
        // Encoder object created to display position values
        m_encoder = m_motor.getEncoder();

        m_initSetpoint = initSetpoint;
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize()
    {
        // PID coefficients
        kP = 1.1; 
        kI = 0;
        kD = 0.02; 
        kIz = 0; 
        kFF = 0; 
        kMaxOutput = 0.75; 
        kMinOutput = -0.75;

        // set PID coefficients
        m_pidController.setP(kP);
        m_pidController.setI(kI);
        m_pidController.setD(kD);
        m_pidController.setIZone(kIz);
        m_pidController.setFF(kFF);
        m_pidController.setOutputRange(kMinOutput, kMaxOutput);

        if (m_calibrating)
        {
            // display PID coefficients on SmartDashboard
            SmartDashboard.putNumber("P Gain", kP);
            SmartDashboard.putNumber("I Gain", kI);
            SmartDashboard.putNumber("D Gain", kD);
            SmartDashboard.putNumber("I Zone", kIz);
            SmartDashboard.putNumber("Feed Forward", kFF);
            SmartDashboard.putNumber("Max Output", kMaxOutput);
            SmartDashboard.putNumber("Min Output", kMinOutput);
            SmartDashboard.putNumber("Setpoint", m_initSetpoint);
        }
    }

  // Called repeatedly when this Command is scheduled to run
    @Override
    protected void execute() {

        if (m_calibrating)
        {
                // read PID coefficients from SmartDashboard
            double p = SmartDashboard.getNumber("P Gain", 0);
            double i = SmartDashboard.getNumber("I Gain", 0);
            double d = SmartDashboard.getNumber("D Gain", 0);
            double iz = SmartDashboard.getNumber("I Zone", 0);
            double ff = SmartDashboard.getNumber("Feed Forward", 0);
            double max = SmartDashboard.getNumber("Max Output", 0);
            double min = SmartDashboard.getNumber("Min Output", 0);
            double setpoint = SmartDashboard.getNumber("Setpoint", 0);

            // if PID coefficients on SmartDashboard have changed, write new values to controller
            if((p != kP)) { m_pidController.setP(p); kP = p; }
            if((i != kI)) { m_pidController.setI(i); kI = i; }
            if((d != kD)) { m_pidController.setD(d); kD = d; }
            if((iz != kIz)) { m_pidController.setIZone(iz); kIz = iz; }
            if((ff != kFF)) { m_pidController.setFF(ff); kFF = ff; }
            if((max != kMaxOutput) || (min != kMinOutput)) { 
                m_pidController.setOutputRange(min, max); 
                kMinOutput = min; kMaxOutput = max; 
            }
            if ((setpoint != m_initSetpoint)) m_initSetpoint = setpoint;
        }

        /**
         * PIDController objects are commanded to a set point using the 
         * SetReference() method.
         * 
         * The first parameter is the value of the set point, whose units vary
         * depending on the control type set in the second parameter.
         * 
         * The second parameter is the control type can be set to one of four 
         * parameters:
         *  com.revrobotics.ControlType.kDutyCycle
         *  com.revrobotics.ControlType.kPosition
         *  com.revrobotics.ControlType.kVelocity
         *  com.revrobotics.ControlType.kVoltage
         */
        m_pidController.setReference(m_initSetpoint, ControlType.kPosition);

        SmartDashboard.putNumber("Climber SetPoint", m_initSetpoint);
        SmartDashboard.putNumber("Climber Encoder", m_encoder.getPosition());

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        // Go back to Voltage control to reset PID loop
        m_pidController.setReference(0.0, ControlType.kVoltage);
        
        // Reset to normal output range
        m_pidController.setOutputRange(-1.0, 1.0); 

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
    protected void interrupted() {
        end();
    }
}
