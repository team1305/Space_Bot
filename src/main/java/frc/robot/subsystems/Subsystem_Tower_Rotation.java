/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Command_Tower_Loop;

/**
 * Add your docs here.
 */
public class Subsystem_Tower_Rotation extends Subsystem {

  //creates int for controller positioning
  private int intPOV;
  
  //grabs device ID from robotmap
  private final WPI_TalonSRX mtRotate = RobotMap.mtTowerRotate;

  //creates a power constant for tower rotation
  private static double TOWERPOWERCONSTANT = 0.5;

  //runs when subsystem is initialized
  public Subsystem_Tower_Rotation() {

    //limits tower motor max speed
    mtRotate.configPeakOutputForward(0.8); // 0.8
    mtRotate.configPeakOutputReverse(-0.8); // -0.8

    mtRotate.configClosedloopRamp(0.05);

    //creates the encoder for the tower
    mtRotate.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);

    //creates forward limit switch and sets it to normally closed
    mtRotate.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
     LimitSwitchNormal.NormallyClosed, 0); // enable limit switch

    //creates reverse limit switch and sets it to normally closed
    mtRotate.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector,
     LimitSwitchNormal.NormallyClosed, 0); // enable limit switch
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Command_Tower_Loop());
  }

  //stops the tower
  public void TowerStop() {
    mtRotate.set(0);
  }

  //sets tower to turn left -- unused
  public void TowerLeft() {
    mtRotate.set(TOWERPOWERCONSTANT);
  }

  //sets tower to turn right -- unused
  public void TowerRight() {
    mtRotate.set(-TOWERPOWERCONSTANT);
  }

  //gets the POV value of a Joystick passed through this function
  public int GetPOV(int intPOVValue) {
    return intPOVValue;
  }

  //gets the position of the tower
  public double getPosition() {
    return mtRotate.getSelectedSensorPosition(0);  
  }

  //sets the PID loop for the tower
  public void setPID () {
    mtRotate.config_kP(0, 0.07, 0);  //0.05
    mtRotate.config_kI(0, 0.0, 0);  //0.0
    mtRotate.config_kD(0, 1.6, 0); //1
  }

  //sets the PID for the tower but different -- unused
  public void setPID2 () {
    mtRotate.config_kP(0, 0.05, 0);  //0.05
    mtRotate.config_kI(0, 0.0, 0);  //0.01
    mtRotate.config_kD(0, 1.6, 0); //1
  }

  //uses PID loop to hold tower in requested position -- unused
  public void holdPosition(double requestedPosition) {
    mtRotate.set(ControlMode.Position, requestedPosition );
  }

  //rotates tower to specified position and uses PID to hold it
  public void SetPosition(double towerRotation) {
    if (towerRotation <= 60000 && towerRotation >= -131500) {
      Robot.tower.setPID();
      mtRotate.set(ControlMode.Position, towerRotation );
      //Robot.tower.holdPosition(Robot.tower.getPosition());
    } 
  }

  //this is for if we want a more aggressive tower spin -- unused
  public void SetPosition2(double towerRotation) {
    if (towerRotation <= 60000 && towerRotation >= -131500) {
      Robot.tower.setPID2();
      mtRotate.set(ControlMode.Position, towerRotation );
      //Robot.tower.holdPosition(Robot.tower.getPosition());
    } 
  }

  //resets the tower encoder -- unused currently, need to find defaulte position of limts
  public void resetEncoder() {
       mtRotate.setSelectedSensorPosition(0, 0, 10); // sensorPos, PIDIdx, timeoutMD
  }  

  //this is the "AI" for the tower rotation
  public void TowerAI() {
    //puts the encoder value on the dashboard
    SmartDashboard.putNumber("Encoder Value", getPosition());
    
    //first determines if the POV is untouched
    if (GetPOV(Robot.oi.getJoystickOperator().getPOV()) != -1) {
      //puts data to the intPOV variable to make code shorter
      intPOV = GetPOV(Robot.oi.getJoystickOperator().getPOV());
      
      //checks to see if the wrist is down and if it is does not run
      if (!Robot.intake.bWristIsDown) {
        if (Robot.oi.getJoystickOperator().getRawButton(8)) { //Start button
          if (intPOV == 90) {
            SetPosition2(getPosition() + 3330); //approx 5 deg (approx 666 per deg)
          } else if (intPOV == 270) {
            SetPosition2(getPosition() - 3330);
          }
        } else {
          if (intPOV == 0) { //forward
            SetPosition(0);
          } else if (intPOV == 90) { //right
            SetPosition(59363);
          } else if (intPOV == 180) { //backward
           SetPosition(-118726);
          } else if (intPOV == 270) { //left
            SetPosition(-59363);
          }
        }
      }

    }
  }
}
