package fc.SimpleRaytracer.Rendering;

import fc.SimpleRaytracer.Math.Vector;

public class Light
{
	public Vector m_Position;
	public Color m_Color;
	
	public Light(Vector pos, Color color)
	{
		this.m_Position = pos;
		this.m_Color = color;
	}
}
