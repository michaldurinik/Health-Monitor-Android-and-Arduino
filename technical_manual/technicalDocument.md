# Technical Document

 

### Table of contents

## 1. Introduction

    Overview
    
The Health and Wellbeing Monitor portrays to its user information collected from sensors in a garment. The information is analysed by a tiny computer in the jacket and passed on an Android mobile telephone. Where upon the data will be asessed and displayed numerically for the user to observe.

    Glossary

**Microcontroler**
Very small computer on single integrated circuit board

**Flora**
Small microcontroler board from Adafruit company used mostly as wearable device or sewn into the clothes.

**GitLab**
Online Git repository manager running on server, where users have access to these files and can collaborate.

**LCD**
Liquid Crystal Display, used mostly in computer display screens.

**GPS**
Global Positioning System is satelite based navigation system which provide location to GPS receiver anywhere on the Earth.

**BLE**
Bluetooth Low Energy Wireless technology standard to exchange data over short distances.

**I2C bus**
Inter-Integrated Circuit, simple and flexible way to transfer digital data between two electronic devices

**3V**
3 volts, or 3 units of electric potential.

**SDA**
Serial Data, conductive wire in I2C for transfering data

**SCL**
Serial Clock, conductive wire in I2C for transfering data

**GND**
Ground or earth, reference point in electrical circuit from which voltages are measured.hone.

**GATT**
Generic Attribute protocol used for communicating with BLE

**UUID**
Universally Unique Identifier a 126-bit number used to identify information in computer systems

**UART**
Universal Asynchronous Receiver/Transmitter a circuit in a microcontroller




## 2. System Architecture

A wearable, but not waterproof micro processing unit called a Flora, developed by AdaFruit. Has attatched to it a heart rate sensor, a multitude of temperature sensors and a bluetooth device to transmit the data from the different sensors.

The User will wear a jacket or a jumper, with:
 - Flora microprocesser
 - Bluefruit LE bluetooth
 - 3 LM35 Temperature sensors
 - Heart Rate light sensor
 - Battery holder with connector and switch

sewn into the chosen garment.

An Adroid phone application with a low energy bluetooth connection to receive data from the Bluefruit LE and display the information on the phones' screen.

Phones used for the testing of the application were:

- HTC one M8api 23 marshmallow
- HTC Desire 825 api 23 marshmallow

Emulators used in the testing of the application were:

- Nexus 5

The phone application provided for interaction with the jacket consists of three main activities:

- Greeting screen
- Personal settings screen
- Sensor monitor screen

The greetings screen is an activity containing a list with the other two activities as the options
The personal settings screen is an activity containg a list of editable and read only choices allowing to user to enter their personal information needed for the smooth running of the product.
From the entries into the height and weight categories the body mass index is calculated and displayed
The Sensor monitor screen is were the magic happens, once selected and the screen is displayed a background service starts and connects to the jackets bluetooth advertisement.
The monitor screen is then updated when the jackets sensors change.

***
This section describes the high-level overview of the system architecture showing the distribution functions across (potential) system modules. Architectural components that are reused or 3rd party should be highlighted. Unlike the architecture in the Functional Specification - this description must reflect the design components of the system as it is demonstrated.
***



## 3. High-Level Design

![]("img/ts_context.png")

***
This section should set out the high-level design of the system. It should include system models showing the relationship between system components and the systems and its environment. These might be object-models, DFD, etc. Unlike the design in the Functional Specification - this description must reflect the design of the system as it is demonstrated.
***
## 4. Problems and Resolution

Understanding and coming to terms with Androids architecture proved to be challenging at first.
Communicating with the main thread.
Communicating with the bluetooth protocol and then getting those communications back to the main thread to update the display screen.
With perserverence, sleepless nights and gigabytes of reverse engineered likened code we powered through with sheer relentless brute force.
Busy schedules, poor project management, budget constraints all had a part in a hectic final few weeks. The initial plan was to have three clones some unplanned for costs meant we had to be careful with just one.
Schedules and responsibilities resticted any prolonged gathering and collaboration and therefore nothing less than copious amounts of coffee was needed at times when nana wouldnt allow during the final sprint. Unfortunatly this also played a part into not keeping in touch with our supervisor as regular as we would have liked and we tried to make up for it by badgering him a little late on.


***
This section should include a description of any major problems encountered during the design and implementation of the system and the actions that were taken to resolve them.
***

## 5. Installation Guide

***
This is a 1 to 2 page section which contains a step by step software installation guide. It should include a detailed description of the steps necessary to install the software, a list of all required software, components, versions, hardware, etc.
***