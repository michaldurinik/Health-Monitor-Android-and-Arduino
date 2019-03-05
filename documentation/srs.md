# Software Requirements Specification

## Health and Wellbeing Monitor

### Michal Durinik
### James Toolen

#### Purpose of this document

This is not a project plan. But a guide to the system architecture and development. It is not for phasing, timelines or deliverables.

This document is divided into three sections:
- Project Overview
- Information Architecture
- Site design

	

#### Project Overview

This document is intended as a complete guide for the user in using the Health and Wellbeing Monitor. Designed specifically for the non technical person. By reading this guide you will learn how to use the Health and Wellbeing Monitor app and sensor shirt.
The product is a Heart rate/ Temperature sensing/ Global positioning phone application with hardware in a sweatshirt. Sensors send data to the phone application where it is transformed for graphical interpretation
Providing the user with real time statistics to use for fitness and wellbeing also if the vital signs are below a specific region the application can send an emergency SMS message to a pre-defined receiver.


#### Hardware and Hosting

The phone app can be hosted on any Android phone with firmware api 23 or higher..
Sweatshirt contains a flora micro processing board with added bluetooth adapter, temperature sensors and heart rate tracker.

#### Information Architecture

Turn shirt component on, and wear the shirt upon the body.

Start Android phone Health and wellbeing application

User is presented with notifications giving permission choices, which are to accept or decline
Grant application the permissions needed to give full experience of the app

![alt text](https://gitlab.computing.dcu.ie/durinim2/2019-ca326-mdurinik-healthmonitor/blob/master/documentation/images/Screenshot_20190305-101742.png "Permissions needed")

Background activities include connection of bluetooth device, retrieving the global position of phone, 

User is presented with the main screen with a list of two choices
>Personal Settings and
>Monitor Progress

![alt text](https://gitlab.computing.dcu.ie/durinim2/2019-ca326-mdurinik-healthmonitor/blob/master/documentation/images/Screenshot_20190305-101632.png "Entry screen")

User chooses the first choice Personal settings and is presented with a list of entry and display items including:
>Height
>Weight
>Body mass index
>Global position
>Saved emergency number

![alt text](https://gitlab.computing.dcu.ie/durinim2/2019-ca326-mdurinik-healthmonitor/blob/master/documentation/images/Screenshot_20190305-101641.png "Settings screen")

All the above will display previously obtained information if available if not the

User can choose any option and be taken to a new screen to enter chosen option. All except two 
The Body mass index which is calculated and displayed by the entering of height and weight the choice of all three will bring the user to the same entry screen.

And the Global Position will update from a background process started with the permissions given at the start of the app

Saved emergency number will display last saved number or if chosen takes the user to an entry screen to obtain a new number

Height, Weight and Body mass index will display previously entered information if available or if any are chosen take the user to a shared entry screen where the user can enter both details and have the calculated BMI displayed
From the main screen again and the user chooses the second option of Monitor Progress the user is taken to a screen upon which is displayed all of the sensor data from the bluetooth connection with the shirt and the sensor on it these include
the current temperature and current heart rate and body mass index

The user will receive notifications when limits are met for matching patterns as such
User is at optimal levels for fitness schedule
User is off Geographical pattern and vitals are distressed

Sms message with coordinates and vital signs sent to predefined emergency number from the entry on the settings screen 