/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.highlevel;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.RobotMap;
import edu.wpi.first.wpilibj.templates.commands.lowlevel.L_BridgeArm_DOWN;
import edu.wpi.first.wpilibj.templates.commands.lowlevel.L_Chassis_DriveStraight;

/**
 *
 * @author team1987
 */
public class H_MiddleAuto extends CommandGroup {
    
    public H_MiddleAuto() {
        
        RobotMap.numBalls = 2;
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
        
        addParallel(new H_ShooterPreset(RobotMap.KEY_MOTOR_SPEED, RobotMap.KEY_MOTOR_ANGLE));
        addSequential(new H_elevatorShooterFeed());
        //addSequential(new H_elevatorShooterFeed());
        addSequential(new L_Chassis_DriveStraight(2, -1));
        addSequential(new L_BridgeArm_DOWN());
        

    }
}
