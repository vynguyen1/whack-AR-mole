void loop() {

  // Auslesen des Datenstroms von Arduino_RX
  fktreadJsonRX();

  if (readJsonRX) {

    // JSONRX String parsen
    StaticJsonDocument<buffersize> docRX;
    bool successRX = parseJsonRX(&docRX, jsonRX);
    // JSONRX String auswerten
    if (successRX) {
      handleJsonRX(docRX);
    }

    // Buffer leeren
    freeJsonRX();
  }

  // Auslesen des Datenstroms des Bluetooth Moduls
  readJsonBT();

  if (readJson) {

    // JSONBT String parsen
    StaticJsonDocument<buffersize> doc;
    bool success = parseJson(&doc, json);

    // JSONBT String auswerten
    if (success) {
      handleJsonBT(doc);
    }

    // Buffer leeren
    freeJson();
  }


}
