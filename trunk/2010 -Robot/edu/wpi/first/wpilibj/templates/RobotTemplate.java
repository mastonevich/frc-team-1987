/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {

    Joystick m_rightStick;			// joystick 1 (arcade stick or right tank stick)
    Joystick m_leftStick;
    static final Relay m_conveyor = new Relay(1);
    static final Relay m_combine = new Relay(2);
    static final DigitalInput m_bottomDI = new DigitalInput(10);
    static final DigitalInput m_topDI = new DigitalInput(9);

    /**0
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        if (m_leftStick.getRawButton(6)) {
            m_conveyor.set(Relay.Value.kReverse);
            m_combine.set(Relay.Value.kReverse);
        } else {
            if (m_leftStick.getRawButton(1)) {
                m_conveyor.set(Relay.Value.kForward);
                m_combine.set(Relay.Value.kForward);
            } else {
                if (!m_topDI.get()) {
                    m_conveyor.set(Relay.Value.kOff);
                    m_combine.set(Relay.Value.kOff);
                }
            }
        }
    }
}
