/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.subsystems;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_ShooterMain;

/**
 *
 * @author team1987
 */
public class Shooter extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    protected AnalogChannel m_shooterPOT;
    protected PIDController m_shooterPID;
    protected DigitalInput m_motorSensor;
    protected AnalogChannel m_rangeFinder;
    //protected AnalogChannel m_batteryVoltage;
    
    //private int m_motorRotations;
    //private boolean wasTriggered = false;
    //private boolean currentValue = false;
    //private boolean previousValue = false;
    
    protected SpeedController m_angleMotor;
    protected SpeedController m_topMotor;
    protected SpeedController m_bottomMotor;
    private int throttleAngle;
    private int angleInPOT;
    private int angleInDegrees;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        //setDefaultCommand(new RunShooter());
        setDefaultCommand(new H_ShooterMain());
    }
    
    public Shooter() {
        m_topMotor = new Jaguar(RobotMap.SHOOTER_TOP_MOTOR_PORT);
        m_bottomMotor = new Jaguar(RobotMap.SHOOTER_BOTTOM_MOTOR_PORT);
        m_angleMotor = new Jaguar(RobotMap.SHOOTER_ANGLE_MOTOR_PORT);         
        m_shooterPOT = new  AnalogChannel(1, RobotMap.SHOOTER_POT_PORT);
        m_motorSensor = new DigitalInput(RobotMap.SHOOTER_MOTOR_SENSOR_PORT);
        m_rangeFinder = new AnalogChannel (1, RobotMap.SHOOTER_ULTRASONIC_SENSOR_PORT);
        //m_batteryVoltage = new AnalogChannel (RobotMap.BATTERY_VOLTAGE_PORT);
        
        //RobotMap.SHOOTER_MOTOR_SENSOR_PORT
        
        
        m_shooterPID = new PIDController(-0.025, -0.0001, 0, m_shooterPOT, m_angleMotor, 0.05);
        m_shooterPID.setInputRange(RobotMap.MAX_POT_DOWN, RobotMap.MAX_POT_UP);
        m_shooterPID.setOutputRange(-0.6, 0.6);
    
    }
   
    public void runShooter(double speedTOP, double speedBOT){
        m_topMotor.set(speedTOP);
        m_bottomMotor.set(speedBOT-.1);
    }
    
    public void runShooter(Joystick stick){
        double speed = (((RobotMap.SHOOTER_MAX_SPEED - RobotMap.SHOOTER_MIN_SPEED) * (stick.getThrottle() - RobotMap.MAX_JOY_THROTTLE_DOWN)) / (RobotMap.MAX_JOY_THROTTLE_UP - RobotMap.MAX_JOY_THROTTLE_DOWN));
        m_topMotor.set(speed);
        m_bottomMotor.set(speed-.1);
    }
    
    public double getShooterSpeed() {
        return m_topMotor.get();
    }
    
  
    public void updateStatus(){
        //SmartDashboard.putString(null, null);
    }
    
     public void setAngle(double angle) {
         if(angle < RobotMap.MAX_ANGLE_UP && angle > RobotMap.MAX_ANGLE_DOWN)
         {
            angleInPOT = (int) ((RobotMap.MAX_POT_UP - RobotMap.MAX_POT_DOWN) * (angle / (RobotMap.MAX_ANGLE_UP - RobotMap.MAX_ANGLE_DOWN)) + RobotMap.MAX_POT_DOWN);
            m_shooterPID.setSetpoint(angleInPOT);
         }
         else
             m_angleMotor.set(0);
    }
     
     //Gives CURRENT angle of the POT
     public double getAngle() {
         angleInDegrees = ((RobotMap.MAX_ANGLE_UP - RobotMap.MAX_ANGLE_DOWN) * (m_shooterPOT.getAverageValue() - RobotMap.MAX_POT_DOWN)) / (RobotMap.MAX_POT_UP - RobotMap.MAX_POT_DOWN);
         return angleInDegrees;
     }
     
     //Gives the angle of the converted Joystick Throttle.
     public double getTargetAngle() {
         return throttleAngle;
     }
     
     public double getRawPOTValue() {
         return m_shooterPOT.getValue();
     }
     
     public void setPID(boolean value) {
         if(value)
             m_shooterPID.enable();
         else
             m_shooterPID.disable();
     }
     
     public boolean getPID() {
         return m_shooterPID.isEnable();
     }
     
     public double getSP() {
         return m_shooterPID.getSetpoint();
     }
     
     public void throttleToShooter(Joystick stick) {
         throttleAngle = (int) (((RobotMap.MAX_ANGLE_UP - RobotMap.MAX_ANGLE_DOWN) * (stick.getThrottle() - RobotMap.MAX_JOY_THROTTLE_DOWN)) / (RobotMap.MAX_JOY_THROTTLE_UP - RobotMap.MAX_JOY_THROTTLE_DOWN));
         System.out.println("Throttle Angle: " + throttleAngle);
         setAngle(throttleAngle);      
         //m_shooterPID.enable();
     }
    
     /*
     public boolean getShooterSensor() {
         return !m_motorSensor.get();
     }
     
     public int getShooterRotations () {
         currentValue = getShooterSensor();
         if((currentValue != previousValue) && currentValue){
             m_motorRotations++;
             previousValue = currentValue;
         }
         else if(currentValue != previousValue)
             previousValue = false;
         return m_motorRotations;
     }
     */
     
     public double getRawUltrasonic() { 
         return m_rangeFinder.getAverageVoltage();
     }
     /*
     public double getBatteryVoltage() {  
         return m_batteryVoltage.getAverageVoltage();
     }
     
     public double getDistance() {
         return (m_rangeFinder.getAverageVoltage() / (m_batteryVoltage.getAverageVoltage() / 512));
     }
     */
     
     public void setTrajectory(double angle, double speed){
         setAngle(angle);    
         runShooter(speed, speed);         
     }

     
}
