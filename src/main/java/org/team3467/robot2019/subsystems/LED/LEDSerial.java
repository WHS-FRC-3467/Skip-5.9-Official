package org.team3467.robot2019.subsystems.LED;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDSerial extends Subsystem {

    public SerialPort serialPort;



//#region Patterns

    //Basic patterns
    public static final int PATTERN_FLASHING_RED = 0;
    public static final int PATTERN_FLASHING_GREEN = 1;
    public static final int PATTERN_FLASHING_BLUE = 2;

    //4 bar lift feedback


    //Limelight feedback
    public static final int PATTERN_LIMELIGHT_LINEUP = 61;
    public static final int PATTERN_LIMELIGHT_LOCKED = 62;

//#endregion

    int currentPattern = -1;


    public LEDSerial() {
        serialPort = new SerialPort(9600, Port.kUSB);
        serialPort.setTimeout(10);

        setLEDPattern(PATTERN_FLASHING_GREEN);
        
    }


    @Override
    protected void initDefaultCommand() {

    }

    public void setLEDPattern(int ledPattern) {
        serialPort.writeString(Integer.toString(ledPattern));
        currentPattern = ledPattern;
    }

    public int getLEDPattern() {
        return currentPattern;
    }




}