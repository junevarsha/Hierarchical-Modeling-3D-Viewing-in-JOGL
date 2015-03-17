
/**
 * Oct 16, 2013 rdb - derived from Box.cpp
 */

public class Rectangle extends Object3D {
	// --------- instance variables -----------------
	float length;

	// ------------- constructor -----------------------
	public Rectangle() {
		length = 10;
	}

	// ------------- drawPrimitives ---------------------------
	public void drawPrimitives() {
		JOGL.glut.glutWireCube(length);

	}
}
