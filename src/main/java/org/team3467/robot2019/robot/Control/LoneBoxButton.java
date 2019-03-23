package org.team3467.robot2019.robot.Control;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * Add your docs here.
 */
public class LoneBoxButton extends Trigger
{
	private ButtonBox m_bb;
	private ButtonBox.Button m_button, m_exclude;
	
	public LoneBoxButton(ButtonBox bb, ButtonBox.Button button, ButtonBox.Button exclude) {
		this.m_bb = bb;
		this.m_button = button;
		this.m_exclude = exclude;
	}	
    
    public boolean get() {
        if (m_bb.getRawButton(m_button.getValue()) && !m_bb.getRawButton(m_exclude.getValue()))
            return true;
        else
            return false;
    }
}