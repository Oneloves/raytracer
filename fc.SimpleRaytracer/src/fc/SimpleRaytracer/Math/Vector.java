package fc.SimpleRaytracer.Math;

public class Vector
{
	public double m_X;
	public double m_Y;
	public double m_Z;
	
	
	public Vector(double x, double y, double z) {
		this.m_X = x;
		this.m_Y = y;
		this.m_Z = z;
	}
	
	
	public Vector neg() {
		return new Vector(-m_X, -m_Y, -m_Z);
	}
	
	
	public Vector mul(double k) {
		return new Vector(k*m_X, k*m_Y, k*m_Z);
	}
	
	public Vector mul(Vector v2) {
		return new Vector(m_X*v2.m_X, m_Y*v2.m_Y, m_Z*v2.m_Z);
	}
	
	public Vector div(double k) {
		return new Vector(m_X/k, m_Y/k, m_Z/k);
	}
	
	
	public Vector add(Vector v2) {
		return new Vector(m_X + v2.m_X, m_Y + v2.m_Y, m_Z + v2.m_Z);
	}
	
	public Vector add(double t) {
		return new Vector(m_X + t, m_Y + t, m_Z + t);
	}
	
	public Vector sub(Vector v2) {
		return new Vector(m_X - v2.m_X, m_Y - v2.m_Y, m_Z - v2.m_Z);
	}
	
	
	public double dot(Vector v2) {
		return m_X*v2.m_X + m_Y*v2.m_Y + m_Z*v2.m_Z;
	}
	
	
	public double length() {
		return Math.sqrt(m_X*m_X + m_Y*m_Y + m_Z*m_Z);
	}
	
	
	public Vector norm() {
		double length = length();
		if (length != 0.0)
			return this.div(length); 
		else
			return this;
	}
	
	
	public Vector cross(Vector v2) {
		return new Vector(
				m_Y * v2.m_Z - m_Z * v2.m_Y,
				m_Z * v2.m_X - m_X * v2.m_Z,
				m_X * v2.m_Y - m_Y * v2.m_X);
	}
}

