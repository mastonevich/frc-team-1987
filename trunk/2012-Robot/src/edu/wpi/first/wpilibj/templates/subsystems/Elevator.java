/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.DigitalInput;
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
    protected DigitalInput m_elevatorSensor;
    protected DigitalInput m_ballSensor;
    private boolean currentValue = false;
    private boolean previousValue = false;
    private int m_ballCount;
    
    public Elevator(){
        m_elevatorMotor = new Relay(RobotMap.ELEVATOR_MOTOR_PORT);
        m_elevatorSensor = new DigitalInput(RobotMap.ELEVATOR_BOTTOM_SENSOR_PORT);
        m_ballSensor = new DigitalInput (RobotMap.ELEVATOR_TOP_SENSOR_PORT);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new L_Elevator_Stop());
    }
    
    public void up(){
        m_elevatorMotor.set(Relay.Value.kReverse);
    }
    
    public void stop(){
        m_elevatorMotor.set(Relay.Value.kOff);
    }
      
    public void down(){
        m_elevatorMotor.set(Relay.Value.kForward);
    }
    
    public boolean getBottomSensor() {
        return !m_elevatorSensor.get();
    }
    
    public boolean getTopSensor() {
         return !m_ballSensor.get();
    }
    
    public void setPreviousValue(boolean value) {
        previousValue = value;
    }     
    
    /*Used to check if a ball has been added to the system. In order to function
     * correctly it is important that 'previousValue' is set to true when called.
     */
    
    public boolean ballAdded() {
          currentValue = !m_elevatorSensor.get();
          if((currentValue != previousValue) && currentValue){
             return true;
             //previousValue = currentValue;
         }
         else if(currentValue != previousValue)
             previousValue = false;
         return false;
     }
     
      /*Used to check if a ball has been added to the system. In order to function
     * correctly it is important that 'previousValue' is set to true when called.
     */
     public boolean ballRemoved() {
         currentValue = !m_ballSensor.get();
         if((currentValue != previousValue) && currentValue){
             return true;
             //previousValue = currentValue;
         }
         else if(currentValue != previousValue)
             previousValue = false;
         return false;
     }
     
     public void decrementBallCount() {
         RobotMap.numBalls--;
     }
     
}
