package org.team3467.robot2019.subsystems.Lift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import org.team3467.robot2019.robot.RobotGlobal;

import edu.wpi.first.wpilibj.command.Subsystem;

public class FourBarLift extends Subsystem {

    WPI_TalonSRX liftmotor = new WPI_TalonSRX(RobotGlobal.CARGO_LIFT);
    private eFourBarLiftPosition currentPosition;

    public enum eFourBarLiftPosition {
        L1(0, "ROCKET LEVEL ONE"),
        L2(1, "ROCKET LEVEL TWO"),
        L3(2, "ROCKET LEVEL THREE"),
        CARGO_SHIP(3,  "CARGO SHIP"),
        INTAKE(4, "INTAKE");

        private final int position;
        private final String name;

        private eFourBarLiftPosition(int position, String name) {
                this.position = position;
                this.name = name;
        }

        public int getPosition() {
            return this.position;
        }

        public String getName() {
            return this.name;
        }

        
    }
    
    

    public FourBarLift() {
            liftmotor.setSensorPhase(true);
            liftmotor.set(ControlMode.PercentOutput, 0.0);
            liftmotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    }
    
    @Override
    protected void initDefaultCommand() {

    }

    public eFourBarLiftPosition getPosition() {
        return currentPosition;
    }
    public void setPosition(eFourBarLiftPosition position) {
        currentPosition = position;

    }

    public int getEncoder() {
        return liftmotor.getSelectedSensorPosition();
    }
    public void zeroEncoder() {
        liftmotor.setSelectedSensorPosition(0,0,0);
    }

    public void moveMagically() {

    }

    public void driveManual(double speed) {
        liftmotor.set(ControlMode.PercentOutput, speed);
    }

  

    


}