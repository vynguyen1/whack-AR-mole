// JSONRX Buffer leeren
void freeJsonRX() {
  for (int i = 0; i < jsonCounter; i++) {
    jsonRX[i] = 0;
  }

  jsonRXCounter = 0;
  readJsonRX = false;
}


void fktreadJsonRX() {

  if (Serial2.available()) {
    // Zeichenweise Auslese des RX Streams
    char b = Serial2.read();

    // Json Buffer bei Zeilenende zum verarbeiten flaggen
    readJsonRX = b == '\n';

    jsonRX[jsonRXCounter] = b;
    jsonRXCounter++;
  }
}

// JSON RX Pakete auseinnander ziehen (deserialize)
bool parseJsonRX(JsonDocument *doc, char buff[]) {
  // deserialize
  DeserializationError error = deserializeJson(*doc, buff);
  // Validierung des Strings, der im JSONRX BUffer hängt
  if (error) {
    Serial.print(F("deserializeJsonRX() failed: "));
    Serial.println(error.c_str());
    freeJsonRX();
    return false;
  } else {
    return true;
  }
}

// Auswerten und "handlen" der einzelnen Datenpakete als DMX Receiver
void handleJsonRX(JsonDocument docRX) {
  valCtrl  = docRX["valCtrl"];
  valPanRX = docRX["valPan"];
  valTiltRX = docRX["valTilt"];
  valfinePanRX = docRX["valfinePan"];
  valfineTiltRX = docRX["valfineTilt"];
  valDim = docRX["valDim"];
  valZoom = docRX["valZoom"];
  valSht = docRX["valSht"];
  valClmix = docRX["valClmix"];
  valWhite = docRX["valWhite"];
  valRed = docRX["valRed"];
  valBlue = docRX["valBlue"];
  valGreen = docRX["valGreen"];

  // Entscheiden, welche Werte als DMX geschrieben werden sollen
  // wenn Crtl inaktiv -> P/T Werte aus BT sonst Werte aus RX
  if (valCtrl == 0) {
    DMXSerial.write(chPan, valPanBT);
    DMXSerial.write(chTilt, valTiltBT);
    DMXSerial.write(chfinePan, valfinePanBT);
    DMXSerial.write(chfineTilt, valfineTiltBT);

    writeDMX();
  }
  else {
    DMXSerial.write(chPan, valPanRX);
    DMXSerial.write(chTilt, valTiltRX);
    DMXSerial.write(chfinePan, valfinePanRX);
    DMXSerial.write(chfineTilt, valfineTiltRX);

    writeDMX();
  }

}
