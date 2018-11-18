package br.com.facamp;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinAnalogInput;
import com.pi4j.io.gpio.Pin;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reader {

    private double instant_value;
    private double calibrated_value;
    private Double average;
    private final int sliding_window_size;
    private int counter;
    private double[] values;
    private final int reading_frequency;
    private final GpioPinAnalogInput input;
    private final String name;

    /**
     * @author Iago Regiani
     * @version 1.0
     * @param sliding_window_size Describes the amount of readings required to
     * perform a mean
     * @param reading_frequency How many samples per second will be read from
     * the input
     * @param inputPin Which rasp analog pin will be used
     * @param gpio Aks the GPIO Controller used where the object is being instantiated
     * @param ID Unique ID to the object that will be monitored
     */
    public Reader(int sliding_window_size, int reading_frequency, Pin inputPin, GpioController gpio, String ID) {
        this.sliding_window_size = sliding_window_size;
        this.reading_frequency = reading_frequency;
        this.input = gpio.provisionAnalogInputPin(inputPin);
        this.values = new double[sliding_window_size];
        this.name = ID;
        new Thread() {
            @Override
            public void run() {
                if(values.length<sliding_window_size){ // ***VERIFY POPULATED ITENS***
                    average = Double.NaN;
                }else{
                    double sum = 0;
                    for (double i: values){
                        sum+=i;
                    }
                    average = sum/sliding_window_size;
                }
            }
        }.start();
    }
    
    /**
     *  Starts the thread responsible for reading and accumulating read and corrected values
     */   
    public void startReading() {
        new Thread() {
            @Override
            public void run() {
                try {
                    instant_value = input.getValue();
                    calibrated_value = 2.121 * (((1023 - instant_value) / 1024.0) * 14.0) - 7.6082;
                    values[counter] = calibrated_value;
                    counter++;
                    if(counter>sliding_window_size){
                        counter = 0;
                    }
                    Thread.sleep(1000 / reading_frequency);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();
    }
    
    /**
     *  Returns the average if it has enough values, otherwise, returns NaN
     */
    public Double getMean() {
        return average;
    }

}
