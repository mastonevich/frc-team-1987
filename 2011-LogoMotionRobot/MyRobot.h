#ifndef MYROBOT_H_
#define MYROBOT_H_

#define TRACKINGSPEED  .165;
#define TRACKINGTURN .19;
#define ElevatorSpeed ;

float Lvl1 = 180;
float Lvl2 = 245;
float Lvl3 = 190;
float Lvl4 = 260;
float Lvl5 = 535;
float Lvl6 = 605;
float EleMin = 75;
float EleMax = 620;
float eleTemp;
float EleRange = (EleMax - EleMin);

bool init = false;
bool error = false; 
bool EleAuto = true;

int eleTol = 1;
int EleCycle = 0;
int NumCycle = 37;
int EMDir = 0;
int EleCountStat = 1;

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
