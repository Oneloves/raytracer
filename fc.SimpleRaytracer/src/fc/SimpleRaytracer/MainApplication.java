package fc.SimpleRaytracer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import fc.SimpleRaytracer.Geometry.Thing;
import fc.SimpleRaytracer.Implicit.Implicit;
import fc.SimpleRaytracer.Implicit.Torus;
import fc.SimpleRaytracer.Material.EnvironementMaterial;
import fc.SimpleRaytracer.Material.TestMaterial;
import fc.SimpleRaytracer.Math.Vector;
import fc.SimpleRaytracer.Octree.Octree;
import fc.SimpleRaytracer.Primitive.Mesh;
import fc.SimpleRaytracer.Primitive.Sphere;
import fc.SimpleRaytracer.Primitive.Triangle;
import fc.SimpleRaytracer.Rendering.Camera;
import fc.SimpleRaytracer.Rendering.Color;
import fc.SimpleRaytracer.Rendering.Light;
import fc.SimpleRaytracer.Rendering.Raytracer;
import fc.SimpleRaytracer.Rendering.Scene;
import fc.SimpleRaytracer.Tool.FastNoise;
import fc.SimpleRaytracer.Tool.Tool;
import fc.SimpleRaytracr.Csg.CsgDifference;
import fc.SimpleRaytracr.Csg.CsgIntersection;

public class MainApplication
{
	public static void main(String[] args) {
		new MainApplication().run(args);
	}
	
	public void run(String[] args) {
		BufferedImage bi = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
		BufferedImage environement = Tool.getImage(Constant.environementPath);
		
		Color diffuseColor1 = new Color(1.0, 0.0, 0.0);
		Color diffuseColor2 = new Color(1.0, 1.0, 0.0);
		Color diffuseColor3 = new Color(0.0, 1.0, 0.0);
		Color diffuseColor4 = new Color(0.0, 0.0, 1.0);
		
		Color ambientColor1 = new Color(0.2, 0.0, 0.0);
		Color ambientColor2 = new Color(0.2, 0.2, 0.0);
		Color ambientColor3 = new Color(0.0, 0.2, 0.0);
		Color ambientColor4 = new Color(0.0, 0.0, 0.2);
		
		Mesh mesh = new Mesh("Bunny.obj", new TestMaterial(diffuseColor1, ambientColor1, 1.0, 0));
		Octree bunnyOctree = new Octree(mesh);

		double k = 3.75;
		double y = -1.5;

		List<Thing> things = new ArrayList<Thing>();

		things.add(bunnyOctree.box);
		//things.add(new Sphere(new Vector(3.0, 2.0, 4.0), 100.0, new EnvironementMaterial(environement), false));
		//things.add(new Sphere(new Vector(-1.0, 0.7, 0.0), 0.25, new TestMaterial(diffuseColor1, ambientColor1, 1.0, 0), true));
		//things.add(new Sphere(new Vector(-2.0, 0.7, -10), 0.25, new TestMaterial(diffuseColor2, ambientColor2, 1.0, 0), true));
		//things.add(new Sphere(new Vector(-2.0, 0.7, -5), 0.25, new TestMaterial(diffuseColor3, ambientColor3, 1.0, 0), true));
		//things.add(new Sphere(new Vector(1.5, -0.7, -5), 0.25, new TestMaterial(diffuseColor1, ambientColor1, 1.0, 0), true));
		//things.add(new Sphere(new Vector(1.0, 2.0, -7), 0.25, new TestMaterial(diffuseColor2, ambientColor2, 1.0, 0), true));
		//things.add(new Sphere(new Vector(-6.0, 2.0, -6), 0.25, new TestMaterial(diffuseColor3, ambientColor3, 1.0, 0), true));
		//things.add(new Sphere(new Vector(-13.0, 2.0, -6), 1.0, new TestMaterial(diffuseColor4, ambientColor4, 1.0, 0), true));
		//things.add(new Torus(new TestMaterial(diffuseColor1, ambientColor1, 1.0, 0)));
		things.add(new Triangle(new Vector(-k,y,-k), new Vector(-k, y, k), new Vector(k, y, k), new TestMaterial(diffuseColor4, ambientColor4, 0, 1.0), true));
		things.add(new Triangle(new Vector(-k,y,-k), new Vector( k, y, k), new Vector(k, y,-k), new TestMaterial(diffuseColor4, ambientColor4, 0, 1.0), true));

		
		// CSG
		//Mesh cube = new Mesh("Cube.obj", new TestMaterial(diffuseColor1, ambientColor1, 0, 0));
		//Octree cubeOctree = new Octree(cube);
		//Thing sphere1 = new Sphere(new Vector(0.5, 0.5, -0.5), 0.33, new TestMaterial(diffuseColor2, ambientColor2, 0, 0), true);
		//things.add(new CsgDifference(cubeOctree.box, sphere1));

		
		// CSG
		//Mesh cube2 = new Mesh("Cube2.obj", new TestMaterial(diffuseColor1, ambientColor1, 0, 0));
		//Octree cube2Octree = new Octree(cube2);
		//Thing sphere2 = new Sphere(new Vector(-0.10, -0.85, 1.2), 0.33, new TestMaterial(diffuseColor2, ambientColor2, 0, 0), true);
		//things.add(new CsgIntersection(cube2Octree.box, sphere2));


		Light[] lights = {
			//new Light(new Vector(3.0, 2.0, 4.0), new Color(1.0, 1.0, 1.0)),
			//new Light(new Vector(-2.0, 2.5, 0.0), new Color(1.0, 1.0, 1.0)),
			//new Light(new Vector( 1.5, 2.5, 1.5), new Color(1.0, 1.0, 1.0)),
			new Light(new Vector( 1.5, 2.5,-1.5), new Color(1.0, 1.0, 1.0)),
			//new Light(new Vector(0.0, 10.0, 0.0), new Color(1.0, 1.0, 1.0)),
		};
		
		Camera camera = new Camera(new Vector(3.0, 2.0, 4.0), new Vector(0.0, 0.0, 0.0));
		//Camera camera = new Camera(new Vector(1.5, 5.5,-1.5), new Vector(0.0, 0.0, 0.0));
		Scene scene = new Scene(things, lights, camera);
		
		try {
			Raytracer raytracer = new Raytracer();
			raytracer.render(scene, bi);
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}
	
	    File outputfile = new File("result.png");
	    
	    try {
	    	ImageIO.write(bi, "png", outputfile);
	    }
	    catch (IOException e) {
	    	System.out.println("Error, IOException caught: " + e.toString());
	    }
	}
}
