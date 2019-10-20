package com.net.examples;


@NotThreadSafe
public class UnsafeSequence {
    private int value;

    /**
     * Returns a unique value.
     */
    public int getNext() {
        return value++;
    }
}

class Sequence {
    @GuardedBy("this")
    private int Value;

    public synchronized int getNext() {
        return Value++;
    }


}
