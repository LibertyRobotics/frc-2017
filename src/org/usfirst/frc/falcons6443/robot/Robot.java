package org.usfirst.frc.falcons6443.robot;

import org.usfirst.frc.falcons6443.robot.commands.*;
import org.usfirst.frc.falcons6443.robot.subsystems.*;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import org.usfirst.frc.falcons6443.robot.commands.MoveByTime;
import org.usfirst.frc.falcons6443.robot.commands.TankDriveWithTriggers;
import org.usfirst.frc.falcons6443.robot.commands.TeleopMode;
import org.usfirst.frc.falcons6443.robot.subsystems.GearHolderSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.NavigationSystem;
import org.usfirst.frc.falcons6443.robot.subsystems.SimpleDriveTrainSystem;
import org.usfirst.frc.falcons6443.robot.utilities.CommandChooser;
import org.usfirst.frc.falcons6443.robot.utilities.Smashboard;

/**
 * ROBOTS DON'T QUIT!
 * The Robot class is FRC team 6443's implementation of WPIlib's IterativeRobot class.
 *
 * @author Christopher Medlin
 */
public class Robot extends IterativeRobot {

	// All the subsystems that the robot possesses
	// If a new subsystem is added, it must also be added to SimpleCommand.
	// From there the subsystem can be referred to from any command that inherits SimpleCommand.
	public static final DriveTrainSystem DriveTrain = new DriveTrainSystem();
	public static final GearHolderSystem GearHolder = new GearHolderSystem();
	public static final NavigationSystem Navigation = new NavigationSystem();
	public static final RopeClimberSystem RopeClimber = new RopeClimberSystem();
  
	public static OI oi;

	private Command autonomy;
	private Command teleop;
//	private SendableChooser<Command> teleOpChooser;
	private SendableChooser<Command> autonomyChooser;

	private CommandChooser teleOpChooser;

	/*
	 * Called when the robot first starts.
	 */
	@Override
	public void robotInit () {
		oi = new OI();
		autonomy = new MoveByTime(4, 0.5);
	}
	
	/*
	 * Called when the robot first enters disabled mode.
	 */
	@Override
	public void disabledInit () {
		
	}
	
	/*
	 * Called periodically when the robot is in disabled mode.
	 */
	@Override
	public void disabledPeriodic () {
		Scheduler.getInstance().run();
	}
	
	/*
	 * Called when the robot first enters autonomous mode.
	 */
	@Override
	public void autonomousInit () {
		/*
		autonomy = (Command) autonomyChooser.getSelected();

		if (autonomy != null) autonomy.start();
		*/
		if (autonomy != null) autonomy.start();
	}

	/*
	 * Called periodically when the robot is in autonomous mode.
	 */
	@Override
	public void autonomousPeriodic () {
		
		Scheduler.getInstance().run();
	}

	/*
	 * Called when the robot first enter teleop mode.
	 */
	@Override
	public void teleopInit () {
		if (autonomy != null) autonomy.cancel();

		teleop = Smashboard.getCommandChooserSelection(teleOpChooser);

		if (teleop !=  null) teleop.start();
	}

	/*
	 * Called periodically when the robot is in teleop mode.
	 */
	@Override
	public void teleopPeriodic () {
		Scheduler.getInstance().run();
	}
	
	/*
	 * Called periodically when the robot is in testing mode.
	 */
	@Override
	public void testPeriodic () {
		LiveWindow.run();
	}
}
