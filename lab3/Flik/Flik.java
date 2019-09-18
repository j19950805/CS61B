/** An Integer tester created by Flik Enterprises.
 *  Original version (return a == b)can't work
 *  because that == only work for numbers between
 *  -128 and 127. So we should use equals method
 *  to compare integers.
 * */

public class Flik {
    public static boolean isSameNumber(Integer a, Integer b) {
        return a.equals(b);
    }
}
