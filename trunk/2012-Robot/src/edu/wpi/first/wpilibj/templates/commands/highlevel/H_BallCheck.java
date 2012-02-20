/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.highlevel;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author team1987
 */
public class H_BallCheck extends CommandBase {
    Command addball;
    
    public H_BallCheck() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        addball = new H_AddBall();
        requires(ballIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        ballIntake.intake();
        if (oi.getJoystick2().getRawButton(7))
            RobotMap.numBalls = 0;
        if (ballIntake.ballCheck() && ballIntake.countCheck()) {
            addball.start();
        }
        else if (!ballIntake.countCheck())
            ballIntake.off();
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
