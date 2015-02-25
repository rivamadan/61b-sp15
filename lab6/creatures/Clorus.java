package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/** An implementation of a motile pacifist photosynthesizer.
 *  @author 
 */
public class Clorus extends Creature {
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;
    /** fraction of energy to retain when replicating. */
    private double repEnergyRetained = 0.5;
    /** fraction of energy to bestow upon offspring. */
    private double repEnergyGiven = 0.5;
    /** probability of taking a move when space is available. */
    private double moveProbability = 0.5;

    /** creates clorus with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /** creates a clorus with energy equal to 1. */
    public Clorus() {
        this(1);
    }

    /** Should return a color with red = 34, blue = 231, and g = 0. */
    public Color color() {
        r = 34;
        b = 231;
		g = 0;
        return color(r, g, b);
    }

    /** If a Clorus attacks another Creature, 
    *   it should gain that Creature's energy.
    */
    public void attack(Creature c) {
        energy = c.energy();
    }

    /** Clorus should lose 0.03 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
    	energy -= 0.03;
    }


    /** Clorus lose 0.01 energy. */
    public void stay() {
    	energy -= 0.01;
    }

    /** Clorus and their offspring each get 50% of the energy. 
    *   Returns a baby Clorus.
     */
    public Clorus replicate() {
    	double babyEnergy = energy * repEnergyGiven;
        energy = energy * repEnergyRetained;
        return new Clorus(babyEnergy);
    }

    /** Clorus take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if any Plips are seen, the Clorus will ATTACK 
     *  one of them randomly.
     *  3. Otherwise, , if the Clorus has energy greater than or equal
     *  to one, it will REPLICATE to a random empty square.
     *  4. Otherwise, MOVE.
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
    	List<Direction> empties = getNeighborsOfType(neighbors, "empty");
    	List<Direction> plips = getNeighborsOfType(neighbors, "plip");
	    if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } if (plips.size() > 0) {
            Direction moveDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, moveDir);
        } if (energy >= 1.0) {
	        Direction moveDir = HugLifeUtils.randomEntry(empties);
	        return new Action(Action.ActionType.REPLICATE, moveDir);
	    }
        Direction moveDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.ActionType.MOVE, moveDir);
    }
}