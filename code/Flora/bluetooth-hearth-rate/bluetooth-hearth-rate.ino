/********************************************************************/
/*                              LIBRARIES                           */
/********************************************************************/

// BASIC
#include <Arduino.h>
//#include <SPI.h>

// Bluefruit BLE module
#include "Adafruit_BLE.h"
#include "Adafruit_BluefruitLE_SPI.h"
#include "Adafruit_BluefruitLE_UART.h"
#include "BluefruitConfig.h"

// Display SSD1306, Monochrome Oled connected to I2C (SDA, SCL pins)
#include <Wire.h>
#include <Adafruit_GFX.h>
#include <Adafruit_SSD1306.h>

// PulseSensor
#define USE_ARDUINO_INTERRUPTS true   // Set-up low-level interrupts for most accurate BPM math.
                                      // must be before including library
#include <PulseSensorPlayground.h>

// Temperature Sensor
#include <OneWire.h> 
#include <DallasTemperature.h>


/********************************************************************/
/*                             DEFINITIONS                          */
/********************************************************************/

// Display
#define SCREEN_WIDTH 128              // OLED display width, in pixels
#define SCREEN_HEIGHT 32              // OLED display height, in pixels
#define OLED_RESET     12             // Reset pin # (or -1 if sharing Arduino reset pin)
Adafruit_SSD1306 display(SCREEN_WIDTH, SCREEN_HEIGHT, &Wire, OLED_RESET);

#define LOGO_HEIGHT   16
#define LOGO_WIDTH    16
static const unsigned char PROGMEM logo_bmp[] =
{ B00000000, B11000000,
 B00000001, B11000000,
 B00000001, B11000000,
 B00000011, B11100000,
 B11110011, B11100000,
 B11111110, B11111000,
 B01111110, B11111111,
 B00110011, B10011111,
 B00011111, B11111100,
 B00001101, B01110000,
 B00011011, B10100000,
 B00111111, B11100000,
 B00111111, B11110000,
 B01111100, B11110000,
 B01110000, B01110000,
 B00000000, B00110000 };
 
// Temperature Sensor
#define ONE_WIRE_BUS 9                // pin number
OneWire oneWire(ONE_WIRE_BUS);        // Setup a oneWire instance to communicate with any OneWire devices 
                                      // (not just Maxim/Dallas temperature ICs) 
DallasTemperature sensors(&oneWire);  // Pass our oneWire reference to Dallas Temperature.

// PulseSensor
PulseSensorPlayground pulseSensor;    // Creates an instance of the PulseSensorPlayground object called "pulseSensor"


// BLE
/*
#if SOFTWARE_SERIAL_AVAILABLE
  #include <SoftwareSerial.h>
#endif
*/

// Create the bluefruit object, either software serial...uncomment these lines
/*
SoftwareSerial bluefruitSS = SoftwareSerial(BLUEFRUIT_SWUART_TXD_PIN, BLUEFRUIT_SWUART_RXD_PIN);

Adafruit_BluefruitLE_UART ble(bluefruitSS, BLUEFRUIT_UART_MODE_PIN,
                      BLUEFRUIT_UART_CTS_PIN, BLUEFRUIT_UART_RTS_PIN);
*/

/* ...or hardware serial, which does not need the RTS/CTS pins. Uncomment this line */
Adafruit_BluefruitLE_UART ble(BLUEFRUIT_HWSERIAL_NAME, BLUEFRUIT_UART_MODE_PIN);

/* ...hardware SPI, using SCK/MOSI/MISO hardware SPI pins and then user selected CS/IRQ/RST */
// Adafruit_BluefruitLE_SPI ble(BLUEFRUIT_SPI_CS, BLUEFRUIT_SPI_IRQ, BLUEFRUIT_SPI_RST);

/* ...software SPI, using SCK/MOSI/MISO user-defined SPI pins and then user selected CS/IRQ/RST */
//Adafruit_BluefruitLE_SPI ble(BLUEFRUIT_SPI_SCK, BLUEFRUIT_SPI_MISO,
//                             BLUEFRUIT_SPI_MOSI, BLUEFRUIT_SPI_CS,
//                             BLUEFRUIT_SPI_IRQ, BLUEFRUIT_SPI_RST);


/********************************************************************/
/*                              VARIABLES                           */
/********************************************************************/

// Pulse Sensor
const int PulseWire = 10;       // PulseSensor PURPLE WIRE connected to ANALOG PIN 0
const int LED = 7;              // The on-board Arduino LED, 7 for Flora.
int Threshold = 550;            // Determine which Signal to "count as a beat" and which to ignore.
                                // Use the "Gettting Started Project" to fine-tune Threshold Value beyond default setting.
                                // Otherwise leave the default "550" value.

