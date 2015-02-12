public class Board {

	private static int boardSize = 8;
	private Piece[][] pieces;
	private String winner;
	private boolean firePlayerTurn = true;
	private int firePieces = boardSize/2 * 3;
	private int waterPieces = boardSize/2 * 3;
	private Piece selectedPiece;

	public Board(boolean shouldBeEmpty) {
		pieces = new Piece[boardSize][boardSize];
		if (!shouldBeEmpty) {
			createPieces();
		}
	}

	private void drawBoard() {
		for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                	StdDrawPlus.setPenColor(StdDrawPlus.GRAY);
                } else {
                	StdDrawPlus.setPenColor(StdDrawPlus.RED);
                }
            	StdDrawPlus.filledSquare(i + .5, j + .5, .5);
            	StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
            	Piece currentPiece = pieceAt(i, j);
            	if (currentPiece == null) {
            		continue;
            	} if (currentPiece.isFire()) {
            		if (currentPiece.isBomb()) {
            			StdDrawPlus.picture(i + .5, j + .5, "img/bomb-fire.png", 1, 1);
            		} else if (currentPiece.isShield()) {
            			StdDrawPlus.picture(i + .5, j + .5, "img/shield-fire.png", 1, 1);
            		} else {
            			StdDrawPlus.picture(i + .5, j + .5, "img/pawn-fire.png", 1, 1);
            		}
            	} else {
            		if (currentPiece.isBomb()) {
            			StdDrawPlus.picture(i + .5, j + .5, "img/bomb-water.png", 1, 1);
            		} else if (currentPiece.isShield()) {
            			StdDrawPlus.picture(i + .5, j + .5, "img/shield-water.png", 1, 1);
            		} else {
            			StdDrawPlus.picture(i + .5, j + .5, "img/pawn-water.png", 1, 1);
            		}	
            	}
            }
        }
	}

	private void createPieces() {
		for (int i = 0; i < boardSize; i++) {
	        if (i % 2 == 0) {
	            place(new Piece(true, this, i, 0, "pawn"), i, 0);
				place(new Piece(true, this, i, 2, "bomb"), i, 2);
				place(new Piece(false, this, i, (boardSize - 2), "shield"), i, 6);

	        } else {
	          	place(new Piece(true, this, i, 1, "shield"), i, 1);
	          	place(new Piece(false, this, i, (boardSize - 3), "bomb"), i, 5);
	          	place(new Piece(false, this, i, (boardSize - 1), "pawn"), i, 7);
	        }
	    }
	}


	public Piece pieceAt(int x, int y) {
		if (x >= boardSize || y >= boardSize) {
			return null;
		} if (pieces[x][y] == null) {
			return null;
		} return pieces[x][y];
	}

	public boolean canSelect(int x, int y) {
		/****FIX THIS SHIT****/
		if (firePlayerTurn) {
			if (!pieceAt(x, y).isFire()) {
				return false;
			} return true;
		} return true;
	}

	public void select(int x, int y) {
		/****FIX THIS SHIT****/
		selectedPiece = pieceAt(x,y);
	}

	public void place(Piece p, int x, int y) {
		if (p != null && (x < boardSize && y < boardSize)) {
			pieces[x][y] = p;
		}
	}

	public Piece remove(int x, int y) {
		if (x >= boardSize || y >= boardSize) {
			System.out.println("Coordinates are out of bounds.");
			return null;
		}
		Piece removedPiece = pieceAt(x, y);
		if (removedPiece == null) {
			System.out.println("No piece here to be removed.");
			return null;
		}
		pieces[x][y] = null;
		if (removedPiece.isFire()) {
			firePieces -= 1;
		} else {
			waterPieces -= 1;
		}
		return removedPiece;
	}

	public boolean canEndTurn() {
		if (selectedPiece.hasCaptured() // FIX! || selectedPiece.hasMoved???//) { 
			return true;
		} return false;
	}

	public void endTurn() {
		firePlayerTurn = false;
	}

	public String winner() {
		if (waterPieces == 0 && firePieces == 0) {
			return "No one";
		} if (waterPieces == 0) {
			return "Fire";
		} if (firePieces == 0) {
			return "Water";
		} else {
			return null;
		}
	}	

	public static void main (String args[]) {
        StdDrawPlus.setXscale(0, boardSize);
        StdDrawPlus.setYscale(0, boardSize);
        Board gameBoard = new Board(false);

        while(gameBoard.winner() == null) {
            gameBoard.drawBoard();
            if (StdDrawPlus.mousePressed()) {
                double x = StdDrawPlus.mouseX();
                double y = StdDrawPlus.mouseY();
                if (gameBoard.canSelect((int) x, (int) y)) {
                	gameBoard.select((int) x, (int) y);
                	// StdDrawPlus.setPenColor(StdDrawPlus.WHITE);
                	// StdDrawPlus.filledSquare(x, y, .5);
                }
            }          
            if (StdDrawPlus.isSpacePressed()){
            	if (gameBoard.canEndTurn()) {
	            	gameBoard.endTurn();
    			}
            }
        	StdDrawPlus.show(10);
        }  
	}
}