package org.team3467.robot2019.subsystems.CargoIntake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Cargo_TalonSRX extends TalonSRX {


    private double m_P = 0.3;
    private double m_I = 0.0;
    private double m_D = 0.0;
    private double m_F = 0.0;
    private double iZone = 0;

    private int m_cruiseVelocity = 300;
    private int m_acceleration = 150;
    private int m_slot = 0;


    public Cargo_TalonSRX(int deviceId) {
            super(deviceId);
        loadDefaults();
            
    }

    public void loadDefaults() {

        this.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 10);
        this.setSensorPhase(true);

        configMotionAcceleration(m_acceleration, 10);
        configMotionCruiseVelocity(m_cruiseVelocity, 10);
        config_kP(m_slot, m_P);
        config_kI(m_slot, m_I);
        config_kD(m_slot, m_D);
        config_kF(m_slot, m_F);

    }



}