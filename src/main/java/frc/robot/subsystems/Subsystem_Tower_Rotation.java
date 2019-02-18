/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.util.Set;

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

  //creates int for position of different tower systems
  private int intPOV;

  
  //grabs device ID from robotmap
  private final WPI_TalonSRX mtRotate = RobotMap.mtTowerRotate;

  //creates a power constant for tower rotation
  private static double TOWERPOWERCONSTANT = 0.5;

  public Subsystem_Tower_Rotation() {

    //limits tower motor max speed
    mtRotate.configPeakOutputForward(1);
    mtRotate.configPeakOutputReverse(-1);

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

  //sets tower to turn left
  public void TowerLeft() {
    mtRotate.set(TOWERPOWERCONSTANT);
  }

  //sets tower to turn right
  public void TowerRight() {
    mtRotate.set(-TOWERPOWERCONSTANT);
  }

  //gets the POV value of a Joystick passed through this function
  public int GetPOV(int intPOVValue) {
    return intPOVValue;
  }

  //gets the position
  public double getPosition() {
    return mtRotate.getSelectedSensorPosition(0);  
  }

  public void setPID () {
    mtRotate.config_kP(0, 0.05, 0);  //0.05
    mtRotate.config_kI(0, 0.0, 0);  //0.01
    mtRotate.config_kD(0, 2, 0); //1
    }

  public void holdPosition(double requestedPosition) {
    mtRotate.set(ControlMode.Position, requestedPosition );
    //stage1L.set(ControlMode.Position, requestedPosition / (Math.PI * 0.5) * 4096);
  }

  public void SetPosition(double towerRotation) {
    //if (towerRotation < 59370) {
      Robot.tower.setPID();
      mtRotate.set(ControlMode.Position, towerRotation );
        
      //Robot.tower.holdPosition(Robot.tower.getPosition()); 
      //stage1L.set(ControlMode.Position, requestedPosition / (Math.PI * 0.5) * 4096);
    //}
  }

  public void resetEncoder() {
     	mtRotate.setSelectedSensorPosition(0, 0, 10);
  }  

  public void TowerAI() {

    SmartDashboard.putNumber("Encoder Value", getPosition());


    if (GetPOV(Robot.oi.getJoystickOperator().getPOV()) != -1) {

      intPOV = GetPOV(Robot.oi.getJoystickOperator().getPOV());
      
      if (!Robot.intake.bWristIsDown) {
        if (intPOV == 0) {
          SetPosition(0);
        } else if (intPOV == 90) {
          SetPosition(59363);
        } else if (intPOV == 180) {
          SetPosition(-118726);
        } else if (intPOV == 270) {
          SetPosition(-59363);
        }
      }

    }
  }
}
