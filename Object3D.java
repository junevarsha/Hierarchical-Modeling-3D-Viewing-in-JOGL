
import java.io.*;
import java.util.*;
import java.awt.Color;

abstract public class Object3D {
	// ------------------ class variables ------------------------------
	static final int MAX_COLORS = 20; // arbitrary number to keep an parameter

	// ------------------ instance variables ------------------------------
	protected float xLoc, yLoc, zLoc; // location (origin) of the object
	protected float xSize, ySize, zSize; // size of the object

	protected float angle;
	protected int dxRot, dyRot, dzRot; // rotation angle and axis

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	protected ArrayList<Color> colors;

	private int incr = 0;

	protected abstract void drawPrimitives();

	// ------------------ Constructors ------------------------------------
	/**
	 * Create a new object3D at position 0,0,0 of size 1,1,1
	 */
	Object3D() {
		colors = new ArrayList<Color>(8);
		Color color = new Color(1.0f, 0.0f, 0.0f); // red is default
		colors.add(color);

		setLocation(0, 0, 0);
		setSize(1, 1, 1);
		setRotate(0, 0, 0, 0);

		setColor(2, color);
	}

	// ------------------ public methods -------------------------------
	// ------------- redraw ---------------------------
	void redraw() {
		// std::cout <<"++++++++++++++++ cube redraw+++++++++++++" << std::endl;
		JOGL.gl.glPushMatrix();
		// float[] rgb = colors.get( 0 ).getComponents( null );
		// JOGL.gl.glColor3f( rgb[ 0 ], rgb[ 1 ], rgb[ 2 ] );
		Color currentColor = colors.get(incr);
		// System.out.println(" Color index " + incr);
		JOGL.gl.glColor3f(currentColor.getRed(), currentColor.getGreen(),
				currentColor.getBlue());
		JOGL.gl.glTranslatef(xLoc, yLoc, zLoc);
		JOGL.gl.glRotatef(angle, dxRot, dyRot, dzRot);
		JOGL.gl.glScalef(xSize, ySize, zSize);

		drawPrimitives();

		JOGL.gl.glPopMatrix();
	}

	/**
	 * set the location of the object to the x,y,z position defined by the args
	 */
	void setLocation(float x, float y, float z) {
		xLoc = x;
		yLoc = y;
		zLoc = z;
	}

	/**
	 * return the value of the x origin of the shape
	 */
	float getX() {
		return xLoc;
	}

	/**
	 * return the value of the y origin of the shape
	 */
	float getY() {
		return yLoc;
	}

	/**
	 * return the value of the z origin of the shape
	 */
	float getZ() {
		return zLoc;
	}

	public float getxSize() {
		return xSize;
	}

	public void setxSize(float xSize) {
		this.xSize = xSize;
	}

	public float getySize() {
		return ySize;
	}

	public void setySize(float ySize) {
		this.ySize = ySize;
	}

	public float getzSize() {
		return zSize;
	}

	public void setzSize(float zSize) {
		this.zSize = zSize;
	}

	public int getDxRot() {
		return dxRot;
	}

	public void setDxRot(int dxRot) {
		this.dxRot = dxRot;
	}

	public int getDyRot() {
		return dyRot;
	}

	public void setDyRot(int dyRot) {
		this.dyRot = dyRot;
	}

	public int getDzRot() {
		return dzRot;
	}

	public void setDzRot(int dzRot) {
		this.dzRot = dzRot;
	}

	/**
	 * return the location as a Point3 object
	 */
	Point3 getLocation() // return location as a Point
	{
		return new Point3(xLoc, yLoc, zLoc);
	}

	// ----------------------- setColor methods ---------------------------
	/**
	 * set the "nominal" color of the object to the specified color; this does
	 * not require that ALL components of the object must be the same color.
	 * Typically, the largest component will take on this color, but the
	 * decision is made by the child class.
	 */
	void setColor(Color c) {
		setColor(0, c);
	}

	/**
	 * set the nominal color (index 0) to the specified color with floats
	 */
	void setColor(float r, float g, float b) {
		setColor(0, new Color(r, g, b));
	}

	/**
	 * set the index color entry to the specified color with floats
	 */
	void setColor(int i, float r, float g, float b) {
		setColor(i, new Color(r, g, b));
	}

	void setColorIndex(int index) {
		this.incr = index;
	}

	/**
	 * set the i-th color entry to the specified color with Color
	 */
	void setColor(int i, Color c) {

		colors.add(Color.BLUE); // put desired color at desired index
		colors.add(Color.MAGENTA);
		colors.add(Color.GREEN);
		colors.add(new Color(0.1f, 0.56f, 1.0f));
		colors.add(Color.orange);
		colors.add(new Color(0.82f, 0.411f, 0.11f));
		colors.add(new Color(0.6f, 1.0f, 0.184f));
		//
		// if ( i < 0 || i > MAX_COLORS ) // should throw an exception!
		// {
		// System.err.println( "*** ERROR *** Object3D.setColor: bad index: "
		// + i + "\n" );
		// return;
		// }
		// float[] rgb = c.getComponents( null );
		// Color newColor = new Color( rgb[ 0 ], rgb[ 1 ], rgb[ 2 ] );
		// if ( i >= colors.size() ) // need to add entries to vector
		// {
		// for ( int n = colors.size(); n < i; n++ ) // fill w/ black if needed
		// colors.add( Color.BLUE ); // put desired color at desired index
		// colors.add(Color.cyan);
		// colors.add(new Color(0.4f, 0.5f, 0.2f));
		// colors.add(Color.GREEN);
		// colors.add(new Color(0.3f, 0.6f, 0.2f));
		// colors.add(new Color(0.1f, 0.5f, 0.7f));
		// colors.add(new Color(0.35f, 0.4f, 0.9f));
		//
		//
		// }
		// else
		// {
		// // now replace old entry
		// colors.set( i, newColor );
		// }
	}

	// ------------------ setSize ----------------------------------------
	/**
	 * set the size of the shape to be scaled by xs, ys, zs That is, the shape
	 * has an internal fixed size, the shape parameters scale that internal
	 * size.
	 */
	void setSize(float xs, float ys, float zs) {
		xSize = xs;
		ySize = ys;
		zSize = zs;
	}

	/**
	 * set the rotation parameters: angle, and axis specification
	 */
	void setRotate(float a, int dx, int dy, int dz) {
		angle = a;
		dxRot = dx;
		dyRot = dy;
		dzRot = dz;
	}
}