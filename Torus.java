
/**
 * Oct 16, 2013 rdb - derived from Box.cpp
 */

public class Torus extends Object3D {
	// --------- instance variables -----------------

	// ------------- constructor -----------------------
	public Torus() {
	}

	// ------------- drawPrimitives ---------------------------
	public void drawPrimitives() {
		JOGL.glut.glutSolidTorus(4, 6, 2, 3);

	}
}
