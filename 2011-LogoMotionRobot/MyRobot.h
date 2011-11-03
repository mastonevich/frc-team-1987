#ifndef MYROBOT_H_
#define MYROBOT_H_

#define TRACKINGSPEED  -.26;
#define TRACKINGTURN .26;
#define ElevatorSpeed ;

float Lvl1 = 160;		//lvl 1 outside
float Lvl2 = 235;		//lvl 2 outside
float Lvl3 = 175;		//lvl 1 middle
float Lvl4 = 239;		//lvl 2 middle
float Lvl5 = 517;		//lvl 3 outside
float Lvl6 = 540;		//lvl 3 middle 561
float Floor = 48;  //48
float EleMin = 45;  
float EleMax = 562;
float eleTemp;
float EleRange = (EleMax - EleMin);
float MissFix = 70;
float EleStop = .18;
bool init = false;
bool error = false; 
bool EleAuto = true;


const int eleTol = 4;  //2
int EleCycle = 0;
const int NumCycle = 50;
int EMDir = 0;
const int EleErrorAdjUp = 25;
const int EleErrorAdjDown = 50;
const int LowEleDead = 33; //45
const int HighEleDead = 563;
bool EleCountStat = 1;

class MyRobot;

class MyRobot : public GenericHID
{
	
public:
	
	float Deadband(float val, float min, float max);
	bool EleDead(void);
	void EleSet(float val);
	void EleCount(void);
	float EleSpeedControl(void);
	
private:
	
 
};


#endif
