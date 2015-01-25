public class Planet {
	public double x, y, xVelocity, yVelocity, mass, xNetForce, yNetForce;
	public String img;

	public Planet(double xPosition, double yPosition, double startXVelocity,
				  double startYVelocity, double planetMass, String planetImg) {
		x = xPosition;
		y = yPosition;
		xVelocity = startXVelocity;
		yVelocity = startYVelocity;
		mass = planetMass;
		img = planetImg;
	}
	
	public double calcDistance(Planet otherPlanet) {
		double xDistance = otherPlanet.x - x;
		double yDistance = otherPlanet.y - y;
		double distance = xDistance*xDistance + yDistance*yDistance;
		return Math.sqrt(distance);
	}
	
	public double calcPairwiseForce(Planet otherPlanet) {
		double r = calcDistance(otherPlanet);
		double G = 6.67e-11;
		double F = (G*mass*otherPlanet.mass)/(r*r);
		return F;
	}
	
	public double calcPairwiseForceY(Planet otherPlanet) {
		double F = calcPairwiseForce(otherPlanet);
		double r = calcDistance(otherPlanet);
		double yDistance = otherPlanet.y - y;
		double yForce = F * (yDistance / r);
		return yForce;
	}
	
	public double calcPairwiseForceX(Planet otherPlanet) {
		double F = calcPairwiseForce(otherPlanet);
		double r = calcDistance(otherPlanet);
		double xDistance = otherPlanet.x - x;
		double xForce = F * (xDistance / r);
		return xForce;
	}
	
	public void setNetForce(Planet[] arrayOfPlanets) {
		int index = 0;
		xNetForce = 0;
		yNetForce = 0;
		while (index < arrayOfPlanets.length){
			Planet currentPlanet = arrayOfPlanets[index];
			if (currentPlanet == this) {
			} else {
				xNetForce += calcPairwiseForceX(currentPlanet);
				yNetForce += calcPairwiseForceY(currentPlanet);
			}
			index += 1;
		}
	}
	
	public void draw() {
		StdDraw.picture(x, y, img);
	}
}