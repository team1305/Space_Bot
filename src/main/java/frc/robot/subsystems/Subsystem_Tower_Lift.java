/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Command_Cargo_Loading;

/**
 * Add your docs here.
 */
public class Subsystem_Tower_Lift extends Subsystem {

  //creates boolean variables to determine tower state
  public boolean bTowerLowerIsUp = false;
  public boolean bTowerUpperIsUp = false;

  //creates variables for PID control
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  
  //grabs device ID from robotmap
  private final Solenoid slndTower2 = RobotMap.slndTowerStage2;
  private final CANSparkMax mtTower1 = RobotMap.mtTowerLift;

  //creates a PID controller for the tower control
  private final CANPIDController pidTower1 = mtTower1.getPIDController();

  //on initialize sets the PID values and motor functions
  public Subsystem_Tower_Lift() {
    //sets the ramprate of the motor to spin up to speed in quarter second
    mtTower1.setClosedLoopRampRate(0.25);

    //switches the limit switches to normally closed to allow the motor to move until it hits them
    mtTower1.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyClosed);
    mtTower1.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyClosed);

    //Creates PID variables and declares the values of them
    kP = 0.8; 
    kI = 0;
    kD = 80; 
    kIz = 0; 
    kFF = 0; 
    kMaxOutput = 0.45; 
    kMinOutput = -0.7;

    //sets the PID controller values to the PID variables
    pidTower1.setP(kP);
    pidTower1.setI(kI);
    pidTower1.setD(kD);
    pidTower1.setIZone(kIz);
    pidTower1.setFF(kFF);
    pidTower1.setOutputRange(kMinOutput, kMaxOutput);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Command_Cargo_Loading());
  }

  //changes axis input into 0 or 1 only creating a button
  public int GetTrigger(double axis) {
    if (axis == 0) {
      return 0;
    } else {
      return 1;
    }
  }

  //creates a function to set the position of the tower
  public void SetPosition(double dtowerHeight) {
    pidTower1.setReference(dtowerHeight, ControlType.kPosition);
  }

  //puts tower to mid level for cargoship / loading station
  public void CargoLoading() {
    SetPosition(-16);
    if (bTowerLowerIsUp) {
      bTowerLowerIsUp = false;
    }
    if (bTowerUpperIsUp) {
      Level2Down();
    }
  }

  //Changes tower lower level to true and raises the tower
  public void Level1Up() {
    SetPosition(-38.6);
    bTowerLowerIsUp = true;
  }

  //Changes tower lower level to false and brings the tower down
  public void Level1Down() {
    SetPosition(0);
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
    SmartDashboard.putBoolean("Tower Level 1", bTowerLowerIsUp);
    SmartDashboard.putBoolean("Tower Level 2", bTowerUpperIsUp);
  }

  //drops tower in increments
  public void Drop() {
    if (bTowerLowerIsUp && bTowerUpperIsUp) {
      Level2Down();
    } else if (bTowerLowerIsUp && !bTowerUpperIsUp) {
      Level1Down();
    } else {
      Level1Down();
    }
    SmartDashboard.putBoolean("Tower Level 1", bTowerLowerIsUp);
    SmartDashboard.putBoolean("Tower Level 2", bTowerUpperIsUp);
 
  }

  //check to see if left trigger is pulled then raises to cargoship height
  public void MidLift() {
    if (GetTrigger(Robot.oi.getJoystickOperator().getRawAxis(2)) == 1) {
      CargoLoading();
    }
  }
}
