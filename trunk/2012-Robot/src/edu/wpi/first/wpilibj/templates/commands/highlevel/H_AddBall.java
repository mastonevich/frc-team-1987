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
public class H_AddBall extends CommandBase {
    
    public H_AddBall() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        elevator.setPreviousValue(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        //System.out.println("RUNNING ADD BALL");
        elevator.up();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return elevator.ballAdded();
    }

    // Called once after isFinished returns true
    protected void end() {
        elevator.stop();
        RobotMap.numBalls++;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
