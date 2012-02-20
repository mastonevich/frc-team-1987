package edu.wpi.first.wpilibj.templates;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    
     //MISC
     public static final int BATTERY_VOLTAGE_PORT = 8;
     //Elevator
     public static final int ELEVATOR_MOTOR_PORT = 2;
     public static final int ELEVATOR_BOTTOM_SENSOR_PORT = 10;
     public static final int ELEVATOR_TOP_SENSOR_PORT = 11;
     
     
     //Shooter
     public static final int SHOOTER_ANGLE_MOTOR_PORT = 6;
     public static final int SHOOTER_TOP_MOTOR_PORT = 3;
     public static final int SHOOTER_BOTTOM_MOTOR_PORT = 4;
     public static final int SHOOTER_POT_PORT = 1;
     public static final float SHOOTER_MAX_SPEED = -1;
     public static final float SHOOTER_MIN_SPEED = 0;
     public static final int SHOOTER_MOTOR_SENSOR_PORT = 12;
     public static final int SHOOTER_ULTRASONIC_SENSOR_PORT = 2;

     
     public static final int MAX_ANGLE_UP = 90;
     public static final int MAX_ANGLE_DOWN = 0;
     public static final int MAX_POT_UP = 967;
     public static final int MAX_POT_DOWN = 617;
     
     public static final float MAX_JOY_THROTTLE_UP = -1;
     public static final float MAX_JOY_THROTTLE_DOWN = 1;
     
     //Intake
     public static final int INTAKE_MOTOR_PORT = 3;
     public static final int INTAKE_BALL_SENSOR = 8;
     
     
     //Chassis
     public static final int LEFT_DRIVE_MOTOR_PORT = 1;
     public static final int RIGHT_DRIVE_MOTOR_PORT = 2;
     
     //BridgeArm
     public static final int BRIDGE_DOWN_SWITCH = 13;
     public static final int BRIDGE_UP_SWITCH = 14;
     public static final int BRIDGE_MOTOR_PORT = 5;
     
     //Playmate TEE HEE :P
     public static final int RIGHT_JOYSTICK_PORT = 1;
     public static final int LEFT_JOYSTICK_PORT = 2;
     
     //GLOBAL VARIABLES
     public static final int maxNumBalls = 2;
     public static int numBalls;
     
     //Presets
     public static final double LEFT_STRAIGHT_MOTOR_SPEED = 0.5;
     public static final double RIGHT_STRAIGHT_MOTOR_SPEED = 0.5;
     
     public static final double FENDER_MOTOR_SPEED = -.537;
     public static final double KEY_MOTOR_SPEED = -.642;
     public static final double LANE_MOTOR_SPEED = -1.0;
     public static final int FENDER_MOTOR_ANGLE = 80;
     public static final int KEY_MOTOR_ANGLE = 69;
     public static final int LANE_MOTOR_ANGLE = 57;
     
     
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
}