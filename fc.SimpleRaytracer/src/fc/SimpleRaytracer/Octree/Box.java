package fc.SimpleRaytracer.Octree;

import java.util.ArrayList;
import java.util.List;

import fc.SimpleRaytracer.Constant;
import fc.SimpleRaytracer.Geometry.AABB;
import fc.SimpleRaytracer.Geometry.Intersection;
import fc.SimpleRaytracer.Geometry.Thing;
import fc.SimpleRaytracer.Material.Material;
import fc.SimpleRaytracer.Material.NormalMaterial;
import fc.SimpleRaytracer.Math.Ray;
import fc.SimpleRaytracer.Math.Vector;
import fc.SimpleRaytracer.Primitive.Triangle;


public class Box implements Thing {

	public AABB aabb;
	public List<Triangle> trianglesInside;
	public Box child[] = new Box[8];
	public Octree octree;
	
	public Box(AABB aabb, List<Triangle> triangles) {
		this.aabb = aabb;
		this.trianglesInside = new ArrayList<Triangle>();
		trianglesInsideOrIntersect(triangles);
		if(this.trianglesInside.size() > Constant.maxTrianglesInside) {
			split();
		}
	}
	
	// Source de cette fonction 
	// https://gist.github.com/yomotsu/d845f21e2e1eb49f647f
	public Boolean isIntersectionTriangleAABB(Triangle triangle) {		  
		// Compute box center and extents of AABoundingBox (if not already given in that format)
		Vector center = aabb.m_Max.add(aabb.m_Min).mul(0.5);
		Vector extents = aabb.m_Max.sub(center);

		// Translate triangle as conceptually moving AABB to origin
		Vector v0 = triangle.m_v0.sub(center);
		Vector v1 = triangle.m_v1.sub(center);
		Vector v2 = triangle.m_v2.sub(center);

		// Compute edge vectors for triangle
		Vector f0 = v1.sub(v0);
		Vector f1 = v2.sub(v1);
		Vector f2 = v0.sub(v2);
		
		// Test axes a00..a22 (category 3)
		Vector a00 = new Vector(0, -f0.m_Z, f0.m_Y );
		Vector a01 = new Vector(0, -f1.m_Z, f1.m_Y);
		Vector a02 = new Vector( 0, -f2.m_Z, f2.m_Y);
		Vector a10 = new Vector(f0.m_Z, 0, -f0.m_X);
		Vector a11 = new Vector(f1.m_Z, 0, -f1.m_X);
		Vector a12 = new Vector(f2.m_Z, 0, -f2.m_X);
		Vector a20 = new Vector(-f0.m_Y, f0.m_X, 0);
		Vector a21 = new Vector(-f1.m_Y, f1.m_X, 0);
		Vector a22 = new Vector(-f2.m_Y, f2.m_X, 0);
		
		// Test axis a00
		double p0 = v0.dot( a00 );
		double p1 = v1.dot( a00 );
		double p2 = v2.dot( a00 );
		double r = extents.m_Y * Math.abs(f0.m_Z) + extents.m_Z * Math.abs(f0.m_Y);

		if ( Math.max(-Math.max( p0, Math.max(p1, p2)), Math.min(p0, Math.min(p1, p2)) ) > r) {
			  return false; // Axis is a separating axis
		}
		
		// Test axis a01
		p0 = v0.dot(a01);
		p1 = v1.dot(a01);
		p2 = v2.dot(a01);
		r = extents.m_Y * Math.abs(f1.m_Z) + extents.m_Z * Math.abs(f1.m_Y);
		
		if( Math.max(-Math.max(p0, Math.max(p1, p2)), Math.min( p0, Math.min(p1, p2 ))) > r ) {
			return false; // Axis is a separating axis
		}

		// Test axis a02
		p0 = v0.dot( a02 );
		p1 = v1.dot( a02 );
		p2 = v2.dot( a02 );
		r = extents.m_Y * Math.abs(f2.m_Z) + extents.m_Z * Math.abs(f2.m_Y);

		if(Math.max(-Math.max(p0, Math.max(p1, p2)), Math.min(p0, Math.min(p1, p2))) > r ) {
			return false; // Axis is a separating axis
		}
		
		// Test axis a10
		p0 = v0.dot( a10 );
		p1 = v1.dot( a10 );
		p2 = v2.dot( a10 );
		r = extents.m_X * Math.abs(f0.m_Z) + extents.m_Z * Math.abs(f0.m_X);
		if ( Math.max(-Math.max( p0, Math.max(p1, p2)), Math.min(p0, Math.min(p1, p2))) > r ) {
			return false; // Axis is a separating axis
		}
		
		// Test axis a11
		p0 = v0.dot( a11 );
		p1 = v1.dot( a11 );
		p2 = v2.dot( a11 );
		r = extents.m_X * Math.abs(f1.m_Z) + extents.m_Z * Math.abs(f1.m_X);

		if(Math.max( -Math.max(p0, Math.max(p1, p2)), Math.min(p0, Math.min(p1, p2))) > r ) {
			return false; // Axis is a separating axis
		}
		
		// Test axis a12
		p0 = v0.dot( a12 );
		p1 = v1.dot( a12 );
		p2 = v2.dot( a12 );
		r = extents.m_X * Math.abs(f2.m_Z) + extents.m_Z * Math.abs(f2.m_X);

		if (Math.max( -Math.max(p0, Math.max(p1, p2)), Math.min( p0, Math.min(p1, p2))) > r ) {
			return false; // Axis is a separating axis
		}
		
		// Test axis a20
		p0 = v0.dot( a20 );
		p1 = v1.dot( a20 );
		p2 = v2.dot( a20 );
		r = extents.m_X * Math.abs(f0.m_Y) + extents.m_Y * Math.abs(f0.m_X);

		if(Math.max(-Math.max(p0, Math.max(p1, p2)), Math.min(p0, Math.min(p1, p2)) ) > r ) {
			return false; // Axis is a separating axis
		}
		
		// Test axis a21
		p0 = v0.dot( a21 );
		p1 = v1.dot( a21 );
		p2 = v2.dot( a21 );
		r = extents.m_X * Math.abs(f1.m_Y) + extents.m_Y * Math.abs(f1.m_X);
		
		if(Math.max( -Math.max(p0, Math.max(p1, p2)), Math.min(p0, Math.min(p1, p2))) > r ) {
			return false; // Axis is a separating axis
		}
		
		// Test axis a22
		p0 = v0.dot( a22 );
		p1 = v1.dot( a22 );
		p2 = v2.dot( a22 );
		r = extents.m_X * Math.abs(f2.m_Y) + extents.m_Y * Math.abs(f2.m_X);

		if(Math.max(-Math.max(p0, Math.max(p1, p2)), Math.min(p0, Math.min(p1, p2))) > r ) {
			return false; // Axis is a separating axis
		}
		
		// Test the three axes corresponding to the face normals of AABB b (category 1).
		// Exit if...
		// ... [-extents.x, extents.x] and [min(v0.x,v1.x,v2.x), max(v0.x,v1.x,v2.x)] do not overlap
		if ( Math.max(v0.m_X, Math.max(v1.m_X, v2.m_X)) < -extents.m_X || Math.min(v0.m_X, Math.min(v1.m_X, v2.m_X)) > extents.m_X) {
			return false;
		}
		// ... [-extents.y, extents.y] and [min(v0.y,v1.y,v2.y), max(v0.y,v1.y,v2.y)] do not overlap
		if ( Math.max(v0.m_Y, Math.max(v1.m_Y, v2.m_Y)) < -extents.m_Y || Math.min(v0.m_Y, Math.min(v1.m_Y, v2.m_Y)) > extents.m_Y) {
			return false;
		}
		// ... [-extents.z, extents.z] and [min(v0.z,v1.z,v2.z), max(v0.z,v1.z,v2.z)] do not overlap
		if ( Math.max(v0.m_Z, Math.max(v1.m_Z, v2.m_Z)) < -extents.m_Z || Math.min(v0.m_Z, Math.min(v1.m_Z, v2.m_Z)) > extents.m_Z) {
			return false;
		}
		
		return true;
	}
	
