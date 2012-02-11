
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    public static final int JOYSTICK_PORT = 1;
    public static final int JOYSTICK_PORT2 = 2;
    private Joystick stick;
    private JoystickButton trigger;
    //private JoystickButton leftSeven; 
    //private JoystickButton leftNine;
    //private JoystickButton leftEleven;
    
    private Joystick stick2;
    
    InternalButton button1 = new InternalButton();
    InternalButton button2 = new InternalButton();
    InternalButton button3 = new InternalButton();
    InternalButton button4 = new InternalButton();
    InternalButton button5 = new InternalButton();
   
    
    
    public OI(){
        stick = new Joystick(JOYSTICK_PORT);
        stick2 = new Joystick(JOYSTICK_PORT2);
        trigger = new JoystickButton(stick, Joystick.ButtonType.kTop.value);
        //leftSeven = stick.getRawButton(7);
        //leftNine = new JoystickButton(stick, Joystick.ButtonType.kNumButton.value);
        //leftEleven = new JoystickButton(stick, Joystick.ButtonType.kNumButton.value);  
        trigger.whenPressed(new IntakeFWD());
        trigger.whenReleased(new IntakeOFF());
        
        SmartDashboard.putData("GO!", button3);
        button3.whenPressed(new TurretPreset(1));
        
        SmartDashboard.putDouble("POINT", 560);
        
        SmartDashboard.putData("Enable", button1);
        button1.whenPressed(new TurretPIDEnable());
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

/***********
 *******************/