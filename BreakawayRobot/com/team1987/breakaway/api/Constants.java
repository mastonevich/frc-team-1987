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

    //Buttons
    public static final int c_kickerRightButton = 1;
    public static final int c_combineOffRightButton = 2;
    public static final int c_combineReverseRightButton = 4;
    public static final int c_combineForwardRightButton = 5;
    public static final int c_LanceRaiseLeftButton = 7;
    public static final int c_winchWindLeftButton = 8;
    public static final int c_winchUnwindLeftButton = 9;
    public static final int c_LanceRetractorLeftButton = 10;
    public static final int c_LanceExtenderLeftButton = 11;
    //Digital Channels
    public static final int c_leftDriveEncoderDigitalChannel1 = 1;
    public static final int c_leftDriveEncoderDigitalChannel2 = 2;
    public static final int c_rightDriveEncoderDigitalChannel1 = 3;
    public static final int c_rightDriveEncoderDigitalChannel2 = 4;
    public static final int c_kickerEncoderDigitalChannel1 = 5;
    public static final int c_kickerEncoderDigitalChannel2 = 6;
    public static final int c_kickerWinderGearToothDigitalChannel = 7;
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
    //Kicker Winder
    public static final double c_kickerStopWinding = 0;
    public static final double c_kickerReturningSpeed = .15;
    public static final double c_kickerWindingSpeed = .15;
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
    public static final int c_kickerReleased = 2;
    public static final int c_kickerReturning = 3;
    public static final int c_kickerLocked = 4;
    public static final int c_kickerWinding = 5;
    //Kicker Print Messages
    public static final String c_strKickerReady = "Kicker Ready";
    public static final String c_strKickerKicking = "Kicker Kicking";
    public static final String c_strKickerReleased = "Kicker Released";
    public static final String c_strKickerReturning = "Returning";
    public static final String c_strKickerLocked = "Kicker Locked";
    public static final String c_strKickerWinding = "Kicker Winding";
    //Random Constants (delays, etc)
    public static final int c_kickerDelay = 1;
    public static final int c_kickerSolenoidDelay = 2;
    public static final int c_kickerEncoderMaxCounts = 50;
    public static final double c_LanceExtendTime = 3;
    public static final int c_gearToothLimit = 6;
}

