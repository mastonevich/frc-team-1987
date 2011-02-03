#ifndef MYROBOT_H_
#define MYROBOT_H_

#define TRACKINGSPEED  .165;
#define TRACKINGTURN .19;

class MyRobot;


class MyRobot : public GenericHID
{
	
public:
	
	float Deadband(float val, float min, float max);
	
private:

};


#endif
