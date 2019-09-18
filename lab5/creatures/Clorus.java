package creatures;

import edu.princeton.cs.introcs.StdRandom;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import static huglife.HugLifeUtils.randomEntry;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.Map;

/**
 * An implementation of a motile pacifist photosynthesizer.
 *
 * @author Josh Hug
 */
public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    /**
     * creates clorus with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    /**
     * creates a clorus with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /**
     * Should return a color with red = 34, blue = 231, green = 0.
     */
    public Color color() {
        return color(r, g, b);
    }

    /**
     * If a Clorus attacks another creature, it should gain that creature’s energy.
     */
    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Cloruses lose 0.03 units of energy when moving.
     */
    public void move() {
        energy -= 0.03;
        energy = Math.max(energy, 0);
    }


    /**
     * Cloruses lose 0.01 energy when staying.
     */
    public void stay() {
        energy -= 0.01;
        energy = Math.max(energy, 0);
    }

    /**
     * Cloruses and their offspring each get 50% of the energy, with none
     * lost to the process. Now that's efficiency! Returns a baby
     * Clorus.
     */
    public Clorus replicate() {
        energy = 0.5 * energy;
        return new Clorus(energy);
    }

    /**
     * Cloruses take exactly the following actions based on NEIGHBORS:
     * 1. If no empty adjacent spaces, STAY.
     * 2. Otherwise, if any Plips are seen, ATTACK one of them randomly.
     * 3. Otherwise, if energy >= 1, REPLICATE to a random empty square.
     * 4. Otherwise, MOVE to a random empty square.
     * <p>
     * Returns an object of type Action. See Action.java for the
     * scoop on how Actions work. See SampleCreature.chooseAction()
     * for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> prey = new ArrayDeque<>();

        for (Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name() == "empty") {
                emptyNeighbors.add(d);
            } else if (neighbors.get(d).name() == "plip") {
                prey.add(d);
            }
        }

        if (emptyNeighbors.isEmpty()) {
            //Rule 1
            return new Action(Action.ActionType.STAY);
        } else if (!prey.isEmpty()) {
            // Rule2
            return new Action(Action.ActionType.ATTACK, randomEntry(prey));
        }

        if (energy >= 1) {
            // Rule 3
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        } else {
            // Rule 4
            return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
        }
    }
}
