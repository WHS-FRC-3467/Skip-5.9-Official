package org.team3467.robot2019.robot.Control;

import edu.wpi.first.wpilibj.GenericHID;
//import edu.wpi.first.wpilibj.hal.FRCNetComm.tResourceType;
//import edu.wpi.first.wpilibj.hal.HAL;

/**
 * Handle input from a custom button box connected to the Driver Station over USB.
 *
 * <p>This class handles input that comes from an MSP Launchpad controller board.
 */
public class ButtonBox extends GenericHID {
  /**
   * Represents the digital buttons.
   */
  public enum Button {
    k1(1),
    k2(2),
    k3(3),
    k4(4),
    k5(5),
    k6(6),
    k7(7),
    k8(8),
    k9(9),
    k10(10),
    k11(11),
    k12(12),
    k13(13),
    k14(14),
    k15(15),
    k16(16),
    k17(17),
    k18(18),
    k19(19),
    k20(20);

    public final int value;

    Button(int value) {
      this.value = value;
    }
  }

  /**
   * Construct an instance of a ButtonBox. The port number is the USB port on the Driver Station.
   *
   * @param port The port on the Driver Station that the joystick is plugged into.
   */
  public ButtonBox(final int port) {
    super(port);

    // HAL.report(tResourceType.kResourceType_XboxController, port);
    // HAL.report(tResourceType.kResourceType_Joystick, port);
  }

@Override
public double getX(Hand hand) {
	// Auto-generated method stub
	return 0;
}

@Override
public double getY(Hand hand) {
	// Auto-generated method stub
	return 0;
}


}