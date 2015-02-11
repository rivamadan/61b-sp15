public class Board {
	private boolean isBoardEmpty;
	private Piece[][] pieces;

	public Board(boolean shouldBeEmpty) {
		isBoardEmpty = shouldBeEmpty;
	}

	private void drawBoard(int N) {
		for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if ((i + j) % 2 == 0) {
                	StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                } else {
                	StdDrawPlus.setPenColor(StdDrawPlus.RED);
                }
            StdDrawPlus.filledSquare(i + .5, j + .5, .5);
            StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
            }
        }
	}

	public static void main (String args[]) {
		int N = 8;
        StdDrawPlus.setXscale(0, N);
        StdDrawPlus.setYscale(0, N);
        Board gameBoard = new Board(false);
        gameBoard.drawBoard(N);
	}

}