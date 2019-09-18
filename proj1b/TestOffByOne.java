import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('f','e'));
        assertTrue(offByOne.equalChars('l','k'));
        assertTrue(offByOne.equalChars('%','&'));
        assertFalse(offByOne.equalChars('c','B'));
        assertFalse(offByOne.equalChars('a','a'));
        assertFalse(offByOne.equalChars('a','A'));
    }
}