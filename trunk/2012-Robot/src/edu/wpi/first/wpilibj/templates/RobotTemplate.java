/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.templates.commands.CommandBase;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_DriveInASquare;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_MiddleAuto;
import edu.wpi.first.wpilibj.templates.commands.highlevel.H_ShooterMain;
import edu.wpi.first.wpilibj.templates.commands.lowlevel.L_Elevator_GetBallCount;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {

    Command autonomousCommand;
    Command printTurretPOT;
    Command printShooterData;
    Command printBallCount;
    

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        autonomousCommand = new H_MiddleAuto();
        printShooterData = new H_ShooterMain();
        printBallCount = new L_Elevator_GetBallCount();
        SmartDashboard.putData(Scheduler.getInstance());

        // Initialize all subsystems
        CommandBase.init();
    }
    public void autonomousInit() {
        // schedule the autonomous command (example)
        autonomousCommand.start();
        //printShooterData.start();
        printBallCount.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
	// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        autonomousCommand.cancel();
        printShooterData.cancel();
        printBallCount.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        printShooterData.start();
        printBallCount.start();
    }
    
    public void updateStatus(){
        //CommandBase.ballIntake.updateStatus(); //updateStatus not made, but is a fancy print statement :P
        //CommandBase.shooter.updateStatus();
    }
}
