package frc.robot;

import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TalonHatch extends Subsystem {

    
    private final WPI_TalonSRX one_talon;
    

    private static TalonHatch dTInstance = new TalonHatch();

    public static TalonHatch getInstance() {
        return TalonHatch.dTInstance;
    }

    public TalonHatch() {
        one_talon = new WPI_TalonSRX(3);

        one_talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);
        one_talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen);

        setTalonBrakes(true);

        
    }

    public void initDefaultCommand() {
    //    setDefaultCommand(new RunTest());
    }

    public WPI_TalonSRX getOne_Talon() {
        return one_talon;
    }


    /**
     * @param setBrake
     */
    public void setTalonBrakes(boolean setBrake) {
        NeutralMode nm = setBrake ? NeutralMode.Brake : NeutralMode.Coast;

        one_talon.setNeutralMode(nm);

        SmartDashboard.putBoolean("Talon Brakes", setBrake);
    }

           
}
