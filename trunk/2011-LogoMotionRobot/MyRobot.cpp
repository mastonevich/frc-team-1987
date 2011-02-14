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
	Joystick stick2;
	Victor *FR;
	Victor *FL;
	Victor *BR;
	Victor *BL; 
	DigitalInput *TrackL;
	DigitalInput *TrackC;
	DigitalInput *TrackR;
	DigitalInput *ElevatorMax;
	DigitalInput *ElevatorMin;
	DigitalInput *ElevatorHeight;
	DigitalInput *ClawOpen;
	DigitalInput *ClawShut;
	DriverStation *ds;
	Jaguar *EM;
	// Jaguar *ER;
	AnalogChannel *ElevatorPOT;
	AnalogChannel *analog5VIn;
	PIDController *ElevatorPID;
	Solenoid *Shoulder;
	Solenoid *Wrist;
	Solenoid *Claw;
	Solenoid *MinibotLock;
	Solenoid *MinibotArm;
	Compressor *AirComp;
	bool WristToggle;
	bool ClawToggle;
	bool ShoulderToggle;
	bool MiniToggle;
	
public:
	RobotDemo(void):
		myRobot(1, 3, 2, 4),	// these must be initialized in the same order
		stick1(1),				// as they are declared above.
		stick2(2)
	{
		WristToggle = false;
		ClawToggle = false;
		ShoulderToggle = false;
		MiniToggle = false;
		FL = new Victor(1);
		FR = new Victor(2);
		BL = new Victor(3);
		BR = new Victor(4);
		myRobot.SetExpiration(0.1);
		TrackL = new DigitalInput(4,1);
	    TrackC = new DigitalInput(4,2);
		TrackR = new DigitalInput(4,3);
		// ElevatorMax = new DigitalInput(?,?);
		// ElevatorMin = new DigitalInput(?,?);
		// ClawOpen = new DigitalInput(?,?);
		// ClawShut = new DigitalInput(?,?);
		AirComp = new Compressor(1,1,1,1);
		ds = DriverStation::GetInstance();
		EM = new Jaguar(5);
		//ER = new Jaguar(6);
		ElevatorPOT = new AnalogChannel(1, 1);
		analog5VIn = new AnalogChannel(1, 7);
		analog5VIn->SetAverageBits(3);
		
		ElevatorPID = new PIDController(0.0025, 0.0001, 0, ElevatorPOT, EM, 0.025);
		ElevatorPID->SetInputRange(0, 1);
		ElevatorPID->SetOutputRange(-1, 1);
		
		// if (maximumPot5V < 800) {	// AIN7 jumper was accidentally removed set default value to 986
		// maximumPot5V = 986;
		// }
		
		// if (stick1.GetRawButton(12)){tensionEnable();tensionPID->SetSetpoint(tHome);}
		// 0.025,0.0001   -0.2,-0.001
		// maximumPot5V = analog5VIn->GetAverageValue();
		// lg->Log(Logger::kINFO,"+5V ADC %1.0f at %1.2f Volts",maximumPot5V,analog5VIn->GetAverageVoltage());

		/*
		Shoulder = new Solenoid(8, 1);
		Wrist = new Solenoid(8, 2);
		Claw = new Solenoid(8, 3);
		MinibotLock = new Solenoid(8, 4);
		MinibotArm = new Solenoid(8, 5);
		*/
	}
		
		
	
	/**
	 * Drive left & right motors for 2 seconds then stop
	 */
	void Autonomous(void)
	{
		float speed = 0;
		float turn = 0;
		float slide = 0;
		bool fork = 0;
		int autoStep = 1;

		while (IsAutonomous() && IsEnabled()) {
			
			ds->GetBatteryVoltage();
			
			if(AirComp->GetPressureSwitchValue() == 0)
			{
				AirComp->Start();
			}
			else if(AirComp->GetPressureSwitchValue() == 1)
			{
				AirComp->Stop();
			}
			
			switch(autoStep) {
				case 1:
					// transformation 
					autoStep++;
					break;
				case 2: 
					// locate line and drive
					
					/*if (TrackL->Get() && TrackR->Get() && TrackC->Get()) {// if all read then reverse for a sec to stop moving
						speed = .18;
						turn = 0;
						slide = 0;
						//printf ("Track All\n");
					}
					*/
					if (TrackL->Get() && TrackR->Get() && fork == 0) { // if the left and right sensors read a value then strafes 
						speed = -.7 * TRACKINGSPEED;
						turn = TRACKINGTURN;
						//printf("TrackL and TrackR\n");
					}
					else if (TrackL->Get() && TrackR->Get() && fork == 1) { // if the left and right sensors read a value then strafes 
						speed = -.7 * TRACKINGSPEED;
						turn = -TRACKINGTURN;
						//printf("TrackL and TrackR\n");
					}
					else if (TrackC->Get()) { // if central tracker reads a value the speed is at .2
						speed = -TRACKINGSPEED;
						turn = 0; 
						//printf ("TrackC\n");
					}
					else if (TrackR->Get()) { // if only the right sensor reads a value the it turns right 
						speed = -.7 * TRACKINGSPEED;
						turn = TRACKINGTURN;
						//printf ("TrackR\n");
					}
					else if (TrackL->Get()) { // if only the left sensor reads a value then it turns left
						speed = -.7 * TRACKINGSPEED;
						turn = -TRACKINGTURN;
						//printf ("TrackL\n");
					}
					else { // if nothing is read motors stop
						speed = 0;
						turn = 0;
						slide = 0;
						//printf(No Track\n");
					}
					
					FR->Set(speed-turn-slide);
					BR->Set(speed-turn+slide);
					FL->Set(-speed-turn-slide);
					BL->Set(-speed-turn+slide);
					
					if (ds->GetDigitalIn(1)) {
						fork = 1;
						//printf ("Will go right.");
					}
					else {
						fork = 0;
						//printf ("Will go left.");
					}

					/*if (distance || T == reached){
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
			
			ds->GetBatteryVoltage();
			
			if(AirComp->GetPressureSwitchValue() == 0)
			{
				AirComp->Start();
			}
			else if(AirComp->GetPressureSwitchValue() == 1)
			{
				AirComp->Stop();
			}			
			
			speed = Deadband(-stick1.GetY(), -0.01, 0.01);
			turn = Deadband(stick1.GetZ(), -0.01, 0.01);
			slide = Deadband(stick1.GetX(), -0.01, 0.01);
			
			if(stick1.GetRawButton(5)) slide=-.2;
			else if(stick1.GetRawButton(6)) slide=.2;
			
			if(stick2.GetRawButton(6)) 
			{
				ElevatorPID->Disable();
				EM->Set(.5); 
			}
			else if(stick2.GetRawButton(7))
			{
				ElevatorPID->Disable();
				EM->Set(-.5);
			}
			
			FR->Set(-speed-turn+slide);
			BR->Set(-speed-turn-slide);
			FL->Set(speed-turn+slide);
			BL->Set(speed-turn-slide);
			
			if(stick1.GetRawButton(7))
			{
				Shoulder->Set(1);
				Wrist->Set(1);
				Claw->Set(1);
				ElevatorPID->Enable();	
				ElevatorPID->SetSetpoint(Lvl1);
				//ER->Set(-EL->Get());	
			}
			if(stick1.GetRawButton(8))
			{
				Shoulder->Set(1);
				Wrist->Set(1);
				Claw->Set(1);
				ElevatorPID->Enable();	
				ElevatorPID->SetSetpoint(Lvl2);
				//ER->Set(-EL->Get());	
			}
			if(stick1.GetRawButton(9))
			{
				Shoulder->Set(1);
				Wrist->Set(1);
				Claw->Set(1);
				ElevatorPID->Enable();	
				ElevatorPID->SetSetpoint(Lvl3);
				//ER->Set(-EL->Get());	
			}	
			if(stick1.GetRawButton(10))
			{
				Shoulder->Set(1);
				Wrist->Set(1);
				Claw->Set(1);
				ElevatorPID->Enable();	
				ElevatorPID->SetSetpoint(Lvl4);
				//ER->Set(-EL->Get());	
			}
			if(stick1.GetRawButton(11))
			{
				Shoulder->Set(1);
				Wrist->Set(1);
				Claw->Set(1);
				ElevatorPID->Enable();	
				ElevatorPID->SetSetpoint(Lvl5);
				//ER->Set(-EL->Get());	
			}
			if(stick1.GetRawButton(12))
			{
				Shoulder->Set(1);
				Wrist->Set(1);
				Claw->Set(1);
				ElevatorPID->Enable();	
				ElevatorPID->SetSetpoint(Lvl6);
				//ER->Set(-EL->Get());	
			}		
			
			//Manual Control
			
			if(stick2.GetRawButton(3) && WristToggle == false)
			{
				Wrist->Set(1);
				WristToggle = true;
			}
			else if(stick2.GetRawButton(3) && WristToggle == true)
			{
				Wrist->Set(0);
				WristToggle = false;
			}
			if(stick2.GetRawButton(1) && ClawToggle == false)
			{
				Claw->Set(1);
				ClawToggle = true;
			}
			else if(stick2.GetRawButton(1) && ClawToggle == true)
			{
				Claw->Set(0);
				ClawToggle = false;
			}
			if(stick2.GetRawButton(2) && ShoulderToggle == false)
			{
				Shoulder->Set(1);
				ShoulderToggle = true;
			}
			else if(stick2.GetRawButton(2) && ShoulderToggle == true)
			{
				Shoulder->Set(0);
				ShoulderToggle = false;
			}
			if(stick2.GetRawButton(10))
			{
				MinibotLock->Set(0);
				MiniToggle = true;
			}
			if(stick2.GetRawButton(11) && MiniToggle == true)
			{
				MinibotArm->Set(1);
			}
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

