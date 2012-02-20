/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.highlevel;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;

/**
 *
 * @author team1987
 */
public class H_elevatorShooterFeed extends CommandBase {
    
    Command lowerBall;
      
    public H_elevatorShooterFeed() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        lowerBall = new H_elevator_lowerBall();
        requires(elevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        elevator.setPreviousValue(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        elevator.up();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return elevator.ballRemoved();
    }

    // Called once after isFinished returns true
    protected void end() {
        elevator.decrementBallCount();
        elevator.stop();
        if(RobotMap.numBalls >= 1)
            lowerBall.start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
