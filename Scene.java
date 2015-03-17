

import java.util.*;
import java.io.*;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;
import javax.media.opengl.fixedfunc.*;
import javax.swing.JLabel;

import com.jogamp.opengl.util.*;
import com.jogamp.opengl.util.gl2.GLUT;

public class Scene {
	// ------------------ class variables ------------------------------
	// ---- draw Axes flag ------------------------
	static boolean drawAxes; // package access

	// ------------------ instance variables ------------------------------
	// ---- objects collection -------
	ArrayList<Object3D> objects;

	// ----gluLookat parameters -------
	float eyeX, eyeY, eyeZ; // gluLookat eye position
	float lookX, lookY, lookZ; // gluLookat look position
	float upX, upY, upZ; // up vector

	// ----gluPerspective parameters ----
	float viewAngle, aspectRatio, near, far;

	private JLabel label;

	// ------------------ Constructors ------------------------------------
	/**
	 * Initialize any values, register callbacks
	 */
	public Scene() {
		objects = new ArrayList<Object3D>();
		resetView();
		drawAxes = true;
		label = new JLabel("Default Text");
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setVerticalTextPosition(JLabel.BOTTOM);

	}

	// ----------- changeEvent -------------------------------------------
	static float delta = 0.1f;

	public void translate(String id, int value) {

		if (objects.size() > 0) {
			System.out.println("Inside Translate");
			Object3D obj = objects.get(0);

			float x = obj.getX();
			float y = obj.getY();
			float z = obj.getZ();
			float changedX = x;
			float changedY = y;
			float changedZ = z;

			if (id.equals("x")) {
				if (delta < 0 && x < 0)
					delta = -delta;
				else if (delta > 0 && x > 2)
					delta = -delta;

				changedX = x + delta;
			}

			// y

			if (id.equals("y")) {
				if (delta < 0 && y < 0)
					delta = -delta;
				else if (delta > 0 && y > 2)
					delta = -delta;

				changedY = y + delta;
			}

			// z

			if (id.equals("z")) {
				if (delta < 0 && z < 0)
					delta = -delta;
				else if (delta > 0 && z > 2)
					delta = -delta;

				changedZ = z + delta;
			}

			obj.setLocation(changedX, changedY, changedZ);
		}

		// if (objects.size() > 0) {
		// Object3D obj1 = objects.get(0);
		// float x1 = obj1.getX();
		// float y1= obj1.getY();
		// float z1 = obj1.getZ();
		// if (delta < 0 && y1 < 0)
		// delta = -delta;
		// else if (delta > 0 && y1 > 2)
		// delta = -delta;
		// obj1.setLocation(y1 + delta, x1, z1);
		//
		// if (objects.size() > 0) {
		// Object3D obj2 = objects.get(0);
		// float x2 = obj2.getX();
		// float y2= obj2.getY();
		// float z2 = obj2.getZ();
		// if (delta < 0 && z2 < 0)
		// delta = -delta;
		// else if (delta > 0 && z2 > 2)
		// delta = -delta;
		// obj2.setLocation(z2 + delta, y2, x2);
		// }
		// }
	}

	public void scale(String id, int value) {
		if (objects.size() > 0) {
			Object3D obj = objects.get(0);

			float xs = obj.getxSize();
			float ys = obj.getySize();
			float zs = obj.getzSize();
			float changedX = xs;
			float changedY = ys;
			float changedZ = zs;

			if (id.equals("xs")) {
				changedX = value;
			}

			// y

			if (id.equals("ys")) {
				changedY = value;
			}

			// z

			if (id.equals("zs")) {
				changedZ = value;
			}

			obj.setSize(changedX, changedY, changedZ);
		}
	}

	public void rotate() {

		System.out.println("hi iam inside rotate");
		if (objects.size() > 0) {
			Object3D obj = objects.get(0);

			float changedAngle = ControlPanel.angle;
			System.out.println(changedAngle);
			int changedX = ControlPanel.rotateX;
			int changedY = ControlPanel.rotateY;
			int changedZ = ControlPanel.rotateZ;

			obj.setRotate(changedAngle, changedX, changedY, changedZ);
		}

	}

	// public void scale(String id, int value) {
	// Object3D obj = objects.get(0);
	// float x = obj.getX();
	// float y = obj.getY();
	//
	// SceneManager first = obj.g(1);
	// if (id.equals("x"))
	// first.setLocation(value, first.getY());
	// else if (event.equals("y"))
	// first.setLocation(first.getX(), value);
	// else
	// first.setSize(value, value);
	// glCanvas.repaint();
	// // glCanvas.paint(Graphic);
	//
	// }

	// -------------- resetView -----------------------------------------
	/**
	 * restore the view to default settings
	 */
	public void resetView() {
		setLookat(10, 3, 10, // eye
				0, 0, 0, // at
				0, 1, 0); // up

		setPerspective(10, 1.33f, 0.1f, 100.0f); // should calc windowWid /
													// windowHt
	}

