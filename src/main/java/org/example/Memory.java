package org.example;

public class Memory {
    private final int base;
    private final int location;

    public Memory(int base, int location) {
        this.base = base;
        this.location = location;
    }

    public int getBase() {
        return base;
    }

    public int getLocation() {
        return location;
    }
}
