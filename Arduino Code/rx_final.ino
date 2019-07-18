#include <DMXSerial.h>
#include <ArduinoJson.h>

#define BAUD 115200
#define JSON_BUFFER 50

//Kontrollvariable
byte chCrtl = 40;
byte valCrtl;

byte valPan;
byte valTilt;
byte valfinePan;
byte valfineTilt;
byte valDim;
byte valZoom;
byte valSht;
byte valClmix;
byte valWhite;
byte valBlue;
byte valRed;
byte valGreen;

byte chPan = 1;
byte chTilt = 3;
byte chfinePan = 2;
byte chfineTilt = 4;
byte chDim = 21;
byte chZoom = 18;
byte chSht = 20;
byte chClmix = 17;
byte chWhite = 14;
byte chBlue = 12;
byte chRed = 8;
byte chGreen = 10;
unsigned int Pan;
unsigned int Tilt;





void setup() {
  Serial.begin(BAUD);
  Serial2.begin(BAUD);
  DMXSerial.init(DMXReceiver);



}

void loop() {

  valDim = DMXSerial.read(chDim);
  valPan = DMXSerial.read(chPan);
  valTilt = DMXSerial.read(chTilt);
  valfinePan = DMXSerial.read(chfinePan);
  valfineTilt = DMXSerial.read(chfineTilt);
  valZoom = DMXSerial.read(chZoom);
  valSht = DMXSerial.read(chSht);
  valClmix = DMXSerial.read(chClmix);
  valWhite = DMXSerial.read(chWhite);
  valBlue = DMXSerial.read(chBlue);
  valRed = DMXSerial.read(chRed);
  valGreen = DMXSerial.read(chGreen);

  valCrtl = DMXSerial.read(chCrtl);

//JSON Objekte erstellen
  StaticJsonDocument<200> doc;

  doc["valDim"] = valDim;
  doc["valPan"] = valPan;
  doc["valTilt"] = valTilt;
  doc["valfinePan"] = valfinePan;
  doc["valfineTilt"] = valfineTilt;
  doc["valZoom"] = valZoom;
  doc["valSht"] = valSht;
  doc["valClmix"] = valClmix;
  doc["valWhite"] = valWhite;
  doc["valRed"] = valRed;
  doc["valGreen"] = valGreen;
  doc["valBlue"] = valBlue;

  doc["valCtrl"] = valCrtl;

 //JSON verpacken und Serialisieren
  serializeJson(doc, Serial2);
  Serial2.print("\n");
  serializeJson(doc, Serial);
  Serial.print("\n");
  
//kurzes Delay fuer die Ordnung
  delay(10);


}
