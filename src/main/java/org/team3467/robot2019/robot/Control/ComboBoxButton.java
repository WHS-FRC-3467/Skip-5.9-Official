package org.team3467.robot2019.robot.Control;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.*;

/**
 * A custom button that is triggered when two buttons on a GenericHID are
 * simultaneously pressed.
 */
public class ComboBoxButton extends Trigger {
	private ButtonBox m_bb;
	private ButtonBox.Button m_button1, m_button2;
	
	public ComboBoxButton(ButtonBox bb, ButtonBox.Button button1, ButtonBox.Button button2) {
		this.m_bb = bb;
		this.m_button1 = button1;
		this.m_button2 = button2;
	}	
    
    public boolean get() {
        return m_bb.getRawButton(m_button1.getValue()) && m_bb.getRawButton(m_button2.getValue());
    }
}
