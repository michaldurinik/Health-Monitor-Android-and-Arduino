Year 3 Project Proposal

# Body Health Monitor

**Michal Durinik**		ID:	15737195  
**James Toolen**		ID: 15735065

**Dr. David Sinclair**		*Supervisor*

# 
### Description
Our proposed project consists of using a fully-featured wearable electronic platform, an Arduino-compatible microcontroller, Adafruit’s FLORA. It will be attached to variety of sensors collecting data, mostly related to the health of the user. Data will be sent to an android phone over bluetooth, where it will be processed. Relevant data will be displayed on microcontroller’s own small LCD. Our hope is to create an easy to use system which can monitor the user’s health and wellbeing. Graphs will be generated in a user friendly way with specific presets based on time periods. With user defined presets and the ability to compare them with previously collected data and that representing an average healthy person of the user's profile. Then it will notify user with recommendations or send alerts in case of abnormal results.

Initially we plan to mainly use this wearable board with heat sensors, GPS, heart rate monitor, accelerometer and bluetooth attachments and then possibly expand as we get more experience with the platform. We want to experiment with another Arduino boards (uno/lilypad, etc.) as well, as in general it is easier to get them or accessories for them compared to the flora board. 

Data gathered will include Location. Body temperature. Distance travelled over a period of time. Fluctuations in heart rate, We would like to look into capturing a respiratory rate also. Some readings to be displayed on an LCD display for instant readability of vital statistics, LCD display also can be programmed from the phone application as to what statistics are to be displayed or to alert the user to any impending serious situation or just display a daily mindful reminder.

A limit for temperature, respiratory and heart rate will be set and an alert with location can be sent to a predefined number if any of these limits are reached. A very handy feature for someone who cares for an ill person.

An elegant touch interface on the Android application will present the data to the user in a variety of ways. Temperature can be presented in Celsius and Fahrenheit for example. Heart rate can be displayed numerically or visually as a flatline with sound or with a ripple type graphic effect. Different modules can be turned off and on. Limits can be changed and set based on ethnicity, sex, age and physical condition. Messages can be relayed to LCD display to alert user of optimum conditions for fat burning or cardio workouts derived from heart rate and body temperature sensors.

If there is time we would like to look into attaching a blood\sugar level testing feature. Maybe trying to incorporate it into the wrist heart rate monitor.
# 
### Programming language(s) 
-	Java for the Android phone application
-	C++ for the Microcontroller/s

### Programming tool(s)
-	Android studio
-	Arduino IDE
-	GitLab

### Learning Challenges
-	C++
-   Microcontrollers programing
-   All different sensors and their communication with board
-   Mobile application programming
-   Microcontroller and phone communication over bluetooth
-	Using the Git repositories interfaces and commands
-	JUnit for testing Java methods
-	Working through a project from concept to product

### Hardware / software platform
-	Windows PC 8.1/10
-	Arch Linux PC
-	Android phone 6.0
-	Android emulator
	
### Special hardware / software requirements
-	Adafruits’ Flora microcontroller
-	Arduino microcontroller
-	Small LCD display
-	Bluetooth module
-	Temperature sensors
-	Heart rate monitor
-	Accelerometer
-	GPS
