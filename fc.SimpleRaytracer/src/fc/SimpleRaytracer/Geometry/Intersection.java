package fc.SimpleRaytracer.Geometry;

import fc.SimpleRaytracer.Math.Ray;
import fc.SimpleRaytracer.Math.Vector;

public class Intersection {
	
	public Thing m_Thing;
	public Ray m_Ray;
	public double m_RayDistance;
	public Vector m_Normal;
	public Vector m_Intersection;
	
	public Intersection(Thing thing, Ray ray, double dist, Vector normal, Vector intersection) {
		this.m_Ray = ray;
		this.m_Thing = thing;
		this.m_Normal = normal;
		this.m_RayDistance = dist;
		this.m_Intersection = intersection;
	}
}
