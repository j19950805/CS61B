import java.util.*;

/**
 * A String-like class that allows users to add and remove characters in the String
 * in constant time and have a constant-time hash function. Used for the Rabin-Karp
 * string-matching algorithm.
 */
class RollingString{
    private LinkedList<Character> string;
    private int hashcode;

    /**
     * Number of total possible int values a character can take on.
     * DO NOT CHANGE THIS.
     */
    static final int UNIQUECHARS = 128;

    /**
     * The prime base that we are using as our mod space. Happens to be 61B. :)
     * DO NOT CHANGE THIS.
     */
    static final int PRIMEBASE = 6113;

    /**
     * Initializes a RollingString with a current value of String s.
     * s must be the same length as the maximum length.
     */
    public RollingString(String s, int length) {
        assert(s.length() == length);
        string = new LinkedList<>();
        for(char c: s.toCharArray()) {
            string.add(c);
        }
        hashcode = hash(string);
    }

    private int hash(LinkedList<Character> string) {
        int hash = 0;
        for (char c: string) {
            hash = ((hash * UNIQUECHARS + (int) c) & 0x7FFFFFFF) % PRIMEBASE;
        }
        return hash;
    }

    /**
     * Adds a character to the back of the stored "string" and 
     * removes the first character of the "string". 
     * Should be a constant-time operation.
     */
    public void addChar(char c) {
        string.add(c);
        char removed = string.remove();
        hashcode += PRIMEBASE;
        hashcode -= (((int) Math.pow(UNIQUECHARS, length() - 1) * removed) & 0x7FFFFFFF) % PRIMEBASE;
        hashcode = ((hashcode * UNIQUECHARS + (int) c) & 0x7FFFFFFF) % PRIMEBASE;
    }


    /**
     * Returns the "string" stored in this RollingString, i.e. materializes
     * the String. Should take linear time in the number of characters in
     * the string.
     */
    public String toString() {
        StringBuilder strb = new StringBuilder();
        strb.append(string);
        return strb.toString();
    }

    /**
     * Returns the fixed length of the stored "string".
     * Should be a constant-time operation.
     */
    public int length() {
        return string.size();
    }


    /**
     * Checks if two RollingStrings are equal.
     * Two RollingStrings are equal if they have the same characters in the same
     * order, i.e. their materialized strings are the same.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }
        return string.equals(((RollingString) o).string);
    }

    /**
     * Returns the hashcode of the stored "string".
     * Should take constant time.
     */
    @Override
    public int hashCode() {
        return hashcode;
    }
}
