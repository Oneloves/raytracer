package fc.SimpleRaytracer.Primitive;

import java.util.ArrayList;
import java.util.List;

import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Geometry.Thing;
import fc.SimpleRaytracer.Material.Material;
import fc.SimpleRaytracer.Math.Ray;
import fc.SimpleRaytracer.Math.Vector;


// Source pour l'intersection rayon triangle:
// https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm
public class Triangle implements Thing {

	public Vector m_v0;
	public Vector m_v1;
	public Vector m_v2;
	public Material m_material;
	public Boolean m_solid;
	
	
	public Triangle(Vector v0, Vector v1, Vector v2, Material material, boolean solid) {
		this.m_v0 = v0;
		this.m_v1 = v1;
		this.m_v2 = v2;
		this.m_material = material;	
		this.m_solid = solid;
	}
	

	@Override
	public Intersection intersect(Ray ray) {
		// TODO Auto-generated method stub
		float EPSILON = 0.0000001f; 
	    Vector vertex0 = m_v0;
	    Vector vertex1 = m_v1;  
	    Vector vertex2 = m_v2;
	    Vector edge1, edge2, h, s, q;
	    double a,f,u,v;
	    edge1 = vertex1.sub(vertex0);
	    edge2 = vertex2.sub(vertex0);
	    h = ray.m_Direction.cross(edge2);
	    a = edge1.dot(h);
	    if (a > -EPSILON && a < EPSILON)
	        return null;
	    f = 1/a;
	    s = ray.m_Origin.sub(vertex0);
	    u = f * (s.dot(h));
	    if (u < 0.0 || u > 1.0)
	        return null;
	    q = s.cross(edge1);
	    v = f * ray.m_Direction.dot(q);
	    if (v < 0.0 || u + v > 1.0)
	        return null;
	    // At this stage we can compute t to find out where the intersection point is on the line.
	    double t = f * edge2.dot(q);
	    if (t > EPSILON) // ray intersection
	    {
			Vector normal = edge1.cross(edge2);
	        //outIntersectionPoint = rayOrigin + rayVector * t;
	    	Vector intersect = ray.m_Direction.mul(t).add(ray.m_Origin);
	        return new Intersection(this, ray, t, normal.norm(), intersect);
	    }
	    else // This means that there is a line intersection but not a ray intersection.
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
		return m_solid;
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
