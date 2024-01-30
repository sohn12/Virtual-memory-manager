package org.example;

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

    public boolean addValueToRam(MemoryLocation mem, int value) throws Exception {
        ramDelay();
        int location =  mem.base() + mem.location();
        if(location >= 0 && location < 32) {
            memory[location] = value;
            return true;
        }
        return false;
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
