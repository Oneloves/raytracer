package fc.SimpleRaytracer.Rendering;


public class Color {
	
	
	public double m_Red;
	public double m_Green;
	public double m_Blue;
	
	
    public static final Color White = new Color(1.0, 1.0, 1.0);
    public static final Color Grey = new Color(0.5, 0.5, 0.5);
    public static final Color Black = new Color(0.0, 0.0, 0.0);
    public static final Color Background = new Color(0.0, 0.0, 0.0);
    public static final Color DefaultColor = Color.Black;
    
    
    public Color(double r, double g, double b) {
    	this.m_Red = r;
    	this.m_Green = g;
    	this.m_Blue = b;
    }
    
    
    public Color scale(double k) { 
    	return new Color(k * m_Red, k * m_Green, k * m_Blue);
    }
    
    
    public Color div(double k) { 
    	return new Color(m_Red / k, m_Green / k, m_Blue / k); 
    }
    
    
    public Color add(Color v2) { 
    	return new Color(m_Red + v2.m_Red, m_Green + v2.m_Green, m_Blue + v2.m_Blue); 
    }
    
    
    public Color mul(Color v2) { 
    	return new Color(m_Red * v2.m_Red, m_Green * v2.m_Green, m_Blue * v2.m_Blue); 
    }
    
    
    public Color toDrawingColor() {
        return new Color(
            Math.floor(Math.min(m_Red, 1.0) * 255.0),
            Math.floor(Math.min(m_Green, 1.0) * 255.0),
            Math.floor(Math.min(m_Blue, 1.0) * 255.0));
    }
    
    
    public int toRGB() {
        int ir = (int)Math.floor(Math.min(m_Red, 1.0) * 255.0);
        int ig = (int)Math.floor(Math.min(m_Green, 1.0) * 255.0);
        int ib = (int)Math.floor(Math.min(m_Blue, 1.0) * 255.0);
        return (255 << 24) | (ir << 16) | (ig << 8) | ib;
    }
}
