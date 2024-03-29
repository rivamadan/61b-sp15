/*
 * JUnit tests for the Triangle class
 */
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author melaniecebula
 */
public class TriangleTest {
  /**  We've already created a testScalene method.  Please fill in testEquilateral, and additionally
   *   create tests for Isosceles, Negative Sides, and Invalid sides
   **/

    @Test
    public void testScalene() {
        Triangle t = new Triangle(30, 40, 50);
        String result = t.triangleType();
        assertEquals("Scalene", result);
    }

    @Test
    public void testEquilateral() {
      Triangle t2 = new Triangle(30,30,30);
      String result2 = t2.triangleType();
      assertEquals("Equilateral", result2);
    }

    @Test
    public void testIsosceles() {
      Triangle t3 = new Triangle(50,30,50);
      String result3 = t3.triangleType();
      assertEquals("Isosceles", result3);
    }

    @Test
    public void testNegativeSides() {
      Triangle t4 = new Triangle(30,40,-50);
      String result4 = t4.triangleType();
      assertEquals("At least one length is less than 0!", result4);
    }

    @Test
    public void testInvalidSides() {
      Triangle t5 = new Triangle(50,10,10);
      String result5 = t5.triangleType();
      assertEquals("The lengths of the triangles do not form a valid triangle!", result5);
    }

    public static void main(String[] args) {
      jh61b.junit.textui.runClasses(TriangleTest.class);
    }
}
