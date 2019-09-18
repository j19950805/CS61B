public class OffByOne implements CharacterComparator{
    /** @override */
    public boolean equalChars(char x, char y) {
        return Math.abs(x - y) == 1;
    }
}