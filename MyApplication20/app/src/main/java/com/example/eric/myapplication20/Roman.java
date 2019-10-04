/**
 * @author Eric Voong
 * @Description: Roman Numeral Converter
 * @Date: 01/24/18 (mm/dd/yy)
 */


package com.example.eric.myapplication20;


import java.util.Arrays;
import java.util.List;

public class Roman {
    public static int num;
    public static String str;
    public static final int[] numbers = { 10000, 9000, 5000, 4000, 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4,
            1 };
    public static final String[] letters = { "Q", "MQ", "E", "ME", "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X",
            "IX", "V", "IV", "I" };
    public static final List<Integer> numbers2 = Arrays.asList(10000, 5000, 1000, 500, 100, 50, 10, 5, 1);
    public static final List<Character> letters2 = Arrays.asList('Q', 'E', 'M', 'D', 'C', 'L', 'X', 'V', 'I');

    /**
     * @description Set instances to a default value
     */
    public Roman() {
        num = 0;
        str = "";
    }

    /**
     *
     * @param i sets the instance as an integer
     */
    public Roman(int i) {
        if (set(i)) {
            num = i;
            str = convertToString(i);
        } else {
            throw new NumberFormatException("Roman Numerals only exist from a range of 1 - 49999.");
        }
    }

    /**
     *
     * @param s sets the the instance as a Roman Numeral
     */
    public Roman(String s) {
        if (set(s)) {
            str = s;
            num = convertToInt(s);
        } else {
            throw new NumberFormatException("Invalid Roman Numeral");
        }
    }

    /**
     *
     * @param n is an integer
     * @return the integer as a RomanNumeral
     */
    public String convertToString(int n) {
        String word = "";
        for (int i = 0; i <= 16; i++) {
            for (int x = n / numbers[i]; x != 0; x--) {
                word += (letters[i]); // adds the Roman Numeral
            }
            n = n % (numbers[i]); // gets the remainder
        }
        return word; // returns the integer as a Roman Numeral
    }

    /**
     *
     * @param s is a Roman numeral
     * @return the Roman numeral as a integer
     */
    public int convertToInt(String s) {
        s = s.toUpperCase(); // converts lower case letters to upper case
        int total = 0;
        char characterList[] = s.toCharArray(); // splices the Roman Numeral
        for (int x = 0; x < characterList.length; x++) {
            if (x + 1 < (characterList.length)) {
                if (letters2.indexOf(characterList[x]) > letters2.indexOf(characterList[x + 1])) { // checks if a lower
                    // numeral before a
                    // higher numeral
                    total -= numbers2.get(letters2.indexOf(characterList[x])); // subtracts the value of the Roman
                    // Numeral
                    continue;
                }
            }
            total += numbers2.get(letters2.indexOf(characterList[x])); // adds the value of the Roman Numeral
        }
        if (!convertToString(total).equals(s)) {
            total = 0; // returns zero if string is invalid
        }
        return total; // returns the Roman Numeral as an integer
    }

    /**
     *
     * @param n is an integer
     * @return true if the integer is a valid number
     */
    public boolean set(int n) {
        return (n <= 49999 && n > 0); // checks if integer is valid
    }

    /**
     *
     * @param s is a Roman NUmeral
     * @return true if string is a valid Roman Numeral
     */
    public boolean set(String s) {
        return(set(convertToInt(s)) & convertToString((convertToInt(s))).equals(s.toUpperCase()));
    }

    /**
     *
     * @param i is a integer
     * @return true if sum is valid
     */
    public boolean add(int i) {
        if (set(i) && set(num + i)) { // checks if sum is valid
            num += i; // adds integer to the instance
            str = convertToString(num);
            return true;
        } else
            return false;
    }

    /**
     *
     * @param s is a Roman Numeral
     * @return true if sum is valid
     */
    public boolean add(String s) {
        if (set(s) && set(num + convertToInt(s))) { // checks if sum is valid
            num += convertToInt(s); // adds the string to the instance
            str = convertToString(num);
            return true;
        } else
            return false;
    }

    /**
     *
     * @param i is a integer
     * @return true if difference is valid
     */
    public boolean subtract(int i) {
        if (set(i) && (set(num - i))) { // checks if difference is valid
            num -= i; // subtracts the instance
            str = convertToString(num);
            return true;
        } else
            return false;
    }

    /**
     *
     * @param s is a string
     * @return true if difference is valid
     */
    public boolean subtract(String s) {
        if (set(s) && set(num - convertToInt(s))) { // checks if difference is valid
            num -= convertToInt(s); // subtracts the instance
            str = convertToString(num);
            return true;
        } else
            return false;
    }

    /**
     *
     * @return instance as a Roman Numeral
     */
    public String toRoman() {
        return str;
    }

    /**
     *
     * @return instance as a integer
     */
    public int toInt() {
        return num;
    }
}
