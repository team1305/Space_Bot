/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Subsystem_Winch extends Subsystem {
  
  //grabs device ID from robotmap
  private final CANSparkMax mtWinch1 = RobotMap.mtWinch1;
  private final CANSparkMax mtWinch2 = RobotMap.mtWinch2;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  //Turns motors to winch climb up
  public void Winch() {
    mtWinch1.set(0.5);
    mtWinch2.set(0.5);
  }

  //Turns motors to unwinch climb for testing
  public void unWinch() {
    mtWinch1.set(-0.5);
    mtWinch2.set(-0.5);
  }

  //stops motors, stopping climbing
  public void Stop() {
    mtWinch1.set(0.0);
    mtWinch2.set(0.0);
  }
}
