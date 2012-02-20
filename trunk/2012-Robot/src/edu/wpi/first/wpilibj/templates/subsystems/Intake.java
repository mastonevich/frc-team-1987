/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_AddBall;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_BallCheck;

/**
 *
 * @author team1987
 */
public class Intake extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    protected Relay m_intakeMotor;
    protected DigitalInput m_ballSensor;
    
    
    
    public Intake() {
        m_intakeMotor = new Relay(1, RobotMap.INTAKE_MOTOR_PORT);
        m_ballSensor = new DigitalInput(RobotMap.INTAKE_BALL_SENSOR);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new H_BallCheck());
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
    
    public boolean getBallSensor() {
        return !m_ballSensor.get();
    }
    
    public boolean ballCheck() {
        return (getBallSensor());
    }
    
    public boolean countCheck() {
        return (RobotMap.numBalls < RobotMap.maxNumBalls);
    }
}
