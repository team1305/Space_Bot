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
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Subsystem_Intake extends Subsystem {

  //creates variables to determine if hatch is grabbed and state of intake wrist
  public boolean bWristIsUp;
  public boolean bHatchGrabbed = false;

  //creates variables to determine if the intake is running, only use for now is for LEDs
  public boolean bIntakeOn = false;
  public boolean bOuttakeOn = false;

  //grabs device IDs from robotmap
  private final CANSparkMax mtIntake = RobotMap.mtIntake;
  private final Solenoid slndWrist = RobotMap.slndIntakeMove;
  private final Solenoid slndHatch = RobotMap.slndHatchIntake;

  public Subsystem_Intake() {
    bWristIsUp = true;
  }

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  //sets intake to half power
  //negative because they mounted it backwards
  public void Intake() {
    mtIntake.set(-0.5);
    bIntakeOn = true;
    bOuttakeOn = false;
  }

  //sets intake to half power for intaking
  public void Outtake() {
    mtIntake.set(0.8);
    bIntakeOn = false;
    bOuttakeOn = true;
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
    bWristIsUp = true;
  }

  //rotates wrist down
  public void WristDown() {
    this.slndWrist.set(false);
    bWristIsUp = false;
  }

  //toggles wrist movement
  public void ToggleWrist() {
    if (bWristIsUp) {
      WristDown();
    } else {
      WristUp();
    }
  }

  //extends pnuematic to grab hatch
  public void GrabHatch() {
    this.slndHatch.set(true);
    bHatchGrabbed = true;
  }

  //retracts pnuematic to lower hatch
  public void DropHatch() {
    this.slndHatch.set(false);
    bHatchGrabbed = false;
  }

  //toggles hatch grabber
  public void ToggleHatchGrabber() {
    if (bHatchGrabbed) {
      DropHatch();
    } else {
      GrabHatch();
    }
  }
}
