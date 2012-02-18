
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.TurretPIDEnable;
import edu.wpi.first.wpilibj.templates.commands.TurretPreset;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_ThrottleToShooterPos;
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
    private JoystickButton bttn3;
    private JoystickButton bttn4;
    private JoystickButton bttn5;
    //private JoystickButton leftNine;
    //private JoystickButton leftEleven;
    
    private Joystick stick2;
    
    InternalButton button1 = new InternalButton();
    InternalButton button2 = new InternalButton();
    InternalButton button3 = new InternalButton();
    InternalButton button4 = new InternalButton();
    InternalButton button5 = new InternalButton();
   
    
    
    public OI(){
        stick = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);
        stick2 = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);
        trigger = new JoystickButton(stick, Joystick.ButtonType.kTop.value);
        bttn11 = new JoystickButton(stick, 11);
        bttn12 = new JoystickButton(stick, 12);
        bttn3 = new JoystickButton(stick, 3);
        bttn4 = new JoystickButton(stick, 4);
        bttn5 = new JoystickButton(stick, 5);
        //leftNine = new JoystickButton(stick, Joystick.ButtonType.kNumButton.value);
        //leftEleven = new JoystickButton(stick, Joystick.ButtonType.kNumButton.value);  
        trigger.whenPressed(new L_Intake_In());
        trigger.whenReleased(new L_Intake_Off());
        
        
        bttn11.whenPressed(new L_Shooter_PIDSet(false));
        bttn12.whenPressed(new L_Shooter_PIDSet(true));
        
        bttn5.whenPressed(new L_Elevator_Up());
        bttn3.whenPressed(new L_Elevator_Down());
        bttn4.whenPressed(new L_Elevator_Stop());
        
        //SmartDashboard.putData("GO!", button3);
        //button3.whenPressed(new TurretPreset(1));
        
        //SmartDashboard.putDouble("POINT", 560);
        
        //SmartDashboard.putDouble("Shooter Angle", 45);
        
        //SmartDashboard.putData("Set ANGLE", button2);
        //button2.whenPressed(new L_Shooter_SDButtonAngle());
        //button2.whenPressed(new H_ThrottleToShooterPos());
        
        //SmartDashboard.putData("Enable", button1);
        //button1.whenPressed(new L_Shooter_PIDSet(true));
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