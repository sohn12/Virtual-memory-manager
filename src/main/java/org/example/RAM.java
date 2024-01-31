package org.example;

import java.util.Arrays;

public class RAM {
    private final int[] memory = new int[32];
    private static RAM ram;
    public void printRam() {
        System.out.println(Arrays.toString(memory));
    }

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

    public void addValueToRam(MemoryLocation mem, int value){
        ramDelay();
        int location =  mem.base() + mem.location();
        if(location >= 0 && location < 32) {
            memory[location] = value;
        }
    }

    public int getValueFromRam(MemoryLocation mem) throws Exception {
        ramDelay();
        int location =  mem.base() + mem.location();
        if(location >= 0 && location < 32) {
            return memory[location];
        }
        throw new Exception("index out of bounds");
    }
}
