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
    
     //Elevator
     public static final int ELEVATOR_MOTOR_PORT = 2;
     //public static final int ELEVATOR_SENSOR_PORT = ;
     
     //Shooter
     public static final int SHOOTER_ANGLE_MOTOR_PORT = 6;
     public static final int SHOOTER_TOP_MOTOR_PORT = 3;
     public static final int SHOOTER_BOTTOM_MOTOR_PORT = 4;
     public static final int SHOOTER_POT_PORT = 1;
     //public static final int SHOOTER_PUNTER_PORT = ;
     //public static final int SHOOTER_TOP_SENSOR_PORT = ;
     //public static final int SHOOTER_BOTTOM_SENSOR_PORT = ;
     public static final int MAX_ANGLE_UP = 90;
     public static final int MAX_ANGLE_DOWN = 0;
     public static final int MAX_POT_UP = 590;
     public static final int MAX_POT_DOWN = 230;
     
     public static final float MAX_JOY_THROTTLE_UP = -1;
     public static final float MAX_JOY_THROTTLE_DOWN = 1;
     
     //Intake
     public static final int INTAKE_MOTOR_PORT = 3;
     
     //Chassis
     public static final int LEFT_DRIVE_MOTOR_PORT = 1;
     public static final int RIGHT_DRIVE_MOTOR_PORT = 2;
     
     //Playmate TEE HEE :P
     public static final int RIGHT_JOYSTICK_PORT = 1;
     public static final int LEFT_JOYSTICK_PORT = 2;
     
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;
}
