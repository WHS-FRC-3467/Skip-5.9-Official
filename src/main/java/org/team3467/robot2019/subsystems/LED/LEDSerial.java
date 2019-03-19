package org.team3467.robot2019.subsystems.LED;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

public class LEDSerial extends Subsystem {

    public SerialPort arduino;



//#region Patterns

    //Basic patterns
    public static final int OFF = 0;
    public static final int P_RAINBOW_FADE = 2;
    public static final int P_STARTUP = 3;
    public static final int P_LIMELIGHT_TARGET_LININGUP= 4;
    public static final int P_LIMELIGHT_LOCKED = 5;
    public static final int P_LIMELIGHT_INTERCEPTING = 99;
    public static final int P_AMERICA = 6;
    public static final int P_POLICE = 7;
    public static final int P_CARGO_IN = 8;
    public static final int P_CARGO_OUT = 9;
    public static final String P_CARGO_HOLDING = "Q";


//#endregion

    int currentPattern = 0;

    // Static subsystem reference
    private static LEDSerial lSInstance = new LEDSerial();

    public static LEDSerial getInstance() {
        return LEDSerial.lSInstance;
    }

    //LEDSerial class constructor
    protected LEDSerial()
    {
        try {
            arduino = new SerialPort(9600, Port.kUSB);
            arduino.setTimeout(10);
        } catch(Exception e) {
            System.out.print("no leds connected");
        }

        
    }


    @Override
    protected void initDefaultCommand() {

    }

    public void setLEDPattern(int ledPattern) {
        try {
            arduino.writeString(Integer.toString(ledPattern));
            currentPattern = ledPattern;
        } catch(Exception e1) {
                // probably not connected to LEDs
        }
      
    }

    public void setLEDPattern(String ledPattern) {
        try {
            arduino.writeString(ledPattern);
        } catch(Exception e1) {
                // probably not connected to LEDs
        }
      
    }

    public int getLEDPattern() {
        return currentPattern;
    }

    public void ledsOff() {
        try {
            arduino.writeString(Integer.toString(0));
        } catch(Exception e1) {
                // probably not connected to LEDs
        }    }  
    public void ledsOn() {
        setLEDPattern(currentPattern);
    }




}