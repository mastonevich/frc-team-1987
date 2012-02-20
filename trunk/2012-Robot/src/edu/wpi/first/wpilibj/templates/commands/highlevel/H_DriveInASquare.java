/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates.commands.highlevel;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.templates.commands.lowlevel.L_Chassis_DriveStraight;
import edu.wpi.first.wpilibj.templates.commands.lowlevel.L_Chassis_Turn;

/**
 *
 * @author team1987
 */
public class H_DriveInASquare extends CommandGroup {
    
    public H_DriveInASquare() {
        addSequential(new L_Chassis_DriveStraight(1, 1));
        addSequential(new L_Chassis_Turn(1));
        addSequential(new L_Chassis_DriveStraight(1, 1));
        addSequential(new L_Chassis_Turn(1));
        addSequential(new L_Chassis_DriveStraight(1, 1));
        addSequential(new L_Chassis_Turn(1));
        addSequential(new L_Chassis_DriveStraight(1, 1));
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
    }
}
