package fc.SimpleRaytracer.Implicit;

import java.util.ArrayList;
import java.util.List;

import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Geometry.Thing;
import fc.SimpleRaytracer.Material.Material;
import fc.SimpleRaytracer.Math.Ray;
import fc.SimpleRaytracer.Math.Vector;


public class Torus implements Thing {

	public Material m_material;
	
	public Torus(Material material) {
		this.m_material = material;
	}
	
	public double torus(Vector p) {
		double R = 0.6;
		double r = 0.25;
		//Vector position = new Vector(-1, -0.5, 0.5);
		Vector position = new Vector(0, 0, 0);
		return Math.pow(Math.pow(p.m_X-position.m_X, 2) + Math.pow(p.m_Y-position.m_Y, 2) + Math.pow(p.m_Z-position.m_Z, 2) + Math.pow(R, 2) - Math.pow(r, 2), 2)
				-4 * Math.pow(R, 2) * (Math.pow(p.m_X-position.m_X, 2) + Math.pow(p.m_Y-position.m_Y, 2));
	}
	
	public Vector estimateNormal(Vector p) {
		double epsilon = 0.05;
		Vector v1 = new Vector(p.m_X + epsilon, p.m_Y, p.m_Z);
		Vector v2 = new Vector(p.m_X - epsilon, p.m_Y, p.m_Z);
		Vector v3 = new Vector(p.m_X, p.m_Y + epsilon, p.m_Z);
		Vector v4 = new Vector(p.m_X, p.m_Y - epsilon, p.m_Z);
		Vector v5 = new Vector(p.m_X, p.m_Y, p.m_Z + epsilon);
		Vector v6 = new Vector(p.m_X, p.m_Y, p.m_Z - epsilon);
	    return new Vector(torus(v1) - torus(v2), torus(v3) - torus(v4), torus(v5) - torus(v6)).norm();
	}
	
	@Override
	public Intersection intersect(Ray ray) {
		// TODO Auto-generated method stub
		double maxDistance = 10;
		double stepSize = 0.001; 
		double t = 0;
		
		while(t<maxDistance) {			
			Vector point = ray.m_Origin.add(ray.m_Direction.mul(t));			
			double eval = torus(point);			
			if(Math.abs(eval)<0.001) {
				Vector normal = estimateNormal(point);
		        return new Intersection(this, ray, t, normal, point);
			}			
			t += stepSize;
		}		
		return null;
	}
	
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return this.m_material;
	}
	
	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public List<Intersection> intersections(Ray r) {
		List<Intersection> intersections = new ArrayList<Intersection>();
		Intersection intersection = intersect(r);
		if(intersection != null) {
			double distDebut = intersection.m_RayDistance;
			do {
				intersections.add(intersection);
				Ray newRay = new Ray(intersection.m_Intersection.add(r.m_Direction.mul(Math.pow(10, -6))), r.m_Direction);
				intersection = intersect(newRay);
				if(intersection !=null){
					intersection.m_RayDistance += distDebut;
				}
			}while(intersection!=null);
		}
		return intersections;
	}
}
