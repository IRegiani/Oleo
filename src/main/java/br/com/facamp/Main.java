package br.com.facamp;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

/*-----------------------------------------------------------------------
----- Project developed with pi4j < https://github.com/Pi4J/pi4j/ > -----
---------- All code is customized to work with Raspberry Pi B -----------
---------------------- CITEC III / FACAMP / 2018/2 ----------------------
-----------------------------------------------------------------------*/
public class Main {

    public static void main(String args[]) {
        final GpioController gpio = GpioFactory.getInstance();
        Reader rd = new Reader(600, 1, null, gpio, "ID000");
        rd.startReading();
        Loader ld = new Loader(30, "https://citec-oil.firebaseio.com/tanks");
        ld.receiveReader(rd);
        ld.start();
        
        //https://github.com/Pi4J/pi4j/blob/master/pi4j-example/src/main/java/LcdExample.java
    }
}
