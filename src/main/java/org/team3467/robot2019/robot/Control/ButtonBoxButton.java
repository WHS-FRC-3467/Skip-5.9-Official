package org.team3467.robot2019.robot.Control;

import edu.wpi.first.wpilibj.buttons.Button;

/**
 * A {@link Button} that gets its state from an {@link ButtbonBox}.
 */
public class ButtonBoxButton extends Button {

  private final ButtonBox m_bBox;
  private final int m_buttonNumber;

  /**
   * Create a button for triggering commands.
   *
   * @param bbox     	The ButtonBox object that has that button
   * @param button      The button number (see {@link Button})
   */
  public ButtonBoxButton(ButtonBox bbox, ButtonBox.Button button) {
    m_bBox = bbox;
    m_buttonNumber = button.value;
  }

  /**
   * Gets the value of the ButtonBox button.
   *
   * @return The value of the button
   */
  public boolean get() {
    return m_bBox.getRawButton(m_buttonNumber);
  }
}