	// --------------------------------------------------------------------
	public void addObject(Object3D newObject) {
		objects.add(newObject);
	}

	// --------------------------------------------------------------------
	public void clear() {
		objects.clear();
		redraw();
	}

	// ---------------------------------------------------------------------
	/**
	 * set lookat parameters
	 */
	public void setLookat(float eyeX, float eyeY, float eyeZ, float lookX,
			float lookY, float lookZ, float upX, float upY, float upZ) {
		this.eyeX = eyeX;
		this.eyeY = eyeY;
		this.eyeZ = eyeZ;
		this.lookX = lookX;
		this.lookY = lookY;
		this.lookZ = lookZ;
		this.upX = upX;
		this.upY = upY;
		this.upZ = upZ;
	}

	// ---------------------------------------------------------------------
	/**
	 * set perspective parameters
	 */
	void setPerspective(float angle, float ratio, float near, float far) {
		this.viewAngle = angle;
		this.aspectRatio = ratio;
		this.near = near;
		this.far = far;
	}

	// ---------------- drawing coordinate axes -----------------------
	/**
	 * Draw the world coord axes to help orient viewer.
	 */
	void drawCoordinateAxes() {
		float scale = 1.8f; // convenient scale factor for experimenting with
							// size
		JOGL.gl.glPushMatrix();

		JOGL.gl.glDisable(GLLightingFunc.GL_LIGHTING);
		JOGL.gl.glScalef(scale, scale, scale);
		float[] origin = { 0, 0, 0 };

		float[] xaxis = { 1, 0, 0 };
		float[] yaxis = { 0, 1, 0 };
		float[] zaxis = { 0, 0, 1 };

		JOGL.gl.glLineWidth(3);

		JOGL.gl.glBegin(GL2.GL_LINES);
		JOGL.gl.glColor3f(1, 0, 0); // X axis is red.
		JOGL.gl.glVertex3fv(origin, 0);
		JOGL.gl.glVertex3fv(xaxis, 0);
		JOGL.gl.glColor3f(0, 1, 0); // Y axis is green.
		JOGL.gl.glVertex3fv(origin, 0);
		JOGL.gl.glVertex3fv(yaxis, 0);
		JOGL.gl.glColor3f(0, 0, 1); // z axis is blue.
		JOGL.gl.glVertex3fv(origin, 0);
		JOGL.gl.glVertex3fv(zaxis, 0);
		JOGL.gl.glEnd();
		JOGL.gl.glPopMatrix();
		JOGL.gl.glEnable(GLLightingFunc.GL_LIGHTING);
	}

	// ---------------------------------------------------------------------
	public void display(GLAutoDrawable drawable) {
		// redraw( drawable );

		redraw();
	}

	public void redraw() {
		// SceneManager.me.sceneInit();
		// Should I use the passed drawable or JOGL.gl???
		JOGL.gl.glMatrixMode(GL2.GL_PROJECTION);
		JOGL.gl.glLoadIdentity(); // Reset The Projection Matrix

		// Only do perspective for now
		if (ControlPanel.persRadio.isSelected()) {
			JOGL.glu.gluPerspective(24.0f, 1.33f, 0.1f, 100.0f);
		}
		if (ControlPanel.orthoRadio.isSelected()) {

			JOGL.gl.glOrthof(-1.0f, 1.0f, -1.0f, 1.0f, 5.0f, 100.0f);
		}

		if (ControlPanel.frustumRadio.isSelected()) {

			JOGL.gl.glFrustum(-2.0, 1.0, -2.0, 1.0, 5, 100);

		}

		if (ControlPanel.rotButton.isSelected())

		{
			System.out.println("calling rotate");

		}

		JOGL.gl.glMatrixMode(GL2.GL_MODELVIEW);
		JOGL.gl.glLoadIdentity();
		// Reset The Projection Matrix

		JOGL.glu.gluLookAt(eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ);

		JOGL.gl.glClear(GL2.GL_DEPTH_BUFFER_BIT | GL2.GL_COLOR_BUFFER_BIT);

		if (SceneManager.drawAxes())
			drawCoordinateAxes();

		// create a vector iterator to access and draw the objects
		for (Object3D obj : objects)
			obj.redraw();
		JOGL.gl.glFlush(); // send all output to display
	}

	// ---------------- setDrawAxes( int ) -----------------------
	/**
	 * 0 means don't draw the axes non-zero means draw them
	 */
	// -------------------------------------------
	public void setDrawAxes(boolean yesno) {
		drawAxes = yesno;
	}

	public void setLabel(String text) {
		getLabel().setText(text);

	}

	public JLabel getLabel() {
		return this.label;
	}
}