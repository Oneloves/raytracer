package fc.SimpleRaytracer.Tool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fc.SimpleRaytracer.Math.Vector;

public class Tool {
	
	public static BufferedImage getImage(String filePath) {
		File file= new File(filePath);
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {e.printStackTrace();}
			  
	  return image;
	}
	
	public static Vector reflect(Vector I, Vector N) {
		double dotResult = N.dot(I);
		double x = I.m_X - 2.0 * dotResult * N.m_X;
		double y = I.m_Y - 2.0 * dotResult * N.m_Y;
		double z = I.m_Z - 2.0 * dotResult * N.m_Z;
		return new Vector(x, y, z);
	}
	
	
	public Vector refraction(Vector I, Vector N, double ior) {
		double cosi = Tool.clamp(I.dot(N), -1, 1);
		double etai = 1;
		double etat = ior;
		Vector n = N;		
		if(cosi < 0)
			cosi = -cosi; 
		else { 
			double tmp = etai;
			etai = etat;
			etat = tmp;
			n= N.neg(); 
		}		
		double eta = etai / etat;
		double k = 1 - eta * eta * (1 - cosi * cosi);		
		if(k<0)
			return null; 		
		return I.mul(eta).add(n.mul((eta * cosi - Math.sqrt(k))));
	}
	
	
	public static Vector clamp(Vector v, double minVal, double maxVal) {
		Vector vector = new Vector(0.0, 0.0, 0.0);
		vector.m_X = Math.min(Math.max(v.m_X, minVal), maxVal);
		vector.m_Y = Math.min(Math.max(v.m_Y, minVal), maxVal);
		vector.m_Z = Math.min(Math.max(v.m_Z, minVal), maxVal);	
		return vector;
	}

	
	public static Double clamp(Double k, double minVal, double maxVal) {
		return  Math.min(Math.max(k, minVal), maxVal);
	}
	
	
	public static Vector refract(Vector I, Vector N, double etai, double ior) {
		double cosi = clamp(N.dot(I), -1.0, 1.0);
		double etat = ior;
		Vector n = N;
		
		if(cosi < 0)
			cosi = -cosi;
		else {
			double tmp = etai;
			etai = etat;
			etat = tmp;
			n = N.neg();
		}
		double eta = etai / etat;
		double k = 1 - eta * eta * (1 - cosi * cosi);
		
		if(k < 0.0)
			return  new Vector(0.0, 0.0, 0.0);
		
		double x = eta * I.m_X + (eta * cosi - Math.sqrt(k)) * n.m_X;
		double y = eta * I.m_Y + (eta * cosi - Math.sqrt(k)) * n.m_Y;
		double z = eta * I.m_Z + (eta * cosi - Math.sqrt(k)) * n.m_Z;
		Vector v_refract = new Vector(x, y, z);
		
		return v_refract;
	}
	
	
	public static Vector shiftPosition(Vector vector, Vector direction) {
		Vector shift = new Vector(0.0, 0.0, 0.0);
		if(direction.m_X < 0.0)
			shift.m_X = -0.0000000001;
		else
			shift.m_X = 0.0000000001;
		
		if(direction.m_Y < 0.0)
			shift.m_Y = -0.0000000001;
		else
			shift.m_Y = 0.0000000001;
		
		if(direction.m_Z < 0.0)
			shift.m_Z = -0.0000000001;
		else
			shift.m_Z = 0.0000000001;
		
		return vector.add(shift);
	}
}
