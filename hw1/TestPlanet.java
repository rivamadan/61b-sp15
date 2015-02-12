public class TestPlanet {
	public static void main(String[] args) {
        checkPlanet();
    }
        
    public static void checkPlanet() {
    	Planet p1 = new Planet(1.0, 2.0, 5.0, 10.0, 10.0, "jupiter.gif");
        Planet p2 = new Planet(2.0, 1.0, 3.0, 4.0, 30.0, "mars.gif");

        System.out.println(p1.calcPairwiseForce(p2));
    }
}