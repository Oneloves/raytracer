package fc.SimpleRaytracer.Primitive;

import java.util.ArrayList;
import java.util.List;

import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Geometry.Thing;
import fc.SimpleRaytracer.Material.Material;
import fc.SimpleRaytracer.Math.Ray;
import fc.SimpleRaytracer.Math.Vector;

public class Sphere implements Thing {

	public double m_rayon;
	public Vector m_position;
	public Material m_material;
	public List<Intersection> m_intersections;
	public Boolean m_solid;
	
	
	public Sphere(Vector position, double rayon, Material material, Boolean solid) {
		this.m_rayon = rayon;
		this.m_position = position;
		this.m_material = material;
		this.m_intersections = new ArrayList<Intersection>();
		this.m_solid = solid;
	}

	
	@Override
	public Intersection intersect(Ray ray) {
		// TODO Auto-generated method stub
		Vector L = m_position.sub(ray.m_Origin);
		double tca = L.dot(ray.m_Direction);
		
		if(tca < 0.0)
			return null;
		
		double d2 = L.dot(L) - tca * tca;
		
		if(d2 > m_rayon * m_rayon)
			return null;
		
		double thc = Math.sqrt(m_rayon * m_rayon - d2);
		double t1 = tca - thc;
		double t2 = tca + thc;
		
		if(t1 > t2) {
			double tmp = t1;
			t1 = t2;
			t2 = tmp;
		}
		
		if(t1 < 0.0) {
			t1 = t2;
			if(t1 < 0.0)
				return null;
		}
		
		Vector positionIntersection = ray.m_Direction.mul(t1).add(ray.m_Origin);
		Vector normal = positionIntersection.sub(m_position);

		return new Intersection(this, ray, t1, normal.norm(), positionIntersection);
	}

	
	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return this.m_material;
	}

	
	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return m_solid;
	}
	
	
	@Override
	public List<Intersection> intersections(Ray ray) {
		List<Intersection> intersections = new ArrayList<Intersection>();
		Intersection intersection = intersect(ray);
		if(intersection != null) {
			double distDebut = intersection.m_RayDistance;
			do {
				intersections.add(intersection);
				Ray newRay = new Ray(intersection.m_Intersection.add(ray.m_Direction.mul(Math.pow(10, -6))), ray.m_Direction);
				intersection = intersect(newRay);
				if(intersection !=null){
					intersection.m_RayDistance += distDebut;
				}
			}while(intersection!=null);
		}
		return intersections;
	}

}
