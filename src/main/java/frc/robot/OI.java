/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.Command_Compressor_OFF;
import frc.robot.commands.Command_Compressor_ON;
import frc.robot.commands.Command_Intake;
import frc.robot.commands.Command_Outtake;
import frc.robot.commands.Command_Shift_Gear;
import frc.robot.commands.Command_Toggle_Hatch;
import frc.robot.commands.Command_Toggle_Wrist;
import frc.robot.commands.Command_Unwinch;
import frc.robot.commands.Command_Winch;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  //declares joystick on port 0
  public Joystick joyxbox1 = new Joystick(0);  // Primary Driver Stick
  
  //declares button mapping of the joyxbox1 controller
	Button btn1_A = new JoystickButton(joyxbox1, 1);  //xbox "A" Button 1
	Button btn1_B = new JoystickButton(joyxbox1, 2); //xbox "B" Button 2
	Button btn1_X = new JoystickButton(joyxbox1, 3); //xbox "X" Button 3
	Button btn1_Y = new JoystickButton(joyxbox1, 4); //xbox "Y" Button 4
	Button btn1_LB = new JoystickButton(joyxbox1, 5); //xbox "LB" Button 5
	Button btn1_RB = new JoystickButton(joyxbox1, 6);  //xbox "RB" Button 6
	Button btn1_Back = new JoystickButton(joyxbox1, 7);  //xbox "Back" Button 7
	Button btn1_Start = new JoystickButton(joyxbox1, 8);  //xbox "Start" Button 8
	Button btn1_LS = new JoystickButton(joyxbox1, 9);  //xbox "Left Stick Click" Button 9
  Button btn1_RS = new JoystickButton(joyxbox1, 10);  //xbox "Right Stick Click" Button 10

  public OI() {
 
    // Primary Driver Stick
    btn1_A.whenPressed(new Command_Toggle_Wrist());//xbox "A" Button 1
    btn1_B.whileHeld(new Command_Toggle_Hatch());//xbox "B" Button 2
    btn1_X.whileHeld(new Command_Winch());//xbox "X" Button 3
    btn1_Y.whileHeld(new Command_Unwinch());//xbox "Y" Button 4
    btn1_LB.whileHeld(new Command_Intake());//xbox "LB" Button 5
    btn1_RB.whileHeld(new Command_Outtake());//xbox "RB" Button 6
    btn1_Back.whenPressed(new Command_Compressor_OFF());//xbox "Back" Button 7
    btn1_Start.whenPressed(new Command_Compressor_ON());//xbox "Start" Button 8
    btn1_LS.whenPressed(new Command_Shift_Gear());//xbox "Left Stick Click" Button 9
    //btn1_RS.whileHeld(new Cmd_LED_Blink());//xbox "Right Stick Click" Button 10
    ////xbox "X Axis" Left Stick - 
    ////xbox "Y Axis" Left Stick - Drive Forward and Reverse
    ////xbox "X Axis 5" Right Stick - 
    ////xbox "Y Axis 4" Right Stick - Drive Left and Right
    ////xbox "Axis 3" Left Trigger - 
    ////xbox "Axis 3" Right Trigger - 
    ////xbox "Up" Direction Pad - Tower up
    ////xbox "Down" Direction Pad - Tower down
    ////xbox "Left" Direction Pad - Tower Left
    ////xbox "Right" Direction Pad - Tower Right
  }
  
  //returns joyxbox1 whenever getJoystickDriver is called
  public Joystick getJoystickDriver() {
    return joyxbox1;
  }
}
