import static org.junit.Assert.*;

import org.junit.Test;

public class FlikTest {

    /**
     * Example test that verifies correctness of the
     * Flik.isSameNumber static method.
     */

    @Test
    public void testFlik() {
        assertTrue(Flik.isSameNumber(1, 1));
        assertTrue(Flik.isSameNumber(500, 500));
        assertTrue(Flik.isSameNumber(-500, -500));
        assertFalse(Flik.isSameNumber(30, 1));
    }

}
