
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_BallCheck;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_ShooterPreset;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_elevatorShooterFeed;
import edu.wpi.first.wpilibj.templates.commands.lowlevel.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
        
    private Joystick stick;
    private JoystickButton trigger;
    private JoystickButton bttn11; 
    private JoystickButton bttn12;
    private JoystickButton bttn2;
    private JoystickButton bttn3;
    private JoystickButton bttn4;
    private JoystickButton bttn5;
    private JoystickButton bttn6;
  
    
    private Joystick stick2;
    private JoystickButton S2trigger;
    private JoystickButton S2bttn8;
    private JoystickButton S2bttn10;
    private JoystickButton S2bttn12;
    
    
    
    InternalButton button1 = new InternalButton();
    InternalButton button2 = new InternalButton();
    InternalButton button3 = new InternalButton();
    InternalButton button4 = new InternalButton();
    InternalButton button5 = new InternalButton();
   
    
    public OI(){
        
        stick = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);
        stick2 = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);

        
        
        
        S2trigger = new JoystickButton(stick2, Joystick.ButtonType.kTop.value);
        S2bttn8 = new JoystickButton(stick2, 8);
        S2bttn10 = new JoystickButton(stick2, 10);
        S2bttn12 = new JoystickButton(stick2, 12);
        
        bttn11 = new JoystickButton(stick, 11);
        bttn12 = new JoystickButton(stick, 12);
        bttn2 = new JoystickButton(stick, 2);
        bttn3 = new JoystickButton(stick, 3);
        bttn4 = new JoystickButton(stick, 4);
        bttn5 = new JoystickButton(stick, 5);
        bttn6 = new JoystickButton(stick, 6);


        S2trigger.whenPressed(new H_elevatorShooterFeed());
        S2bttn8.whenPressed(new H_ShooterPreset(RobotMap.FENDER_MOTOR_SPEED, RobotMap.FENDER_MOTOR_ANGLE));
        S2bttn10.whenPressed(new H_ShooterPreset(RobotMap.KEY_MOTOR_SPEED, RobotMap.KEY_MOTOR_ANGLE));
        S2bttn12.whenPressed(new H_ShooterPreset(RobotMap.LANE_MOTOR_SPEED, RobotMap.LANE_MOTOR_ANGLE));
        
        bttn11.whenPressed(new L_Shooter_PIDSet(false));
        bttn12.whenPressed(new L_Shooter_PIDSet(true));
        
        bttn2.whenPressed(new H_BallCheck());
      
        bttn4.whenPressed(new L_Intake_In());
        bttn6.whenPressed(new L_Intake_Off());
  
        bttn5.whenPressed(new L_Elevator_Stop());
        bttn3.whenPressed(new L_Elevator_Up());
       
    }
    
    public Joystick getJoystick(){
        return stick;
    }
    
    public Joystick getJoystick2() {
        return stick2;
    }
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // Another type of button you can create is a DigitalIOButton, which is
    // a button or switch hooked up to the cypress module. These are useful if
    // you want to build a customized operator interface.
    // Button button = new DigitalIOButton(1);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

/*********** Tee hee hee......
 *******************/