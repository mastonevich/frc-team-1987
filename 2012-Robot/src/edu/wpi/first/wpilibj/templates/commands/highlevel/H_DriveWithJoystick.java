/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.highlevel;
import edu.wpi.first.wpilibj.Joystick;
import  edu.wpi.first.wpilibj.templates.OI;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author team1987
 */
public class H_DriveWithJoystick extends CommandBase {
    
    public H_DriveWithJoystick() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(chassis); // reserve the chassis subsystem
    }

    // Called just before this Command runs the first time
    protected void initialize() {

        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        chassis.setMotors(oi.getJoystick());
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
