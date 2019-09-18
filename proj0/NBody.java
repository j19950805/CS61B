public class NBody {	
	public static double readRadius(String filename) {
		In in = new In(filename);
		in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Body[] readbodies(String filename) {
		In in = new In(filename);
		int N = in.readInt();
		in.readDouble();
		Body[] bodies = new Body[N]; 
		for (int i = 0; i < N; i++) {
			double xP = in.readDouble();
			double yP = in.readDouble();
			double xV = in.readDouble();	
			double yV = in.readDouble();
			double m = in.readDouble();
			String img = in.readString();
			bodies[i] = new Body(xP, yP, xV, yV, m, img);
		}
		return bodies;
	}

	public static void main(String[] args) {
		/** Collect input data */
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = readRadius(filename);
		Body[] bodies = readbodies(filename);
		int N = bodies.length;

		StdDraw.enableDoubleBuffering();
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();

		double[] xForces = new double[N];
		double[] yForces = new double[N];
		for (double t = 0; t <= T; t += dt) {
			StdDraw.picture(0, 0, "images/starfield.jpg");
			for (Body b: bodies) {
				b.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);

			for(int i = 0; i < N; i++) {
				xForces[i] = bodies[i].calcNetForceExertedByX(bodies);
				yForces[i] = bodies[i].calcNetForceExertedByY(bodies);
			}

			for(int i = 0; i < N; i++) {
				bodies[i].update(dt, xForces[i], yForces[i]);
			}
		}

		StdOut.printf("%d\n", N);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < N; i++) {
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
				      bodies[i].xxPos, bodies[i].yyPos, bodies[i].xxVel,
				      bodies[i].yyVel, bodies[i].mass, bodies[i].imgFileName);   
		}
	}
}
