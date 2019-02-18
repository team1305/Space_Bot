/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Subsystem_Tower_Lift extends Subsystem {

  //creates boolean variables to determine tower state
  public boolean bTowerLowerIsUp = false;
  public boolean bTowerUpperIsUp = false;
  
  //grabs device ID from robotmap
  public final Solenoid slndTower1 = RobotMap.slndTowerStage1;
  public final Solenoid slndTower2 = RobotMap.slndTowerStage2;

  @Override
  public void initDefaultCommand() {
    //setDefaultCommand(new Command_Tower_Lift());
  }

  //Changes tower lower level to true and extends the pnuematic
  public void Level1Up() {
    this.slndTower1.set(true);
    bTowerLowerIsUp = true;
  }

  //Changes tower lower level to false and retracts the pnuematic
  public void Level1Down() {
    this.slndTower1.set(false);
    bTowerLowerIsUp = false;
  }

  //Changes tower upper level to true and extends the pnuematic
  public void Level2Up() {
    this.slndTower2.set(true);
    bTowerUpperIsUp = true;
  }

  //Changes tower upper level to true and retracts the pnuematic
  public void Level2Down() {
    this.slndTower2.set(false);
    bTowerUpperIsUp = false;
  }

  //lifts tower in increments
  public void Lift() {
    if (!bTowerLowerIsUp && !bTowerUpperIsUp) {
      Level1Up();
    } else if (bTowerLowerIsUp && !bTowerUpperIsUp) {
      Level2Up();
    }

  }

  //drops tower in increments
  public void Drop() {
    if (bTowerLowerIsUp && bTowerUpperIsUp) {
      Level2Down();
    } else if (bTowerLowerIsUp && !bTowerUpperIsUp) {
      Level1Down();
    }

  }
}
