package org.team3467.robot2019.subsystems.Cargo;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public class CargoIntake extends Subsystem {

    private boolean IntakeRollerState;
    private eCargoIntakeArmPosition activeIntakeArmPosition;
    private eCargoIntakeArmPosition standbyIntakeArmPosition;


    Cargo_WPI_TalonSRX Cargo_Intake_Arm_1 = new Cargo_WPI_TalonSRX(RobotGlobal.CARGO_INTAKE_ARM_1);
    Cargo_WPI_TalonSRX Cargo_Intake_Arm_2 = new Cargo_WPI_TalonSRX(RobotGlobal.CARGO_INTAKE_ARM_2);
    WPI_VictorSPX Cargo_Intake_Rollers = new WPI_VictorSPX(RobotGlobal.CARGO_INTAKE_ROLLER);

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new CrawlBot());
    }

    public enum eCargoIntakeArmPosition {
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

        public CargoIntake() {
        IntakeRollerState = false;

        Cargo_Intake_Arm_1.follow(Cargo_Intake_Arm_2);
        
        Cargo_Intake_Arm_2.setSensorPhase(true);
        Cargo_Intake_Arm_2.set(ControlMode.PercentOutput, 0.0);
        Cargo_Intake_Arm_2.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        Cargo_Intake_Arm_1.setNeutralMode(NeutralMode.Brake);
        Cargo_Intake_Arm_2.setNeutralMode(NeutralMode.Brake);
    }
        




    //#region Roller System

        //TODO make this an enum for crawl, intake speeds

    public void roller_setRollers(double speed, int direction) {
        Cargo_Intake_Rollers.set(ControlMode.PercentOutput, speed*(double)direction);
     }
     
     public void roller_stopRollers() {
        Cargo_Intake_Rollers.set(ControlMode.PercentOutput, 0);
     }

    //#endregion

   

    //#region Arm System

    public int getArmEncoder() {
        return Cargo_Intake_Arm_2.getSelectedSensorPosition();
    }
    public void zeroArmEncoder() {
        Cargo_Intake_Arm_1.setSelectedSensorPosition(0,0,0);
    }

    // setter methods
    public void setStandbyRollerPosition(eCargoIntakeArmPosition position) {
        standbyIntakeArmPosition = position;
    }

    // getter methods
    public eCargoIntakeArmPosition getArmActivePosition() {
            return activeIntakeArmPosition;
    }
    public eCargoIntakeArmPosition getArmStandbyPosition() {
        return standbyIntakeArmPosition;
}

    
    //  move methods
    public void arm_moveToStandbyPosition() {
        activeIntakeArmPosition = standbyIntakeArmPosition;
        Cargo_Intake_Arm_2.set(ControlMode.MotionMagic, standbyIntakeArmPosition.getSetpoint());
    }

    public void arm_moveDirectlyToPosition(eCargoIntakeArmPosition position) {
        activeIntakeArmPosition = position;
        Cargo_Intake_Arm_2.set(ControlMode.MotionMagic, position.getSetpoint());
    }

    public void driveManualArm(double speed, int direction) {
        Cargo_Intake_Arm_2.set(speed * (double)direction);
    }

    //#endregion
}

    