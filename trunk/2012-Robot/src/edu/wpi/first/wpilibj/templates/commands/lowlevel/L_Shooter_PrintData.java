/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.lowlevel;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author team1987
 */
public class L_Shooter_PrintData extends CommandBase {
    
    public L_Shooter_PrintData() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        //requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        SmartDashboard.putDouble("Current Angle:", shooter.getAngle());
        SmartDashboard.putDouble("Target Angle:", shooter.getTargetAngle());
        SmartDashboard.putDouble("Set Point: ", shooter.getSP());
        SmartDashboard.putDouble("Current Point: ", shooter.getRawPOTValue());
        SmartDashboard.putBoolean("  Shooter PID (ON/OFF)", shooter.getPID());
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
