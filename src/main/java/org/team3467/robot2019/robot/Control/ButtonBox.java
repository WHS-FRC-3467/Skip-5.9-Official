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
    kCollectCargo (1),
    kStowCargo (2),
	kSpitCargo (3),
	kGrabHatch (4),
	kReleaseHatch (5),
	kStowHatch (6),
	kLiftCargo1 (7),
	kLiftCargo2 (8),
	kLiftCargo3 (9),
	kLiftCargoShip (10),
	kLiftHatch1 (11),
	kLiftHatch2 (12),
	kLiftHatch3 (13),
	kReverseIntakeRoller (14),
	kQueueClimber (15),
	kClimbHab2 (16),
	kClimbHab3 (17),
	kClimbRetract (18),
	kClimbCrawl (19),
	kAutoLineUp (20);

    public final int value;

    Button(int value) {
      this.value = value;
    }

    public int getValue() {
        return this.value;
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