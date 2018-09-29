package fc.SimpleRaytracer.Material;

import java.awt.image.BufferedImage;

import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Math.Vector;
import fc.SimpleRaytracer.Rendering.Color;
import fc.SimpleRaytracer.Rendering.Light;
import fc.SimpleRaytracer.Rendering.Raytracer;

public class EnvironementMaterial extends Material {

	public BufferedImage image;
	
	public EnvironementMaterial(BufferedImage image) {
		this.image = image;
	}
	
	@Override
	public Color shade(Intersection isect, Light light, Raytracer tracer, double e) {
		Vector N = isect.m_Normal;
		
		double u = N.m_X/2 + 0.5;
		double v = N.m_Y/2 + 0.5;
		
		int x = (int)(u * image.getWidth());
		int y = (int)(v * image.getHeight());
		
		Color color = new Color(0.0, 0.0, 0.0);
		int clr =  image.getRGB(x, y); 
		int  red = (clr & 0x00ff0000) >> 16;
		int  green = (clr & 0x0000ff00) >> 8;
		int  blue  =  clr & 0x000000ff;
		
		color.m_Red = (double)red/255.0;
		color.m_Green = (double)green/255.0;
		color.m_Blue = (double)blue/255.0;
		  
		return color;
	}
	
	@Override
	public double getReflexBlend() {
		// TODO Auto-generated method stub
		return 0;
	}
}

