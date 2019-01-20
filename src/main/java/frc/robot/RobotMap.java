/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  
  //declares motor controllers used
  public static CANSparkMax mtDriveLeft1;
  public static CANSparkMax mtDriveLeft2;
  public static CANSparkMax mtDriveRight1;
  public static CANSparkMax mtDriveRight2;

  //declares encoders for motor controllers
  public static CANEncoder enDriveLeft1;
  public static CANEncoder enDriveLeft2;
  public static CANEncoder enDriveRight1;
  public static CANEncoder enDriveRight2;

  //declares robot drive variable
  public static DifferentialDrive drRobotDrive;

  public static void init() {

    //declares the digital ID of drive motors
    mtDriveLeft1 = new CANSparkMax(0, MotorType.kBrushless);
    mtDriveLeft2 = new CANSparkMax(1, MotorType.kBrushless);
    mtDriveRight1 = new CANSparkMax(2, MotorType.kBrushless);
    mtDriveRight2 = new CANSparkMax(3, MotorType.kBrushless);

    //declares digital ID of drive encoders
    enDriveLeft1 = new CANEncoder(mtDriveLeft1);
    enDriveLeft2 = new CANEncoder(mtDriveLeft2);
    enDriveRight1 = new CANEncoder(mtDriveRight1);
    enDriveRight2 = new CANEncoder(mtDriveRight2);

    
    
  }


}
