public class Piece {
	private boolean firePiece;
	private Board board;
	private int xPosition;
	private int yPosition;
	private String pieceType;
	private boolean hasCaptured;
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

		board.place(this, x, y);
		board.remove(xPosition, yPosition);

		int xDifference = x - xPosition;
		int yDifference = y - yPosition;
		if (xDifference == -2 && yDifference == -2) {
			board.remove(xPosition - 1, yPosition - 1);
			bombCapture(x, y);
			hasCaptured = true;
		} else if (xDifference == 2 && yDifference == 2) {
			board.remove(xPosition + 1, yPosition + 1);
			bombCapture(x, y);
			hasCaptured = true;
		} else if (xDifference == -2 && yDifference == 2) {
			board.remove(xPosition - 1, yPosition + 1);
			bombCapture(x, y);
			hasCaptured = true;
		} else if (xDifference == 2 && yDifference == -2) {
			board.remove(xPosition + 1, yPosition - 1);
			bombCapture(x, y);
			hasCaptured = true;
		}
		
		xPosition = x;
		yPosition = y;
		if ((yPosition == 7 && firePiece) || (yPosition == 0 && !firePiece)) {
			isKing = true;
		}
	}

	private void bombCapture(int x, int y) {
		if (isBomb()) {
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					if ((board.pieceAt(x + i, y + j) != null) && (!board.pieceAt(x + i, y + j).isShield())) {
						board.remove(x + i, y + j);
					}
				}
			} board.remove(x, y);
		}
	}

	public boolean hasCaptured() {
		return hasCaptured;
	}

	public void doneCapturing() {
		hasCaptured = false;
	}

}
