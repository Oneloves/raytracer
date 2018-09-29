package fc.SimpleRaytracer.Rendering;

import fc.SimpleRaytracer.Math.Vector;


public class Camera {
	
	
	public Vector m_Position;
	public Vector m_ForwardDirection;
	public Vector m_RightDirection;
	public Vector m_UpDirection;
	public Vector m_LookAt;
	
	
	public double m_FocalDistance = 0.8;

	
    public Camera(Vector pos, Vector lookAt) {
    	this.m_Position = pos;
    	this.m_LookAt = lookAt;
        Vector down = new Vector(0.0, -1.0, 0.0);
        this.m_ForwardDirection = lookAt.sub(pos).norm();
        this.m_RightDirection = m_ForwardDirection.cross(down).norm();
        this.m_UpDirection = m_ForwardDirection.cross(m_RightDirection).norm();
    }
}
