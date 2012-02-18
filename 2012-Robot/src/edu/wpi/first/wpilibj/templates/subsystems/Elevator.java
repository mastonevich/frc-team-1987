/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.lowlevel.L_Elevator_Stop;


/**
 *
 * @author team1987
 */
public class Elevator extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    protected Relay m_elevatorMotor;
    
    public Elevator(){
        m_elevatorMotor = new Relay(RobotMap.ELEVATOR_MOTOR_PORT);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new L_Elevator_Stop());
    }
    
    public void up(){
        m_elevatorMotor.set(Relay.Value.kForward);
    }
    
    public void stop(){
        m_elevatorMotor.set(Relay.Value.kOff);
    }
      
    public void down(){
        m_elevatorMotor.set(Relay.Value.kReverse);
    }
}
