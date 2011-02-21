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
	Encoder *FREncoder;
	Encoder *FLEncoder;
	Encoder *BREncoder;
	Encoder *BLEncoder;
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
	bool AutoToggle;
	bool lastButton1a;
	bool lastButton1b;
	bool lastButton2;
	bool lastButton3;
	bool lastButton10;
	bool lastButton11;
	bool lastButton8;
	bool EleManUse; 
	float maximumPot5V;
	float EleState;
	float eleCurr;
	
	
	
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
		AutoToggle = false;
		lastButton1a = false;
		lastButton1b = false;
		lastButton2 = false;
		lastButton3 = false;
		lastButton10 = false;
		lastButton11 = false;
		lastButton8 = false;
		EleManUse = false;
		EleState = Floor;	
		
		FL = new Victor(1);
		FR = new Victor(3);
		BL = new Victor(2);
		BR = new Victor(4);
		myRobot.SetExpiration(0.1);
		TrackL = new DigitalInput(4,12);
	    TrackC = new DigitalInput(4,13);
		TrackR = new DigitalInput(4,14);
		// ElevatorMax = new DigitalInput(?,?);
		// ElevatorMin = new DigitalInput(?,?);
		// ClawOpen = new DigitalInput(?,?);
		// ClawShut = new DigitalInput(?,?);
		FREncoder = new Encoder(4,6);
		FLEncoder = new Encoder(4,7);
		BREncoder = new Encoder(4,8);
		BLEncoder = new Encoder(4,9);
		AirComp = new Compressor(4,11,4,1);
		ds = DriverStation::GetInstance();
		EM = new Jaguar(5);
		//ER = new Jaguar(6);
		ElevatorPOT = new AnalogChannel(1, 1);
		ElevatorPOT->SetAverageBits(4);
		analog5VIn = new AnalogChannel(1, 7);
		analog5VIn->SetAverageBits(3);
		
		//ElevatorPID = new PIDController(-0.6, -0.0001, 0, ElevatorPOT, EM, 0.005);
		//ElevatorPID->SetInputRange(0, 986);
		//ElevatorPID->SetOutputRange(-0.5, 1);
		
		//if (maximumPot5V < 800) {	// AIN7 jumper was accidentally removed set default value to 986
		//maximumPot5V = 986;
		//}
		
		// if (stick1.GetRawButton(12)){tensionEnable();tensionPID->SetSetpoint(tHome);}
		// 0.025,0.0001   -0.2,-0.001
		// lg->Log(Logger::kINFO,"+5V ADC %1.0f at %1.2f Volts",maximumPot5V,analog5VIn->GetAverageVoltage());

		Shoulder = new Solenoid(8, 3);
		Wrist = new Solenoid(8, 2);
		Claw = new Solenoid(8, 1);
		MinibotLock = new Solenoid(8, 4);
		MinibotArm = new Solenoid(8, 5);
		
	}
		
	
	
	
	
	
		
	
	/**
	 * Drive left & right motors for 2 seconds then stop
	 */
	void Autonomous(void)
	{
		float speed = .3;
		float turn = 0;
		float slide = 0;
		bool AStr = ds->GetDigitalIn(1);
		bool AFL = ds->GetDigitalIn(2);
		bool AFR = ds->GetDigitalIn(3);
		bool Mid = ds->GetDigitalIn(5);
		bool Top = ds->GetDigitalIn(6);
		int autoStep = 1;

		while (IsAutonomous() && IsEnabled()) {
			/*
			printf("AStr = %i \n", AStr);
			printf("AFL = %i \t", AFL);
			printf("AFR = %i \t", AFR);
			printf("Mid = %i \t", Mid);
			printf("Top = %i \t", Top);
			
			printf("L-%i \t",TrackL->Get());
			printf("C-%i \t",TrackC->Get());
			printf("R-%i \t",TrackR->Get());
			printf("avg Value = %d \t", ElevatorPOT->GetAverageValue());
			*/
			printf("State = %f \t", EleState);
			
			
			EleSet(EleState);		
			
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
				case 1:  // transformation
					Claw->Set(0);
					FLEncoder->Start();
					FREncoder->Start();
					BREncoder->Start();
					BLEncoder->Start();
					
					if(Mid)
					{
						if (AStr && !(AFL || AFR) && error == false)
						{
							EleState = Lvl4;
							Wrist->Set(0);
							Shoulder->Set(0);
							printf("1");
							ForkPass = true;
						}
						else if((AFL || AFR) && !AStr && error == false)
						{
							EleState = Lvl2;
							Wrist->Set(0);
							Shoulder->Set(0);
							printf("2");
						}
					}
					else if(Top)
					{
						if (AStr && !(AFL || AFR) && error == false)
						{
							EleState = Lvl6;
							Wrist->Set(0);
							Shoulder->Set(0);
							printf("3");
							ForkPass = true;
						}
						else if((AFL || AFR) && !AStr && error == false)
						{
							EleState = Lvl5;
							Wrist->Set(0);
							Shoulder->Set(0);
							printf("4");
						}
					}
					else if(error == false)
					{
						EleState = Floor;
						Wrist->Set(0);
						Shoulder->Set(0);
						Claw->Set(1);
						printf("5");
						break;
					}
					/*
					if(EleState > (eleCurr + 5) || EleState < (eleCurr - 5))
					{
						//EleSet(EleState);
						printf("Setting Elevator");
					}
					*/
					if(EleState <= (eleCurr + 5) && EleState >= (eleCurr - 5))
					{
						autoStep++;		
						printf("Case 2");
					}	
					autoStep++;
					break;
				case 2: 
					// locate line and drive					
					
					if(TrackL->Get() && TrackC->Get() && TrackR->Get() && ForkPass == true) // if all three read stop
					{
						FR->Set(-.2);
						BR->Set(-.2);
						FL->Set(-.2);
						BL->Set(-.2);
						autoStep++;
						break;
					}
					if(TrackL->Get() && TrackC->Get() && TrackR->Get() && ForkPass == false)
					{
						ForkPass = true;
						FR->Set(-.5);
						BR->Set(-.5);
						FL->Set(-.5);
						BL->Set(-.5);
						
					}
					else if (TrackL->Get() && TrackR->Get() && AFR) { // if the left and right sensors read a value then turns right 
						speed = 0;
						turn = .7;
						ForkPass = true;
						//printf("TrackL and TrackR\n");
					}
					else if (TrackL->Get() && TrackR->Get() && AFL) { // if the left and right sensors read a value then turns left 
						speed = 0;
						turn = -.7;
						ForkPass = true;
						//printf("TrackL and TrackR\n");
					}
					else if (TrackC->Get()) { // if central tracker reads a value the speed is at .2 forward
						speed = TRACKINGSPEED;
						turn = 0; 
						// printf("STRAIGHT \n");
						//printf ("TrackC\n");
					}
					else if (TrackR->Get()) { // if only the right sensor reads a value the it turns right 
						speed = .7 * TRACKINGSPEED;
						turn = TRACKINGTURN;
						// printf("RIGHT \n");
						//printf ("TrackR\n");
					}
					else if (TrackL->Get()) { // if only the left sensor reads a value then it turns left
						speed = .7 * TRACKINGSPEED;
						turn = -TRACKINGTURN;
						// printf("LEFT \n");
						//printf ("TrackL\n");
					}
					
					else { // if nothing is read motors stop
						speed = 0;
						turn = 0;
						slide = 0;
						//printf(No Track\n");
					}
					
					FR->Set(-speed-turn-slide);
					BR->Set(-speed-turn+slide);
					FL->Set(speed-turn-slide);
					BL->Set(speed-turn+slide);
					
					
					/*
					if(FREncoder->Get() == 100 || FLEncoder->Get() == 100 || BREncoder->Get() == 100 || BLEncoder->Get() == 100)
					{
						FR->Set(0);
						BR->Set(0);
						FL->Set(0);
						BL->Set(0);
						autoStep++;
					}
					*/
					//else if(TrackL->Get() && TrackC->Get() && TrackR->Get())
					//{
					//	autostep++;
					//}
					break;
				case 3:				// place tube
					Wait(1.0);
					Claw->Set(0);
					printf("********SCORE********");
					autoStep++;
					break;

			}	
		printf("EleCycle = %i \n", EleCycle);
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
		//printf("error = %i \n", error);
		myRobot.SetSafetyEnabled(true);
		
		while (IsOperatorControl())
		{			
			ds->GetBatteryVoltage();
			
			if(AirComp->GetPressureSwitchValue() == 0)
			{
				AirComp->Start();
				//printf("Pressure = 0 \n");
			}
			else if(AirComp->GetPressureSwitchValue() == 1)
			{
				AirComp->Stop();
				//printf("Pressure = %d \n", AirComp->GetPressureSwitchValue());
			}			
			
			speed = Deadband(stick1.GetY(), -0.01, 0.01);
			turn = Deadband(stick1.GetZ(), -0.01, 0.01);
			slide = Deadband(stick1.GetX(), -0.01, 0.01);
			
			if(stick1.GetRawButton(5)) slide=-.2;
			else if(stick1.GetRawButton(6)) slide=.2;
			
			
			// maximumPot5V = analog5VIn->GetAverageValue();
			
			FR->Set(-speed-turn-slide);
			BR->Set(-speed-turn+slide);
			FL->Set(speed-turn-slide);
			BL->Set(speed-turn+slide);
			
			if(stick1.GetRawButton(2) && error == false)
			{
				Shoulder->Set(0);
				Claw->Set(1);
				Wrist->Set(0);
				EleState = Floor;
			}
			if(stick1.GetRawButton(11) && error == false)
			{
				Shoulder->Set(0);
				Wrist->Set(0);
				EleState = Lvl1;	
			}
			if(stick1.GetRawButton(12) && error == false)
			{
				Shoulder->Set(0);
				Wrist->Set(0);
				EleState = Lvl2;
			}
			if(stick1.GetRawButton(9) && error == false)
			{
				Shoulder->Set(0);
				Wrist->Set(0);
				EleState = Lvl3;
			}	
			if(stick1.GetRawButton(10) && error == false)
			{
				Shoulder->Set(0);
				Wrist->Set(0);
				EleState = Lvl4;
			}
			if(stick1.GetRawButton(7) && error == false)
			{
				Shoulder->Set(0);
				Wrist->Set(0);
				EleState = Lvl5;
			}
			if(stick1.GetRawButton(8) && error == false)
			{
				Shoulder->Set(0);
				Wrist->Set(0);
				EleState = Lvl6;
			}	
			
			if(EleAuto == false)
			{
				if(stick2.GetRawButton(6)) 
				{
					//EleState = (ElevatorPOT->GetValue() + 5);
					EM->Set(.7); 
					EleManUse = true; 
					EleAuto = false;
				}
				else if(stick2.GetRawButton(7))
				{
					//EleState = (ElevatorPOT->GetValue() - 5);
					EM->Set(-.5);
					EleManUse = true; 
				}
				else if(EleManUse == true)
				{
					EM->Set(0);
					EleState = ElevatorPOT->GetValue();
					EleManUse = false; 
					error = false;
				}
			}
			else if(EleAuto == true)
			{
				EleSet(EleState);
			}
			
			if(stick2.GetRawButton(8) && !lastButton8 && AutoToggle == false)
			{
				EleAuto = false;
				AutoToggle = true;
			}
			else if(stick2.GetRawButton(8) && !lastButton8 && AutoToggle == true)
			{
				EleAuto = true;
				AutoToggle = false;
			}
				
				
			//Manual Control
			
			if(stick2.GetRawButton(3) && WristToggle == false && !lastButton3)
			{
				Wrist->Set(1);
				WristToggle = true;
			}
			else if(stick2.GetRawButton(3) && WristToggle == true && !lastButton3)
			{
				Wrist->Set(0);
				WristToggle = false;
			}
			
			if(((stick1.GetTrigger() == 1) && ClawToggle == false && !lastButton1b) || (stick2.GetRawButton(1) && ClawToggle == false && !lastButton1a))
			{
				Claw->Set(1);
				ClawToggle = true;
				//printf("Claw opening \n");
			}
			else if(((stick1.GetTrigger() == 1) && ClawToggle == true && !lastButton1b) || (stick2.GetRawButton(1) && ClawToggle == true && !lastButton1a))
			{
				Claw->Set(0);
				ClawToggle = false;
				//printf("Claw closing \n");
			}
			
			if(stick2.GetRawButton(2) && ShoulderToggle == false && !lastButton2)
			{
				Shoulder->Set(0);
				ShoulderToggle = true;
			}
			else if(stick2.GetRawButton(2) && ShoulderToggle == true && !lastButton2)
			{
				Shoulder->Set(1);
				ShoulderToggle = false;
			}
			if(stick2.GetRawButton(10) && !lastButton10)
			{
				MinibotLock->Set(0);
				MiniToggle = true;
			}
			if(stick2.GetRawButton(11) && MiniToggle == true  && !lastButton11)
			{
				MinibotArm->Set(1);
			}
			
			lastButton1a = stick2.GetRawButton(1);
			lastButton1b = stick1.GetRawButton(1);
			lastButton2 = stick2.GetRawButton(2);
			lastButton3 = stick2.GetRawButton(3);
			lastButton10 = stick2.GetRawButton(10);
			lastButton11 = stick2.GetRawButton(11);
			lastButton8 = stick2.GetRawButton(8);

			
			//printf("x= %f y= %f z= %f \r\n", stick1.GetX(), stick1.GetY(), stick1.GetZ());
			//printf("speed= %f slide= %f turn= %f \n", speed, slide, turn);
			
			//TrackL->Get() ? printf("TrackL - ") : printf("\t - ");
			//TrackC->Get() ? printf("TrackC - ") : printf("\t - ");
			//TrackR->Get() ? printf("TrackR\n") : printf("\t\n");
			
			// printf("track1= %b track2= %b track3= %b \n", Track1->Get(), Track2->Get(), Track3->Get() ); 
			// printf("err = %i \t", error);
			printf("C %f \t", eleCurr);
			// printf("S %f \t", EleState);
			printf(" T %f \n", eleTemp);
			// printf("Ct %d \t", EleCycle);
			// printf("D %i \n", EMDir);
		
			// printf("Dead = %d \t", EleDead());
			// printf("Status = %d \n", EleCountStat);
			// printf("POT = %d \t", ElevatorPOT->GetValue());
			// printf("EMS = %f\n", EM->Get());
			// printf("throttle = %f \n", stick2.GetThrottle());
			// myRobot.ArcadeDrive(stick1.getRawAxis(1), stick1.getRawAxis(2), true); // drive with arcade style (use right stick)
			// myRobot.MecanumDrive_Polar(stick1.GetRawAxis(1))
			Wait(0.005);				// wait for a motor update time
		}
	}
	
	float Deadband(float val, float min, float max) 
	{
		if(val>min && val<max) return 0;
		return val;	
	}
	
	bool EleDead(void)
	{
		return !(LowEleDead <= ElevatorPOT->GetAverageValue() && ElevatorPOT->GetAverageValue() <= HighEleDead);
	}
	
	void EleSet(float val)
	{
		EleCount();
		eleCurr = ElevatorPOT->GetAverageValue();		
		if(init == false)
		{
			eleTemp = eleCurr;
			init = true;
		}
		if(val <= EleMax && val >= EleMin && error == false)
		{
			if(val <= (eleCurr + 5) && val >= (eleCurr - 5))
			{
				EM->Set(0);
				EMDir = 0;			
			}
			else if(val > (eleCurr - eleTol))
			{
				EM->Set(EleSpeedControl());
				EMDir = 1;				
				if(EleCycle > NumCycle)
				{
					eleTemp = eleCurr;
					EleCycle = 0;
				}
				if (EM->Get() != 0 && error == false)
				{
					if((eleCurr <= (eleTemp + eleTol)) && (eleCurr >= (eleTemp - eleTol)) && EleCycle == NumCycle)
					{
						error = true;
						printf("***************************Err***********************\n");
						printf("***************************Err***********************\n");
						printf("***************************Err***********************\n");
						printf("***************************Err***********************\n");
						printf("***************************Err***********************\n");						
					}
				}
			}
			else if(val < (eleCurr + eleTol))
			{
				EM->Set(EleSpeedControl());	
				EMDir = -1;				
				if(EleCycle > NumCycle)
				{
					eleTemp = eleCurr;
					EleCycle = 0;
				}
				if (EM->Get() != 0 && error == false)
				{
					if((eleCurr <= (eleTemp + eleTol)) && (eleCurr >= (eleTemp - eleTol)) && EleCycle == NumCycle)
					{
						error = true;
						printf("***************************Err DOWN***********************\n");
						printf("***************************Err DOWN***********************\n");
						printf("***************************Err DOWN***********************\n");
						printf("***************************Err DOWN***********************\n");
						printf("***************************Err DOWN***********************\n");
					}
				}
			}
		}

		if(error == true && EMDir == 1 && !EleDead() && EleCountStat == 1)
		{
			EleState = eleCurr - EleErrorAdj;
			if(EleState <= EleMin)
			{
				EleState = Floor;
			}
			printf("Setadj, stop counter, going up\n");
			EleCountStat = 0;
			error = false;
			// EM->Set(0);
		}
		else if(error == true && EMDir == -1 && !EleDead() && EleCountStat == 1)
		{
			EleState = eleCurr + EleErrorAdj;
			if(EleState >= EleMax)
			{
				EleState = EleMax;
			}
			printf("Setadj, stop counter, going down\n");
			EleCountStat = 0;
			error = false;
			// EM->Set(0);
		}
		else if(EleDead())
		{
			EM->Set(0);
			printf("XXXXXXXXXXXXXXXXX DEAD XXXXXXXXXXXXXXXXXX\n");
		}
		else if(error == true || val < EleMin || val > EleMax)
		{
			printf("Set cycle = 0");
			EleCycle = 0;
		}
	}
	
	void EleCount(void)
	{
		if(EleCountStat == 1)
		{
			EleCycle++;
		}
		else if(EleState == eleCurr && EleCountStat == 0)
		{
			EleCycle = 0;
			EleCountStat = 1;
			error = false; 
			//printf("3");
			printf("******Count Resumed*****\n");
		}
	}
	
	float EleSpeedControl(void)
	{
		float SpeedRequest = (EleState - eleCurr)/EleRange;
		//printf("SR = %f /t", SpeedRequest);
		if(0 < SpeedRequest && SpeedRequest <= .1)
		{
			return .65;
		}
		else if(.1 < SpeedRequest && SpeedRequest <= .6)
		{
			return .8;
		}
		else if(1 >= SpeedRequest && SpeedRequest > .6)
		{
			return 1;
		}
		else if(-1 <= SpeedRequest && SpeedRequest <= -.7)
		{
			return -.7;
		}
		else if(-.7 < SpeedRequest && SpeedRequest < -.4)
		{
			return SpeedRequest;
		}
		else if(-.4 <= SpeedRequest && SpeedRequest < 0)
		{
			return -.4;
		}
		else
		{
			return 0;
		}
	}
	
};

START_ROBOT_CLASS(RobotDemo);

