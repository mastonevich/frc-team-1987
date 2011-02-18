#ifndef MYROBOT_H_
#define MYROBOT_H_

#define TRACKINGSPEED  .165;
#define TRACKINGTURN .19;
#define maximumPot5V ;
#define ElevatorSpeed ;

float Lvl1 = 900;
float Lvl2 = 920;
float Lvl3 = 600;
float Lvl4 = 620;
float Lvl5 = 300;
float Lvl6 = 300;

class MyRobot;

class MyRobot : public GenericHID
{
	
public:
	
	float Deadband(float val, float min, float max);
	
private:
	
 
};


#endif
