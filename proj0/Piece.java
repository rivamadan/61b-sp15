public class Piece {
	private boolean firePiece;
	private Board board;
	private int xPosition;
	private int yPosition;
	private String pieceType;
	private boolean hasCaputured;

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
		return pieceType.equals("king");
	}

	public boolean isBomb() {
		return pieceType.equals("bomb");
	}

	public boolean isShield() {
		return pieceType.equals("shield");
	}

	public void move(int x, int y) {}

	public boolean hasCaputured() {
		return hasCaputured;
	}

	public void doneCapturing() {
		hasCaputured = false;
	}

}