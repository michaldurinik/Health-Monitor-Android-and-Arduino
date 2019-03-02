//TMP36 Pin Variables
int sensorPin = 12
; //the analog pin the TMP36's Vout (sense) pin is connected to
                        //the resolution is 10 mV / degree centigrade with a
                        //500 mV offset to allow for negative temperatures
                        
//pulse
int PulseSensorPurplePin = 10;        // Pulse Sensor PURPLE WIRE connected to ANALOG PIN 0
int LED13 = 7;   //  The on-board Arduion LED


int Signal;                // holds the incoming raw data. Signal value can range from 0-1024
int Threshold = 550;            // Determine which Signal to "count as a beat", and which to ingore.

 
/*
 * setup() - this function runs once when you turn your Arduino on
 * We initialize the serial connection with the computer
 */
void setup()
{
  Serial.begin(9600);  //Start the serial connection with the computer
                       //to view the result open the serial monitor 
  pinMode(LED13,OUTPUT);         // pin that will blink to your heartbeat!
}
 
void loop()                     // run over and over again
{
 //getting the voltage reading from the temperature sensor
 int reading = analogRead(sensorPin); 

 //Serial.print(reading); Serial.println(" reading");
 
 // converting that reading to voltage, for 3.3v arduino use 3.3
 float voltage = reading * 3.3;
 voltage /= 1024.0; 
 
 // print out the voltage
 //Serial.print(voltage); Serial.println(" volts");
 
 // now print out the temperature
 float temperatureC = voltage * 100 ;  //converting from 10 mv per degree wit 500 mV offset
                                               //to degrees ((voltage - 500mV) times 100)
 //Serial.print(temperatureC); Serial.println(" degrees C");
 
 // now convert to Fahrenheit
 float temperatureF = (temperatureC * 9.0 / 5.0) + 32.0;
 //Serial.print(temperatureF); Serial.println(" degrees F");

 int myBPM = pulseSensor.getBeatsPerMinute();  // Calls function on our pulseSensor object that returns BPM as an "int".
                                               // "myBPM" hold this BPM value now. 

  if (pulseSensor.sawStartOfBeat()) {            // Constantly test to see if "a beat happened". 
   Serial.println("♥  A HeartBeat Happened ! "); // If test is "true", print a message "a heartbeat happened".
   Serial.print("BPM: ");                        // Print phrase "BPM: " 
   Serial.println(myBPM);                        // Print the value inside of myBPM. 
  }
  
    delay(20);                    // considered best practice in a simple sketch.

}
