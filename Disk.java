

import javax.media.opengl.GL;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.ImmModeSink;

public class Disk extends Object3D {
	// --------- instance variables -----------------
	float length;
	GLUquadric quadric;

	// ------------- constructor -----------------------
	public Disk() {
		length = 1;
		quadric = JOGL.glu.gluNewQuadric();

	}

	// ------------- drawPrimitives ---------------------------
	public void drawPrimitives() {

		JOGL.glu.gluDisk(quadric, 1.8, 1.5, 20, 10);
	}
}