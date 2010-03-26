/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team1987.breakaway.api;

/**
 *
 * @author Broncobot
 */
public final class Constants {
    //this combined with the final makes the class uninstantiable.

    private Constants() {
    }

    //Analog Channels
    public static final int c_kickerWinderRevSensorAnalogChannel = 7;
    //Right JS Buttons
    public static final int c_kickerRightButton = 1;
    public static final int c_combineOffRightButton1 = 4;
    public static final int c_combineOffRightButton2 = 5;
    public static final int c_combineReverseRightButton = 2;
    public static final int c_combineForwardRightButton = 3;
    public static final int c_herderTrajetoryRightButton = 7;
    //Left JS Buttons
    //public static final int c_LanceActivateLeftButton = 3;
    //public static final int c_LanceDeactivateLeftButton = 2;
    public static final int c_manualLanceExtendLeftButton = 3;
    public static final int c_manualLanceRetractLeftButton = 2;
    public static final int c_manualLanceRaiseLeftButton = 6;
    public static final int c_manualLanceLowerLeftButton = 7;
    public static final int c_winchWindLeftButton = 1;
    public static final int c_winchUnwindLeftButton1 = 4;
    public static final int c_winchUnwindLeftButton2 = 5;
    //Digital Channels
    public static final int c_leftDriveEncoderDigitalChannel1 = 1;
    public static final int c_leftDriveEncoderDigitalChannel2 = 2;
    public static final int c_rightDriveEncoderDigitalChannel1 = 3;
    public static final int c_rightDriveEncoderDigitalChannel2 = 4;
    public static final int c_kickerEncoderDigitalChannel1 = 5;
    public static final int c_kickerEncoderDigitalChannel2 = 6;
    public static final int c_ballDetectorDigitalChannel = 7;
    public static final int c_LanceExtendedDigitalChannel = 8;
    public static final int c_kickerSolenoidReturnedDigitalChannel = 10;
    public static final int c_kickerSolenoidExtendedDigitalChannel = 9;
    public static final int c_compressorPressureSwitchDigitalChannel = 11;
    public static final int c_LanceRaisedDigitalChannel = 12;
    public static final int c_kickerWinderEmergencyStopDigitalChannel = 13;
    //Enhanced IO Channel
    public static final int c_kickerLowTrajectoryIOChannel = 9;
    //Joystick Ports
    public static final int c_joystickRightPort = 2;
    public static final int c_joystickLeftPort = 1;
    //Motor PWM Channels
    public static final int c_leftDriveMotor1PWMChannel = 1;
    public static final int c_leftDriveMotor2PWMChannel = 2;
    public static final int c_rightDriveMotor1PWMChannel = 3;
    public static final int c_rightDriveMotor2PWMChannel = 4;
    public static final int c_kickerWinderPWMChannel = 5;
    public static final int c_winchPWMChannel = 6;
    //Solenoid Channels
    public static final int c_LanceRaiseSolenoidInChannel = 4;
    public static final int c_LanceRaiseSolenoidOutChannel = 3;
    public static final int c_herderSolenoidsInChannel = 6;
    public static final int c_herderSolenoidsOutChannel = 5;
    public static final int c_kickerSolenoidInChannel = 1;
    public static final int c_kickerSolenoidOutChannel = 2;
    //Spike Channels
    public static final int c_LanceExtenderSpikeChannel = 1;
    public static final int c_combineSpikeChannel = 2;
    public static final int c_compressorSpikeChannel = 3;
    //Kicker 8pos Switch
    public static final int c_kickerSwitchBits = 255;
    public static final int c_kickerSwitchPos1 = 1;
    public static final int c_kickerSwitchPos2 = 2;
    public static final int c_kickerSwitchPos3 = 4;
    public static final int c_kickerSwitchPos4 = 8;
    public static final int c_kickerSwitchPos5 = 16;
    public static final int c_kickerSwitchPos6 = 32;
    public static final int c_kickerSwitchPos7 = 64;
    public static final int c_kickerSwitchPos8 = 128;
    //Kicker States
    public static final int c_kickerReady = 0;
    public static final int c_kickerKicking = 1;
    public static final int c_kickerReturning = 2;
    public static final int c_kickerLocking = 3;
    public static final int c_kickerLocked = 4;
    public static final int c_kickerWinding = 5;
    //Kicker Print Messages
    public static final String c_strKickerReady = "Kicker Ready";
    public static final String c_strKickerKicking = "Kicker Kicking";
    public static final String c_strKickerReturning = "Returning";
    public static final String c_strKickerLocking = "Kicker Locking";
    public static final String c_strKickerLocked = "Kicker Locked";
    public static final String c_strKickerWinding = "Kicker Winding";
    //Random Constants (delays, etc)
    public static final int c_kickerTriggerDelay = 2;
    public static final double c_kickerDelay = .25;
    public static final int c_herderSolenoidDelay = 1;
    public static final double c_kickerWinderLockVoltage = .25; //Recently changed from .25
    public static final double c_kickerWinderMaxVoltage = 5 / 360 * 309;
    public static final double c_LanceExtendTime = 3;
    public static final int c_gearToothLimit = 6;
    public static final double c_kickerWinderVoltageTolerance = .1;
    public static final double c_kickerStopWinding = 0;
    public static final double c_kickerReturningSpeed = .2;
    public static final double c_kickerHomingSpeed = .07;
    public static final double c_kickerWindingSpeed = .6;
    public static final double c_autoDriveSpeed = -.7;
    public static final double c_autoCloseInSpeed = -.5;
    public static final double c_autoTurnSpeed = -.25;
    public static final double c_autoKickSequenceStopDelay = 1;
    public static final double c_autoECLimit = 1300; //Needs to be 4000ish, changed for now
    public static final double c_autoZone3FirstBallEncoderCount = 1050;
    public static final double c_autoZone3ConsecBallEncoderCount = 1300;
    public static final double c_autoZone1or2FirstBallEncoderCount = 500;
    public static final double c_autoZone1or2ConsecEncoderCount = 750;
    public static final double c_kickerLockFailTime = 1.5;
    public static final double c_kickerWinderHomingVoltage = 4.25;
}

