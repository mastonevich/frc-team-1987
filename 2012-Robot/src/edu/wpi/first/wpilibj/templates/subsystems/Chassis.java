/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_DriveWithJoystick;

/**
 *
 * @author team1987
 */
public class Chassis extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    //RobotDrive drive;
    Victor m_rightMotor = new Victor(RobotMap.RIGHT_DRIVE_MOTOR_PORT);
    Victor m_leftMotor = new Victor(RobotMap.LEFT_DRIVE_MOTOR_PORT);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new H_DriveWithJoystick()); //set the default command
    }
    
    public Chassis(){
    }
    
    public void straight(){
        //drives the robot straight
    }
    
    public void turn(){
        //turns the robot a certain amount
    }

    public void setMotors(Joystick stick){
        double leftMotorSpeed;
        double rightMotorSpeed;
        leftMotorSpeed = -(-stick.getAxis(Joystick.AxisType.kY) + stick.getAxis(Joystick.AxisType.kX) + stick.getAxis(Joystick.AxisType.kZ));
        rightMotorSpeed = (-stick.getAxis(Joystick.AxisType.kY) - stick.getAxis(Joystick.AxisType.kX) - stick.getAxis(Joystick.AxisType.kZ));
        //SmartDashboard.putDouble("X:", stick.getAxis(Joystick.AxisType.kX));
        //SmartDashboard.putDouble("Y:", stick.getAxis(Joystick.AxisType.kY));
        //SmartDashboard.putDouble("Z:", stick.getAxis(Joystick.AxisType.kZ));
        
        //System.out.println("L Motor: " + leftMotorSpeed + " R Motor: " + rightMotorSpeed);
        
        m_leftMotor.set(leftMotorSpeed);
        m_rightMotor.set(rightMotorSpeed);
    }

}
