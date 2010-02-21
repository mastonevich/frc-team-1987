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

    private Constants(){
    }

    public static final double c_stringPOT_max = 11;
    public static final double c_stringPOT_min = -11;

    public static final int c_joystickRightPort = 2;
    public static final int c_joystickLeftPort = 1;
    
    //Solenoids
    public static final int c_LanceRaiseSolenoidInChannel = 4;
    public static final int c_LanceRaiseSolenoidOutChannel = 3;
    public static final int c_herderSolenoidsInChannel = 6;
    public static final int c_herderSolenoidsOutChannel = 5;
    public static final int c_kickerSolenoidInChannel = 1;
    public static final int c_kickerSolenoidOutChannel = 2;

    //Relays
    public static final int c_LanceExtenderRelayChannel = 1;
    public static final int c_combineRelayChannel = 2;
    public static final int c_compressorRelayChannel = 3;

    public static final int c_stringPOTChannel = 2;

    //Digital Channels
    public static final int c_leftDriveEncoderDigitalChannel1 = 1;
    public static final int c_leftDriveEncoderDigitalChannel2 = 2;
    public static final int c_rightDriveEncoderDigitalChannel1 = 3;
    public static final int c_rightDriveEncoderDigitalChannel2 = 4;
    public static final int c_kickerEncoderDigitalChannel1 = 5;
    public static final int c_kickerEncoderDigitalChannel2 = 6;
    public static final int c_LanceRaisedDigitalChannel = 7;
    public static final int c_LanceReturnedDigitalChannel = 8;
    public static final int c_kickerSolenoidExtendedDigitalChannel = 9;
    public static final int c_kickerSolenoidReturnedDigitalChannel = 10;
    public static final int c_compressorPressureSwitchDigitalChannel = 11;
    public static final int c_kickerWinderGearToothChannel = 12;
    
    public static final int c_kickerEncoderMaxCounts = 0;

    public static final double c_LanceExtendTime = 3;

    //Motors
    public static final int c_leftDriveMotor1 = 1;
    public static final int c_leftDriveMotor2 = 2;
    public static final int c_rightDriveMotor1 = 3;
    public static final int c_rightDriveMotor2 = 4;
    public static final int c_kickerWinderChannel = 5;
    public static final int c_winchChannel = 6;
 
    //Kicker Winder
    public static final double c_kickerStopWinding = 0;
    public static final double c_kickerReturningSpeed = 1;
    public static final double c_kickerWindingSpeed = 1;

    //Buttons
    public static final int c_kickerRightButton = 1;
    public static final int c_winchStartLeftButton = 6;
    public static final int c_LanceRaiseLeftButton = 7;
    public static final int c_LanceExtenderLeftButton = 11;
    public static final int c_LanceRetractorLeftButton = 10;
    public static final int c_combineForwardRightButton = 5;
    public static final int c_combineOffRightButton = 2;
    public static final int c_combineReverseRightButton = 4;
    
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
    
    public static final int c_kickerLowTrajectoryIOChannel = 9;

    //Kicker States
    public static final int c_kickerReady = 0;
    public static final int c_kickerKicking = 1;
    public static final int c_kickerReleased = 2;
    public static final int c_kickerReturning = 3;
    public static final int c_kickerLocked = 4;
    public static final int c_kickerWinding = 5;

}

