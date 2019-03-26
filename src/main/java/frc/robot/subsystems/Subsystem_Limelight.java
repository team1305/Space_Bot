/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Subsystem_Limelight extends Subsystem {

  public static NetworkTable table;

  private NetworkTableEntry camMode, ledMode, tx, ty, ta, tv, ts, tl;

  public Subsystem_Limelight() {

    table = NetworkTableInstance.getDefault().getTable("limelight");

    ledMode = table.getEntry("ledMode");

    camMode = table.getEntry("stream");
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }


}
