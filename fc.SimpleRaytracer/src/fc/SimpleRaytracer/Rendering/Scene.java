package fc.SimpleRaytracer.Rendering;

import java.util.List;

import fc.SimpleRaytracer.Geometry.Thing;

public class Scene
{
	public List<Thing> m_Things;
	public Light[] m_Lights;
	public Camera m_Camera;
	
	public Scene(List<Thing> things, Light[] lights, Camera camera)
	{
		this.m_Things = things;
		this.m_Lights = lights;
		this.m_Camera = camera;
	}
}
