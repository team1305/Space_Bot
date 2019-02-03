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

  private boolean bTowerLowerIsUp = false;
  private boolean bTowerUpperIsUp = false;
  
  public final Solenoid slndTower1 = RobotMap.slndTowerStage1;
  public final Solenoid slndTower2 = RobotMap.slndTowerStage2;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void Level1Up() {
    this.slndTower1.set(true);
    bTowerLowerIsUp = true;
  }

  public void Level1Down() {
    this.slndTower1.set(false);
    bTowerLowerIsUp = false;
  }

  public void Level2Up() {
    this.slndTower2.set(true);
    bTowerUpperIsUp = true;
  }

  public void Level2Down() {
    this.slndTower2.set(false);
    bTowerUpperIsUp = false;
  }

  public void TowerUp() {
    if (bTowerLowerIsUp == false) {
      Level1Up();
    } else if (bTowerUpperIsUp == false) {
      Level2Up();
    }
  }

  public void TowerDown() {
    if (bTowerUpperIsUp == true) {
      Level2Down();
    } else if (bTowerLowerIsUp == true) {
      Level1Down();
    }
  }
}
