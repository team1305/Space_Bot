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

  private boolean bWristIsUp = true;
  private boolean bHatchGrabbed = false;

  private final CANSparkMax mtIntake = RobotMap.mtIntake;
  private final Solenoid slndWrist = RobotMap.slndIntakeMove;
  private final Solenoid slndHatch = RobotMap.slndHatchIntake;
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void Intake() {
    mtIntake.set(-1.0);
  }

  public void Outtake() {
    mtIntake.set(1.0);
  }

  public void IntakeStop() {
    mtIntake.set(0.0);
  }

  public void WristUp() {
    this.slndWrist.set(true);
    bWristIsUp = true;
  }

  public void WristDown() {
    this.slndWrist.set(false);
    bWristIsUp = false;
  }

  public void ToggleWrist() {
    if (bWristIsUp) {
      WristDown();
    } else {
      WristUp();
    }
  }

  public void GrabHatch() {
    this.slndHatch.set(false);
    bHatchGrabbed = false;
  }

  public void DropHatch() {
    this.slndHatch.set(true);
    bHatchGrabbed = true;
  }

  public void ToggleHatchGrabber() {
    if (bHatchGrabbed) {
      DropHatch();
    } else {
      GrabHatch();
    }
  }
}