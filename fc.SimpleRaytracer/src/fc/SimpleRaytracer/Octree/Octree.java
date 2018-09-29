package fc.SimpleRaytracer.Octree;



import fc.SimpleRaytracer.Geometry.AABB;
import fc.SimpleRaytracer.Primitive.Mesh;

public class Octree {

	public Box box;
	public AABB aabb;
	public Mesh mesh;
	
	public Octree(Mesh mesh) {
		this.mesh = mesh;
		this.aabb = new AABB(mesh.xMin, mesh.yMin, mesh.zMin, mesh.xMax, mesh.yMax, mesh.zMax);
		box = new Box(this.aabb, mesh.triangles);
	}
	
}
