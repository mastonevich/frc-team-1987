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
float EleMin = 70;
float EleMax = 620;


class MyRobot;

class MyRobot : public GenericHID
{
	
public:
	
	float Deadband(float val, float min, float max);
	float EleIdle();
	void EleSet(float val);
	
private:
	
 
};


#endif
