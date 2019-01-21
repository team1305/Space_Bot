/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Subsystem_Drive extends Subsystem {

  // Motor Refrances
  private final CANSparkMax mtLeft1 = RobotMap.mtDriveLeft1;
  private final CANSparkMax mtLeft2 = RobotMap.mtDriveLeft2;
  private final CANSparkMax mtRight1 = RobotMap.mtDriveRight1;
  private final CANSparkMax mtRight2 = RobotMap.mtDriveRight2;

  // Drive Encorder Refrance
  private final CANEncoder enLeft1 = RobotMap.enDriveLeft1;
  private final CANEncoder enLeft2 = RobotMap.enDriveLeft2;
  private final CANEncoder enRight1 = RobotMap.enDriveRight1;
  private final CANEncoder enRight2 = RobotMap.enDriveRight2;

  // Internal subsystem parts
  private DifferentialDrive drRobotDrive;

  // Subsystem State Value
  private Double enLeftStart;
  private Double enRightStart;
  private Double enLeftCurrent;
  private Double enRightCurrent;
  

  public Subsystem_Drive() {
    SpeedControllerGroup scgLeft = new SpeedControllerGroup(mtLeft1, mtLeft2);
    SpeedControllerGroup scgRight = new SpeedControllerGroup(mtRight1, mtRight2);

    DifferentialDrive drRobotDrive = new DifferentialDrive(scgLeft, scgRight);
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public Double getleftEncederValue() {
    Double enCode1 = enLeft1.getPosition();
    Double enCode2 = enLeft2.getPosition();
    if (enCode1 == enCode2){
      return enCode1 - enLeftStart;
    }else{
      System.out.println("Left encoder1:" + enCode1.toString() + " encoder2:" + enCode2.toString());
      return Math.max(enCode1, enCode2) - enLeftStart;
    }
  }

  public Double getRightEncederValue() {
    Double enCode1 = enRight1.getPosition();
    Double enCode2 = enRight2.getPosition();
    if (Math.abs(enCode1) == Math.abs(enCode2)){
      return enCode1 - enRightStart;
    }else{
      System.out.println("Left encoder1:" + enCode1.toString() + " encoder2:" + enCode2.toString());
      return Math.max(enCode1, enCode2) - enRightStart;
    }
  }

  public Boolean resetEncoder(){
    enLeftStart = getleftEncederValue();
    enRightStart = getRightEncederValue();
    return true;
  }
}
