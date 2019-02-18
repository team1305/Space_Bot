/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;


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

  //creates integer and boolean values for the line sensors and a total value
  private boolean bSensorL;
  private boolean bSensorR;
  private boolean bSensorF;
  private int intSensorL;
  private int intSensorR;
  private int intSensorF;
  public int intSensorValues;
  
  //grabs sensor mapping from RobotMap
  private final DigitalInput snsrLeft = RobotMap.snsrLineSensorL;
  private final DigitalInput snsrRight = RobotMap.snsrLineSensorR;
  private final DigitalInput snsrForward = RobotMap.snsrLineSensorF;

  //grabs solenoid of the tower lights from RobotMap
  private final Solenoid slndTowerLights = RobotMap.slndTowerLEDs;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Command_Line_Sensing());
  }

  //main line sensing loop
  public void LineSensing() {
    //loop only runs if RB is pressed on driver joystick
    if(Robot.oi.getJoystickDriver().getRawButton(6)) {
      //gets the boolean data of the line sensors
      bSensorL = snsrLeft.get();
      bSensorR = snsrRight.get();
      bSensorF = snsrForward.get();
  
      //converts the line sensors from boolean to integers
      intSensorL = bSensorL ? 1:0;
      intSensorR = bSensorR ? 1:0;
      intSensorF = bSensorF ? 1:0;
  
      //creates essentially a binary string of numbers
      intSensorValues = (intSensorL * 100) + (intSensorF * 10) + intSensorR;
  
      //outputs sensor values to the dashboard
      SmartDashboard.putNumber("Line Sensor Values", intSensorValues);
  
      //creates a switch based on the sensor data
      switch(intSensorValues) {
        case 101:
          this.slndTowerLights.set(true);
          Robot.drive.DriveStop();
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
        default: //should not run but if it does outputs an error message
          System.out.println("Sensor Error " + intSensorValues); 
      }
    } else {this.slndTowerLights.set(false);}

  }
}
