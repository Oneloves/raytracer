package fc.SimpleRaytracer.Material;

import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Rendering.Color;
import fc.SimpleRaytracer.Rendering.Light;
import fc.SimpleRaytracer.Rendering.Raytracer;

public abstract class Material
{
	public abstract Color shade(Intersection isect, Light light, Raytracer tracer, double e);	
	public abstract double getReflexBlend();

}
