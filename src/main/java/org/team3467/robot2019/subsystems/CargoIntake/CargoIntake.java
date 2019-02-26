package org.team3467.robot2019.subsystems.CargoIntake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoIntake extends Subsystem
{

    private eCargoIntakeArmPosition activeIntakeArmPosition;

    Cargo_TalonSRX m_intakeArm = new Cargo_TalonSRX(RobotGlobal.CARGO_INTAKE_ARM_1);
    TalonSRX        m_intakeArm_2 = new TalonSRX(RobotGlobal.CARGO_INTAKE_ARM_2);
    TalonSRX       m_intakeRoller = new TalonSRX(RobotGlobal.CARGO_INTAKE_ROLLER);

    protected void initDefaultCommand()
    {
        setDefaultCommand(new DriveCargoIntakeRoller());
    }

    public enum eCargoIntakeArmPosition
    { 
        CRAWL(0000,"CRAWL"),
        INTAKE(0000, "INTAKE"),
        RETRACTED(0000, "RETRACTED");

        private int IntakeArmPosition;
        private String IntakeArmPositionName;

        private eCargoIntakeArmPosition(int pos, String name) {
                IntakeArmPosition = pos;
                IntakeArmPositionName = name;   
        }

        public int getSetpoint() {
            return IntakeArmPosition;
        }

        public String getSetpointName() {
            return IntakeArmPositionName;
        }
    }

    public CargoIntake()
    {

        m_intakeArm.setNeutralMode(NeutralMode.Brake);
        m_intakeArm_2.setNeutralMode(NeutralMode.Brake);
        m_intakeArm_2.follow(m_intakeArm);

        driveManualArm(0.0);
        zeroArmEncoder();
        driveManualRoller(0.0);

    }

    //#region Roller System

        //TODO make this an enum for crawl, intake speeds

    public void driveManualRoller(double speed) {
        m_intakeRoller.set(ControlMode.PercentOutput, speed);
     }

    //#endregion

   

    //#region Arm System

    public int getArmEncoderPosition() {
        return m_intakeArm.getSelectedSensorPosition();
    }

    public void zeroArmEncoder() {
        m_intakeArm.setSelectedSensorPosition(0,0,0);
    }

    // getter methods
    public eCargoIntakeArmPosition getArmActivePosition() {
            return activeIntakeArmPosition;
    }
    
    //  move methods
    

    public void moveArmToPosition(eCargoIntakeArmPosition position) {
        activeIntakeArmPosition = position;
        m_intakeArm.set(ControlMode.MotionMagic, position.getSetpoint());
    }

    public void driveManualArm(double speed) {
        m_intakeArm.set(ControlMode.PercentOutput, speed);
    }

    //#endregion
}

    