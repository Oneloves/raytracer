package fc.SimpleRaytracer.Material;

import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Math.Vector;
import fc.SimpleRaytracer.Rendering.Color;
import fc.SimpleRaytracer.Rendering.Light;
import fc.SimpleRaytracer.Rendering.Raytracer;

public class NormalMaterial extends Material {

	@Override
	public Color shade(Intersection isect, Light light, Raytracer tracer, double e) {
		Vector normal = isect.m_Normal;
		return new Color(Math.max(normal.m_X, 0.0), Math.max(normal.m_Y, 0.0), Math.max(normal.m_Z, 0.0));
	}

	@Override
	public double getReflexBlend() {
		// TODO Auto-generated method stub
		return 0;
	}
}
