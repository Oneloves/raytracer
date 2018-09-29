package fc.SimpleRaytracer.Primitive;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fc.SimpleRaytracer.Material.Material;
import fc.SimpleRaytracer.Math.Vector;

public class Mesh {

	
	public String filePath;
	public Material material;
	public List<Vector> vectors;
	public List<Triangle> triangles;
	
	public double xMin;
	public double xMax;
	public double yMin;
	public double yMax;
	public double zMin;
	public double zMax;
	
	
	public Mesh(String filePath, Material material) {
		this.filePath = filePath;
		this.material = material;
		load();
		buildMesh();
	}
	
	
	public void load() {
		vectors = new ArrayList<Vector>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while((line = bufferedReader.readLine()) != null) {
            	if(line.length() > 2)
					if(line.charAt(0) == 'v' && line.charAt(1) == ' ') {
						String sCoordonate[] = line.split(" ");
						double x = Double.parseDouble(sCoordonate[1]);
						double y = Double.parseDouble(sCoordonate[2]);
						double z = Double.parseDouble(sCoordonate[3]);
						Vector vector = new Vector(x, y, z);
						vectors.add(vector);
					}
            }   
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {}
        catch(IOException ex) {}
	}
	
	
	public void addTriangle(String line) {
		line = line.replaceAll("//", "/");
		String sFace[] = line.split(" ");
		String index0[] = sFace[1].split("/");
		String index1[] = sFace[2].split("/");
		String index2[] = sFace[3].split("/");
		Vector v0 = vectors.get(Integer.parseInt(index0[0])-1);
		Vector v1 = vectors.get(Integer.parseInt(index1[0])-1);
		Vector v2 = vectors.get(Integer.parseInt(index2[0])-1);
		compare(v0);
		compare(v1);
		compare(v2);
		Triangle triangle = new Triangle(v0, v1, v2, this.material, true);
		triangles.add(triangle);
	}	
	
	
	public void buildMesh() {
		triangles = new ArrayList<Triangle>();
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);            
            String line;
            Boolean isFirst = true;
            while((line = bufferedReader.readLine()) != null) {
            	if(line.length()>0)
					if(line.charAt(0) == 'f') {
						if(isFirst) {
							addTriangle(line);
				            Triangle firstTriangle = triangles.get(0);            
				            initMinMax(firstTriangle);
				            isFirst = false;
						}
						else
							addTriangle(line);
					}
            }
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {}
        catch(IOException ex) {}
	}
	
	
	public void compare(Vector vertex) {
		if(vertex.m_X >= xMax)
			xMax = vertex.m_X;
		if(vertex.m_X <= xMin)
			xMin = vertex.m_X;
		
		if(vertex.m_Y >= yMax)
			yMax = vertex.m_Y;
		if(vertex.m_Y <= yMin)
			yMin = vertex.m_Y;
		
		if(vertex.m_Z >= zMax)
			zMax = vertex.m_Z;
		if(vertex.m_Z <= zMin)
			zMin = vertex.m_Z;
	}
	
	
	public void initMinMax(Triangle triangle) {
        Vector v0 = triangle.m_v0;
        Vector v1 = triangle.m_v1;
        Vector v2 = triangle.m_v2;        
        //Xmax
        if(v0.m_X >= v1.m_X && v0.m_X >= v2.m_X)
        	xMax = v0.m_X;
        else if(v1.m_X >= v0.m_X && v1.m_X >= v2.m_X)
        	xMax = v1.m_X;
        else
        	xMax = v2.m_X;        
        //Xmin
        if(v0.m_X <= v1.m_X && v0.m_X <= v2.m_X)
        	xMin = v0.m_X;
        else if(v1.m_X <= v0.m_X && v1.m_X <= v2.m_X)
        	xMin = v1.m_X;
        else
        	xMin = v2.m_X;        
        //Ymax
        if(v0.m_Y >= v1.m_Y && v0.m_Y >= v2.m_Y)
        	yMax = v0.m_Y;
        else if(v1.m_Y >= v0.m_Y && v1.m_Y >= v2.m_Y)
        	yMax = v1.m_Y;
        else
        	yMax = v2.m_Y;        
        //Ymin
        if(v0.m_Y <= v1.m_Y && v0.m_Y <= v2.m_Y)
        	yMin = v0.m_Y;
        else if(v1.m_Y <= v0.m_Y && v1.m_Y <= v2.m_Y)
        	yMin = v1.m_Y;
        else
        	yMin = v2.m_Y;        
        //Zmax
        if(v0.m_Z >= v1.m_Z && v0.m_Z >= v2.m_Z)
        	zMax = v0.m_Z;
        else if(v1.m_Z >= v0.m_Z && v1.m_Z >= v2.m_Z)
        	zMax = v1.m_Z;
        else
        	zMax = v2.m_Z;        
        //Zmin
        if(v0.m_Z <= v1.m_Z && v0.m_Z <= v2.m_Z)
        	zMin = v0.m_Z;
        else if(v1.m_Z <= v0.m_Z && v1.m_Z <= v2.m_Z)
        	zMin = v1.m_Z;
        else
        	zMin = v2.m_Z;
	}
}
