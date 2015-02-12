import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestPiece {

	private Board b;
	private Piece firePiece;
	private Piece waterPiece;
	private Piece bombPiece;
	private Piece pawnPiece;
	private Piece shieldPiece;

	@Before
	public void setup() {
		b = new Board(false);
		firePiece = new Piece(true, b, 1, 1, "pawn");
		waterPiece = new Piece(false, b, 2, 2, "pawn");
		bombPiece = new Piece(false, b, 1, 1, "bomb");
		pawnPiece = new Piece(false, b, 2, 2, "pawn");
		shieldPiece = new Piece(true, b, 1, 1, "shield");
	}

	@Test
	public void testIsFire() {
		assertEquals(true, firePiece.isFire());
		assertEquals(false, waterPiece.isFire());
	}

	@Test
	public void testSide() {
		assertEquals(0, firePiece.side());
		assertEquals(1, waterPiece.side());
	}

	@Test
	public void testIsBomb() {
		assertEquals(true, bombPiece.isBomb());
		assertEquals(false, pawnPiece.isBomb());
	}

	@Test
	public void testIsShield() {
		assertEquals(true, shieldPiece.isShield());
		assertEquals(false, bombPiece.isShield());
	}
	
	public static void main(String[] args) {
    	jh61b.junit.textui.runClasses(TestPiece.class);
	}
}
