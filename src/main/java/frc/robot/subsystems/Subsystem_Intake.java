/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Command_Kick_Hatch;

/**
 * Add your docs here.
 */
public class Subsystem_Intake extends Subsystem {

  //creates variables to determine if hatch is grabbed and state of intake wrist
  public boolean bWristIsDown;
  public boolean bHatchGrabbed;
  public boolean bHatchKicked;

  //creates variables to determine if the intake is running, only use for now is for LEDs
  public boolean bIntakeOn = false;
  public boolean bOuttakeOn = false;

  //grabs device IDs from robotmap
  private final CANSparkMax mtIntake = RobotMap.mtIntake;
  private final Solenoid slndWrist = RobotMap.slndIntakeMove;
  private final Solenoid slndHatch = RobotMap.slndHatchIntake;
  private final Solenoid slndKick = RobotMap.slndHatchKickers;

  //when the subsystem is initialized sets the default position of the intake
  public Subsystem_Intake() {
    bWristIsDown = false;
    bHatchGrabbed = true;
    bHatchKicked = false;

    mtIntake.setOpenLoopRampRate(0.2);
    //jaredRocks(true);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Command_Kick_Hatch());
  }

  //sets intake to half power
  //negative because they mounted it backwards
  public void Intake() {
    mtIntake.set(-0.5);
    bIntakeOn = true;
    bOuttakeOn = false;
    DropHatch();
  }

  //sets intake to half power for outtaking -- pew pew
  public void Outtake() {
    mtIntake.set(1);
    bIntakeOn = false;
    bOuttakeOn = true;
  }

  //sets the outtake speed when line is sensed -- unused
  public void OuttakeSpeed(double nspeed) {
    mtIntake.set(nspeed);
    if (nspeed == 0) {
      bIntakeOn = false;
      bOuttakeOn = false;
    } else {
      bIntakeOn = false;
      bOuttakeOn = true;
    }
    
  }

  //stops intake movement
  public void IntakeStop() {
    mtIntake.set(0.0);
    bIntakeOn = false;
    bOuttakeOn = false;
  }

  //rotates wrist up
  public void WristUp() {
    this.slndWrist.set(true);
    bWristIsDown = true;
    SmartDashboard.putBoolean("Intake", bWristIsDown);

  }

  //rotates wrist down
  public void WristDown() {
    this.slndWrist.set(false);
    bWristIsDown = false;
    SmartDashboard.putBoolean("Intake", bWristIsDown);

  }

  //toggles wrist movement
  public void ToggleWrist() {
    if (bWristIsDown) {
      WristDown();
      DropHatch();
    } else {
      WristUp();
      GrabHatch();
    }
    SmartDashboard.putBoolean("Intake", bWristIsDown);
    SmartDashboard.putBoolean("J Hook", bHatchGrabbed);
  }

  //extends pnuematic to grab hatch
  public void GrabHatch() {
    this.slndHatch.set(false);
    bHatchGrabbed = false;
    SmartDashboard.putBoolean("J Hook", bHatchGrabbed);
  }

  //retracts pnuematic to lower hatch
  public void DropHatch() {
    this.slndHatch.set(true);
    bHatchGrabbed = true;
    SmartDashboard.putBoolean("J Hook", bHatchGrabbed);
  }

  //toggles hatch grabber
  public void ToggleHatchGrabber() {
    if (bHatchGrabbed) {
      GrabHatch();
    } else {
      DropHatch();
    }
    SmartDashboard.putBoolean("J Hook", bHatchGrabbed);
  }

  //extends hatch kicking solenoids
  public void KickHatch() {
    this.slndKick.set(true);
    bHatchKicked = true;
    SmartDashboard.putBoolean("kicker", bHatchKicked);
  }

  //asks the solenoids to nicely retract
  public void DontKickHatch() {
    this.slndKick.set(false);
    bHatchKicked = false;
    SmartDashboard.putBoolean("kicker", bHatchKicked);
  }

  //changes axis input into 0 or 1 only creating a button
  public int GetTrigger(double axis) {
    if (axis == 0) {
      return 0;
    } else {
      return 1;
    }
  }

  //main command for launching the hatch
  public void LaunchHatch() {
    //creats a switch based on the value of the right trigger
    switch (GetTrigger(Robot.oi.getJoystickOperator().getRawAxis(3))) {
      case 0:
        DontKickHatch();
      break;
      case 1:
        DropHatch();
        KickHatch();
      break;
    }
    if (GetTrigger(Robot.oi.getJoystickOperator().getRawAxis(2)) == 1) {
      mtIntake.set(0.2);
    } else {mtIntake.set(0.0);}
  }
}
