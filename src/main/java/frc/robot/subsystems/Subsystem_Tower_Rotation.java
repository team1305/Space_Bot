/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Subsystem_Tower_Rotation extends Subsystem {
  
  private final WPI_TalonSRX mtRotate = RobotMap.mtTowerRotate;

  private static double TOWERPOWERCONSTANT = 0.1;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void TowerStop() {
    mtRotate.set(0);
  }

  public void TowerLeft() {
    mtRotate.set(0.3);
  }

  public void TowerRight() {
    mtRotate.set(-0.3);
  }

}