// TMP36 Pin Variables
//int sensorPin = 9;            // the analog pin the TMP36's Vout (sense) pin is connected to
                                // the resolution is 10 mV / degree centigrade with a
                                // 500 mV offset to allow for negative temperature

// BLE Service information
int32_t hrmServiceId;
int32_t hrmMeasureCharId;
int32_t hrmLocationCharId;
int32_t uuid;

                              
// A small helper
void error(const __FlashStringHelper*err) {
  Serial.println(err);
  while (1);
}


/********************************************************************/
/*                               SETUP                              */
/********************************************************************/

void setup(void)
{

  // while (!Serial);    // required for Flora & Micro
  // delay(500);         // turns out not required and can work on battery without serial connected.
  sensors.begin(); //dallas temperature sensors
  boolean success;

  Serial.begin(115200);
  ////////////////////////////////
  //Display
  // SSD1306_SWITCHCAPVCC = generate display voltage from 3.3V internally
  if(!display.begin(SSD1306_SWITCHCAPVCC, 0x3C)) { // Address 0x3C for 128x32
    Serial.println(F("SSD1306 allocation failed"));
    for(;;); // Don't proceed, loop forever
  }

  // Show initial display buffer contents on the screen --
  // the library initializes this with an Adafruit splash screen.
  //display.display();
  //delay(2000); // Pause for 2 seconds
  display.clearDisplay();

  display.setTextSize(2); // Draw 2X-scale text
  display.setTextColor(WHITE);
  display.setCursor(0, 0);
  display.println(F("Health"));
  display.println(F("Monitor"));
  display.display();      // Show initial text
  // delay(500);
  // display.startscrollright(0x00, 0x0F);
  // delay(4300);
  // display.stopscroll();


  /********************************************************************/
  // PulseSensor

  Serial.println(F("Adafruit Bluefruit Heart Rate Monitor (HRM) Example"));
  Serial.println(F("---------------------------------------------------"));

  // Configure the PulseSensor object, by assigning our variables to it. 
  pulseSensor.analogInput(PulseWire);   
  pulseSensor.blinkOnPulse(LED);       //auto-magically blink Arduino's LED with heartbeat.
  pulseSensor.setThreshold(Threshold);

  // Double-check the "pulseSensor" object was created and "began" seeing a signal. 
  if (pulseSensor.begin()) {
    Serial.println("We created a pulseSensor Object !");  //This prints one time at Arduino power-up,  or on Arduino reset.  
  }


  /********************************************************************/
  // Bluefruit

  /* Initialise the module */
  Serial.print(F("Initialising the Bluefruit LE module: "));

  if ( !ble.begin(VERBOSE_MODE) )
  {
    error(F("Couldn't find Bluefruit, make sure it's in CoMmanD mode & check wiring?"));
  }
  Serial.println( F("OK!") );

  /* Perform a factory reset to make sure everything is in a known state */
  Serial.println(F("Performing a factory reset: "));
  if (! ble.factoryReset() ){
    error(F("Couldn't factory reset"));
  }

  /* Disable command echo from Bluefruit */
  ble.echo(false);

  //Serial.println("Requesting Bluefruit info:");
  /* Print Bluefruit information */
  //ble.info();

  // this line is particularly required for Flora, but is a good idea
  // anyways for the super long lines ahead!
  ble.setInterCharWriteDelay(5); // 5 ms


  /********************************************************************/
  // BLE AT commands and UUIDs (GATT advertisement characteristics)

  /* Change the device name to make it easier to find */
  Serial.println(F("Setting device name to 'Health Monitor': "));

  if (! ble.sendCommandCheckOK(F("AT+GAPDEVNAME=Health Monitor")) ) {
    error(F("Could not set device name?"));
  }

  /* Add the Heart Rate Service definition */
  /* Service ID should be 1 */
  Serial.println(F("Adding the Heart Rate Service definition (UUID = 0x180D): "));
  success = ble.sendCommandWithIntReply( F("AT+GATTADDSERVICE=UUID=0x180D"), &hrmServiceId);
  if (! success) {
    error(F("Could not add HRM service"));
  }

  /* Add the Heart Rate Measurement characteristic */
  /* Chars ID for Measurement should be 1 */
  Serial.println(F("Adding the Heart Rate Measurement characteristic (UUID = 0x2A37): "));
  success = ble.sendCommandWithIntReply( F("AT+GATTADDCHAR=UUID=0x2A37, PROPERTIES=0x10, MIN_LEN=2, MAX_LEN=3, VALUE=00-40"), &hrmMeasureCharId);
  if (! success) {
    error(F("Could not add HRM characteristic"));
  }

  /* Add the Body Sensor Location characteristic */
  /* Chars ID for Body should be 2, Finger = 3 */
  Serial.println(F("Adding the Body Sensor Location characteristic (UUID = 0x2A38): "));
  success = ble.sendCommandWithIntReply( F("AT+GATTADDCHAR=UUID=0x2A38, PROPERTIES=0x02, MIN_LEN=1, VALUE=3"), &hrmLocationCharId);
  if (! success) {
    error(F("Could not add BSL characteristic"));
  }

  /* Add the Heart Rate Service to the advertising data (needed for Nordic apps to detect the service) */
  Serial.print(F("Adding Heart Rate Service UUID to the advertising payload: "));
  ble.sendCommandCheckOK( F("AT+GAPSETADVDATA=02-01-06-05-02-0d-18-0a-18") );

  //ble.sendCommandWithIntReply( F("AT+BLEGETADDR"), &uuid ); //get device address 48bit: "D4:57:7B:EA:6C:D7"
  //AT+GATTADDSERVICE=UUID128=00001101-0000-1000-8000-00805f9b34fb; 

  /* Reset the device for the new service setting changes to take effect */
  // Serial.print(F("Performing a SW reset (service changes require a reset): "));
  ble.reset();

  Serial.println();
}


