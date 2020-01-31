package com.uwece651.medicationapp;

public class appArrayHandling {

    public static String[] add(String[] originalArray, String newItem) //found code online - HR
    {
        int currentSize = originalArray.length;
        int newSize = currentSize + 1;
        String[] tempArray = new String[ newSize ];
        for (int i=0; i < currentSize; i++)
        {
            tempArray[i] = originalArray [i];
        }
        tempArray[newSize- 1] = newItem;
        return tempArray;
    }

    public static String[] remove(String[] originalArray, String itemToRemove) {
        int currentSize = originalArray.length;
        int newSize = currentSize - 1;
        String[] tempArray = new String[0];

        for (int i = 0; i < currentSize; i++) {
            if (originalArray[i] != itemToRemove) {
                tempArray = add(tempArray, originalArray[i]);
            }
        }

        return tempArray;
    }

    public static Patient[] add(Patient[] originalArray, Patient newItem) //found code online - HR
    {
        int currentSize = originalArray.length;
        int newSize = currentSize + 1;
        Patient[] tempArray = new Patient[ newSize ];
        for (int i=0; i < currentSize; i++)
        {
            tempArray[i] = originalArray [i];
        }
        tempArray[newSize- 1] = newItem;
        return tempArray;
    }

}
