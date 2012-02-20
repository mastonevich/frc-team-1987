/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.highlevel;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.lowlevel.L_BridgeArm_DOWN;
import edu.wpi.first.wpilibj.templates.commands.lowlevel.L_BridgeArm_UP;

/**
 *
 * @author team1987
 */
public class H_BridgeArm_ChangePosition extends CommandBase {
    
    Command toUP;
    Command toDOWN;
    
    public H_BridgeArm_ChangePosition() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        toUP = new L_BridgeArm_UP();
        toDOWN = new L_BridgeArm_DOWN();
        requires(bridgeArm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (oi.getJoystick().getRawButton(8))
            toUP.start();
        if (oi.getJoystick().getRawButton(7))
            toDOWN.start();
        
        SmartDashboard.putBoolean("Bottom Switch", bridgeArm.getDownSwitch());
        SmartDashboard.putBoolean("Top Switch", bridgeArm.getUpSwitch());
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
