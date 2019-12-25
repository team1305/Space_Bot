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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.commands.Command_Limelight_Loop;

/**
 * Add your docs here.
 */
public class Subsystem_Limelight extends Subsystem {

  public static NetworkTable table;

  // public static NetworkTable ai_table;
  

  public String loopState;

  // public double target_width, target_X = 0;

  // public double current_angle;

  // public double thor_Threshold, speedLeft, speedRight, dif;

  // public boolean bIsDrivingToTarget = false;

  private double conversionConstant = 659.24;

  private NetworkTableEntry camMode, ledMode, tx, ty, ta, tv, ts, tl;
  // private NetworkTableEntry nt_ithor_value1, nt_ithor_value2, nt_ibackuploops;
  // private NetworkTableEntry nt_ndrive_speed1, nt_ndrive_speed2, nt_nbackupspeed, nt_nangle_speedfactor1, nt_nangle_speedfactor2;
  // private NetworkTableEntry nt_btwophasedrive, nt_bphase1correction, nt_bgethatch, nt_bdobackup, nt_bturnturret, nt_bthrowhatch, nt_bpushhatch, nt_bdefault_ledson;

  public double ithor_value1; // ithor_value2, idirection, iloopcount, ibackuploops;
  // public double ndrive_speed1, ndrive_speed2, nbackupspeed, nangle_speedfactor1, nangle_speedfactor2;
  // public boolean btwophasedrive, bphase1correction, bgethatch, bdobackup, bturnturret, bthrowhatch, bpushhatch,
  //     bforward, bdefault_ledson;

  public Subsystem_Limelight() {

    table = NetworkTableInstance.getDefault().getTable("limelight");

    loopState = "HUNT";

    // Get stats

    tx = table.getEntry("tx");
    ty = table.getEntry("ty");
    ta = table.getEntry("ta");
    tv = table.getEntry("tv");
    ts = table.getEntry("ts");
    tl = table.getEntry("tl");
    ledMode = table.getEntry("ledMode");
    camMode = table.getEntry("camMode");

  }

  public void TrackingLoop() {

    SmartDashboard.putBoolean("targetValid", targetValid());

    if (Robot.oi.joyxbox1.getRawButton(5)) {
      switch (loopState) {
        case "HUNT":

          if (targetValid()) {
            SetLoopState("TRACKRIGHTTARGET");
          }
        break;
        case "TRACKRIGHTTARGET":
          if (targetValid()) {
            if (getHorizontalOffset() < 0) { //target left
            
              Robot.tower.SetPosition(Robot.tower.getPosition()
              + ((getHorizontalOffset() - 4) * conversionConstant));
            } else if (getHorizontalOffset() > 0) { //target right

              Robot.tower.SetPosition(Robot.tower.getPosition()
              +((getHorizontalOffset() + 3.5) * conversionConstant));
            }
          } else {
            SetLoopState("HUNT");
          }
        
        break;
      }
    } else {
      SetLoopState("HUNT");
    }
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new Command_Limelight_Loop());
  }

  public double targetWidth() {
    return table.getEntry("thor").getDouble(0.0);
  }

  public boolean isTarget() {
    return table.getEntry("tv").getDouble(0) == 1;
  }

  public double targetAngle() {
    return table.getEntry("tx").getDouble(0.0);
  }

  public void SetLoopState(String cloopstate) {

    loopState = cloopstate;
  }

  // Methods to get information

  public double getHorizontalOffset() {

    return tx.getDouble(0.0);

  }

  public double getVerticalOffset() {

    return ty.getDouble(0.0);

  }

  public double getTargetArea() {

    return ta.getDouble(0.0);

  }

  public double getTargetSkew() {

    return ts.getDouble(0.0);

  }

  public double getLatency() {

    return tl.getDouble(0.0);

  }

  public boolean targetValid() {

    if (tv.getDouble(0.0) == 1.0) {

      if (ta.getDouble(0.0) >= 0.4) {

        return true;
      } else {

        return false;
      }

    }

    else {

      return false;

    }

  }
  
  public void setLed(String mode) {

    if (mode.equals("on")) {

      ledMode.setNumber(0);

    }

    else if (mode.equals("off")) {

      ledMode.setNumber(1);

    }

    else if (mode.equals("blink")) {

      ledMode.setNumber(2);

    }

    else {

      System.out.println("Limelight:   setLed(String mode) -----> mode not recognised\nSetting Leds to blink.");

      ledMode.setNumber(2);

    }

  }

  // Sets the camera to a operation mode

  // String mode must be either "vision" or "driver"

  public void setCameraMode(String mode) {

    if (mode.equals("vision")) {

      camMode.setNumber(0);

    }

    else if (mode.equals("driver")) {

      camMode.setNumber(1);

    }

    else {

      System.out
          .println("Limelight:   setCameraMode(String mode) -----> mode not recognised\nSetting camera to vision.");

      camMode.setNumber(0);

    }

  }

  public void debug() {

  }

}
