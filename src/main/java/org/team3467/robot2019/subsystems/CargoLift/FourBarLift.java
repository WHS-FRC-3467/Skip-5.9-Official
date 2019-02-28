package org.team3467.robot2019.subsystems.CargoLift;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team3467.robot2019.robot.RobotGlobal;
import org.team3467.robot2019.subsystems.MagicTalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

public class FourBarLift extends Subsystem {

    MagicTalonSRX liftmotor = new MagicTalonSRX("FBL",RobotGlobal.CARGO_LIFT,0);
    
    private eFourBarLiftPosition moveToPosition;

    //TODO implement encoder values here
    public enum eFourBarLiftPosition {
        L1(0, "ROCKET LEVEL ONE"),
        L2(1, "ROCKET LEVEL TWO"),
        L3(2, "ROCKET LEVEL THREE"),
        CARGO_SHIP(3,  "CARGO SHIP"),
        INTAKE(4, "INTAKE");

        private final int setpoint;
        private final String name;

        private eFourBarLiftPosition(int position, String name) {
                this.setpoint = position;
                this.name = name;
        }

        public int getSetpoint() {
            return this.setpoint;
        }

        public String getName() {
            return this.name;
        }

        
    }
    
    
    // Static subsystem reference
    private static FourBarLift fBInstance = new FourBarLift();

    public static FourBarLift getInstance() {
        return FourBarLift.fBInstance;
    }

    //FourBarLift class constructor
    protected FourBarLift()
    {
        liftmotor.setSensorPhase(true);
        liftmotor.set(ControlMode.PercentOutput, 0.0);

    }
    
    @Override
    protected void initDefaultCommand() {

    }

    public eFourBarLiftPosition getPosition() {
        return moveToPosition;
    }
    public void setPosition(eFourBarLiftPosition position) {
        moveToPosition = position;

    }

    public int getEncoder() {
        return liftmotor.getSelectedSensorPosition(0);
    }
    public void zeroEncoder() {
        liftmotor.setSelectedSensorPosition(0,0,0);
    }

    public void moveMagically() {
        liftmotor.set(ControlMode.MotionMagic, moveToPosition.getSetpoint());
    }

    public void moveMagically(eFourBarLiftPosition pos) {
        if(safetyCheck()) {
            liftmotor.set(ControlMode.MotionMagic, pos.getSetpoint());
            moveToPosition = pos;
        }
    }

    public void driveManual(double speed, int direction) {
        liftmotor.set(ControlMode.PercentOutput, speed*(double)direction);
    }

  
    private boolean safetyCheck() {
        boolean passed = true;

        System.out.println("Safety check at FBL passed? : " + passed);
        return passed;
    }


    


}