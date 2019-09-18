public class Body {
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Body(double xP, double yP, double xV, 
			double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Body(Body b) {
		xxPos = b.xxPos;
		yyPos = b.yyPos;
	       	xxVel = b.xxVel;
	        yyVel =	b.yyVel;
	        mass = b.mass;
	       	imgFileName = b.imgFileName;
	}

	public double calcDistance(Body b) {
		double dx = xxPos - b.xxPos;
		double dy = yyPos - b.yyPos;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public double calcForceExertedBy(Body b) {
		double r = calcDistance(b);
		return 6.67E-11 * mass * b.mass / (r * r);
	}
	
	public double calcForceExertedByX(Body b) {
		return calcForceExertedBy(b) * (b.xxPos - xxPos) / calcDistance(b);
	}
	
	public double calcForceExertedByY(Body b) {
		return calcForceExertedBy(b) * (b.yyPos - yyPos) / calcDistance(b);
	}

	public double calcNetForceExertedByX(Body[] bodies) {
		double NetFx = 0;
		for (Body b: bodies) {
			if (!this.equals(b)){
			NetFx += calcForceExertedByX(b);
			}
		}
		return NetFx;
	}
	
	public double calcNetForceExertedByY(Body[] bodies) {
		double NetFy = 0;
		for (Body b: bodies) {
			if (!this.equals(b)){
			NetFy += calcForceExertedByY(b);
			}
		}
		return NetFy;
	}

	public void update(double dt, double fX, double fY) {
		double aX = fX / mass;
		double aY = fY / mass;
		xxVel = xxVel + dt * aX;
		yyVel = yyVel + dt * aY;
		xxPos = xxPos + dt * xxVel;
		yyPos = yyPos + dt * yyVel;
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
	}
}
