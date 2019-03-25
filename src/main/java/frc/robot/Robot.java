/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Subsystem_Compressor_Power;
import frc.robot.subsystems.Subsystem_Drive;
import frc.robot.subsystems.Subsystem_Intake;
import frc.robot.subsystems.Subsystem_RGBLED_CAN;
import frc.robot.subsystems.Subsystem_Tower_Lift;
import frc.robot.subsystems.Subsystem_Tower_Rotation;
import frc.robot.subsystems.Subsystem_Winch;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //declares subsystem variables so commands can access subsystems
  public static Subsystem_Drive drive = new Subsystem_Drive();
  public static Subsystem_Compressor_Power compressor = new Subsystem_Compressor_Power();
  public static Subsystem_Tower_Rotation tower = new Subsystem_Tower_Rotation();
  public static Subsystem_Intake intake = new Subsystem_Intake();
  public static Subsystem_Winch winch = new Subsystem_Winch();
  public static Subsystem_Tower_Lift towerLift = new Subsystem_Tower_Lift();
  public static Subsystem_RGBLED_CAN rgbLedController = new Subsystem_RGBLED_CAN();
  public static OI oi;

  //creates a sendable chooser -- isnt used at all
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {

    //resets the encoder on initialize
    Robot.tower.resetEncoder();
    oi = new OI();
    //m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    // chooser.addOption("My Auto", new MyAutoCommand());
    // SmartDashboard.putData("Auto mode", m_chooser);
    SmartDashboard.putBoolean("Tower Level 1", towerLift.bTowerLowerIsUp);
    SmartDashboard.putBoolean("Tower Level 2", towerLift.bTowerUpperIsUp);
    SmartDashboard.putBoolean("Intake", intake.bWristIsDown);
    SmartDashboard.putBoolean("J Hook", intake.bHatchGrabbed);
    SmartDashboard.putBoolean("Gear", drive.bIsLow);
    Robot.rgbLedController.RGBledCAN();

    //starts camera stream
    CameraServer.getInstance().startAutomaticCapture();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    //puts robot into low gear once auto/sandstorm starts
    Robot.drive.LowGear();
    // m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    // if (m_autonomousCommand != null) {
    //   m_autonomousCommand.start();
    // }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    // Scheduler.getInstance().run();

    //in auto/sandstorm calls teleop
    teleopPeriodic();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
