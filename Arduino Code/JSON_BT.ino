// JSONBT Buffer leeren
void freeJson() {
  for (int i = 0; i < jsonCounter; i++) {
    json[i] = 0;
  }

  jsonCounter = 0;
  readJson = false;
}

//JSON_BT Buffer
void readJsonBT() {

  if (BTSerial.available()) {
    // Zeichenweise auslese des BT Streams
    char c = BTSerial.read();
    // Json Buffer bei Zeilenende zum verarbeiten flaggen
    readJson = c == '\n';

    json[jsonCounter] = c;
    jsonCounter++;
  }
}

// JSON_BT Pakete auseinnander ziehen (deserialize)
bool parseJson(JsonDocument *doc, char buff[]) {
  // deserialize
  DeserializationError error = deserializeJson(*doc, buff);

  // Validierung des Strings, der im JSON_BT BUffer hängt
  if (error) {
    Serial.print(F("deserializeJsonBT() failed: "));
    Serial.println(error.c_str());
    freeJson();
    return false;
  } else {
    return true;
  }
}

// Auswerten und "handlen" der einzelnen Datenpakete
void handleJsonBT(JsonDocument doc) {
  valPanBT16  = doc["Pan"];
  valTilBT16 = doc["Tilt"];

  // 16Bit P/T Werte in coarse und fine 8Bit-Werte aufteilen
  valPanBT = highByte(valPanBT16);
  valfinePanBT = lowByte(valPanBT16);
  valTiltBT = highByte(valTilBT16);
  valfineTiltBT = lowByte(valTilBT16);

}
