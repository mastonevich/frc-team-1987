/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.RobotMap;

/**
 *
 * @author team1987
 */
public class SetTurretPosition extends CommandBase {
    
    protected double SP = 460;
    
    public SetTurretPosition(double setPoint) {
        SP = setPoint;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(turret);
    }

    // Called just before this Command runs the first time
    protected void initialize() {    
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        turret.setTurretPosition(SP);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
        //turret.isTargeted()=-;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        //turret.disablePID();
    }
}
