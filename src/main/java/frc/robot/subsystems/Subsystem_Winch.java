/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Subsystem_Winch extends Subsystem {

  //creates a loop counter to slow the climb -- currently unused
  private int loopcounter;
  
  //grabs device ID from robotmap
  private final CANSparkMax mtWinch1 = RobotMap.mtWinch1;
  private final CANSparkMax mtWinch2 = RobotMap.mtWinch2;

  //grabs port ID of the solenoid
  private final Solenoid slndRelease = RobotMap.slndClimbRelease;
  private final Solenoid slndSpear = RobotMap.slndSpear;
  private final Solenoid slndlights = RobotMap.slndTowerLEDs;

  //on initialization of the subsystem sets motor settings
  public Subsystem_Winch() {
    mtWinch1.setOpenLoopRampRate(0.4);
    mtWinch2.setOpenLoopRampRate(0.4);
    //loopcounter = 0;
    mtWinch2.follow(mtWinch1);   
    mtWinch1.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen); 
  }

  @Override
  public void initDefaultCommand() {
  }

  public void UpdateLimitSwitch() {
    boolean bLimit = mtWinch1.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen).get();
    // Robot.oi.SetSpearButton(bLimit);
    System.out.println("LimitSwitch = " + bLimit);
    if (bLimit && Robot.oi.joyxbox1.getRawButton(2)) {
      Spear();
    }
  }

  //Turns motors to winch climb up
  public void Winch() {
    //if (loopcounter<25){
      mtWinch1.set(-0.9); //0.85 // 0.8 // .75
    //}
    //else{
      //mtWinch1.set(0.5);
    //}    
    //loopcounter ++;
  }

  //Turns motors to unwinch climb for testing
  public void unWinch() {
    mtWinch1.set(0.2);
  }

  //resets loop counter
  public void resetcount() {
    loopcounter =0;
  }

  //stops motors, stopping climbing
  public void Stop() {
    mtWinch1.set(0.0);
  }

  //rotates lock up
  public void Release() {
    this.slndRelease.set(true);
  }
  
  //rotates lock down
  public void Lock() {
    this.slndRelease.set(false);
  }

  public void Spear() {
    this.slndSpear.set(true);
  }

  //turns tower LEDs
  public void LEDsOn() {
    this.slndlights.set(true);
  }

  //turns the tower LEDs off
  public void LEDsOFF() {
    this.slndlights.set(false);
  }

}
