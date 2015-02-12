import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestBoard {

	private Board b;

	@Before
	public void setup() {
		b = new Board(false);
	}

	@Test
	public void testPieceAt() {
		assertEquals(true, b.pieceAt(1, 1).isShield());
		assertEquals(false, b.pieceAt(0, 0).isShield());
		assertEquals(false, b.pieceAt(7, 7).isFire());
		assertEquals(null, b.pieceAt(4, 4));
		assertEquals(null, b.pieceAt(8, 8));
	}

	@Test
	public void testPlace() {
		b.place(b.pieceAt(1,1), 2, 2);
		assertEquals(true, b.pieceAt(2, 2).isShield());

		b.place(b.pieceAt(1,5), 2, 4);
		assertEquals(true, b.pieceAt(2, 4).isBomb());
		assertEquals(false, b.pieceAt(2, 4).isFire());

		b.place(b.pieceAt(4,4), 5, 5);
		assertEquals(true, b.pieceAt(5, 5).isBomb());

		b.place(b.pieceAt(8,8), 7, 7);
		assertEquals(false, b.pieceAt(7, 7).isFire());
	}

	@Test
	public void testCanSelect() {

	}

	@Test
	public void testRemove() {
		assertEquals(true, b.remove(0,2).isBomb());
		assertEquals(null, b.pieceAt(0,2));

		assertEquals(true, b.remove(2,0).isFire());
		assertEquals(null, b.pieceAt(2,0));
	}

	@Test
	public void testWinner() {
		assertEquals(null, b.winner());

		for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
				b.remove(i, j);
			}
		}
		b.remove(7, 1);
		assertEquals("Water", b.winner());
	}

	@Test
	public void testNoWinner() {
		for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
				b.remove(i, j);
			}
		}
		assertEquals("No one", b.winner());
	}

	public static void main(String[] args) {
    	jh61b.junit.textui.runClasses(TestBoard.class);
    }
}