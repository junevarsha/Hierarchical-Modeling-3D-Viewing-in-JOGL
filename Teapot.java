
public class Teapot extends Object3D {
	// --------- instance variables -----------------
	float length;

	// ------------- constructor -----------------------
	public Teapot() {
		length = 1;

	}

	// ------------- drawPrimitives ---------------------------
	public void drawPrimitives() {
		JOGL.glut.glutWireTeapot(length);
	}
}