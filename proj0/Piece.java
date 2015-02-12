public class Piece {
	private boolean firePiece;
	private Board board;
	private int xPosition;
	private int yPosition;
	private String pieceType;
	private boolean hasCaputured;
<<<<<<< HEAD
	private boolean isKing;
=======
>>>>>>> 6c2a38d2ac4e38f5838ce660737ee8f3a461b35c

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
<<<<<<< HEAD
		return isKing;
=======
		return pieceType.equals("king");
>>>>>>> 6c2a38d2ac4e38f5838ce660737ee8f3a461b35c
	}

	public boolean isBomb() {
		return pieceType.equals("bomb");
	}

	public boolean isShield() {
		return pieceType.equals("shield");
	}

<<<<<<< HEAD
	public void move(int x, int y) {
		/****FIX THIS SHIT****/
		doneCapturing();
	}
=======
	public void move(int x, int y) {}
>>>>>>> 6c2a38d2ac4e38f5838ce660737ee8f3a461b35c

	public boolean hasCaputured() {
		return hasCaputured;
	}

	public void doneCapturing() {
		hasCaputured = false;
	}

}