package fc.SimpleRaytracer.Material;

import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Math.Vector;
import fc.SimpleRaytracer.Rendering.Color;
import fc.SimpleRaytracer.Rendering.Light;
import fc.SimpleRaytracer.Rendering.Raytracer;

public class LambertMaterial extends Material {

	@Override
	public Color shade(Intersection isect, Light light, Raytracer tracer, double e) {
		// TODO Auto-generated method stub		
		Vector N = isect.m_Normal.norm();
		Vector L = light.m_Position.sub(isect.m_Intersection);
		double lambertian = Math.max(N.dot(L), 0.0);
		return new Color(lambertian, lambertian, lambertian);
	}

	
	@Override
	public double getReflexBlend() {
		// TODO Auto-generated method stub
		return 0.5;
	}
}
