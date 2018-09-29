package fc.SimpleRaytracer.Geometry;

import fc.SimpleRaytracer.Math.Vector;

public class AABB {
	public Vector m_Min;
	public Vector m_Max;
	
	public AABB(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
		this.m_Min = new Vector(xMin, yMin, zMin);
		this.m_Max = new Vector(xMax, yMax, zMax);
	}
}
