

public class Cone extends Object3D {
	// --------- instance variables -----------------
	float height;

	

	// ------------- constructor -----------------------
	public Cone() {
		height = 0.2f;

	}

	

	// ------------- drawPrimitives ---------------------------
	public void drawPrimitives() {

		
		JOGL.glut.glutSolidCone(height, 0.5f, 20, 30);

	}
}
