package org.example;

import java.util.Arrays;

public class RAM {
    private final int[] memory = new int[Constants.RAM_SIZE];
    public void printRam() {
        System.out.println(Arrays.toString(memory));
    }

    private void ramDelay() {
        try {
            Thread.sleep(Constants.RAM_DELAY);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void addValueToRam(MemoryLocation mem, int value){
        ramDelay();
        int location =  mem.base() + mem.offset();
        if(location >= 0 && location < Constants.RAM_SIZE) {
            memory[location] = value;
        }
    }

    public int getValueFromRam(MemoryLocation mem) throws Exception {
        ramDelay();
        int location =  mem.base() + mem.offset();
        if(location >= 0 && location < Constants.RAM_SIZE) {
            return memory[location];
        }
        throw new Exception("index out of bounds");
    }
}
