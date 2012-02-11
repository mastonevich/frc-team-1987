/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.RunShooter;

/**
 *
 * @author team1987
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    protected SpeedController m_TMOTOR;
    protected SpeedController m_BMOTOR;
    
    
    public Shooter() {
        m_TMOTOR = new Jaguar(3);
        m_BMOTOR = new Jaguar(4);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new RunShooter());
    }
    
    public void runShooter(double speedTOP, double speedBOT){
        m_TMOTOR.set(speedTOP);
        m_BMOTOR.set(speedBOT);
    }
    
    public void updateStatus(){
        //SmartDashboard.putString(null, null);
    }
}
