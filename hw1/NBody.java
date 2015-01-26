public class NBody {
	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		
		In in = new In(filename);
		int numPlanets = in.readInt();
		double radius = in.readDouble();
		
		Planet[] allPlanets = new Planet[numPlanets];
		for(int i = 0; i < numPlanets; i ++) {
			allPlanets[i] = getPlanet(in);
		}
		
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, "images/starfield.jpg");
		for(int i = 0; i < numPlanets; i ++) {
			allPlanets[i].draw();
		}
		
		
		int time = 0;
		while (time <= T) {
			for(int i = 0; i < numPlanets; i += 1) {
				allPlanets[i].setNetForce(allPlanets);
				allPlanets[i].update(dt);
			}
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for(int i = 0; i < numPlanets; i ++) {
				allPlanets[i].draw();
			}
			StdDraw.show(10);
			time += dt;
		}	
		
		StdOut.printf("%d\n", numPlanets);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < numPlanets; i++) {
			Planet currentPlanet = allPlanets[i];
		    StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
		    		currentPlanet.x, currentPlanet.y, currentPlanet.xVelocity, currentPlanet.yVelocity, currentPlanet.mass, currentPlanet.img);
		}
	}
	
	public static Planet getPlanet(In in) {
		Planet nextPlanet = new Planet(in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readDouble(), in.readString());
		return nextPlanet;
	}
}