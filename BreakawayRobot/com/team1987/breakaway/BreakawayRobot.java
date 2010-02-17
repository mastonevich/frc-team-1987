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
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationLCD;

import com.team1987.breakaway.api.Constants;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class BreakawayRobot extends IterativeRobot {

    DriverStation m_DS;
    DriverStationEnhancedIO m_DSEIO;
    DriverStationLCD m_DSLCD;
    RobotDrive m_robotDrive;		// robot will use PWM 1-4 for drive motors
    Joystick m_rightStick;			// joystick 1 (arcade stick or right tank stick)
    public Joystick m_leftStick;			// joystick 2 (tank left stick)
    Compressor m_compressor;
    //Solenoids
    Solenoid m_LanceAngleSolenoidIn;
    Solenoid m_LanceAngleSolenoidOut;
    Solenoid m_herderSolenoid1In;
    Solenoid m_herderSolenoid1Out;
    Solenoid m_herderSolenoid2In;
    Solenoid m_herderSolenoid2Out;
    Solenoid m_kickerSolenoidIn;
    Solenoid m_kickerSolenoidOut;
    Relay m_LanceExtenderRelay;
    Relay m_combineRelay;
    AnalogChannel m_analogChannel1;
    Victor m_kickerWinder;
    Victor m_winch;
    double kScoreThreshold = .01;
    AxisCamera cam;
    TrackerDashboard trackerDashboard = new TrackerDashboard();
    int kickerStrength;

    public BreakawayRobot() {

        m_DS = DriverStation.getInstance();

        m_DSEIO = m_DS.getEnhancedIO();

        m_DSLCD = DriverStationLCD.getInstance();


        // Create a robot using standard right/left robot drive on PWMS 1, 2, 3, and 4
        m_robotDrive = new RobotDrive(Constants.c_leftDriveMotor1, Constants.c_leftDriveMotor2,
                Constants.c_rightDriveMotor1, Constants.c_rightDriveMotor2);

        // Define joysticks being used at USB port #1 and USB port #2 on the Drivers Station
        m_rightStick = new Joystick(Constants.c_joystickRightPort);
        m_leftStick = new Joystick(Constants.c_joystickLeftPort);
        m_compressor = new Compressor(Constants.c_compressorPressureSwitchChannel,
                Constants.c_compressorRelayChannel);
        m_LanceAngleSolenoidIn = new Solenoid(Constants.c_LanceAngleSolenoidInChannel);
        m_LanceAngleSolenoidOut = new Solenoid(Constants.c_LanceAngleSolenoidOutChannel);
        m_herderSolenoid1In = new Solenoid(Constants.c_herderSolenoid1InChannel);
        m_herderSolenoid1Out = new Solenoid(Constants.c_herderSolenoid1OutChannel);
        m_herderSolenoid2In = new Solenoid(Constants.c_herderSolenoid2InChannel);
        m_herderSolenoid2Out = new Solenoid(Constants.c_herderSolenoid1OutChannel);
        m_kickerSolenoidIn = new Solenoid(Constants.c_kickerSolenoidInChannel);
        m_kickerSolenoidOut = new Solenoid(Constants.c_kickerSolenoidOutChannel);

        m_analogChannel1 = new AnalogChannel(Constants.c_stringPOTChannel);
        m_kickerWinder = new Victor(Constants.c_kickerWinderChannel);

        m_LanceExtenderRelay = new Relay(Constants.c_LanceExtenderRelayChannel, Relay.Direction.kBoth);
        m_combineRelay = new Relay(Constants.c_combineRelayChannel, Relay.Direction.kBoth);

    }

    public void robotInit() {
        m_compressor.start();
        m_LanceAngleSolenoidIn.set(true);
        m_LanceAngleSolenoidOut.set(false);
        m_herderSolenoid1In.set(true);
        m_herderSolenoid1Out.set(false);
        m_herderSolenoid2In.set(true);
        m_herderSolenoid2Out.set(false);
        m_kickerSolenoidIn.set(true);
        m_kickerSolenoidOut.set(false);

        m_kickerWinder.set(Constants.c_victorStop);

        m_LanceExtenderRelay.set(Relay.Value.kOff);
        m_combineRelay.set(Relay.Value.kOff);

        for(int i = 1; i < 9; i++) {
            try {
                m_DSEIO.setDigitalConfig(i, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            } catch(DriverStationEnhancedIO.EnhancedIOException ex) {
                ex.printStackTrace();
            }
        }

        Timer.delay(10.0);
        cam = AxisCamera.getInstance();
        cam.writeResolution(AxisCamera.ResolutionT.k320x240);
        cam.writeBrightness(0);
        cam.writeColorLevel(0);

        Watchdog.getInstance().setExpiration(1);
    }

    //Inits
    public void disabledInit() {
        if(m_leftStick.getZ() <= 0) {
            m_DSLCD.println(DriverStationLCD.Line.kUser2, 1, "Kicker Strength = " + kickerStrength());
            m_DSLCD.updateLCD();
        }
    }

    public void autonomousInit() {
    }

    public void teleopInit() {
        boolean lastTrigger = false;
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1,
                "Starting Camera Code");
        DriverStationLCD.getInstance().updateLCD();
        Timer.delay(1.0); //Wait one second so user can see starting message
    }

    //Periodics
    public void disabledPeriodic() {
        if(m_leftStick.getZ() <= 0) {
            m_DSLCD.println(DriverStationLCD.Line.kUser2, 1, "Kicker Strength = " + kickerStrength());
            m_DSLCD.updateLCD();
        }
    }

    public void autonomousPeriodic() {
        if(m_leftStick.getZ() <= 0) {
            m_DSLCD.println(DriverStationLCD.Line.kUser2, 1, "Kicker Strength = " + kickerStrength());
            m_DSLCD.updateLCD();
        }
    }

    public void teleopPeriodic() {
        // feed the user watchdog at every period when in autonomous
        Watchdog.getInstance().feed();

        m_robotDrive.arcadeDrive(m_rightStick, false);			// drive with arcade style (use right stick)


        //Herder Code
        if(m_rightStick.getRawButton(Constants.c_kickerRightButton)) {
            m_herderSolenoid1In.set(false);
            m_herderSolenoid1Out.set(true);
            m_herderSolenoid2In.set(false);
            m_herderSolenoid2Out.set(true);
            m_kickerSolenoidIn.set(false);
            m_kickerSolenoidOut.set(true);
        }
        else {
            m_herderSolenoid1In.set(true);
            m_herderSolenoid1Out.set(false);
            m_herderSolenoid2In.set(true);
            m_herderSolenoid2Out.set(false);
            m_kickerSolenoidIn.set(true);
            m_kickerSolenoidOut.set(false);
        }

        //Lance Angle Code
        if(m_leftStick.getRawButton(Constants.c_LanceAngleLeftButton)) {
            m_LanceAngleSolenoidIn.set(false);
            m_LanceAngleSolenoidOut.set(true);
        }
        else {
            m_LanceAngleSolenoidIn.set(true);
            m_LanceAngleSolenoidOut.set(false);
        }

        //Lance Extender Code
        if(m_leftStick.getRawButton(Constants.c_LanceExtenderLeftButton)) {
            if(m_analogChannel1.getVoltage() > Constants.c_stringPOT_min) {
                m_LanceExtenderRelay.set(Relay.Value.kForward);
            }
            else {
                m_LanceExtenderRelay.set(Relay.Value.kOff);
            }
        }
        else if(m_leftStick.getRawButton(Constants.c_LanceRetractorLeftButton)) {
            if(m_analogChannel1.getVoltage() < Constants.c_stringPOT_max) {
                m_LanceExtenderRelay.set(Relay.Value.kReverse);
            }
            else {
                m_LanceExtenderRelay.set(Relay.Value.kOff);
            }
        }
        else {
            m_LanceExtenderRelay.set(Relay.Value.kOff);
        }

        if(m_leftStick.getZ() <= 0) {
            m_DSLCD.println(DriverStationLCD.Line.kUser2, 1, "Kicker Strength = " + kickerStrength());
            m_DSLCD.updateLCD();
        }


        try {
            DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1,
                    "Camera Running     ");
            DriverStationLCD.getInstance().updateLCD();
            if(cam.freshImage()) {// && turnController.onTarget()) {
                ColorImage image = cam.getImage();
                Thread.yield();
                Target[] targets = Target.findCircularTargets(image);
                Thread.yield();
                image.free();
                if(targets.length == 0 || targets[0].m_score < kScoreThreshold) {
                    System.out.println("No target found");
                    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1,
                            "Not Found              ");
                    Target[] newTargets = new Target[targets.length + 1];
                    newTargets[0] = new Target();
                    newTargets[0].m_majorRadius = 0;
                    newTargets[0].m_minorRadius = 0;
                    newTargets[0].m_score = 0;
                    for(int i = 0; i < targets.length; i++) {
                        newTargets[i + 1] = targets[i];
                    }
                }
                else {
                    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1,
                            "Pos X: " + targets[0].m_xPos);
                    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 1,
                            "Pos Y: " + targets[0].m_yPos);
                    DriverStationLCD.getInstance().updateLCD();
                    System.out.println(targets[0]);
                    System.out.println("Target Angle: " + targets[0].getHorizontalAngle());
                }
            }
        } catch(NIVisionException ex) {
            ex.printStackTrace();
        } catch(AxisCameraException ex) {
            ex.printStackTrace();
        }

    }

    //Continuous WE DID NOT USE THIS LAST YEAR AND MAY NOT THIS YEAR
    public void disabledContinuous() {
    }

    public void autonomousContinuous() {
    }

    public void teleopContinuous() {
    }

    public int kickerStrength() {

        try {
            kickerStrength = ~m_DSEIO.getDigitals() & Constants.c_kickerSwitchBits;
            switch(kickerStrength) {
                case Constants.c_kickerSwitchPos1:
                    break;
                case Constants.c_kickerSwitchPos2:
                    break;
                case Constants.c_kickerSwitchPos3:
                    kickerStrength = 3;
                    break;
                case Constants.c_kickerSwitchPos4:
                    kickerStrength = 4;
                    break;
                case Constants.c_kickerSwitchPos5:
                    kickerStrength = 5;
                    break;
                case Constants.c_kickerSwitchPos6:
                    kickerStrength = 6;
                    break;
                case Constants.c_kickerSwitchPos7:
                    kickerStrength = 7;
                    break;
                case Constants.c_kickerSwitchPos8:
                    kickerStrength = 8;
                    break;
            }

        } catch(DriverStationEnhancedIO.EnhancedIOException ex) {
            ex.printStackTrace();
        }
        return kickerStrength;

    }

}
