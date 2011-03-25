#ifndef MYROBOT_H_
#define MYROBOT_H_

#define TRACKINGSPEED  -.26;
#define TRACKINGTURN .2;
#define ElevatorSpeed ;

float Lvl1 = 180;		//lvl 1 outside
float Lvl2 = 245;		//lvl 2 outside
float Lvl3 = 190;		//lvl 1 middle
float Lvl4 = 260;		//lvl 2 middle
float Lvl5 = 535;		//lvl 3 outside
float Lvl6 = 605;		//lvl 3 middle
float Floor = 65;
float EleMin = 60;
float EleMax = 620;
float eleTemp;
float EleRange = (EleMax - EleMin);

bool init = false;
bool error = false; 
bool EleAuto = true;


const int eleTol = 2;
int EleCycle = 0;
const int NumCycle = 50;
int EMDir = 0;
const int EleErrorAdj = 50;
const int LowEleDead = 60;
const int HighEleDead = 700;
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
