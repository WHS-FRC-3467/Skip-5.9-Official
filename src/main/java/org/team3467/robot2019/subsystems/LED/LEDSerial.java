package org.team3467.robot2019.subsystems.LED;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDSerial extends Subsystem {

    public SerialPort serialPort;



//#region Patterns

    //Basic patterns
    public static final int P_LIMELIGHT_LINEUP = 0;
    public static final int P_LIMELIGHT_DONE = 1;
    public static final int P_RAINBOW_FADE = 2;
    public static final int P_MURICA = 3;
    public static final int P_CARGO_INTAKE_IN = 4;
    public static final int P_CARGO_INTAKE_OUT = 5;



    //4 bar lift feedback


    //Limelight feedback
    public static final int PATTERN_LIMELIGHT_LINEUP = 61;
    public static final int PATTERN_LIMELIGHT_LOCKED = 62;

//#endregion

    int currentPattern = -1;

    // Static subsystem reference
    private static LEDSerial lSInstance = new LEDSerial();

    public static LEDSerial getInstance() {
        return LEDSerial.lSInstance;
    }

    //LEDSerial class constructor
    protected LEDSerial()
    {
        try {
        serialPort = new SerialPort(9600, Port.kUSB);
        serialPort.setTimeout(10);
        } catch(Exception e) {
            System.out.print("no leds connected");
        }

        
    }


    @Override
    protected void initDefaultCommand() {

    }

    public void setLEDPattern(int ledPattern) {
        try {
            serialPort.writeString(Integer.toString(ledPattern));
            currentPattern = ledPattern;
        } catch(Exception e1) {
            System.out.println("that didnt work :/");
        }
      
    }

    public int getLEDPattern() {
        return currentPattern;
    }




}