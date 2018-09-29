package fc.SimpleRaytracer.Geometry;

import java.util.List;

import fc.SimpleRaytracer.Material.Material;
import fc.SimpleRaytracer.Math.Ray;

public interface Thing {
	
	List<Intersection> intersections(Ray r);
	public Intersection intersect(Ray ray);
	public Material getMaterial();
	public boolean isSolid();
}