	public Boolean vertexIsInside(Vector vertex) {
		if(vertex.m_X <= aabb.m_Max.m_X && vertex.m_Y <= aabb.m_Max.m_Y && vertex.m_Z <= aabb.m_Max.m_Z)
			if(vertex.m_X >= aabb.m_Min.m_X && vertex.m_Y >= aabb.m_Min.m_Y && vertex.m_Z >= aabb.m_Min.m_Z)
				return true;
		return false;
	}
	
	
	public Boolean triangleIsInsideOrIntersect(Triangle triangle) {
		if(vertexIsInside(triangle.m_v0))
			return true;
		if(vertexIsInside(triangle.m_v1))
			return true;
		if(vertexIsInside(triangle.m_v2))
			return true;
		return false;
	}
	
	
	public void trianglesInsideOrIntersect(List<Triangle> triangles) {
		for(int i=0; i<triangles.size(); i++)
			if(triangleIsInsideOrIntersect(triangles.get(i)))
				trianglesInside.add(triangles.get(i));
			else if(isIntersectionTriangleAABB(triangles.get(i)))
				trianglesInside.add(triangles.get(i));
	}
	
	
	public void split() {
		double xMiddle = (aabb.m_Max.m_X + aabb.m_Min.m_X) * 0.5;
		double yMiddle = (aabb.m_Max.m_Y + aabb.m_Min.m_Y) * 0.5;
		double zMiddle = (aabb.m_Max.m_Z + aabb.m_Min.m_Z) * 0.5;
		
		AABB aabbUpLeftFront = new AABB(
				aabb.m_Min.m_X, yMiddle, aabb.m_Min.m_Z,
				xMiddle, aabb.m_Max.m_Y, zMiddle);
		Box boxUpLeftFront = new Box(aabbUpLeftFront, trianglesInside);
		
		AABB aabbUpLeftBack = new AABB(
				aabb.m_Min.m_X, yMiddle, zMiddle,
				xMiddle, aabb.m_Max.m_Y, aabb.m_Max.m_Z);
		Box boxUpLeftBack = new Box(aabbUpLeftBack, trianglesInside);
		
		AABB aabbDownLeftBack = new AABB(
				aabb.m_Min.m_X, aabb.m_Min.m_Y, zMiddle,
				xMiddle, yMiddle, aabb.m_Max.m_Z);
		Box boxDownLeftBack = new Box(aabbDownLeftBack, trianglesInside);
		
		AABB aabbDownLeftFront = new AABB(
				aabb.m_Min.m_X, aabb.m_Min.m_Y, aabb.m_Min.m_Z,
				xMiddle, yMiddle, zMiddle);
		Box boxDownLeftFront = new Box(aabbDownLeftFront, trianglesInside);
		
		AABB aabbUpRightFront = new AABB(
				xMiddle, yMiddle, aabb.m_Min.m_Z,
				aabb.m_Max.m_X, aabb.m_Max.m_Y, zMiddle);
		Box boxUpRightFront = new Box(aabbUpRightFront, trianglesInside);
		
		AABB aabbUpRightBack = new AABB(
				xMiddle, yMiddle, zMiddle, 
				aabb.m_Max.m_X, aabb.m_Max.m_Y, aabb.m_Max.m_Z);
		Box boxUpRightBack = new Box(aabbUpRightBack, trianglesInside);
		
		AABB aabbDownRightFront = new AABB(
				xMiddle, aabb.m_Min.m_Y, aabb.m_Min.m_Z,
				aabb.m_Max.m_X, yMiddle, zMiddle);
		Box boxDownRightFront = new Box(aabbDownRightFront, trianglesInside);
	
		AABB aabbDownRightBack = new AABB(
				xMiddle, aabb.m_Min.m_Y, zMiddle,
				aabb.m_Max.m_X, yMiddle, aabb.m_Max.m_Z);
		Box boxDownRightBack = new Box(aabbDownRightBack, trianglesInside);
		
		child[0] = boxUpLeftFront;			
		child[1] = boxUpLeftBack;		
		child[2] = boxDownLeftBack;			
		child[3] = boxDownLeftFront;
		child[4] = boxUpRightFront;			
		child[5] = boxUpRightBack;			
		child[6] = boxDownRightFront;			
		child[7] = boxDownRightBack;
	}

