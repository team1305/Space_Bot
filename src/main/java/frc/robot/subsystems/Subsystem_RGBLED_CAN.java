/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.CANifier.LEDChannel;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.Command_CAN_Lights;

/**
 * Add your docs here.
 */
public class Subsystem_RGBLED_CAN extends Subsystem {

	//creates variables to control the power and brightness
	//of the LEDS
	double dPower;
	double dBrightness;

	//creates variable to control LEDs on and off
	boolean bLoopOn = false;

	//variables to store last light value
	double dPercentOutput1, dPercentOutput2, dPercentOutput3;

	//declares LED channel
	LEDChannel ledchannel;
  
	//grabs device ID of canifier from robotmap
	private final CANifier LEDcontroller = RobotMap.RGBLEDController;
	
	//sets LED state to on when system is created
	public void RGBledCAN() {
   		SetLoopState(true);
  	}
	
	//sets LED beahaviours
  	public void Setlights() {
    	if (bLoopOn) {
			if (Robot.intake.bIntakeOn) {
				LEDGreen();
			} else if (Robot.intake.bOuttakeOn) {
				LEDRed();
			} else if (Robot.drive.bIsLow) {
				LEDBlue();
			} else if (!Robot.drive.bIsLow) {
				LEDCyan();
			} else {
				Show1305();
			}
		}
	}

	//Bright Blue
	public void Show1305() {
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelA);	//Blue
	 	LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelB);	//Red
	 	LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelC);	//Green	
	}
	
	//Restores the last used colour
	public void RestoreLast() { 	
	 	LEDcontroller.setLEDOutput(dPercentOutput1, LEDChannel.LEDChannelA); // Blue
	 	LEDcontroller.setLEDOutput(dPercentOutput2, LEDChannel.LEDChannelB); // Red
	 	LEDcontroller.setLEDOutput(dPercentOutput3, LEDChannel.LEDChannelC); // Green
  	}
    
    //creates a function to enable LEDs to set to true
  	public void SetLoopState(boolean bloopState) {
    	bLoopOn = bloopState;
  	}
	
	//gets the state of lights
  	public boolean GetLoopState() {
    	return bLoopOn;
 	}  
    
  	@Override   
  	public void initDefaultCommand() {
    	setDefaultCommand(new Command_CAN_Lights());	
  	}

  	@Override
  	public void periodic() {

  	}

	//shuts LEDs off
  	public void LEDOff() {
    	LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelA);	//Blue
    	LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelB);	//Red
    	LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelC);	//Green
    	dPercentOutput1 = 0.0;
    	dPercentOutput2 = 0.0;
    	dPercentOutput3 = 0.0;
    	 
    }
	
	//Red
  	public void LEDRed() {	
	 	LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelA);	//Blue
	 	LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelB);	//Red
	 	LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelC);	//Green
    	dPercentOutput1 = 0.0;
   		dPercentOutput2 = 1.0;
		dPercentOutput3 = 0.0;
	}
	
	//Green
  	public void LEDGreen() {
		LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelA);	//Blue
		LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelB);	//Red
	 	 LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelC);	//Green
   		dPercentOutput1 = 0.0;
    	dPercentOutput2 = 0.0;
    	dPercentOutput3 = 1.0; 	
  	}
	
	//Blue
  	public void LEDBlue() {
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelA);	//Blue
		LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelB);	//Red
		LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelC);	//Green
   		dPercentOutput1 = 1.0;
   		dPercentOutput2 = 0.0;
   		dPercentOutput3 = 0.0;	
  	}
	
	//Low power blue
  	public void LEDBlueLow() {
		LEDcontroller.setLEDOutput(0.1, LEDChannel.LEDChannelA);	//Blue
		LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelB);	//Red
		LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelC);	//Green
   		dPercentOutput1 = 0.1;
   		dPercentOutput2 = 0.0;
   		dPercentOutput3 = 0.0;
	}
	
	//adjustable blue
	public void LEDBluePower(double power) {
    	LEDcontroller.setLEDOutput(power, LEDChannel.LEDChannelA);	//Blue
	 	LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelB);	//Red
	 	LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelC);	//Green
   		dPercentOutput1 = power;
   		dPercentOutput2 = 0.0;
   		dPercentOutput3 = 0.0;
	} 	
	
	//Yellow
	public void LEDyellow() {
		LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelA);	//Blue
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelB);	//Red
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelC);	//Green
   		dPercentOutput1 = 0.0;
   		dPercentOutput2 = 1.0;
		dPercentOutput3 = 1.0;
	}
	
	//Cyan
	public void LEDCyan() {
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelA);	//Blue
		LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelB);	//Red
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelC);	//Green	
   		dPercentOutput1 = 1.0;
   		dPercentOutput2 = 0.0;
   		dPercentOutput3 = 1.0;
  	}

	//Magenta
  	public void LEDMagenta() {
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelA);	//Blue
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelB);	//Red
		LEDcontroller.setLEDOutput(0.0, LEDChannel.LEDChannelC);	//Green
   		dPercentOutput1 = 1.0;
   		dPercentOutput2 = 1.0;
   		dPercentOutput3 = 0.0;
  	}

	//White
  	public void LEDWhite() {
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelA);	//Blue
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelB);	//Red
		LEDcontroller.setLEDOutput(1.0, LEDChannel.LEDChannelC);	//Green
    	dPercentOutput1 = 1.0;
   		dPercentOutput2 = 1.0;
   		dPercentOutput3 = 1.0;
  	}
}
