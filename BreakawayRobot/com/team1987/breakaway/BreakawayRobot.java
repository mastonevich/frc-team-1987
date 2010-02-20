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
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;


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
    DigitalInput m_kickerReleaseExtended;
    DigitalInput m_kickerReleaseReturned;
    DigitalInput m_LanceReleaseExtended;
    DigitalInput m_LanceReleaseReturned;
    Encoder m_leftDriveEncoder;
    Encoder m_rightDriveEncoder;
    Encoder m_kickerEncoder;
    int kickerState;
    int kickerStrength;
    int kickerEncoderCountLimit;
    boolean kickerLowTrajectory;

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
        m_compressor = new Compressor(Constants.c_compressorPressureSwitchDigitalChannel,
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

        m_LanceExtenderRelay = new Relay(Constants.c_LanceExtenderRelayChannel,
                Relay.Direction.kBoth);
        m_combineRelay = new Relay(Constants.c_combineRelayChannel, Relay.Direction.kBoth);
        m_kickerReleaseExtended = new DigitalInput(Constants.c_kickerReleaseExtendedDigitalChannel);
        m_kickerReleaseReturned = new DigitalInput(Constants.c_kickerReleaseReturnedDigitalChannel);
        m_LanceReleaseExtended = new DigitalInput(Constants.c_LanceReleaseExtendedDigitalChannel);
        m_LanceReleaseReturned = new DigitalInput(Constants.c_LanceReleaseReturnedDigitalChannel);
        m_leftDriveEncoder = new Encoder(Constants.c_leftDriveEncoderDigitalChannel1,
                Constants.c_leftDriveEncoderDigitalChannel2);
        m_rightDriveEncoder = new Encoder(Constants.c_rightDriveEncoderDigitalChannel1,
                Constants.c_rightDriveEncoderDigitalChannel2);
        m_kickerEncoder = new Encoder(Constants.c_kickerEncoderDigitalChannel1,
                Constants.c_kickerEncoderDigitalChannel2);

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

        m_kickerEncoder.start();

        //instantiate digital input pins
        for(int i = 1; i < 10; i++) {
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
    }

    public void autonomousInit() {
    }

    public void teleopInit() {
    }

    //Periodics
    public void disabledPeriodic() {
        setKickerStrength();
    }

    public void autonomousPeriodic() {
    }

    public void teleopPeriodic() {
        // feed the user watchdog at every period when in autonomous
        Watchdog.getInstance().feed();

        // drive with arcade style (use right stick)
        m_robotDrive.arcadeDrive(m_rightStick, false);

        try {
            kickerLowTrajectory = m_DSEIO.getDigital(Constants.c_kickerLowTrajectoryIOChannel);
        } catch(DriverStationEnhancedIO.EnhancedIOException ex) {
            ex.printStackTrace();
        }

        //Kicker/Herder Code
        switch(kickerState) {
            case Constants.c_kickerReady:
                if(m_rightStick.getRawButton(Constants.c_kickerRightButton)) {
                    if(kickerLowTrajectory) {
                        m_herderSolenoid1In.set(false);
                        m_herderSolenoid1Out.set(true);
                        m_herderSolenoid2In.set(false);
                        m_herderSolenoid2Out.set(true);
                        //add code to restore to default position before 2 seconds
                    }
                    m_kickerSolenoidIn.set(false);
                    m_kickerSolenoidOut.set(true);
                    kickerState = Constants.c_kickerKicking;
                }
                break;
            case Constants.c_kickerKicking:
                if(!m_kickerReleaseExtended.get()) kickerState = Constants.c_kickerReleased;
                break;
            case Constants.c_kickerReleased:
                m_kickerSolenoidIn.set(true);
                m_kickerSolenoidOut.set(false);
                kickerState = Constants.c_kickerReturning;
                break;
            case Constants.c_kickerReturning:
                if(!m_kickerReleaseReturned.get()) {
                    m_kickerEncoder.reset();
                    kickerState = Constants.c_kickerLocked;
                }
                else m_kickerWinder.set(Constants.c_kickerReturningSpeed);
                break;
            case Constants.c_kickerLocked:
                m_kickerWinder.set(Constants.c_kickerWindingSpeed);
                kickerState = Constants.c_kickerWinding;
                break;
            case Constants.c_kickerWinding:
                if(m_kickerEncoder.get() > kickerEncoderCountLimit) {
                    m_kickerWinder.set(Constants.c_kickerStopWinding);
                    kickerState = Constants.c_kickerReady;
                }
                break;
        }

        //m_kickerSolenoidIn.set(true);
        //m_kickerSolenoidOut.set(false);
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

        setKickerStrength();








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

    public void setKickerStrength() {
        if(m_leftStick.getZ() <= 0) {
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

            kickerEncoderCountLimit = kickerStrength/8*Constants.c_kickerEncoderMaxCounts;

            m_DSLCD.println(DriverStationLCD.Line.kUser5, 1, "Kicker Strength = " + kickerStrength);
            m_DSLCD.updateLCD();
        }
    }

}
