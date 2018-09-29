package fc.SimpleRaytracer.Math;

public class Ray
{
	public Vector m_Origin;
	public Vector m_Direction;
	public boolean m_IsRefractionRay = false;
	
	public Ray(Vector start, Vector dir)
	{
		this.m_Origin = start;
		this.m_Direction = dir;
	}
}
