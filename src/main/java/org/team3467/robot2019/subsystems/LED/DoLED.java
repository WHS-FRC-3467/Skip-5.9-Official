package org.team3467.robot2019.subsystems.LED;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.InstantCommand;

public class DoLED extends InstantCommand {
    static I2C Wire = new I2C(Port.kOnboard, 4);


    @Override
    protected void execute() {
        System.out.println("howdy");

	 String WriteString = "go";
	 char[] CharArray = WriteString.toCharArray();
	 byte[] WriteData = new byte[CharArray.length];
	 for (int i = 0; i < CharArray.length; i++) {
     Wire.transaction(WriteData, WriteData.length, null, 0);
     }
     cancel();

}

@Override
protected boolean isFinished() {
    return false;
}
        
    }