/********************************************************************/
/*                                LOOP                              */
/********************************************************************/

void loop(void)
{
  // PulseSensor only send data to display and BLE when heart beat happens
  int heart_rate = pulseSensor.getBeatsPerMinute();   // Calls function on our pulseSensor object that returns BPM as an "int".
                                                      // "myBPM" hold this BPM value now.  
  if (pulseSensor.sawStartOfBeat()) {                 // Constantly test to see if "a beat happened". 
    // Serial.println("♥  A HeartBeat Happened ! ");    // If test is "true", print a message "a heartbeat happened".
    Serial.print("BPM: ");                              // Print phrase "BPM: " 
    Serial.println(heart_rate);                         // Print the value inside of myBPM.

    // sending BPM data to BLE as HRM GATT
    ble.print( F("AT+GATTCHAR=") );
    ble.print( hrmMeasureCharId );
    ble.print( F(",00-") );
    ble.println(heart_rate, HEX);

    // sending BLE data as UART (serial)
    //ble.print("AT+BLEUARTTX="); //responsible for sending to phone over UART
    //ble.print(heart_rate);
    //ble.println(F(" BPM  "));

    // call sensors.requestTemperatures() to issue a global temperature 
    // request to all devices on the bus 

    // Temperature Sensor
    //Serial.print(" Requesting temperatures..."); 
    sensors.requestTemperatures(); // Send the command to get temperature readings 
    Serial.println("DONE"); 
    Serial.print("Temperature is: "); 
    float temp = sensors.getTempCByIndex(0);
    temp = temp + 6.0; //offset for finger position vs core temperature
    Serial.print(temp);

    //Serial.print(sensors.getTempCByIndex(0)); // Why "byIndex"?  
    // You can have more than one DS18B20 on the same bus.  
    // 0 refers to the first IC on the wire 

    delay (1000); //give time for phone to give response back

    /* Check if command executed OK */
    if ( !ble.waitForOK() )
    {
      Serial.println(F("Failed to get response!"));
    }

    display.clearDisplay();

    display.setTextSize(2);             // Normal 1:1 pixel scale
    display.setTextColor(WHITE);        // Draw white text
    display.setCursor(0,0);             // Start at top-left corner
    display.print(heart_rate);
    display.println(F(" BPM  "));

    display.setTextSize(1);             // Normal 1:1 pixel scale
    display.setTextColor(WHITE);        // Draw white text
    display.setCursor(85,0);            // Start at top-left corner

    display.print(temp);
    display.println(F(" C"));
    display.display();
}
delay(20);                              // if not pulse reading
  
  // LM35 temp sensor
  /*
  int reading = analogRead(sensorPin); 

  Serial.print(reading); Serial.println(" reading");

  // converting that reading to voltage, for 3.3v arduino use 3.3
  float voltage = reading * 3.3;
  voltage /= 1024.0; 

  // print out the voltage
  Serial.print(voltage); Serial.println(" volts");

  // now print out the temperature
  float temperatureC = voltage * 100 ;  //converting from 10 mv per degree wit 500 mV offset
                                                //to degrees ((voltage - 500mV) times 100)
  Serial.print(temperatureC); Serial.println(" degrees C");

  // now convert to Fahrenheit
  float temperatureF = (temperatureC * 9.0 / 5.0) + 32.0;
  Serial.print(temperatureF); Serial.println(" degrees F");
  */
}
