/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.Command_PID_Winch;

/**
 * Add your docs here.
 */
public class Subsystem_Winch extends Subsystem {

  private int loopcounter;
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;
  private double setPoint, processVariable;

  private boolean bIsReleased = false;
  
  //grabs device ID from robotmap
  private final CANSparkMax mtWinch1 = RobotMap.mtWinch1;
  private final CANSparkMax mtWinch2 = RobotMap.mtWinch2;

  private final CANEncoder enWinch = RobotMap.enWinch1;

  private final CANPIDController pidController;

  //grabs port ID of the solenoid
  private final Solenoid slndRelease = RobotMap.slndClimbRelease;



  public Subsystem_Winch() {
    loopcounter = 0;
    // mtWinch2.follow(mtWinch1);

    pidController = mtWinch1.getPIDController();

    kP = 0.0002;
    kI = 0.000001;
    kD = 0.002;
    kIz = 0;
    kFF = 0.00015;
    kMaxOutput = 1;
    kMinOutput = -1;

    pidController.setP(kP);
    pidController.setI(kI);
    pidController.setD(kD);
    pidController.setIZone(kIz);
    pidController.setFF(kFF);
    pidController.setOutputRange(kMinOutput, kMaxOutput);

    // display PID coefficients on SmartDashboard
    SmartDashboard.putNumber("P Gain", kP);
    SmartDashboard.putNumber("I Gain", kI);
    SmartDashboard.putNumber("D Gain", kD);
    //SmartDashboard.putNumber("I Zone", kIz);
    SmartDashboard.putNumber("Feed Forward", kFF);

    
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Command_PID_Winch());
  }

  public void PIDLoop() {
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    // double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != kP)) { pidController.setP(p); kP = p; }
    if((i != kI)) { pidController.setI(i); kI = i; }
    if((d != kD)) { pidController.setD(d); kD = d; }
    // if((iz != kIz)) { pidController.setIZone(iz); kIz = iz; }
    if((ff != kFF)) { pidController.setFF(ff); kFF = ff; } 
    pidController.setOutputRange(kMinOutput, kMaxOutput); 

    processVariable = enWinch.getVelocity();
    
    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("Process Variable", processVariable);
    SmartDashboard.putNumber("Output", mtWinch1.getAppliedOutput());
    SmartDashboard.putNumber("Master", mtWinch1.getAppliedOutput());
    SmartDashboard.putNumber("Slave", mtWinch2.getAppliedOutput());

  }

  //Turns motors to winch climb up
  public void Winch() {
    //pidController.setReference(1000, ControlType.kVelocity);
    if (loopcounter<25){
      mtWinch1.set(1);
      mtWinch2.set(1);
    }
    else{
      mtWinch1.set(0.5);
      mtWinch2.set(0.5);
    }    
    loopcounter ++;
  }

  //Turns motors to unwinch climb for testing
  public void unWinch() {
    pidController.setReference(-200, ControlType.kVelocity);
  }

    //stops motors, stopping climbing
    public void resetcount() {
      loopcounter =0;
    }

  //stops motors, stopping climbing
  public void Stop() {
    mtWinch1.set(0.0);
    mtWinch2.set(0);
  }

  //rotates lock up
  public void Release() {
    this.slndRelease.set(true);
    bIsReleased = true;
  }
  
  //rotates lock down
  public void Lock() {
    this.slndRelease.set(false);
    bIsReleased = false;
  }
  
  //toggles lock movement
  public void ToggleLock() {
    if (bIsReleased) {
      Release();
    } else {
      Lock();
    }
  }
}
