package org.usfirst.frc.team957.robot;


import edu.wpi.first.wpilibj.CANTalon;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.CANTalon;

import edu.wpi.first.wpilibj.IterativeRobot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Button;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

import edu.wpi.first.wpilibj.CameraServer;

import edu.wpi.first.wpilibj.SampleRobot;

import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;

/**

 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;  

    CameraServer server;
    

 // Defines Joystick 1 and 2 and defines all 4 drive CAN Talons

    Joystick controller1 = new Joystick(0);

    CANTalon t0 = new CANTalon(0);
    CANTalon t1 = new CANTalon(1);
    CANTalon t2 = new CANTalon(2);
    CANTalon t3 = new CANTalon(3);   
    
    RobotDrive myRobot;
    CANTalon frontRight, frontLeft, backRight, backLeft, FeedArm;
    Joystick ohWhatJoy;
    Talon Shooter, Feeder;
    Relay BoulderRoller;
    int ShootToggle, LoopCount;

    //Shooter Talon
    Talon nT0 = new Talon(0);

    //Ball Feed Talon
    Talon nT1 = new Talon(1);

    
    double shootertime;

  
    double speed = 0.75;

    /**

     * This function is run when the robot is first started up and should be

     * used for any initialization code.

     */

    public void robotInit() {
       
    	chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);

        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        ohWhatJoy = new Joystick(0);
        frontRight = new CANTalon(0);
        frontLeft = new CANTalon(2);
        backRight = new CANTalon(1);
        backLeft = new CANTalon(3);
        myRobot = new RobotDrive(backLeft, frontLeft, backRight, frontRight);
        myRobot.setInvertedMotor(MotorType.kFrontRight, true);
        myRobot.setInvertedMotor(MotorType.kRearRight, true);
        Shooter = new Talon(0);
        Feeder = new Talon(1);
        Feeder.enableDeadbandElimination(true);
        Shooter.enableDeadbandElimination(true);
        FeedArm = new CANTalon(5);
        BoulderRoller = new Relay(0);
        ShootToggle = 0;
        
        
        
        server = CameraServer.getInstance();
        server.setQuality(5);
        //\the camera name (ex "cam0") can be found through the roborio web interface
        server.startAutomaticCapture("cam0");

    }

    

    /**

     * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
     * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
     * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
     * below the Gyro
     *
     * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
     * If using the SendableChooser make sure to add them to the chooser code above as well.
     */

    public void autonomousInit() {
        autoSelected = (String) chooser.getSelected();
//       autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
        System.out.println("Auto selected: " + autoSelected);

    }


    /**
     * This function is called periodically during autonomous
     */

    public void autonomousPeriodic() {

        switch(autoSelected) {
        case customAuto:
        //Put custom auto code here   
            break;
        
        case defaultAuto:
        default:
         break;
        }
    }

    /**
     * This function is called periodically during operator control
     */

    public void teleopPeriodic() {

        int POV = controller1.getPOV();
        if(POV == 45 || POV == 225){
        	POV = POV + 45;
        
        }
        if(POV == 135 || POV == 315){
        	POV = POV - 45;
        }
        SmartDashboard.putNumber("povUp", POV);
    //forward
        if(POV == 0){
            t0.set(-1 * speed); //right talons
            t1.set(-1 * speed);
            t2.set(-1 * -speed); //left talons
            t3.set(-1 * -speed);
        }
   //backward
        if(POV == 180){
            t0.set(1 * speed); //right talons
            t1.set(1 * speed);
            t2.set(1 * -speed); //left talons
            t3.set(1 * -speed);
        }
   //turn left
        if(POV == 90){
            t0.set(1 * speed); //right talons
            t1.set(1 * speed);
            t2.set(-1 * -speed); //left talons
            t3.set(-1 * -speed);
        }	
   //turn right
        if(POV == 270){
            t0.set(-1 * speed); //right talons
            t1.set(-1 * speed);
            t2.set(1 * -speed); //left talons
            t3.set(1 * -speed);
        }    	
 
    	//myRobot.arcadeDrive(1*(ohWhatJoy.getRawAxis(0)),(1*ohWhatJoy.getRawAxis(1)));
          /** DRIVETRAIN **/  
        // Asks the controller (Xbox360 or Dualshock 4 defined as XB360 using DS4W software)
        // to give variables labeled axis1 and axis2 axis 1 and 5, and place them in variables,
        //respectively.
        double axis1 = controller1.getRawAxis(1);
        double axis2 = controller1.getRawAxis(5);
       //Takes axis1 or 2 and puts them in the SRX move command, thereby moving the motors

        if(POV == -1){
        	t0.set(axis1 * speed); //right talons
            t1.set(axis1 * speed);
            t2.set(axis2 * -speed); //left talons
            t3.set(axis2 * -speed);       	
        }       
   
            //Talons and variables for ball feed and shooter

        //this checks if Button 5 is being pressed, and then is reset to false if true later33

        boolean but8 = controller1.getRawButton(8);
        
        if(true == but8){
            if(shootertime == 0){
                shootertime = 120;    
            }
        }
        if(shootertime > 0){
            nT0.set(-1);
            if(shootertime < 60){
                nT1.set(-1);
            }
            shootertime--;
        }else{
            nT1.set(0);
            nT0.set(0);
        }
        //Roller and arm control
        Relay.Value Roller;
        Roller=(ohWhatJoy.getRawButton(1))?Relay.Value.kOn:Relay.Value.kOff;
        BoulderRoller.set(Roller);
        if(ohWhatJoy.getRawButton(5))
        	FeedArm.set(-.5);
        else{
        	if(ohWhatJoy.getRawButton(6))
            	FeedArm.set(.5);
        	else
        		FeedArm.set(0);
        };
        
        
   
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
}



