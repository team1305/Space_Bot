/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.io.Console;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Command_Line_Sensing;

/**
 * Add your docs here.
 */
public class Subsystem_Line_Sensing extends Subsystem {

  private int intSensorL;
  private int intSensorR;
  private int intSensorF;
  public int intSensorValues;
  
  private final DigitalInput snsrLeft = RobotMap.snsrLineSensorL;
  private final DigitalInput snsrRight = RobotMap.snsrLineSensorR;
  private final DigitalInput snsrForward = RobotMap.snsrLineSensorF;

  private final Solenoid slndTowerLights = RobotMap.slndTowerLEDs;

  private boolean bSensorL;
  private boolean bSensorR;
  private boolean bSensorF;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Command_Line_Sensing());
  }

  public void LineSensing() {
    if(Robot.oi.getJoystickDriver().getRawButton(6)) {
      bSensorL = snsrLeft.get();
      bSensorR = snsrRight.get();
      bSensorF = snsrForward.get();
  
      intSensorL = bSensorL ? 1:0;
      intSensorR = bSensorR ? 1:0;
      intSensorF = bSensorF ? 1:0;
  
      intSensorValues = (intSensorL * 100) + (intSensorF * 10) + intSensorR;
  
      SmartDashboard.putNumber("Line Sensor Values", intSensorValues);
  
      switch(intSensorValues) {
        case 101:
          this.slndTowerLights.set(true);
        break;
        case 10:
          this.slndTowerLights.set(true);
        break;
        case 100:
          this.slndTowerLights.set(false);
        break;
        case 1:
          this.slndTowerLights.set(false);
        break;
        case 110:
          this.slndTowerLights.set(false);
        break;
        case 11:
          this.slndTowerLights.set(false);
        break;
        case 0:
          this.slndTowerLights.set(false);
        break;
        case 111:
          this.slndTowerLights.set(false);
        break;
        default: System.out.println("Sensor Error " + intSensorValues);
      }
    }

  }
}
