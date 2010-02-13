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

    public static final int c_joystickRightPort = 1;
    public static final int c_joystickLeftPort = 2;
    public static final int c_compressorPressureSwitchChannel = 1;
    public static final int c_compressorRelayChannel = 1;
    public static final int c_hookAngleSolenoid1Channel = 1;
    public static final int c_hookAngleSolenoid2Channel = 8;
    public static final int c_stringPOTChannel = 2;
    public static final int c_victor1Channel = 5;
    public static final int c_hookExtenderChannel = 2;

    public static final double c_stringPOT_max = 4.5;
    public static final double c_stringPOT_min = .5;

    public static final int c_leftDriveMotor1 = 1;
    public static final int c_leftDriveMotor2 = 2;
    public static final int c_rightDriveMotor1 = 3;
    public static final int c_rightDriveMotor2 = 4;

    public static final int c_victorStop = 0;

    public static final int c_hookAngleButton = 7;
    public static final int c_hookExtenderButton = 11;
    public static final int c_hookRetractorButton = 10;

}

