/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.highlevel;

import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author team1987
 */
public class H_ShooterPreset extends CommandBase {
    private double m_angle;
    private double m_speed;
    
    public H_ShooterPreset(double speed, double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(shooter);
        this.m_angle = angle;
        this.m_speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {   
        shooter.setPID(true);
        shooter.setTrajectory(m_angle, m_speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