	@Override
	public Intersection intersect(Ray ray) {
		// TODO Auto-generated method stub
		
		 double tmin = (aabb.m_Min.m_X - ray.m_Origin.m_X) / ray.m_Direction.m_X;
		 double tmax = (aabb.m_Max.m_X - ray.m_Origin.m_X) / ray.m_Direction.m_X;
		 if (tmin > tmax) {
			 double tmp = tmax;
			 tmax = tmin;
			 tmin = tmp;
		 }
		 
		 double tymin = (aabb.m_Min.m_Y - ray.m_Origin.m_Y) / ray.m_Direction.m_Y;
		 double tymax = (aabb.m_Max.m_Y - ray.m_Origin.m_Y) / ray.m_Direction.m_Y;
		 if (tymin > tymax) {
			 double tmp = tymax;
			 tymax = tymin;
			 tymin = tmp;
		 }
		 
		 if ((tmin > tymax) || (tymin > tmax))
			 return null;
		 
		 if (tymin > tmin)
			 tmin = tymin;
		 
		 if (tymax < tmax)
			 tmax = tymax;
		 
		 double tzmin = (aabb.m_Min.m_Z - ray.m_Origin.m_Z) / ray.m_Direction.m_Z;
		 double tzmax = (aabb.m_Max.m_Z - ray.m_Origin.m_Z) / ray.m_Direction.m_Z;
		 
		 if (tzmin > tzmax) {
			 double tmp = tzmax;
			 tzmax = tzmin;
			 tzmin = tmp;
		 }
		 
		 if ((tmin > tzmax) || (tzmin > tmax))
			 return null;
		 
		 if (tzmin > tmin)
			 tmin = tzmin;
		 
		 if (tzmax < tmax)
			 tmax = tzmax;

		double closest = Double.POSITIVE_INFINITY;
		Intersection closestIntersection = null;
		
		//If the box is not a leaf.
		if(child[0] != null) {
			for(int i=0; i<child.length; i++) {
				Intersection inter = child[i].intersect(ray);
				if (inter != null && inter.m_RayDistance < closest) {
					closestIntersection = inter;
					closest = inter.m_RayDistance;
				}			
			}
		}
		
		else {
			//Else is a leaf, so we can look for triangles inside.
			for(int i=0; i<trianglesInside.size(); i++) {
				Intersection inter = trianglesInside.get(i).intersect(ray);
				if (inter != null && inter.m_RayDistance < closest) {
					closestIntersection = inter;
					closest = inter.m_RayDistance;
				}
			 }
		}
		 
		 //return new Intersection(this, ray, dist, new Vector(1, 1, 1), intersection);
		 return closestIntersection;
	}

	@Override
	public Material getMaterial() {
		// TODO Auto-generated method stub
		return new NormalMaterial();
	}

	@Override
	public boolean isSolid() {
		// TODO Auto-generated method stub
		return true;
	}
	
	
	@Override
	public List<Intersection> intersections(Ray r) {
		List<Intersection> intersections = new ArrayList<Intersection>();
		Intersection intersection = intersect(r);
		if(intersection != null) {
			double distDebut = intersection.m_RayDistance;
			do {
				intersections.add(intersection);
				Ray newRay = new Ray(intersection.m_Intersection.add(r.m_Direction.mul(Math.pow(10, -6))), r.m_Direction);
				intersection = intersect(newRay);
				if(intersection !=null){
					intersection.m_RayDistance += distDebut;
				}
			}while(intersection!=null);
		}
		return intersections;
	}
}
