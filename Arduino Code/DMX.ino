void writeDMX() {

  DMXSerial.write(chDim, valDim);    ///Dimmer
  DMXSerial.write(chZoom, valZoom);     ///Zoom
  DMXSerial.write(chSht, valSht);     ///Shutter
  DMXSerial.write(chWhite, valWhite);    ///Weiß
  DMXSerial.write(chBlue, valBlue);   ///Blau
  DMXSerial.write(chRed, valRed);   ///Rot
  DMXSerial.write(chGreen, valGreen);   ///grün
  DMXSerial.write(chClmix, valClmix);   ///Colour Mix Control
}
