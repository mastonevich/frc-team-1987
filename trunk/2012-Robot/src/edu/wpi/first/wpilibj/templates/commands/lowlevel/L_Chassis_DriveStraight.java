/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.lowlevel;

import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author team1987
 */
public class L_Chassis_DriveStraight extends CommandBase{
    private double m_timeout;
    private int m_dir;
    
    public L_Chassis_DriveStraight(double timeout, int dir) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        m_timeout = timeout;
        m_dir = dir;
        requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(m_timeout);
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        System.out.println("DRIVING BACKWARDS!" + m_dir);
        chassis.straight(m_dir);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
