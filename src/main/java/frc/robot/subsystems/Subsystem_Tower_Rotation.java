/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.Command_Tower_Loop;

/**
 * Add your docs here.
 */
public class Subsystem_Tower_Rotation extends Subsystem {
  
  private final WPI_TalonSRX mtRotate = RobotMap.mtTowerRotate;

  private static double TOWERPOWERCONSTANT = 0.5;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Command_Tower_Loop());
  }

  public void TowerStop() {
    mtRotate.set(0);
  }

  public void TowerLeft() {
    mtRotate.set(TOWERPOWERCONSTANT);
  }

  public void TowerRight() {
    mtRotate.set(-TOWERPOWERCONSTANT);
  }

}
