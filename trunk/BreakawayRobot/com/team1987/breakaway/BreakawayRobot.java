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
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.AnalogChannel;

import com.team1987.breakaway.api.Constants;

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
    Relay m_relay1;
    AnalogChannel m_analogChannel1;
    Victor m_victor1;

    public BreakawayRobot() {
        // Create a robot using standard right/left robot drive on PWMS 1, 2, 3, and 4
        m_robotDrive = new RobotDrive(Constants.c_leftDriveMotor1, Constants.c_leftDriveMotor2,
                Constants.c_rightDriveMotor1, Constants.c_rightDriveMotor2);

        // Define joysticks being used at USB port #1 and USB port #2 on the Drivers Station
        m_rightStick = new Joystick(Constants.c_joystickRightPort);
        m_leftStick = new Joystick(Constants.c_joystickLeftPort);
        m_compressor = new Compressor(Constants.c_compressorPressureSwitchChannel,
                Constants.c_compressorRelayChannel);
        m_solenoid1 = new Solenoid(Constants.c_hookAngleSolenoid1Channel);
        m_solenoid2 = new Solenoid(Constants.c_hookAngleSolenoid2Channel);
        m_relay1 = new Relay(Constants.c_hookExtenderChannel, Relay.Direction.kBoth);
        m_analogChannel1 = new AnalogChannel(Constants.c_stringPOTChannel);
        m_victor1 = new Victor(Constants.c_victor1Channel);
    }

    public void robotInit() {
        m_compressor.start();
        m_solenoid1.set(true);
        m_solenoid2.set(false);
        m_relay1.set(Relay.Value.kOff);
        m_victor1.set(Constants.c_victorStop);
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
        if(m_rightStick.getRawButton(Constants.c_hookAngleButton)) {
            m_solenoid1.set(false);
            m_solenoid2.set(true);
        }
        else {
            m_solenoid1.set(true);
            m_solenoid2.set(false);
        }

        if(m_rightStick.getRawButton(Constants.c_hookExtenderButton)) {
            if(m_analogChannel1.getVoltage() > Constants.c_stringPOT_min) {
                m_relay1.set(Relay.Value.kForward);
            }
            else {
                m_relay1.set(Relay.Value.kOff);
            }
        }
        else if(m_rightStick.getRawButton(Constants.c_hookRetractorButton)) {
            if(m_analogChannel1.getVoltage() < Constants.c_stringPOT_max) {
                m_relay1.set(Relay.Value.kReverse);
            }
            else {
                m_relay1.set(Relay.Value.kOff);
            }
        }
        else {
            m_relay1.set(Relay.Value.kOff);
        }

    }

    //Continuous WE DID NOT USE THIS LAST YEAR AND MAY NOT THIS YEAR
    public void disabledContinuous() {
    }

    public void autonomousContinuous() {
    }

    public void teleopContinuous() {
    }

}
