package fc.SimpleRaytracer.Rendering;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import fc.SimpleRaytracer.Constant;
import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Geometry.Thing;
import fc.SimpleRaytracer.Material.Material;
import fc.SimpleRaytracer.Math.Ray;
import fc.SimpleRaytracer.Math.Vector;
import fc.SimpleRaytracer.Tool.Tool;


public class Raytracer {
	final int m_MaxDepth = 5;
	double m_LastDist;
	
	private Intersection intersect(Ray ray, Scene scene) {
		double closest = Double.POSITIVE_INFINITY;
		Intersection closestIntersection = null;
		
		for (Thing thing : scene.m_Things) {
			Intersection inter = thing.intersect(ray);
			if (inter != null && inter.m_RayDistance < closest) {
				closestIntersection = inter;
				closest = inter.m_RayDistance;
			}
		}
		return closestIntersection;
	}
	
	
	public Intersection reflection(Intersection isect, Scene scene) {
		isect.m_Normal = isect.m_Normal.norm();
		//Descarte
		Vector reflectDirection = Tool.reflect(isect.m_Ray.m_Direction.norm(), isect.m_Normal);
		//Need to shift the position to not compute the same intersection
		isect.m_Intersection = Tool.shiftPosition(isect.m_Intersection, isect.m_Normal);
		Ray reflectRay = new Ray(isect.m_Intersection, reflectDirection.norm());
		Intersection closestIntersection = intersect(reflectRay, scene);


		return closestIntersection;
	}
	
	
	
