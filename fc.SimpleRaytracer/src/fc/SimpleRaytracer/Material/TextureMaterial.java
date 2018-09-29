package fc.SimpleRaytracer.Material;

import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Math.Vector;
import fc.SimpleRaytracer.Rendering.Color;
import fc.SimpleRaytracer.Rendering.Light;
import fc.SimpleRaytracer.Rendering.Raytracer;
import fc.SimpleRaytracer.Tool.FastNoise;
import fc.SimpleRaytracer.Tool.FastNoise.CellularDistanceFunction;
import fc.SimpleRaytracer.Tool.FastNoise.FractalType;
import fc.SimpleRaytracer.Tool.FastNoise.Interp;
import fc.SimpleRaytracer.Tool.FastNoise.NoiseType;

public class TextureMaterial extends Material {

	@Override
	public Color shade(Intersection isect, Light light, Raytracer tracer, double e) {
		// TODO Auto-generated method stub
		//Compute local lighting
		FastNoise fastNoise = new FastNoise(1337);

		fastNoise.SetNoiseType(NoiseType.Cellular);
		fastNoise.SetFrequency(0.2f);
		fastNoise.SetInterp(Interp.Quintic);
		fastNoise.SetFractalType(FractalType.FBM);
		fastNoise.SetFractalOctaves(5);
		fastNoise.SetFractalLacunarity(2.0f);
		fastNoise.SetCellularDistanceFunction(CellularDistanceFunction.Euclidean);
		fastNoise.SetGradientPerturbAmp(30.0f);
		fastNoise.SetFractalGain(0.5f);
		
		double noise = (double)fastNoise.GetNoise((float)isect.m_Intersection.m_X*300, (float)isect.m_Intersection.m_Y*300, (float)isect.m_Intersection.m_Z*300);

		
		Vector noise3D = new Vector(noise,noise,noise);

		return new Color(noise3D.m_X, noise3D.m_Y, noise3D.m_Z);
	}

	@Override
	public double getReflexBlend() {
		// TODO Auto-generated method stub
		return 1;
	}

}
