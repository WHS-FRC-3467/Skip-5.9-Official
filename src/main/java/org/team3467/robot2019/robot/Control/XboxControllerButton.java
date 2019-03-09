package org.team3467.robot2019.robot.Control;

/*----------------------------------------------------------------------------*/
/* Copyright (c) 2008-2017 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//package edu.wpi.first.wpilibj.buttons;


import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A {@link Button} that gets its state from an {@link XboxController}.
 */
public class XboxControllerButton extends Button {

  private final XboxController m_joystick;
  private final int m_buttonNumber;

  /**
   * Create a joystick button for triggering commands.
   *
   * @param joystick     The XboxController object that has that button
   * @param button       The button number (see {@link Button})
   */
  public XboxControllerButton(XboxController joystick, XboxController.Button button) {
    m_joystick = joystick;
    m_buttonNumber = button.value;
  }

  /**
   * Gets the value of the joystick button.
   *
   * @return The value of the joystick button
   */
  public boolean get() {
    return m_joystick.getRawButton(m_buttonNumber);
  }
}