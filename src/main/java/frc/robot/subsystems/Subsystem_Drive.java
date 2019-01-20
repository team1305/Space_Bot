/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.Command_Drive_With_Joystick;

/**
 * Add your docs here.
 */
public class Subsystem_Drive extends Subsystem {

  //grabs drive motor information from RobotMap
  private final CANSparkMax mtLeft1 = RobotMap.mtDriveLeft1;
  private final CANSparkMax mtLeft2 = RobotMap.mtDriveLeft2;
  private final CANSparkMax mtRight1 = RobotMap.mtDriveRight1;
  private final CANSparkMax mtRight2 = RobotMap.mtDriveRight2;

  //graps drive encoder information from RobotMap
  private final CANEncoder enLeft1 = RobotMap.enDriveLeft1;
  private final CANEncoder enLeft2 = RobotMap.enDriveLeft2;
  private final CANEncoder enRight1 = RobotMap.enDriveRight1;
  private final CANEncoder enRight2 = RobotMap.enDriveRight2;

  //decalres a differential drive
  private DifferentialDrive drRobotDrive;

  public Subsystem_Drive() {

    //creates motor controller groups for left and right motors
    SpeedControllerGroup scgLeft = new SpeedControllerGroup(mtLeft1, mtLeft2);
    SpeedControllerGroup scgRight = new SpeedControllerGroup(mtRight1, mtRight2);

    //declares left and right motor groups used by differential drive
    DifferentialDrive drRobotDrive = new DifferentialDrive(scgLeft, scgRight);
  }

  @Override
  public void initDefaultCommand() {

    //unless interupted the default command will allow driver to drive with joystick
    setDefaultCommand(new Command_Drive_With_Joystick());
  }

  public void driveWithJoystick(Joystick stick) {

    //creates variables for joystick x and y values
    double zRotation = stick.getRawAxis(4)* -1;
    double xSpeed = stick.getY()* 1;

    //uses joystick
    drRobotDrive.curvatureDrive(xSpeed, zRotation, true);
  }

  public void driveStop() {

    drRobotDrive.arcadeDrive(0, 0);
  }
}