	public Vector refract(Vector I, Vector N, double ior) {
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
	
	public Color traceRay(Ray primaryRay, Scene scene, int depth, double a_RIndex) {
		
		//Primary Ray intersection
		Intersection isect = intersect(primaryRay, scene);
		if (isect == null)
			return Color.Background;		
		m_LastDist = isect.m_RayDistance;
		Color color = shade(isect, scene, depth, a_RIndex);
		
		//Reflection
		Intersection reflect = isect;
		for(int i=0; i<Constant.reflectionNb; i++) {
			double reflectionBlend = reflect.m_Thing.getMaterial().getReflexBlend();
			reflect = reflection(reflect, scene);
			if (reflect == null || reflectionBlend==0.0) {
				break;
			}
			Color tmpColor = shade(reflect, scene, depth, a_RIndex);
			color = color.add(tmpColor.scale(reflectionBlend));
		}
		
		return color;
	}
	
	
	public boolean isSeeLight(Intersection isect, Scene scene, Light light) {
		//Need to shift the position to not compute the same intersection
		isect.m_Intersection = Tool.shiftPosition(isect.m_Intersection, isect.m_Normal.norm());
		Ray shadowRay = new Ray(isect.m_Intersection, light.m_Position.norm());
		double closest = Double.POSITIVE_INFINITY;
		for (Thing thing : scene.m_Things) {
			if(thing != isect.m_Thing && thing.isSolid()) {
				Intersection inter = thing.intersect(shadowRay);
				if(inter != null && inter.m_RayDistance < closest) {
					return false;
				}
			}
		}
		return true;
	}
	
	
	public Color softShadow(Intersection isect, Scene scene, Light light, Color color) {
		double jitter[] = Constant.jitterSoftShadow;
		double jitterLenght = Constant.jitterSoftShadow.length/2;
		Color shadowColor = new Color(0,0,0);
		for(int sample=0; sample<jitterLenght; sample++) {
			double x = jitter[2*sample] + isect.m_Intersection.m_X;
			double y = jitter[2*sample+1] + isect.m_Intersection.m_Y;
			Intersection isectShift = isect;
			isectShift.m_Intersection.m_X = x;
			isectShift.m_Intersection.m_Y = y;
			boolean seeLight = isSeeLight(isectShift, scene, light);
			if(seeLight)
				shadowColor = shadowColor.add(color);
		}
		color = shadowColor.div(jitter.length);
		return color;
	}
	
	
	public Color shade(Intersection isect, Scene scene, int depth, double a_RIndex) {
		Material material = isect.m_Thing.getMaterial();
		Color color = new Color(0,0,0);
        for (Light light : scene.m_Lights){
        	Color tmpColor;
        	//If shadow is activate or softShadow
        	if(Constant.shadow || Constant.softShadow) {
        		//Here no need to compute the shadow
	        	if(isSeeLight(isect, scene, light))
	        		tmpColor = material.shade(isect, light, this, a_RIndex);
	        	//If soft shadow is activate
	        	else if(Constant.softShadow){
	        		tmpColor = material.shade(isect, light, this, a_RIndex);
	        		tmpColor = softShadow(isect, scene, light, tmpColor);
	        	}
	        	//If only shadow is activate
	        	else
	        		tmpColor = new Color(0.0, 0.0, 0.0);
        	}
        	//If no shadow or softShadow
        	else
        		tmpColor = material.shade(isect, light, this, a_RIndex);
        	color = color.add(tmpColor);
        }
        return color;
	}
	
	/*
	public Color traceRay(Ray primaryRay, Scene scene, int depth, double a_RIndex) {
		//Primary Ray intersection
		Intersection isect = intersect(primaryRay, scene);
		if (isect == null)
			return Color.Background;		
		m_LastDist = isect.m_RayDistance;
		Color color = shade(isect, scene, depth, a_RIndex);
		
		return color;
	}
	*/
	/*
	public Color shade(Intersection isect, Scene scene, int depth, double a_RIndex) {
		Material material = isect.m_Thing.getMaterial();
		Color c = new Color(0,0,0);
        for (Light light : scene.m_Lights) {
    		Color color = material.shade(isect, light, this, a_RIndex);
    		c = c.add(color);
        }
        return c;
	}
	 */
	
	public Vector getPoint(double x, double y, Camera camera) {
		double dx = x - (Constant.width / 2.0);
		dx /= (double)Math.max(Constant.width, Constant.height);
		double dy = y - (Constant.height / 2.0);
		dy /= (double)Math.max(Constant.width, Constant.height);
        Vector result = camera.m_RightDirection.mul(dx).add(camera.m_UpDirection.mul(dy)).add(camera.m_ForwardDirection.mul(camera.m_FocalDistance)).norm();
        return result;
	}
	
	public Color antiAliassing(double x, double y, Scene scene, Camera camera, double jitter[], int jitterLenght) {
		Color color = new Color(0.0, 0.0, 0.0);
		for(int sample=0; sample<jitterLenght; sample++) {
			double i = x + jitter[2*sample];
			double j = y + jitter[2*sample+1];
			Vector dir = getPoint(i, j, camera);
			Color colorTmp = traceRay(new Ray(camera.m_Position, dir), scene, 0, 1.0);
			color = color.add(colorTmp);
		}
		return color.div(jitterLenght);
	}
	
	
	public Color depthOfField(double x, double y, Scene scene, double jitter[], int jitterLenght, double jitterAliassing[], int jitterAliassingLenght) {
		Color color = new Color(0.0, 0.0, 0.0);
		Color tmpColor;
		for(int sample=0; sample<jitterLenght; sample++) {
			Vector position = scene.m_Camera.m_Position;
			position.m_X = position.m_X + jitter[sample*2];
			position.m_Y = position.m_Y + jitter[sample*2 + 1];
			Camera camera = new Camera (position, scene.m_Camera.m_LookAt);
			if(Constant.antiAliassingX21 == true || Constant.antiAliassingX4 == true)
				tmpColor = antiAliassing((double)x, (double)y, scene, camera, jitterAliassing, jitterAliassingLenght);
			else {
				Vector dir = getPoint((double)x, (double)y, scene.m_Camera);
				tmpColor = traceRay(new Ray(scene.m_Camera.m_Position, dir), scene, 0, 1.0);
			}	
			color = tmpColor.add(color);
		}	
		return color.div(jitterLenght);
	}
	
	
	public void render(Scene scene, BufferedImage image) {

		double jitterAliassing[] = null;
		int jitterAliassingLenght = 0;
		
		if(Constant.antiAliassingX4) {
			jitterAliassing = Constant.jitterAliasingX4;
			jitterAliassingLenght = 4;
		}
		else if(Constant.antiAliassingX21) {
			jitterAliassing = Constant.jitterAliasingX21;
			jitterAliassingLenght = 21;
		}
		
		//No Antialiassing No DepthOfField
		if(Constant.antiAliassingX21 == false && Constant.antiAliassingX4 == false && Constant.depthOfField == false)
			for (int y=0; y < Constant.height; y++) {
				System.out.println("Raytracing scanline: " + y + "/" + Constant.height);
				for (int x=0; x < Constant.width; x++) {
					Vector dir = getPoint((double)x, (double)y, scene.m_Camera);
					Color color = traceRay(new Ray(scene.m_Camera.m_Position, dir), scene, 0, 1.0);
					int rgb = color.toRGB();
					image.setRGB(x, Constant.height-y-1, rgb);
					
					
				}
			}
		
		// DepthOfField With AntiAliassing Or Not
		else if(Constant.depthOfField) {
			double[] jitterDepthOfField = Constant.jitteDepthOfField;
			int jitterDepthOfFieldLenght = jitterDepthOfField.length/2;
			
			for (int y=0; y < Constant.height; y++) {
				System.out.println("Raytracing scanline: " + y + "/" + Constant.height);
				for (int x=0; x < Constant.width; x++) {
					Color color = depthOfField(x, y, scene, jitterDepthOfField, jitterDepthOfFieldLenght, jitterAliassing, jitterAliassingLenght);
					int rgb = color.toRGB();
					image.setRGB(x, Constant.height-y-1, rgb);
				}			
			}
		}
		
		//Only Antialiassing No DepthOfField
		else
			for (int y=0; y < Constant.height; y++) {
				System.out.println("Raytracing scanline: " + y + "/" + Constant.height);
				for (int x=0; x < Constant.width; x++) {
					Color color = antiAliassing((double)x, (double)y, scene, scene.m_Camera, jitterAliassing, jitterAliassingLenght);
					int rgb = color.toRGB();
					image.setRGB(x, Constant.height-y-1, rgb);
				}
			}
		System.out.println("Complete.");
	}
}
