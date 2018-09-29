package fc.SimpleRaytracer;

public class Constant {

	public static final String environementPath = "environement.jpg";
	
	public static final Boolean depthOfField = false;
	public static final Boolean antiAliassingX4 = true;
	public static final Boolean antiAliassingX21 = false;
	public static final Boolean shadow = true;
	public static final Boolean softShadow = true;
	
	public static final double epsilon = 0.0000001;
	
	public static final int width = 800;
	public static final int height = 800;

	public static final int reflectionNb = 1;
	public static final int refractionNb = 3;
	
	public static final double iorNothing = 1.0;
	public static final double iorWater = 1.3;
	public static final double iorGlass = 1.5;
	public static final double iorDiamond = 2.46;
	
	public static final int maxTrianglesInside = 32;
	
	public static final double jitterAliasingX4[] = { 
		-0.5,  0.5, -0.5, -0.5, 0.5, 0.5, 0.5, -0.5
	};
	
	public static final double jitterAliasingX21[] = { 
		0.25, 0.75, 0.75, 0.75, 0.25, 0.25, 0.75, 0.25, -0.25, 0.75, -0.75, 0.75,
		-0.25, 0.25, -0.75, 0.25, -0.25, -0.75, -0.75, -0.75, -0.25, -0.25, -0.75,
		-0.25, 0.25, -0.75, 0.75, -0.75, 0.25, -0.25, 0.75, -0.25, -0.5, 0.5, -0.5, 
		-0.5, 0.5, 0.5, 0.5, -0.5, 0.0, 0.0
	};
	
	public static final double jitterSoftShadow[] = { 
		0.01, 0.00, 0.01, 0.01, 0.01, 0.02, 0.01, 0.03, 0.01, 0.04, 0.01, 0.05, 0.01, 0.06, 0.02, 0.00, 0.02, 0.01, 0.02, 0.02, 0.02, 0.03, 0.02, 0.04, 0.02, 0.05,
		0.03, 0.00,0.03, 0.01, 0.03, 0.02, 0.03, 0.03, 0.03, 0.04, 0.04, 0.00, 0.04, 0.01, 0.04, 0.02, 0.04, 0.03, 0.05, 0.00, 0.05, 0.01, 0.05, 0.02, 0.06, 0.00,
		0.06, 0.01, 0.07, 0.00,	-0.01, 0.00, -0.01, 0.01, -0.01, 0.02, -0.01, 0.03, -0.01, 0.04, -0.01, 0.05, -0.01, 0.06, -0.02, 0.00, -0.02, 0.01, -0.02, 0.02,
		-0.02, 0.03, -0.02, 0.04, -0.02, 0.05, -0.03, 0.00, -0.03, 0.01, -0.03, 0.02, -0.03, 0.03, -0.03, 0.04, -0.04, 0.00, -0.04, 0.01, -0.04, 0.02, -0.04, 0.03,
		-0.05, 0.00, -0.05, 0.01, -0.05, 0.02, -0.06, 0.00, -0.06, 0.01, -0.07, 0.00, 0.01, -0.01, 0.01, -0.02, 0.01, -0.03, 0.01, -0.04, 0.01, -0.05, 0.01, -0.06,
		0.02, -0.01, 0.02, -0.02, 0.02, -0.03, 0.02, -0.04, 0.02, -0.05, 0.03, -0.01, 0.03, -0.02, 0.03, -0.03, 0.03, -0.04, 0.04, -0.01, 0.04, -0.02, 0.04, -0.03, 
		0.05, -0.01, 0.05, -0.02,0.06, -0.01, -0.01, -0.01, -0.01, -0.02, -0.01, -0.03, -0.01, -0.04, -0.01, -0.05, -0.01, -0.06, -0.02, -0.01, -0.02, -0.02, 
		-0.02, -0.03, -0.02, -0.04, -0.02, -0.05, -0.03, -0.01, -0.03, -0.02, -0.03, -0.03, -0.03, -0.04, -0.04, -0.01, -0.04, -0.02, -0.04, -0.03, -0.05, -0.01, 
		-0.05, -0.02, -0.06, -0.01
	};
	
	public static final double jitteDepthOfField[] = {
		0.00, 0.00, 0.01, 0.00, 0.02, 0.00, 0.00, 0.01, 0.01, 0.01, -0.01, 0.00,
		-0.01, 0.01, -0.02, 0.00, 0.00, -0.01, 0.01, -0.01, -0.01, -0.01
	};
	
	public static final double jitterVerticalMotion[] = {
		-0.01, 0.00,
		-0.02, 0.00,
		-0.04, 0.00,
		-0.05, 0.00,
		-0.06, 0.00,
		-0.07, 0.00,
		-0.08, 0.00,
		-0.09, 0.00,
		-0.01, 0.00
	};

}
