package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the clorus class
 *  @authr Jennifer Chen
 */

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(0.05);
        assertEquals(0.05, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(0.02, c.energy(), 0.01);
        c.stay();
        assertEquals(0.01, c.energy(), 0.01);
        c.move();
        assertEquals(0, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus c1 = new Clorus(2);
        Clorus c2 = c1.replicate();
        assertEquals(1, c1.energy(), 0.01);
        assertEquals(1, c2.energy(), 0.01);
        assertNotEquals(c1 ,c2);
        assertEquals(new Color(34, 0, 231), c1.color());
        assertEquals(new Color(34, 0, 231), c2.color());
    }

    @Test
    public void testChoose() {

        // No empty adjacent spaces; stay.
        Clorus c = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Plip());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        // A plip nearby and there's empty space, attact.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> plipLeft = new HashMap<Direction, Occupant>();
        plipLeft.put(Direction.TOP, new Empty());
        plipLeft.put(Direction.BOTTOM, new Impassible());
        plipLeft.put(Direction.LEFT, new Plip());
        plipLeft.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(plipLeft);
        expected = new Action(Action.ActionType.ATTACK, Direction.LEFT);

        assertEquals(expected, actual);

        // Energy >= 1; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> topEmpty = new HashMap<Direction, Occupant>();
        topEmpty.put(Direction.TOP, new Empty());
        topEmpty.put(Direction.BOTTOM, new Impassible());
        topEmpty.put(Direction.LEFT, new Impassible());
        topEmpty.put(Direction.RIGHT, new Impassible());

        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected, actual);


        // Energy >= 1; replicate towards an empty space.
        c = new Clorus(1.2);
        HashMap<Direction, Occupant> allEmpty = new HashMap<Direction, Occupant>();
        allEmpty.put(Direction.TOP, new Empty());
        allEmpty.put(Direction.BOTTOM, new Empty());
        allEmpty.put(Direction.LEFT, new Empty());
        allEmpty.put(Direction.RIGHT, new Empty());

        actual = c.chooseAction(allEmpty);
        Action unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);


        // Energy < 1; move.
        c = new Clorus(.99);

        actual = c.chooseAction(allEmpty);
        unexpected = new Action(Action.ActionType.STAY);

        assertNotEquals(unexpected, actual);


        // Energy < 1; move.
        c = new Clorus(.99);

        actual = c.chooseAction(topEmpty);
        expected = new Action(Action.ActionType.MOVE, Direction.TOP);

        assertEquals(expected, actual);

    }
}
