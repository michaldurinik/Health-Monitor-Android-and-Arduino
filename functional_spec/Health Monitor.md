Health Monitor 
===


### Software Requirements Specification for HealthMonitor

By **Michal Durinik** & **James Toolen**
**06.12.2018**

Table of Contents

1.	Introduction
1.1	Purpose
1.2	Intended Audience and Reading Suggestions
1.3	Product Scope
1.4	References

2.	Overall Description
2.1	Product Perspective
2.2	Product Functions
2.3	User Classes and Characteristics
2.4	Operating Environment
2.5	User Documentation
2.6 Assumptions and Dependencies
3.	External Interface Requirements
3.1	User Interfaces
3.2	Hardware Interfaces
3.3	Software Interfaces
3.4	Communications Interfaces

4.  Appendix A: Glossary
<div style="page-break-after: always;"></div>


### 1. Introduction

##### 1.1 Purpose 

This is revision number One in which we aim to produce an easy to use a system where Jimmy McGill can monitor Jimmy's health and wellbeing. Data will be collected through the of AdaFruits wearable technology connected to a variety of sensors that collect vital signs of a body. Data collected will be portrayed to the user via an Android application in a variety of representations, graphs, pie charts, recommendations, timescales. (this list is not exhaustive) under preset settings with memory allocations for a number of user-defined settings. This document aims to cover the entire product. This specification is not complete.


##### 1.2 Intended Audience and Reading Suggestions

This is a functional specification for the user of our product and will contain the minimum technical jargon as possible, A glossary has been included to help with understanding and to clear up the ambiguity. It contains a walkthrough of the products use. Pretty pictures of our vision of how we want the user interface to look and feel, our thoughts on users interactions and expected experience.

##### 1.3 Product Scope

We envisage Chuck wearing our wristwatch and Hawaiian shirt all kitted out with sensors, relaying the information to the Android phone in Chucks fannypack as he goes Jogs on the treadmill. Chuck's objective is to stay trim, just in case, the day comes he bumps into the ex-wife and can benefit from knowing when his temperature and heart rate are meeting optimal conditions to safely push his body a little further. Our aim is to provide Chuck with data views representing different aspects to his training regime in an easy to use and interpret application with save options.
Chuck can be a little absent-minded, and these days its getting worse. Chucks brother Jimmy likes to keep his own mind at ease and checks up on Chuck whenever he gets the chance. But time seems to be short these days and distances longer. Jimmy worries for his brother.
Jimmy bought his brother a gift, a lovely Hawaiian shirt, and bracelet. He installed an app onto Chuck's phone and connected to the bracelet by turning on the Bluetooth application and accepting the connection. Jimmy only has to use a certain pre-programmed setting and enter his own phone number to receive alerts to Jimmy's phone. Jimmy will only receive the dangerous vital sign data and a coordinate location of Chuck's whereabouts.

<div style="page-break-after: always;"></div>

##### 1.4 References

Comparison of pedometer and accelerometer accuracy under controlled conditions.
https://www.ncbi.nlm.nih.gov/pubmed/12750599,
https://insights.ovid.com/crossref?an=00005768-200305000-00022
Le Masurier GC1, Tudor-Locke C.
2003/05/01

<div style="page-break-after: always;"></div>

### 2. Overall Description

##### 2.1 Product Perspective

This is a new, self-contained product. It may contain certain characteristics of fitness tracking monitors already on the market. Aside from the heart rate trackers and the pedometers, our aim is to incorporate these with temperature sensors, GPS location, accelerometer, quick message LCD display and smartphone technology to produce a unique and fuller experience for the user.

##### 2.2 Product Functions

Chuck gets instant updates of heart rate on LCD
Chuck gets instant current velocity updates on LCD
Chuck gets instant body temperature updates on LCD
Chuck can access graph representations of the above variables on phone application
Chuck can use the application to help monitor and learn about his personal vital statistics and personal thresholds 
Chuck can enter Jimmy and/or Kims details to be alerted of Chuck's location if Chucks vital signs should go beyond Chuck's boundaries.

