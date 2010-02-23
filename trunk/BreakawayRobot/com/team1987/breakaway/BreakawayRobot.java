/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package com.team1987.breakaway;

import com.team1987.breakaway.api.Constants;

import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.camera.AxisCameraException;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.image.ColorImage;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GearTooth;
import edu.wpi.first.wpilibj.Watchdog;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class BreakawayRobot extends IterativeRobot {
    //Digital Inputs

    DigitalInput m_kickerSolenoidExtended;
    DigitalInput m_kickerSolenoidReturned;
    DigitalInput m_LanceLowered;
    DigitalInput m_LanceExtended;
    DigitalInput m_kickerWinderEmergencyStop;
    //Drive Control
    RobotDrive m_robotDrive;		// robot will use PWM 1-4 for drive motors
    //Driver Station Objects
    DriverStation m_DS;
    DriverStationEnhancedIO m_DSEIO;
    DriverStationLCD m_DSLCD;
    //Encoders
    Encoder m_leftDriveEncoder;
    Encoder m_rightDriveEncoder;
    Encoder m_kickerEncoder;
    GearTooth m_kickerWinderGearTooth;
    //Joysticks
    Joystick m_rightStick;			// joystick 1 (arcade stick or right tank stick)
    Joystick m_leftStick;			// joystick 2 (tank left stick)
    Compressor m_compressor;
    //Relays
    Relay m_LanceExtenderRelay;
    Relay m_combineRelay;
    //Solenoids
    Solenoid m_LanceRaiseSolenoidIn;
    Solenoid m_LanceRaiseSolenoidOut;
    Solenoid m_herderSolenoidsIn;
    Solenoid m_herderSolenoidsOut;
    Solenoid m_kickerSolenoidIn;
    Solenoid m_kickerSolenoidOut;
    //Timers
    Timer m_LanceExtenderTimer;
    Timer m_kickerSolenoidDelayTimer;
    Timer m_kickerDelayTimer;
    //Variables
    boolean kickerLowTrajectory;
    boolean LanceExtendTimerLock;
    boolean kickerDelayLock;
    double kScoreThreshold;
    int kickerState;
    int kickerStrength;
    int kickerEncoderCountLimit;
    int printedKickerState;
    String strKickerState;
    //Victors
    Victor m_kickerWinder;
    Victor m_winch;
    AxisCamera cam;
    TrackerDashboard trackerDashboard;

    public BreakawayRobot() {

        System.out.println("Instantiating Breakaway robot");


        //Watchdog Config
        Watchdog.getInstance().setExpiration(1);
        Watchdog.getInstance().feed();

        //Instantiating driver station, analogIO card, and driver station message display
        m_DS = DriverStation.getInstance();
        m_DSEIO = m_DS.getEnhancedIO();
        m_DSLCD = DriverStationLCD.getInstance();


        // Create a standard right/left robot drive on PWMS 1, 2, 3, and 4
        m_robotDrive = new RobotDrive(Constants.c_leftDriveMotor1PWMChannel,
                Constants.c_leftDriveMotor2PWMChannel,
                Constants.c_rightDriveMotor1PWMChannel, Constants.c_rightDriveMotor2PWMChannel);

        // Define joysticks being used at USB port #1 and USB port #2 on the Drivers Station
        m_rightStick = new Joystick(Constants.c_joystickRightPort);
        m_leftStick = new Joystick(Constants.c_joystickLeftPort);

        //Instantiate Compressor using digital channel to monitor pressure and spike channel to activate compressor
        m_compressor = new Compressor(Constants.c_compressorPressureSwitchDigitalChannel,
                Constants.c_compressorSpikeChannel);

        //Instantiate solenoids using solenoid channels
        m_LanceRaiseSolenoidIn = new Solenoid(Constants.c_LanceRaiseSolenoidInChannel);
        m_LanceRaiseSolenoidOut = new Solenoid(Constants.c_LanceRaiseSolenoidOutChannel);
        m_herderSolenoidsIn = new Solenoid(Constants.c_herderSolenoidsInChannel);
        m_herderSolenoidsOut = new Solenoid(Constants.c_herderSolenoidsOutChannel);
        m_kickerSolenoidIn = new Solenoid(Constants.c_kickerSolenoidInChannel);
        m_kickerSolenoidOut = new Solenoid(Constants.c_kickerSolenoidOutChannel);

        //Instantiate victors using PWM channels
        m_kickerWinder = new Victor(Constants.c_kickerWinderPWMChannel);
        m_winch = new Victor(Constants.c_winchPWMChannel);

        //Instantiate Relays using a spike channel and declaring valid directions
        m_LanceExtenderRelay = new Relay(Constants.c_LanceExtenderSpikeChannel,
                Relay.Direction.kBoth);
        m_combineRelay = new Relay(Constants.c_combineSpikeChannel, Relay.Direction.kBoth);

        //Instantiate digital inputs using digital channels
        m_kickerSolenoidExtended = new DigitalInput(Constants.c_kickerSolenoidExtendedDigitalChannel);
        m_kickerSolenoidReturned = new DigitalInput(Constants.c_kickerSolenoidReturnedDigitalChannel);
        m_LanceLowered = new DigitalInput(Constants.c_LanceRaisedDigitalChannel);
        m_LanceExtended = new DigitalInput(Constants.c_LanceExtendedDigitalChannel);
        m_kickerWinderEmergencyStop = new DigitalInput(
                Constants.c_kickerWinderEmergencyStopDigitalChannel);

        //Instantiate Encoders using digital channels
        m_leftDriveEncoder = new Encoder(Constants.c_leftDriveEncoderDigitalChannel1,
                Constants.c_leftDriveEncoderDigitalChannel2);
        m_rightDriveEncoder = new Encoder(Constants.c_rightDriveEncoderDigitalChannel1,
                Constants.c_rightDriveEncoderDigitalChannel2);
        m_kickerEncoder = new Encoder(Constants.c_kickerEncoderDigitalChannel1,
                Constants.c_kickerEncoderDigitalChannel2);
        m_kickerWinderGearTooth = new GearTooth(Constants.c_kickerWinderGearToothDigitalChannel);

        //Instantiate Timers
        m_LanceExtenderTimer = new Timer();
        m_kickerSolenoidDelayTimer = new Timer();
        m_kickerDelayTimer = new Timer();

        //Instantiate tracker dashboard for camera
        trackerDashboard = new TrackerDashboard();

        //Initialize Variables
        LanceExtendTimerLock = false;
        kScoreThreshold = .01;
        kickerDelayLock = false;
    }

    public void robotInit() {
        Watchdog.getInstance().setExpiration(1);
        Watchdog.getInstance().feed();

        m_compressor.start();
        m_herderSolenoidsIn.set(true);
        m_herderSolenoidsOut.set(false);
        m_kickerSolenoidIn.set(true);
        m_kickerSolenoidOut.set(false);

        m_kickerWinder.set(Constants.c_kickerStopWinding);

        m_LanceExtenderRelay.set(Relay.Value.kOff);
        m_combineRelay.set(Relay.Value.kReverse);

        m_leftDriveEncoder.start();
        m_rightDriveEncoder.start();
        m_kickerEncoder.start();
        m_kickerWinderGearTooth.start();
        if(!m_LanceExtended.get()) {
            m_LanceRaiseSolenoidIn.set(true);
            m_LanceRaiseSolenoidOut.set(false);
        }


        //instantiate digital input pins
        for(int i = 1; i < 10; i++) {
            try {
                m_DSEIO.setDigitalConfig(i, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            } catch(DriverStationEnhancedIO.EnhancedIOException ex) {
                ex.printStackTrace();
            }
        }
        cam = AxisCamera.getInstance();
        cam.writeResolution(AxisCamera.ResolutionT.k320x240);
        cam.writeBrightness(0);
        cam.writeColorLevel(0);

        m_LanceExtenderTimer.start();
        m_kickerSolenoidDelayTimer.start();
        m_kickerDelayTimer.start();

    }

    //Inits
    public void disabledInit() {
        Watchdog.getInstance().feed();
    }

    public void autonomousInit() {
        System.out.println("Feeding Watchdog - autonomousInit");
        Watchdog.getInstance().feed();
    }

    public void teleopInit() {
        System.out.println("Feeding Watchdog - teleopInit");
        Watchdog.getInstance().feed();
        kickerState = Constants.c_kickerReleased;
        if(!m_LanceExtended.get()) {
        }
    }

    //Periodics
    public void disabledPeriodic() {
        Watchdog.getInstance().feed();
        setKickerStrength();
        printMessages();
    }

    public void autonomousPeriodic() {
        // feed the user watchdog
        Watchdog.getInstance().feed();


    }

    public void teleopPeriodic() {

        // feed the user watchdog
        Watchdog.getInstance().feed();

        // drive with arcade style (use right stick)
        m_robotDrive.arcadeDrive(m_rightStick, false);

        //Change kicker trajectory
        try {
            kickerLowTrajectory = m_DSEIO.getDigital(Constants.c_kickerLowTrajectoryIOChannel);
        } catch(DriverStationEnhancedIO.EnhancedIOException ex) {
            ex.printStackTrace();
        }

        setKickerStrength();
        kicker(); //Sets the state of the kicker and performs actions depending on state
        combine();
        LanceRaise();
        LanceExtend();
        winchControl();
        camera();
        printMessages();

    }

    //Continuous WE DID NOT USE THIS LAST YEAR AND MAY NOT THIS YEAR
    public void disabledContinuous() {
    }

    public void autonomousContinuous() {
    }

    public void teleopContinuous() {
    }

    public void camera() {
        try {
            if(cam.freshImage()) {// && turnController.onTarget()) {
                ColorImage image = cam.getImage();
                Thread.yield();
                Target[] targets = Target.findCircularTargets(image);
                Thread.yield();
                image.free();
                if(targets.length == 0 || targets[0].m_score < kScoreThreshold) {
                    System.out.println("Target Not Found ");
                    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kMain6, 1,
                            "Target Not Found              ");
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
                    /*DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1,
                    "Pos X: " + targets[0].m_xPos);
                    DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser4, 1,
                    "Pos Y: " + targets[0].m_yPos);
                    DriverStationLCD.getInstance().updateLCD();
                     * */
                    m_DSLCD.println(DriverStationLCD.Line.kMain6, 1, "Target Found              ");
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

    public void combine() {
        if(m_rightStick.getRawButton(Constants.c_combineForwardRightButton)) {
            m_combineRelay.set(Relay.Value.kReverse);
        }
        else if(m_rightStick.getRawButton(Constants.c_combineOffRightButton)) {
            m_combineRelay.set(Relay.Value.kOff);
        }
        else if(m_rightStick.getRawButton(Constants.c_combineReverseRightButton)) {
            m_combineRelay.set(Relay.Value.kForward);
        }
    }

    public void LanceExtend() {
        if(m_leftStick.getRawButton(Constants.c_LanceExtenderLeftButton) && !m_LanceLowered.get()) {
            m_LanceExtenderRelay.set(Relay.Value.kReverse);
        }
        else if(m_leftStick.getRawButton(Constants.c_LanceRetractorLeftButton)) {
            m_LanceExtenderRelay.set(Relay.Value.kForward);
        }
        else {
            m_LanceExtenderRelay.set(Relay.Value.kOff);
        }
    }

    public void LanceRaise() {
        if(m_leftStick.getRawButton(Constants.c_LanceRaiseLeftButton)) {
            if(m_LanceLowered.get()) {
                m_LanceRaiseSolenoidIn.set(false);
                m_LanceRaiseSolenoidOut.set(true);
            }
            else if(!m_LanceLowered.get() && !m_LanceExtended.get()) {
                m_LanceRaiseSolenoidIn.set(true);
                m_LanceRaiseSolenoidOut.set(false);
            }
        }
    }

    public void kicker() {

        switch(kickerState) {
            case Constants.c_kickerReady:
                strKickerState = Constants.c_strKickerReady;
                if(m_rightStick.getRawButton(Constants.c_kickerRightButton)) {
                    if(kickerLowTrajectory) {
                        m_herderSolenoidsIn.set(false);
                        m_herderSolenoidsOut.set(true);
                    }
                    m_kickerSolenoidIn.set(false);
                    m_kickerSolenoidOut.set(true);
                    m_kickerSolenoidDelayTimer.reset();
                    kickerState = Constants.c_kickerKicking;
                }
                break;

            case Constants.c_kickerKicking:
                strKickerState = Constants.c_strKickerKicking;
                if(m_kickerSolenoidReturned.get()) {
                    kickerState = Constants.c_kickerReleased;
                }
                break;

            case Constants.c_kickerReleased:
                strKickerState = Constants.c_strKickerReleased;
                if(m_kickerSolenoidDelayTimer.get() > Constants.c_kickerSolenoidDelay) {
                    m_kickerWinderGearTooth.reset();
                    /*if(!kickerDelayLock) {
                    m_kickerDelayTimer.reset();
                    kickerDelayLock = true;
                    }*/
                    m_herderSolenoidsIn.set(true);
                    m_herderSolenoidsOut.set(false);
                    //if(m_kickerDelayTimer.get() > Constants.c_kickerDelay) {
                    m_kickerSolenoidIn.set(true);
                    m_kickerSolenoidOut.set(false);
                    kickerState = Constants.c_kickerReturning;
                    //kickerDelayLock = false;
                    //}
                }
                break;

            case Constants.c_kickerReturning:
                strKickerState = Constants.c_strKickerReturning;
                if(!m_kickerSolenoidExtended.get()) {
                    m_kickerEncoder.reset();
                    kickerState = Constants.c_kickerLocked;
                }
                else {
                    m_kickerWinder.set(Constants.c_kickerReturningSpeed);
                }
                break;

            case Constants.c_kickerLocked:
                strKickerState = Constants.c_strKickerLocked;
                m_kickerWinder.set(Constants.c_kickerWindingSpeed);
                kickerState = Constants.c_kickerWinding;
                break;

            case Constants.c_kickerWinding:
                strKickerState = Constants.c_strKickerWinding;
                if(m_kickerWinderGearTooth.get() >= kickerStrength) {
                    m_kickerWinder.set(Constants.c_kickerStopWinding);
                    kickerState = Constants.c_kickerReady;
                }
                /*if(m_kickerEncoder.get() > kickerEncoderCountLimit) {
                    m_kickerWinder.set(Constants.c_kickerStopWinding);
                    kickerState = Constants.c_kickerReady;
                }*/
                break;
        }
        if((!m_kickerWinderEmergencyStop.get()) || (m_kickerWinderGearTooth.get() >= Constants.c_gearToothLimit)) {
            m_kickerWinder.set(Constants.c_kickerStopWinding);
            kickerState = Constants.c_kickerReady;
        }
    }

    public void printMessages() {
        if(m_rightStick.getZ() > 0) {
            m_DSLCD.println(DriverStationLCD.Line.kUser2, 1, "KSIn=" + m_kickerSolenoidReturned.get() + "                    ");
            m_DSLCD.println(DriverStationLCD.Line.kUser3, 1, "KSOut=" + m_kickerSolenoidExtended.get() + "                    ");
        }
        else if(m_leftStick.getZ() > 0) {
            m_DSLCD.println(DriverStationLCD.Line.kUser2, 1,
                    "E Stop=" + m_kickerWinderEmergencyStop.get() + "                    ");
            m_DSLCD.println(DriverStationLCD.Line.kUser3, 1, "Kicker Encoder= " + m_kickerEncoder.get() + "                    ");
            m_DSLCD.println(DriverStationLCD.Line.kUser4, 1,
                    "Kicker Geartooth= " + m_kickerWinderGearTooth.get() + "                    ");
            m_DSLCD.println(DriverStationLCD.Line.kUser5, 1, "LDE=" + m_leftDriveEncoder.get() + "                    ");
            m_DSLCD.println(DriverStationLCD.Line.kUser6, 1, "RDE=" + m_rightDriveEncoder.get() + "                    ");

        }
        else {
            m_DSLCD.println(DriverStationLCD.Line.kUser2, 1, strKickerState + "                    ");
            m_DSLCD.println(DriverStationLCD.Line.kUser3, 1, "Kicker Strength=" + kickerStrength + "                    ");
            m_DSLCD.println(DriverStationLCD.Line.kUser4, 1, "Lance Extended=" + m_LanceExtended.get() + "                    ");
            m_DSLCD.println(DriverStationLCD.Line.kUser5, 1, "Lance Lowered=" + m_LanceLowered.get() + "                    ");
            m_DSLCD.println(DriverStationLCD.Line.kUser6, 1, "                              ");
        }
        m_DSLCD.updateLCD();
    }

    public void setKickerStrength() {
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

        kickerEncoderCountLimit = kickerStrength / 8 * Constants.c_kickerEncoderMaxCounts;

    }

    public void winchControl() {
        if(m_leftStick.getRawButton(Constants.c_winchWindLeftButton) && m_LanceLowered.get() && !m_LanceExtended.get()) {
            m_winch.set(-1.0);
        }
        else if(m_leftStick.getRawButton(Constants.c_winchUnwindLeftButton)) {
            m_winch.set(1.0);
        }
        else {
            m_winch.set(0);
        }
    }

}
