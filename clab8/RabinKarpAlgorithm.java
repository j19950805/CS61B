public class RabinKarpAlgorithm {


    /**
     * This algorithm returns the starting index of the matching substring.
     * This method will return -1 if no matching substring is found, or if the input is invalid.
     */
    public static int rabinKarp(String input, String pattern) {
        if (input.length() < pattern.length()) {
            return -1;
        }
        int m = pattern.length();
        int n = input.length();
        RollingString pString = new RollingString(pattern, m);
        RollingString rollingInput = new RollingString(input.substring(0, m), m);
        int pHash = pString.hashCode();
        for (int i = m; i <= n; i++) {
            int inputHash = rollingInput.hashCode();
            if (inputHash == pHash && rollingInput.equals(pString)) {
                return i - m;
            }
            if (i < n) {
                rollingInput.addChar(input.charAt(i));
            }
        }
        return -1;
    }

}
