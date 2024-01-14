package logic;

import java.util.Random;

public class NumField {
    // This class contains a value of button and helps to check if button is pressed or not, is active in this move or not
    private final byte VALUE;
    public boolean isActive; // Is the field active on this turn
    public boolean isPressed; // Was the field pressed in past or not

    public NumField(int value){
        // Constructor of NumField that takes a value as an argument and sets it.
        VALUE = (byte) value;
        isActive = true;
        isPressed = false;
    }

    NumField(byte value){
        VALUE = value;
        isActive = true;
        isPressed = false;
    }

    NumField() {
        // Constructor of NumField that also creates a random value for the field
        Random random = new Random();
        VALUE = (byte) random.nextInt(1, 9+1);
        isActive = true;
        isPressed = false;
    }

    public byte getValue(){
        return VALUE;
    }
}
