#include "WPILib.h"
#include "MyRobot.h"
/**
 * This is a demo program showing the use of the RobotBase class.
 * The SimpleRobot class is the base of a robot application that will automatically call your
 * Autonomous and OperatorControl methods at the right time as controlled by the switches on
 * the driver station or the field controls.
 */ 
class RobotDemo : public SimpleRobot
{
private:
	
	RobotDrive myRobot; // robot drive system
	Joystick stick1; // only joystick
	Victor *FR;
	Victor *FL;
	Victor *BR;
	Victor *BL; 
	DigitalInput *TrackL;
	DigitalInput *TrackC;
	DigitalInput *TrackR;
	
public:
	RobotDemo(void):
		myRobot(1, 3, 2, 4),	// these must be initialized in the same order
		stick1(1)		// as they are declared above.
	{
		
		FL = new Victor(1);
		FR = new Victor(2);
		BL = new Victor(3);
		BR = new Victor(4);
		myRobot.SetExpiration(0.1);
		TrackL = new DigitalInput(4,1);
	    TrackC = new DigitalInput(4,2);
		TrackR = new DigitalInput(4,3);
		
	}

	/**
	 * Drive left & right motors for 2 seconds then stop
	 */
	void Autonomous(void)
	{
		float speed = 0;
		float turn = 0;
		float slide = 0;
		int autoStep = 1;
		
		while (IsAutonomous() && IsEnabled()) {
			switch(autoStep) {
				case 1:
					// transformation 
					autoStep++;
					break;
				case 2: 
					// locate line/ drive
					
					if (TrackC->Get()) { // if centeral tracker reads a value the speed is at .2
						speed = TRACKINGSPEED;
						turn = 0; 
						//printf ("TrackC\n");
					}
					else if (TrackL->Get() && TrackR->Get()) { // if the left and right sensors read a value then strafes 
						speed = .7 * TRACKINGSPEED;
						turn = 1.2 * -TRACKINGSPEED;
						//printf("TrackL and TrackR\n");
					}
					else if (TrackL->Get()) { // if only the left sensor reads a value then it turns left
						speed = .7 * TRACKINGSPEED;
						turn = 1.2 * -TRACKINGSPEED;
						//printf ("TrackL\n");
					}
					else if (TrackR->Get()) { // if only the right sensor reads a value the it turns right 
						speed = .7 * TRACKINGSPEED;
						turn = 1.2 * TRACKINGSPEED;
						//printf ("TrackR\n");
					}
					else if (TrackL->Get() && TrackR->Get() && TrackC->Get()) {
						speed = 0;
						turn = 0;
						slide = 0;
					}
					else {
						speed = 0;
						turn = 0;
						slide = 0;
					}
					
					
					
					FR->Set(speed-turn-slide);
					BR->Set(speed-turn+slide);
					FL->Set(-speed-turn-slide);
					BL->Set(-speed-turn+slide);
					
					if (stick1.GetRawButton(4)) {
					printf("FR = %f FL = %f BR = %f BL %f", FR->Get(), FL->Get(), BR->Get(), BL->Get());
					}
					else {
					printf("Speed = %f Slide = %f Turn = %f\n", speed, slide, turn);
					}
					
					/*if (distance == reached){
						speed = 0;
						autoStep++;
					}*/
					break;
				/*case 3:
					// targeting
					autoStep++;
					break;
				case 4:
					// place tube
					autoStep = 1;
					break;*/
			}
		/*myRobot.SetSafetyEnabled(false);
		myRobot.Drive(0.5, 0.0); 	// drive forwards half speed
		Wait(2.0); 					// for 2 seconds
		myRobot.Drive(0.0, 0.0); 	// stop robot
		*/
		
		//TrackL->Get() ? printf("TrackL - ") : printf("\t - ");
		//TrackC->Get() ? printf("TrackC - ") : printf("\t - ");
		//TrackR->Get() ? printf("TrackR\n") : printf("\t\n");
				
		}
	}

	/**
	 * Runs the motors with arcade steering. 
	 */
	void OperatorControl(void)
	{
		float speed;
		float turn;
		float slide;
		
		myRobot.SetSafetyEnabled(true);
		while (IsOperatorControl())
		{
			
			speed = Deadband(-stick1.GetY(), -0.01, 0.01);
			turn = Deadband(stick1.GetZ(), -0.01, 0.01);
			slide = Deadband(stick1.GetX(), -0.01, 0.01);
			
			if(stick1.GetRawButton(7)) slide=-.2;
			else if(stick1.GetRawButton(8)) slide=.2;
			
			FR->Set(speed-turn-slide);
			BR->Set(speed-turn+slide);
			FL->Set(-speed-turn-slide);
			BL->Set(-speed-turn+slide);
			
			
			//printf("x= %f y= %f z= %f \r\n", stick1.GetX(), stick1.GetY(), stick1.GetZ());
			//printf("speed= %f slide= %f turn= %f \n", speed, slide, turn);
			
			//TrackL->Get() ? printf("TrackL - ") : printf("\t - ");
			//TrackC->Get() ? printf("TrackC - ") : printf("\t - ");
			//TrackR->Get() ? printf("TrackR\n") : printf("\t\n");
			
			//printf("track1= %b track2= %b track3= %b \n", Track1->Get(), Track2->Get(), Track3->Get() ); 
			
			//myRobot.ArcadeDrive(stick1.getRawAxis(1), stick1.getRawAxis(2), true); // drive with arcade style (use right stick)
			//myRobot.MecanumDrive_Polar(stick1.GetRawAxis(1))
			Wait(0.005);				// wait for a motor update time
		}
	}
	
	float Deadband(float val, float min, float max) {
		if(val>min && val<max) return 0;
		return val;
			
	}
	
};

START_ROBOT_CLASS(RobotDemo);

