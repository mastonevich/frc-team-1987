/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author team1987
 */
public class BallIntake extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    protected Relay m_intakeMotor;
    
    
    
    public BallIntake() {
        m_intakeMotor = new Relay(RobotMap.intakeSpike);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void intake(){
        m_intakeMotor.set(Relay.Value.kForward);
    }
    
    public void outake(){
        m_intakeMotor.set(Relay.Value.kReverse);
    }
    
    public void off(){
        m_intakeMotor.set(Relay.Value.kOff);
    }
}
