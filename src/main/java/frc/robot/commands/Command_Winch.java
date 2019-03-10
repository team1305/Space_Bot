/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Command_Winch extends Command {

  private boolean bReleased;

  public Command_Winch() {

    bReleased = false;

    requires(Robot.drive);
    requires(Robot.winch);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.oi.joyxbox1.getRawButton(4)) {
      if(!bReleased) {
        Robot.winch.Release();
        bReleased = true;
      }
      Robot.winch.Winch();
      Robot.drive.ClimbSpeed();
      Robot.winch.LEDsOn();
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    //Robot.winch.resetcount();
    Robot.winch.Stop();
    Robot.winch.LEDsOFF();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
    end();
  }
}
