public class Piece {
	private boolean firePiece;
	private Board board;
	private int xPosition;
	private int yPosition;
	private String pieceType;
	private boolean hasCaputured;
	private boolean isKing;


	public Piece(boolean isFire, Board b, int x, int y, String type) {
		firePiece = isFire;
		board = b;
		xPosition = x;
		yPosition = y;
		pieceType = type;
	}

	public boolean isFire() {
		return firePiece;
	}

	public int side() {
		if (firePiece) {
			return 0;
		} return 1;
	}

	public boolean isKing() {
		return isKing;
	}

	public boolean isBomb() {
		return pieceType.equals("bomb");
	}

	public boolean isShield() {
		return pieceType.equals("shield");
	}

	public void move(int x, int y) {
		/****FINISHHH THIS SHIT****/
		//*fix capturing!!!!!!!! not removing the right locationssss
		int xDifference = x - xPosition;
		int yDifference = y - xPosition;
		if (xDifference == 2 || xDifference == -2) {
			if (xDifference == -2 || yDifference == -2) {
				board.remove(x + 1, y + 1);
			} else if (xDifference == 2 || yDifference == 2) {
				board.remove(x - 1, y - 1);
			} else if (xDifference == -2 || yDifference == 2) {
				board.remove(x + 1, y - 1);
			} else {
				board.remove(x - 1, y + 1);
			}
		}
		board.remove(xPosition, yPosition);
		xPosition = x;
		yPosition = y;
		board.place(this, xPosition, yPosition);
		if ((yPosition == 7 && firePiece) || (yPosition == 0 && !firePiece)) {
			isKing = true;
		}

		hasCaputured = true;
		doneCapturing();
	}

	public boolean hasCaputured() {
		return hasCaputured;
	}

	public void doneCapturing() {
		hasCaputured = false;
	}

}