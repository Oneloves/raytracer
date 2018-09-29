package fc.SimpleRaytracer.Implicit;

import fc.SimpleRaytracer.Math.Vector;

public class Implicit {

	public double torus(Vector v) {
		double R = 20.0;
		double r = 10.0;
		return Math.pow(Math.pow(v.m_X, 2) + Math.pow(v.m_Y, 2) + Math.pow(v.m_Z, 2) + Math.pow(R, 2) - Math.pow(r, 2), 2)
				-4 * Math.pow(r, 2) * (Math.pow(v.m_X, 2) + Math.pow(v.m_Y, 2));
	}
	
}
