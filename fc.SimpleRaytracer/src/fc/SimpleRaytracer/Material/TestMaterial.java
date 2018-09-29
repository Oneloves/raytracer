package fc.SimpleRaytracer.Material;


import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Math.Vector;
import fc.SimpleRaytracer.Rendering.Color;
import fc.SimpleRaytracer.Rendering.Light;
import fc.SimpleRaytracer.Rendering.Raytracer;
import fc.SimpleRaytracer.Tool.Tool;

public class TestMaterial  extends Material {

	double m_Ka;
	double m_Kd;
	double m_Ks;
	double m_Shininess;
	double m_Ior;
	double m_ReflexBlend;
	Color m_DiffuseColor;
	Color m_AmbientColor;
	double m_reflectBlend;
	
	public TestMaterial(Color diffuseColor, Color ambientColor, double specular, double reflectBlend) {
		this.m_Ka = 1;
		this.m_Kd = 1;
		this.m_Shininess = 15;
		this.m_DiffuseColor = diffuseColor;
		this.m_AmbientColor = ambientColor;
		this.m_ReflexBlend = 0;
		this.m_Ior = 0;
		this.m_Ks = specular;
		m_reflectBlend = reflectBlend;
	}
	
	
	public Color phongShading(Intersection isect, Light light) {
		Vector N = isect.m_Normal;
		Vector L = light.m_Position.sub(isect.m_Intersection).norm();
		
		double lambertian = Math.max(N.dot(L), 0.0);
		double specular = 0.0;
		
		if(lambertian > 0.0) {
			Vector R = Tool.reflect(L.neg(), N);
			Vector V = isect.m_Ray.m_Direction.norm().neg();
			double specAngle = Math.max(V.dot(R), 0.0);
		    specular = Math.pow(specAngle, m_Shininess);
		}
		
		double red =  m_Ka * m_AmbientColor.m_Red + m_Kd * lambertian * m_DiffuseColor.m_Red + m_Ks * specular * light.m_Color.m_Red;
		double green = m_Ka * m_AmbientColor.m_Green + m_Kd * lambertian * m_DiffuseColor.m_Green + m_Ks * specular * light.m_Color.m_Green;
		double blue = m_Ka * m_AmbientColor.m_Blue + m_Kd * lambertian * m_DiffuseColor.m_Blue + m_Ks * specular * light.m_Color.m_Blue;
		return new Color(red, green, blue);
	}


	@Override
	public Color shade(Intersection isect, Light light, Raytracer tracer, double e) {
		// TODO Auto-generated method stub	
		//Compute local lighting
		Color phongColor = phongShading(isect, light);
		Color color = phongColor;
		return color;
	}
	
	@Override
	public double getReflexBlend() {
		// TODO Auto-generated method stub
		return m_reflectBlend;
	}
}