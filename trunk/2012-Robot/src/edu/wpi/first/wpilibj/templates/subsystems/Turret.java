/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.MoveTurret;


/**
 *
 * @author team1987
 */
public class Turret extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    protected AnalogChannel  m_POT;
    protected SpeedController m_TMOTOR;
    protected static int MAX_RIGHT = 860;
    protected static int MAX_LEFT = 60;
    protected PIDController m_turretPID;
    
    
    
    public Turret() {
        m_POT = new  AnalogChannel(1,1);
        m_TMOTOR = new Jaguar(6);   
        m_turretPID = new PIDController(-0.01, -0.001, 0, m_POT, m_TMOTOR, 0.05);
        m_turretPID.setInputRange(100, 800);
        m_turretPID.setOutputRange(-0.5, 0.5);
        //m_turretPID.setTolerance(20);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new MoveTurret());
        //set the default command
    }
    
    public void setTurretPosition(double setPoint){
        m_turretPID.setSetpoint(setPoint);
    }
    
    public void setPID(boolean status){
        if(status)
            m_turretPID.enable();
        else
            m_turretPID.disable();
    }
    
    public int getTurretPosition() {
        return m_POT.getValue();
    }
    
    public boolean isTargeted() {
        return m_turretPID.onTarget();
    }
    
    public void moveTurret(double speed) {
        m_turretPID.disable();
        if(!((m_POT.getValue() > MAX_RIGHT) || (m_POT.getValue() < MAX_LEFT))){
            m_TMOTOR.set(speed);
            System.out.println("Moving NORMAL; POT: " + m_POT.getValue());
        }
        else{
            if((m_POT.getValue() >= MAX_RIGHT) && (speed < 0)){
                m_TMOTOR.set(speed);
                System.out.println("Moving LEFT; POT: " + m_POT.getValue());
            }
            else if((m_POT.getValue() <= MAX_LEFT) && (speed > 0)){
                System.out.println("Moving RIGHT; POT: " + m_POT.getValue());
                m_TMOTOR.set(speed);
            }
            else{
                m_TMOTOR.set(0);
            }
            
        }
           
    }
    
    
    public void print(){
        SmartDashboard.putInt("POT:", getTurretPosition());
        SmartDashboard.putDouble("Target:", m_turretPID.getSetpoint());
    }
}
