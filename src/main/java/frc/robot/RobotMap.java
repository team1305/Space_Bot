/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  
  //declares sparkmax motor controllers used and digital ID of motor
  public static CANSparkMax mtDriveLeft1 = new CANSparkMax(1, MotorType.kBrushless);
  public static CANSparkMax mtDriveLeft2 = new CANSparkMax(2, MotorType.kBrushless);
  public static CANSparkMax mtDriveRight1 = new CANSparkMax(3, MotorType.kBrushless);
  public static CANSparkMax mtDriveRight2 = new CANSparkMax(4, MotorType.kBrushless);
  public static CANSparkMax mtWinch1 = new CANSparkMax(5, MotorType.kBrushless);
  public static CANSparkMax mtWinch2 = new CANSparkMax(6, MotorType.kBrushless);
  public static CANSparkMax mtIntake = new CANSparkMax(7, MotorType.kBrushless);
  public static CANSparkMax mtTowerLift = new CANSparkMax(8, MotorType.kBrushless);

  //declares encoders for motor controllers and motors connected to encoder
  public static CANEncoder enDriveLeft1 = new CANEncoder(mtDriveLeft1);
  public static CANEncoder enDriveLeft2 = new CANEncoder(mtDriveLeft2);
  public static CANEncoder enDriveRight1 = new CANEncoder(mtDriveRight1);
  public static CANEncoder enDriveRight2 = new CANEncoder(mtDriveRight2);
  public static CANEncoder enWinch1 = new CANEncoder(mtWinch1);
  public static CANEncoder enTowerLift = new CANEncoder(mtTowerLift);

  //declares Digital input devices which in this case are line sensors
  public static DigitalInput snsrLineSensorL = new DigitalInput(2);
  public static DigitalInput snsrLineSensorR = new DigitalInput(1);
  public static DigitalInput snsrLineSensorF = new DigitalInput(0);

  //declares CANifier for LED control
  public static CANifier RGBLEDController = new CANifier(20);

  //declares compressor port
  public static Compressor cmpRobotCompressor = new Compressor(0);

  //declares digital id for tower motor 
  public static WPI_TalonSRX mtTowerRotate = new WPI_TalonSRX(0);

  //declares port number of solenoid
  public static Solenoid slndGearShifter = new Solenoid(3);
  public static Solenoid slndTowerStage2 = new Solenoid(6);
  public static Solenoid slndIntakeMove = new Solenoid(2);
  public static Solenoid slndHatchIntake = new Solenoid(4);
  public static Solenoid slndHatchKickers = new Solenoid(7);
  public static Solenoid slndClimbRelease = new Solenoid(1);
  public static Solenoid slndTowerLEDs = new Solenoid(0);

  //runs on initialize
  public static void init() {    

    //because the tower likes to spin -- this doesn't actually work
    mtTowerRotate.setNeutralMode(NeutralMode.Brake);
  }


}
