public class Board {

    public static final int SIZE = 8;

	private Piece[][] pieces;
    private boolean isFireTurn;

    public Board() {
        pieces = new Piece[SIZE][SIZE];
        isFireTurn = true;
    }

    /** Makes a custom Board. Not a completely safe operation because you could do
    * some bad stuff here, but this is for the purposes of testing out hash
    * codes so let's forgive Chris. 
    */
    public Board(Piece[][] pieces) {
        this.pieces = pieces;
        isFireTurn = true;
    }

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Board)) {
			return false;
		}
        Board other = (Board) o;
        for (int i = 0; i < pieces.length; i++) {
            /* We should probably look through all of them just in case 
             * someone makes a funny Board with Pieces all over the place,
             * and not just in the correct every-other-fashion. */
            for (int j = 0; j < pieces[i].length; j++) {
                Piece p1 = this.pieces[i][j];
                Piece p2 = other.pieces[i][j];

                // Null check
                if (p1 == null && p2 == null) {
                    continue;
                } else if (p1 == null || p2 == null) {
                    // One is null, but the other is not
                    return false;
                }

                if (!p1.equals(p2)) {
                    return false;
                }
            }
        }
        return true;
	}

    @Override
    public int hashCode() {
        /* The key here is to realize that shifting does magical things in 
         * amplifying differences. Also observe that there are only at most
         * 64/2 = 32 pieces. A perfect hash is not possible, but we can get 
         * reasonably scrambled. The best way is to just mess around until
         * you hit some golden jacket that gives a nice distribution. You 
         * probably want to do some stuff with shifting the answer or 
         * the hash code output. */
        int answer = 0;
        for (int y = 0; y < SIZE; y++) {
            int x = y % 2; 
            /* Only pick up the pieces that are actually there. We know that 
             * there are only Pieces every 2 squares. */
            for (; x < SIZE; x += 2) {
                Piece curr = pieces[x][y];
                if (curr != null) {
                    answer += curr.hashCode() + 1;
                    answer = answer << 1;
                    /* If Piece.hashCode() only return 0 or 1, we would have
                     * a perfect hash. But unfortunately, it'll be somewhere
                     * between 0-11. We add 1 so that we get a range of 
                     * 1-12 (and reserve 0 for empty) */
                }
            }
        }
        return answer ^ ((int) (isFireTurn?1:0) << 31);
        // Basically, make the answer negative if it's fire's turn.
        // Using xor and ternary operator so it fits in one line nicely (imo)
    }
}