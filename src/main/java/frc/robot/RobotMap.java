/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  
  //declares motor controllers used and digital ID of motor
  public static CANSparkMax mtDriveLeft1 = new CANSparkMax(1, MotorType.kBrushless);
  public static CANSparkMax mtDriveLeft2 = new CANSparkMax(2, MotorType.kBrushless);
  public static CANSparkMax mtDriveRight1 = new CANSparkMax(3, MotorType.kBrushless);
  public static CANSparkMax mtDriveRight2 = new CANSparkMax(4, MotorType.kBrushless);


  //declares encoders for motor controllers and motors connected to encoder
  public static CANEncoder enDriveLeft1 = new CANEncoder(mtDriveLeft1);
  public static CANEncoder enDriveLeft2 = new CANEncoder(mtDriveLeft2);
  public static CANEncoder enDriveRight1 = new CANEncoder(mtDriveRight1);
  public static CANEncoder enDriveRight2 = new CANEncoder(mtDriveRight2);

  //declares compressor port
  public static Compressor cmpRobotCompressor = new Compressor(0);

  //declares digital id for tower motor 
  public static WPI_TalonSRX mtTowerRotate = new WPI_TalonSRX(0);

  //declares port number of gear shift solenoid
  public static Solenoid slndGearShifter = new Solenoid(4);

  public static void init() {

    //reverses left1 because for some reason it is the only one
    // the does not go the same direction
    mtDriveLeft1.getInverted();
    
    
  }


}
