/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.team1987.breakaway;

import edu.wpi.first.wpilibj.Watchdog;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class BreakawayRobot extends IterativeRobot {

    RobotDrive m_robotDrive;		// robot will use PWM 1-4 for drive motors
    Joystick m_rightStick;			// joystick 1 (arcade stick or right tank stick)
    Joystick m_leftStick;			// joystick 2 (tank left stick)
    Compressor m_compressor;
    Solenoid m_solenoid1;
    Solenoid m_solenoid2;

    public BreakawayRobot() {
        // Create a robot using standard right/left robot drive on PWMS 1, 2, 3, and 4
        m_robotDrive = new RobotDrive(1, 2, 3, 4);

        // Define joysticks being used at USB port #1 and USB port #2 on the Drivers Station
        m_rightStick = new Joystick(1);
        m_leftStick = new Joystick(2);
        m_compressor = new Compressor(1,1);
        m_solenoid1 = new Solenoid(1);
        m_solenoid2 = new Solenoid(8);

    }

    public void robotInit() {
        m_compressor.start();
        m_solenoid1.set(true);
        m_solenoid2.set(false);
    }

    //Inits
    public void disabledInit() {
    }

    public void autonomousInit() {
    }

    public void teleopInit() {
    }

    //Periodics
    public void disabledPeriodic() {
    }

    public void autonomousPeriodic() {
    }

    public void teleopPeriodic() {
        // feed the user watchdog at every period when in autonomous
        Watchdog.getInstance().feed();

        m_robotDrive.arcadeDrive(m_rightStick, false);			// drive with arcade style (use right stick)

    }

    //Continuous WE DID NOT USE THIS LAST YEAR AND MAY NOT THIS YEAR
    public void disabledContinuous() {
    }

    public void autonomousContinuous() {
    }

    public void teleopContinuous() {
    }
}
