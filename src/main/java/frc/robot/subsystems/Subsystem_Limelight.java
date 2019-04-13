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
import frc.robot.RobotMap;
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

  private NetworkTableEntry stream, camMode, ledMode, tx, ty, ta, tv, ts, tl;
  // private NetworkTableEntry nt_ithor_value1, nt_ithor_value2, nt_ibackuploops;
  // private NetworkTableEntry nt_ndrive_speed1, nt_ndrive_speed2, nt_nbackupspeed, nt_nangle_speedfactor1, nt_nangle_speedfactor2;
  // private NetworkTableEntry nt_btwophasedrive, nt_bphase1correction, nt_bgethatch, nt_bdobackup, nt_bturnturret, nt_bthrowhatch, nt_bpushhatch, nt_bdefault_ledson;

  public double ithor_value1; // ithor_value2, idirection, iloopcount, ibackuploops;
  // public double ndrive_speed1, ndrive_speed2, nbackupspeed, nangle_speedfactor1, nangle_speedfactor2;
  // public boolean btwophasedrive, bphase1correction, bgethatch, bdobackup, bturnturret, bthrowhatch, bpushhatch,
  //     bforward, bdefault_ledson;

  public Subsystem_Limelight() {

    // navx = new AHRS(I2C.Port.kMXP);
    // AI Variables
    // bdefault_ledson = true;
    // ithor_value1 = 100;
    // ithor_value2 = 290;
    // ndrive_speed1 = 0.7;
    // ndrive_speed2 = 0.4;
    // nbackupspeed = 0.5;
    // nangle_speedfactor1 = 0.1;
    // nangle_speedfactor2 = 0.1;
    // idirection = 1;
    // bforward = true;

    // btwophasedrive = true;
    // bphase1correction = false;
    // bgethatch = false;
    // bdobackup = false;
    // ibackuploops = 25; // half a second back = 25, 50 = 1 second backup 
    // bturnturret = false;
    // bthrowhatch = false;
    // bpushhatch = false;

    // ai_table = NetworkTableInstance.getDefault().getTable("ai_table");
  
//    load_ai_table();


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
    stream = table.getEntry("stream");

    // camMode.setNumber(2);
    // stream.setNumber(2);

    // AI Variables

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

  // public void AI_Loop() {
  //   SmartDashboard.putBoolean("HAVE_TARGET", isTarget());
  //   SmartDashboard.putNumber("THOR_value", targetWidth());
  //   // read_ai_table() {

  //   if (Robot.oi.joyxbox1.getRawButton(5)) { // Driver LB
  //     setLed("on");

  //     if (Math.abs(Robot.tower.getPosition()) < 5000) {
  //       bforward = true;
  //     } else {
  //       bforward = false;
  //     }

  //       switch (loopState) {
  //       case "HUNT": // looking for target
  //         iloopcount = 0;
  //         if (isTarget()) {
  //           setloopstate("DRIVETOTARGET1");
  //         }
  //         break;

  //       case "DRIVETOTARGET1":
  //         if (isTarget()) {
  //           if (btwophasedrive) {
  //             if (targetWidth() < ithor_value1) {
  //               // drive straight until we reach target goal phase 1
  //               bIsDrivingToTarget = true;
  //               dif = 0;
  //               if (bphase1correction) {
  //                 dif = targetAngle() * nangle_speedfactor1; // tx
  //               }

  //               if (bforward) {
  //                  speedLeft = ndrive_speed1 + dif;
  //                  speedRight = ndrive_speed1 - dif;
  //               } else { // drive backwards
  //                 speedLeft = (0 - ndrive_speed1) - dif;
  //                 speedRight = (0 - ndrive_speed1) + dif;                 
  //               }
  //               Robot.drive.DriveTank(speedLeft, speedRight);
  //             } else { // we reached our first target
  //               setloopstate("DRIVETOTARGET2");
  //             }
  //           } else { // automatically jump to second phase
  //             setloopstate("DRIVETOTARGET2");
  //           }
  //         } else { // lost target
  //           Robot.drive.DriveStop();
  //           setloopstate("HUNT");
  //         }
  //         break;

  //       case "DRIVETOTARGET2":
  //         if (isTarget()) {  
  //           if (targetWidth() <  ithor_value2) {
  //             // drive to target      
  //             bIsDrivingToTarget = true;
  //             dif = targetAngle() * nangle_speedfactor2; // tx
            
  //             if (bforward) {
  //               speedLeft = ndrive_speed2 + dif;
  //               speedRight = ndrive_speed2 - dif;
  //             } else { // drive backwards
  //               speedLeft = (0 - ndrive_speed2) - dif;
  //               speedRight = (0 - ndrive_speed2) + dif;                 
  //             }
  //             Robot.drive.DriveTank(speedLeft, speedRight);
  //           } else { // WE'RE AT TARGET
  //               Robot.drive.DriveStop();
  //             if (!Robot.intake.bHatchGrabbed) { // we have a hatch
  //               setloopstate("DROPHATCH");   
  //             } else { // Get a hatch
  //               setloopstate("GETHATCH");
  //             }
  //           }
  //         } else { // lost target
  //           Robot.drive.DriveStop();
  //           setloopstate("HUNT");
  //         }
  //         break;

  //       case "DROPHATCH":
  //         if (bthrowhatch) {
  //           Robot.intake.DropHatch();
  //           Robot.intake.KickHatch();
  //           Robot.intake.DontKickHatch();
  //         } else if (bpushhatch) {
  //           Robot.intake.DropHatch();
  //           Robot.intake.KickHatch(); // Push Hatch Out, and keep it there?          
  //         }           
  //         setloopstate("DONE");               
  //         break;

  //       case "GETHATCH":
  //         if (bgethatch) {
  //           Robot.intake.GrabHatch();
  //         }
  //         if (bdobackup) {
  //           setloopstate("BACKUP");
  //         } else {
  //           setloopstate("DONE");
  //         }
  //         break;

  //       case "BACKUP":
  //         iloopcount++;
  //         if (iloopcount < ibackuploops) {
  //           if (bforward) {
  //             Robot.drive.DriveTank(0 - nbackupspeed, 0 - nbackupspeed);
  //           } else {
  //             Robot.drive.DriveTank(nbackupspeed, nbackupspeed);
  //           }
  //         } else {
  //           Robot.drive.DriveStop();
  //           if (bturnturret) {
  //             setloopstate("TURNTURRET");
  //           } else {
  //              setloopstate("DONE");
  //           }
  //         }
  //         break;

  //       case "TURNTURRET":
  //         if (bforward) {
  //           Robot.tower.SetPosition(-118726);
  //         } else {
  //           Robot.tower.SetPosition(0);
  //         }
  //         break;
        

  //       case "DEPLOYBALL":
  //         break;
        
  //       case "DONE":
  //         break;

  //       default:
  //         break;
  //       }

  //   } else {
  //     setloopstate("HUNT");
  //     if (bdefault_ledson) {
  //       setLed("on");
  //     } else {
  //       setLed("off");
  //     }
  //   }

  // }

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

  // Methods to set Camera settings

  // Controls Leds

  // String mode must be either "on" or "off" or "blink"

  // public void load_ai_table() {
  //   nt_ithor_value1.setNumber(ithor_value1);
  //   nt_ithor_value2.setNumber(ithor_value2);
  //   nt_ibackuploops.setNumber(ibackuploops);

  //   nt_ndrive_speed1.setNumber(ndrive_speed1);
  //   nt_ndrive_speed2.setNumber(ndrive_speed2);
  //   nt_nbackupspeed.setNumber(nbackupspeed);
  //   nt_nangle_speedfactor1.setNumber(nangle_speedfactor1);
  //   nt_nangle_speedfactor2.setNumber(nangle_speedfactor2);
    
  //   nt_btwophasedrive.setBoolean(btwophasedrive);
  //   nt_btwophasedrive.setBoolean(bphase1correction);
  //   nt_btwophasedrive.setBoolean(bgethatch);
  //   nt_btwophasedrive.setBoolean(bdobackup);
  //   nt_btwophasedrive.setBoolean(bturnturret);
  //   nt_btwophasedrive.setBoolean(bthrowhatch);
  //   nt_btwophasedrive.setBoolean(bpushhatch);
  //   nt_btwophasedrive.setBoolean(bdefault_ledson);
  // }

  // public void read_ai_table() {
  //   ithor_value1 = nt_ithor_value1.getDouble(100);
  //   ithor_value2 = nt_ithor_value2.getDouble(290);
  //   ibackuploops = nt_ibackuploops.getDouble(25);

  //   ndrive_speed1 = nt_ndrive_speed1.getDouble(0.7);
  //   ndrive_speed2 = nt_ndrive_speed2.getDouble(0.4);
  //   nbackupspeed = nt_nbackupspeed.getDouble(0.5);
  //   nangle_speedfactor1 = nt_nangle_speedfactor1.getDouble(0.1);
  //   nangle_speedfactor2 = nt_nangle_speedfactor2.getDouble(0.1);
    
  //   btwophasedrive = nt_btwophasedrive.getBoolean(true);
  //   bphase1correction = nt_bphase1correction.getBoolean(false);
  //   bgethatch = nt_bgethatch.getBoolean(false);
  //   bdobackup = nt_bdobackup.getBoolean(false);
  //   bturnturret = nt_bturnturret.getBoolean(false);
  //   bthrowhatch = nt_bthrowhatch.getBoolean(false);
  //   bpushhatch = nt_bpushhatch.getBoolean(false);
  //   bdefault_ledson = nt_bdefault_ledson.getBoolean(false);
  // }
  
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

    /*
     * SmartDashboard.putString("Limelight Horizontal",
     * Double.toString(tx.getDouble(0.0)));
     * 
     * SmartDashboard.putString("Limelight Vertical",
     * Double.toString(ty.getDouble(0.0)));
     * 
     * SmartDashboard.putString("Limelight Area",
     * Double.toString(ta.getDouble(0.0)));
     * 
     * SmartDashboard.putString("Limelight Skew",
     * Double.toString(ts.getDouble(0.0)));
     * 
     * SmartDashboard.putString("Limelight Latency",
     * Double.toString(tl.getDouble(0.0)));
     * 
     * SmartDashboard.putString("Limelight Valid",
     * Boolean.toString(tv.getDouble(0.0) == 1.0));
     */
  }

}
