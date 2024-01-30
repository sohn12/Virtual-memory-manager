package org.example;

import java.util.HashMap;
import java.util.Map;

public class RAM {
    private final int[] memory = new int[32];

    private static RAM ram;

    private void ramDelay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private RAM() {

    }

    public static RAM getInstance() {
        if(ram == null) {
            ram = new RAM();
        }
        return ram;
    }

    public boolean addValueToRam(Memory mem, int value) throws Exception {
        ramDelay();
        int location =  mem.getBase() + mem.getLocation();
        if(location >= 0 && location < 32) {
            memory[location] = value;
            return true;
        }
        return false;
    }

    public int getValueFromRam(Memory mem) throws Exception {
        ramDelay();
        int location =  mem.getBase() + mem.getLocation();
        if(location >= 0 && location < 32) {
            return memory[location];
        }
        throw new Exception("index out of bounds");
    }

}
