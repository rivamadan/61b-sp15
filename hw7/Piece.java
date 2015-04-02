public class Piece {

    public final boolean side; // true means this is a fire piece
    private String type;

    /** Determines whether or not this piece is a King. */
    private boolean isKing;

    /* True if this piece has captured a piece this turn and is considering a
	  multi-capture.*/
    private boolean hasCaptured;

    /** Represents the piece's current position on its board. */
    private int x, y;
    /** Keeps track of the board to which this piece belongs.
     *  Necessary for callbacks. */

    private static int shifter = 1;

    /** Returns whether or not this piece is on the Fire team. */
    public boolean isFire() {
        return side;
    }

    /**
     * Constructs a new Piece.
     * 
     * @param team Which team the Piece will belong to.
     * @param b    Which board the Piece is on.
     * @param x    The initial X position of the Piece.
     * @param y    The initial Y position of the Piece.
     */
    public Piece(boolean isFire, int x, int y, String type, boolean isKing) {
        side = isFire;
        this.isKing = isKing;
        this.x = x;
        this.y = y;
        this.type = type;
        hasCaptured = false;
    }

    /** Returns whether or not this piece is a Bomb Piece. Overridden in
     *  subclasses. */
    public boolean isBomb() {
        return type.equalsIgnoreCase("bomb");
    }

    /** Promotes this piece to a king, if not already done so. */
    public void promote() {
        this.isKing = true;
    }

    /** Returns whether or not this piece is a Shield Piece. Overridden in
     *  subclasses. */
    public boolean isShield() {
        return type.equalsIgnoreCase("shield");
    }

    /** Returns whether or not the piece is a King. */
    public boolean isKing() {
        return isKing;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Piece) {
            Piece other = (Piece) o;
            return (x == other.x) && (y == other.y) && (side == other.side) 
                    && (isKing == other.isKing) && (type.equals(other.type) 
                    && (hasCaptured == other.hasCaptured));
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = x;
        hash = hash & (y << 4);
        if (isKing) {
            hash = hash & (1 << 8);
        } if (side) {
            hash = hash & (1 << 9);
        } if (hasCaptured) {
            hash = hash & (1 << 10);
        } if (isShield()) {
            hash = hash & (1 << 11);
        } if (isBomb()) {
            hash = hash & (1 << 12);
        }
        return hash;
    }

    public static void main(String[] args) {
        Piece p1 = new Piece(false, 0, 0, "bomb", false);
        Piece p1AndHalf = new Piece(true, 0, 0, "regular", true);
        System.out.println(p1.hashCode());
        System.out.println(p1AndHalf.hashCode());
        Piece p2 = new Piece(false, 1, 0, "bomb", false);
        System.out.println(p2.hashCode());
    }
}
