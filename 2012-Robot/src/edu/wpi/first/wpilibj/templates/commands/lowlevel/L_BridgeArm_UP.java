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
public class L_BridgeArm_UP extends CommandBase {
    
    public L_BridgeArm_UP() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(bridgeArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        bridgeArm.up();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return bridgeArm.getUpSwitch();
    }

    // Called once after isFinished returns true
    protected void end() {
        bridgeArm.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
