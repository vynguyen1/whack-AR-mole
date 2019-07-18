#include <DmxSimple.h>
#include <DMXSerial.h>
#include <ArduinoJson.h>

#define BTSerial Serial3
#define BAUD 115200
#define buffersize 200


// JSON_BT Buffer + Counter + Flag Bluetooth
char json[buffersize] = {0};
int jsonCounter = 0;
bool readJson = false;

// JSON Variablen Bluetooth
int valPanBT;
int valTiltBT;
unsigned int valPanBT16;
unsigned int valTilBT16;
int valfinePanBT;
int valfineTiltBT;


// JSON Variablen DMX Reciever
int valPanRX;
int valTiltRX;
int valfinePanRX;
int valfineTiltRX;

// JSON Buffer + Counter + Flag RX
char jsonRX[buffersize] = {0};
int jsonRXCounter = 0;
bool readJsonRX = false;

//DMX Adresse des Device bzw value
byte valCtrl;

//Variablen DMX Kanäle
byte fixtureCh  =   1;
byte chLevel;
byte chPan  = 1;
byte chfinePan  = 2;
byte chTilt = 3;
byte chfineTilt = 4;
byte chDim  = 21;
byte chZoom = 18;
byte chSht = 20;
byte chWhite = 14;
byte chBlue = 12;
byte chRed = 8;
byte chGreen = 10;
byte chClmix = 17;

// values DMX Kanäle (nur Reciever)
byte valDim;
byte valZoom;
byte valSht;
byte valClmix;
byte valWhite;
byte valBlue;
byte valRed;
byte valGreen;

int state;


char channel;
char value;




void setup() {
  Serial.begin(BAUD);
  Serial2.begin(BAUD);
  BTSerial.begin(BAUD);
  DMXSerial.init(DMXController);


}
