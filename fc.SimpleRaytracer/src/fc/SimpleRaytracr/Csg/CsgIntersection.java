package fc.SimpleRaytracr.Csg;

import java.util.List;

import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Geometry.Thing;
import fc.SimpleRaytracer.Material.Material;
import fc.SimpleRaytracer.Math.Ray;
import fc.SimpleRaytracer.Math.Vector;

public class CsgIntersection implements Thing {

	
	Thing m_Thing1;
	Thing m_Thing2;
	Material m_material;
	
	public CsgIntersection(Thing thing1, Thing thing2) {
		m_Thing1 = thing1;
		m_Thing2 = thing2;
	}
	
	
	@Override
	public Intersection intersect(Ray ray) {
		// TODO Auto-generated method stub
		List<Intersection> intersectionsThing1 = m_Thing1.intersections(ray);
		List<Intersection> intersectionsThing2 = m_Thing2.intersections(ray);

		if(intersectionsThing1.isEmpty())
			return null;		
		if(intersectionsThing2.isEmpty())
				return null;

		for(int i=0; i<intersectionsThing1.size()-1; i=i+2) {
			double a = intersectionsThing1.get(i).m_RayDistance;
			double b = intersectionsThing1.get(i+1).m_RayDistance;
			
			for(int j=0; j<intersectionsThing2.size(); j++) {
				double c = intersectionsThing2.get(j).m_RayDistance;
				if(c>=a && c<=b) {
					m_material = intersectionsThing2.get(j).m_Thing.getMaterial();
					Vector positionIntersection = intersectionsThing2.get(j).m_Intersection;
					Vector normal = intersectionsThing2.get(j).m_Normal;
					double t1 =  intersectionsThing2.get(j).m_RayDistance;
					return new Intersection(this, ray, t1, normal, positionIntersection);					
				}				
				else {
					m_material = intersectionsThing1.get(i).m_Thing.getMaterial();
					Vector positionIntersection = intersectionsThing1.get(i).m_Intersection;
					Vector normal = intersectionsThing1.get(i).m_Normal;
					double t1 =  intersectionsThing1.get(i).m_RayDistance;
					return new Intersection(this, ray, t1, normal, positionIntersection);					
				}
			}
		}
		
		return null;
	}
	

	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return m_material; 
	}

	
	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<Intersection> intersections(Ray r) {
		// TODO Auto-generated method stub
		return null;
	}
}
