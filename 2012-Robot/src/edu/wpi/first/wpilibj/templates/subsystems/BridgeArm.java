/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_BridgeArm_ChangePosition;

/**
 *
 * @author team1987
 */
public class BridgeArm extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Jaguar m_armMotor = new Jaguar(1, RobotMap.BRIDGE_MOTOR_PORT);
    DigitalInput m_armUpSwitch = new DigitalInput(RobotMap.BRIDGE_UP_SWITCH);
    DigitalInput m_armDownSwitch = new DigitalInput(RobotMap.BRIDGE_DOWN_SWITCH);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new H_BridgeArm_ChangePosition());
    }
    
    public void lowerBridge() {
        if (!m_armDownSwitch.get())
            m_armMotor.set(.5);            
    }
    
    public void raiseBridge() {
        if (!m_armUpSwitch.get())
            m_armMotor.set(-.5);            
    }
    
    public void changePosition() {
        if (m_armUpSwitch.get())
            m_armMotor.set(-.5);
        else if(m_armDownSwitch.get())
            m_armMotor.set(.5);
        else
            
            m_armMotor.set(.1);
    }
    
    public void up(){
        m_armMotor.set(1.0);
    }
    
    public void down(){
        m_armMotor.set(-1.0);
    }
    
   public void stop(){
        m_armMotor.set(0.0);
    }
    
    public boolean getDownSwitch() {
        return m_armDownSwitch.get();
    }
    
    public boolean getUpSwitch() {
        return m_armUpSwitch.get();
    }
}
