
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;

public class Clown extends CompositeObjectClass {
	// --------- instance variables -----------------
	// Cone cone;

	Box body;

	Disk ring;

	Sphere face;
	Sphere rightEye;
	Sphere leftEye;

	Cone hat;

	public static JLabel label;

	protected float xSize, ySize, zSize;
	protected float angle, dxRot, dyRot, dzRot;
	protected ArrayList<Color> colors;
	protected float xLoc, yLoc, zLoc;

	public float getxLoc() {
		return xLoc;
	}

	public void setxLoc(float xLoc) {
		this.xLoc = xLoc;
	}

	public float getyLoc() {
		return yLoc;
	}

	public void setyLoc(float yLoc) {
		this.yLoc = yLoc;
	}

	public float getzLoc() {
		return zLoc;
	}

	public void setzLoc(float zLoc) {
		this.zLoc = zLoc;
	}

	public Cone getCone() {
		return hat;
	}

	public void setCone(Cone cone) {
		this.hat = cone;
	}

	// --------//-------------//--------------

	public Box getBox() {
		return body;
	}

	public void setBox(Box box) {
		this.body = box;
	}

	// ----------- // --------- //-----

	// ---------//------------------//----------------------
	public Disk getDisk() {
		return ring;
	}

	public void setDisk(Disk disk) {
		this.ring = disk;
	}

	// ---------//------------------//----------------------

	public Sphere getSphere() {
		return face;
	}

	public void setSphere(Sphere sphere) {
		this.face = sphere;
	}

	public Sphere getSphere1() {
		return rightEye;
	}

	public void setSphere1(Sphere sphere1) {
		this.rightEye = sphere1;
	}

	public Sphere getSphere2() {
		return rightEye;
	}

	public void setSphere2(Sphere sphere2) {
		this.rightEye = sphere2;
	}

	// ------ // --------
	public Clown() {
		body = new Box();
		ring = new Disk();
		face = new Sphere();
		rightEye = new Sphere();
		leftEye = new Sphere();
		hat = new Cone();

	}

	public void setColorIndexes(int boxIncr, int sphereIncr, int coneIncr,
			int diskIncr) {
		hat.setColorIndex(coneIncr);
		face.setColorIndex(sphereIncr);
		body.setColorIndex(boxIncr);
		ring.setColorIndex(diskIncr);
	}

	@Override
	protected void drawPrimitives() {

		body.setLocation(1.5f, 0.5f, 1.3f);
		body.setSize(1.7f, 1.8f, 1.3f);
		body.setRotate(45.0f, 0, 1, 0);
		body.redraw();

		face.setLocation(4.0f, 0.3f, 3.4f);
		face.setSize(0.1f, 1.2f, 0.3f);
		face.setRotate(90.0f, 1, 0, 1);
		face.redraw();
		face.setSize(0.7f, 0.7f, 0.5f);
		face.setLocation(1.5f, 2.0f, 1.3f);
		face.redraw();

		rightEye.setColorIndex(3);
		rightEye.setSize(0.03f, 0.09f, 0.06f);
		rightEye.setLocation(1.7f, 2.1f, 2.0f);
		rightEye.redraw();

		leftEye.setColorIndex(3);
		leftEye.setSize(0.03f, 0.09f, 0.06f);
		leftEye.setLocation(2.7f, 2.2f, 2.0f);
		leftEye.redraw();

		hat.setLocation(3.8f, 3.1f, 3.6f);
		hat.setSize(1.7f, 1.6f, 1.3f);
		hat.setRotate(270.0f, 1, 0, 0);
		hat.redraw();
	}
}