![](https://i.imgur.com/tRLwvPd.png)

<div style="page-break-after: always;"></div>

##### 2.3 User Classes and Characteristics

Performance athleates
Fitness enthusiasts
Weight watchers
Dementia sufferers
Parental guidence

##### 2.4 Operating Environment

1. ##### AdaFruit Flora:
    * Chipset: ATmega32u4
    * Battery input (JST): 3.5-16V
    * Regulator is MIC5225-3.3 (reverse polarity protection)
    * 2A max rated connector.
    * Input Voltage (recommend): 6VDC
    * USB input: 4.5V-5.5V with 500mA fuse
    * 3.3V output pad(recommend) < 100mA
    * Clock speed: 8MHz
    * VBAT output pad: 2 schottkey diode connection
    * Current Draw: 8mA quiescent + 2mA when for LED.

2. ##### Arduino UNO
    * Microcontroller: ATmega328
    * Operating Voltage: 5V
    * Input Voltage (recommended): 7-12V
    * Input Voltage (limits): 6-20V
    * Digital I/O Pins: 14 (of which 6 provide PWM output)
    * Analog Input Pins: 6
    * DC Current per I/O Pin: 40 mA
    * DC Current for 3.3V Pin: 50 mA
    * Flash Memory: 32 KB of which 0.5 KB used by bootloader
    * Clock Speed: 16 MHz

3. ##### Android 6.0 Marshmallow

4. ##### Wear OS

<div style="page-break-after: always;"></div>

##### 2.5 User Documentation

Online User manual and help guide.

##### 2.6 Assumptions and Dependencies

Not applicable as everything is Open source.


### 3. External Interface Requirements

##### 3.1 User Interfaces

###### (This specification is not final)

Chuck will open the application on his smartphone.
Chuck will see a welcome screen while the app loads.
Chuck will be taken to a menu screen and given the options:
1. Start Session
3. View Personal
4. Choose Setting

Chuck chooses 1.
  * Chuck is taken to a screen that displays (landscape):
  1. Main focus: Timer/Distance Covered
  2. Felt Top Left: Battery level of braclet/Flora
  3. Felt Top Right: Heart rate bpm
  4. Felt Bottom Right: 
  5. Felt Bottom Left: Body Temperature

Chuck chooses 2.
  * Chuck is taken to an options screen:
  1. My Progress
  2. My Limits
      * Chuck chooses 1.
          * Chuck is taken to a pre-defined screen setting choice of displayed data:
      * Chuck chooses 2.
          * Chuck is taken to a pre-defined screen setting choice of displayed data:

<div style="page-break-after: always;"></div>

Chuck chooses 3.
  * Chuck is taken to an options screen:
  1. Choices
  2. Personal
  3. Alterations
      * Chuck chooses 1.
          * Chuck is taken to options screen when selected each options brings Chuck to a description page of that setting and what to expect:
              1. Movement Tracker
              2. Fitness Tracker
              3. Health Tracker
              4. Location Tracker
      * Chuck Chooses 2.
          * Chuck is taken to an options screen:
              1. Views Configuration
              2. Data Configuration
                  * Chuck chooses 1. and is presented with a range of graph representaions to display a cardinality of input
                  * Chuck chooses 2. and is presented with dimensional options for data collections
      * Chuck chooses 3.
          * Chuck is taken to an input screen where all variable data can be updated manually



##### 3.2 Hardware Interfaces
Each sensor will connect to exactly one other sensor via I2C bus and 2 pins to carry current, the last sensor will be connected to LCD and this entire series to Flora board.
* Sensors and LCD display
    * 2 pins will be used on each side of connection of the I2C bus.
        1. SDA
        2. SCL
    * 2 more pins will be used to carry current
        1. 3V
        2. GND

<div style="page-break-after: always;"></div>

* Bluetooth module 
    * connects to Flora board via pins:
        1. RX
        2. TX
        3. 3V
        4. GND
    * connects to smarthphone via:
        1. bluetooth wireless radio transmitter/receiver

* Battery
    * connects to Flora via pins:
        1. VBATT
        2. GND
        
        
##### 3.3 Software Interfaces

Android 6.0 Marshmallow
Arduino Playground
Flora-2 packages
Sent data will all be in/a bit string

##### 3.4 Communications Interfaces

None - changing xml permissions on android phone gives access to sms messaging.

<div style="page-break-after: always;"></div>

### Appendix A: Glossary

**IDE**
An Integrated Development Environment for software developers which is providing tools for programmers to maximize their productivity.

**Android studio**
The official (IDE) for Google's Android operating system which is used in majority of the world's smartphones.

**Microcontroler**
Very small computer on single integrated circuit board

**Arduino**
Company and product with the same name. Is microcontroler board with variety of sensors, digital and analog inputs/outputs for building digital devices.

**Flora**
Small microcontroler board from Adafruit company used mostly as wearable device or sewin into the clothes.

**C++**
General purpose programming language, specially good in environment where efficiency and flexibility is needed.

**Java**
General purpose programming language, which has advantage or running in all environments without chnages needed.

**Git**
Version-control system for keeping track of changes in computer files

**GitLab**
Online Git repository manager running on server, where users have access to these files and can collaborate.

**LCD**
Liquid Crystal Display, used mostly in computer display screens.

**GPS**
Global Positioning System is satelite based navigation system which provide location to GPS receiver anywhere on the Earth.

**Bluetooth**
Wireless technology standard to exchange data over short distances.

**I2C bus**
Inter-Integrated Circuit, simple and flexible way to transfer digital data between two electronic devices

**3V**
3 volts, or 3 units of electric potential.

**SDA**
Serial Data, conductive wire in I2C for transfering data

**SCL**
Serial Clock, conductive wire in I2C for transfering data

**GND**
Ground or earth, reference point in electrical circuit from which voltages are measured.