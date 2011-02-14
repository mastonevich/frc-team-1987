#ifndef MYROBOT_H_
#define MYROBOT_H_

#define TRACKINGSPEED  .165;
#define TRACKINGTURN .19;
#define maximumPot5V ;
#define ElevatorSpeed ;

float Lvl1 = 100;
float Lvl2 = 200;
float Lvl3 = 300;
float Lvl4 = 400;
float Lvl5 = 500;
float Lvl6 = 600;

class MyRobot;

class MyRobot : public GenericHID
{
	
public:
	
	float Deadband(float val, float min, float max);
	
private:
	
 
};


#endif
