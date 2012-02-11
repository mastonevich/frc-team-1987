/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.templates.commands.DriveWithJoystick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author team1987
 */
public class Chassis extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    //RobotDrive drive;
    Victor m_rightMotor = new Victor(RobotMap.rightMotor);
    Victor m_leftMotor = new Victor(RobotMap.leftMotor);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveWithJoystick()); //set the default command
    }
    
    public Chassis(){
        //drive = new RobotDrive(2,1);
        //drive.setSafetyEnabled(false);
    }
    
    public void straight(){
        //drive.arcadeDrive(1.0,0.0);
    }
    
    public void turnLeft(){
        //drive.arcadeDrive(0.0,1.0);
    }
    
    public void arcadeDrive(Joystick stick) {
        //drive.arcadeDrive(stick);
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
