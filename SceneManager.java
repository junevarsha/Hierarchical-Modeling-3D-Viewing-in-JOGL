/**
 * SceneManager: Manages all the scenes and initiates drawing of current scene.
 *               Implements the Singleton class pattern, but also uses a static
 *               interface to interact with ControlPanel and the Scene class
 * 
 */
import java.util.*;
import java.awt.*;

import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.glu.*;

import com.jogamp.opengl.util.gl2.GLUT;

public class SceneManager implements GLEventListener {
	// ------------------- class variables ---------------------------
	public static SceneManager me = null;
	private static boolean drawAxesFlag = true;
	private static LinkedList<Scene> allScenes;
	private static int curSceneIndex = -1;
	private static Scene curScene = null;
	private static float[] diffuseLight;
	private static GLCanvas canvas = null;

	// ------ color increment operators -----
	private static int coneIncr = 2;

	public static int sphereIncr = 4;

	private static int boxIncr = 3;

	private static int diskIncr = 2;

	private static int teaIncr = 6;

	// ------------------- instance variables ------------------------

	// For testing, we'll keep pointers to a bunch of created objects in a
	// vector
	// also. This makes it easy to just select previously created objects
	// for multiple scenes, or to copy them and change transformations.
	private ArrayList<Object3D> someObjects;

	// ------------------ constructor --------------------------------
	public static SceneManager getInstance() {
		if (me == null)
			me = new SceneManager();
		return me;
	}

	private SceneManager() {
		allScenes = new LinkedList<Scene>();
		someObjects = new ArrayList<Object3D>();
		// lightColor = new Color( 0.7f, 0.7f, 0.7f );
		diffuseLight = new float[] { 0.0f, 0.7f, 0.7f, 1f };
	}

	// ----------------- setCanvas -------------------------------------
	/**
	 * Main program needs to tell sceneManager about the GL canvas to use
	 */
	public void setCanvas(GLCanvas glCanvas) {
		canvas = glCanvas;
	}

	// ------------------------- addScene -------------------------------
	/**
	 * Add a new scene to the scene collection
	 */
	private void addScene(Scene newScene) {
		allScenes.add(newScene);
		curScene = newScene;
		curSceneIndex = allScenes.size() - 1;
	}

	// ------------------------- nextScene -------------------------------
	/**
	 * update current scene to next one with wraparound this is a static method
	 * to facilitate interaction with ControlPanel
	 */
	public static void nextScene() {
		curSceneIndex++;
		if (curSceneIndex >= allScenes.size()) {
			curSceneIndex = 0;
		}// wrap around
		canvas.repaint();
	}

	public static void previousScene() {

		// wrap around

		if (curSceneIndex == 0) {
			curSceneIndex = allScenes.size() - 1;
		} else {

			curSceneIndex--;
		}
		canvas.repaint();

	}

	// ------------------------- setDrawAxes( boolean ) ------------------
	/**
	 * set the status of the axes drawing; called by ControlPanel
	 */
	public static void setDrawAxes(boolean onoff) {
		drawAxesFlag = onoff;
		canvas.repaint();
	}

	// ------------------------- drawAxes() ------------------
	/**
	 * retrieve axes drawing status; called by Scene
	 */
	public static boolean drawAxes() {
		return drawAxesFlag;
	}

	// ------------ setLightColor ----------------------
	public static void setLightColor(Color c) {
		diffuseLight[0] = c.getRed() / 255.0f;
		diffuseLight[1] = c.getGreen() / 255.0f;
		diffuseLight[2] = c.getBlue() / 255.0f;
		System.out.println("New Color: " + c);
		sceneInit();
		canvas.repaint();
	}

	// +++++++++++++++ GLEventListener override methods ++++++++++++++++++++
	// -------------------- display -------------------------------------
	/**
	 * Override the parent display method In this framework, the display method
	 * is responsible for setting up the projection specification, but the
	 * "render" method is responsible for the View and Model specifications.
	 * 
	 * This display method is reasonably application-independent; It defines a
	 * pattern that can be reused with the exception of the specifying the
	 * actual objects to render.
	 */
	@Override
	public void display(GLAutoDrawable drawable) {

		curScene = allScenes.get(curSceneIndex);

		if (ControlPanel.rotButton.isSelected())

		{
			System.out.println("calling rotate");
			curScene.rotate();

		}

		colorChange();

		if (curScene != null) {
			sceneInit();

			curScene.display(drawable);
		} else
			System.err.println("??? Trying to draw null scene");
		incrementSphere();

	}

	// --------------------- dispose ------------------------------
	@Override
	public void dispose(GLAutoDrawable arg0) {
		// nothing to dispose of...
	}

	// --------------------- init ------------------------------
	@Override
	public void init(GLAutoDrawable drawable) {
		JOGL.gl = drawable.getGL().getGL2();

		JOGL.gl.setSwapInterval(1); // animation event occurs (maybe)
									// only at end of frame draw.
									// 0 => render as fast as possible
		JOGL.glu = new GLU();
		JOGL.glut = new GLUT();
		sceneInit();

		if (ControlPanel.rotButton.isSelected())

		{
			System.out.println("calling rotate");
			curScene.rotate();

		}

		makeSimpleScenes(); // make scenes with at least 1 example of each
							// Object3D
		makeMultiObjectScenes();

		nextScene();

	}

	// --------------------- reshape ----------------------------------------
	/**
	 * Window has been resized, readjust internal information
	 */
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
		System.out.println("reshape");
		JOGL.gl = drawable.getGL().getGL2();
		JOGL.gl.glViewport(0, 0, w, h);
		System.out.println("Viewport size: " + w + " x " + h);
	}

	// ----------------- makeBox ----------------------------
	/**
	 * A convenience function to create a Sphere with a uniform scale, a
	 * specified color, and at 0,0,0.
	 */
	Box makeBox(float scale, Color c) {
		Box box = new Box();
		// box.setColor(c);
		box.setColorIndex(3);
		box.setLocation(0, 0, 0);
		box.setSize(scale, scale, scale);
		return box;
	}

	Teapot makeTea(float scale, Color c) {
		Teapot tea = new Teapot();
		// tea.setColor(c);
		tea.setColorIndex(teaIncr);
		tea.setLocation(0, 0, 0);
		tea.setSize(scale, scale, scale);
		return tea;
	}

	// ----------------- makeBall ----------------------------
	Sphere makeBall(float scale, Color c) {
		Sphere sp = new Sphere();
		sp.setLocation(0, 0, 0);
		sp.setSize(scale, scale, scale);
		sp.setColorIndex(sphereIncr);

		// sp.setColor(c);
		return sp;
	}

	Disk makeTetra(float scale, Color c) {
		Disk tet = new Disk();
		tet.setLocation(0, 0, 0);
		tet.setSize(scale, scale, scale);
		tet.setColorIndex(diskIncr);
		// tet.setColor(Color.CYAN);
		return tet;
	}

	Cone makeCone(float scale, Color c) {
		Cone con = new Cone();
		con.setLocation(0, 0, 0);
		con.setSize(scale, scale, scale);
		// con.setColor(Color.MAGENTA);
		con.setColorIndex(coneIncr);
		return con;
	}

	Clown makeComp(float scale, Color c) {
		Clown man = new Clown();
		man.setLocation(0, 0, 0);
		man.setSize(scale, scale, scale);
		man.setColorIndexes(boxIncr, sphereIncr, coneIncr, diskIncr);
		// comp.setColor( c );
		// Box compBox = man.getBox();
		// Sphere compSphere = man.getSphere();
		//
		// //Disk compDisk = man.getDisk();
		// // compCone.setLocation(1, 0, 0);
		// //compDisk.setLocation(2.0f, 2.0f, 2.0f);
		// compBox.setLocation(1.0f, 1.0f, 1.0f);
		// compSphere.setLocation(1, 1, 2);

		return man;
	}

	ClownFriends makeFriends(float scale, Color c) {
		ClownFriends manf = new ClownFriends();
		manf.setLocation(0, 0, 0);
		manf.setSize(scale, scale, scale);
		manf.getManObj().setColorIndexes(coneIncr, sphereIncr, boxIncr,
				diskIncr);
		manf.getManObjNew().setColorIndexes(coneIncr, sphereIncr, boxIncr,
				diskIncr);
		return manf;
	}

	ClownFamily makeFamily(float scale, Color c) {
		ClownFamily manfam = new ClownFamily();
		manfam.setLocation(0, 0, 0);
		manfam.setSize(scale, scale, scale);

		manfam.getManObjFam().getManObj()
				.setColorIndexes(boxIncr, sphereIncr, coneIncr, diskIncr);
		manfam.getManObjFam().getManObjNew()
				.setColorIndexes(boxIncr, sphereIncr, coneIncr, diskIncr);

		return manfam;
	}

	// --------------------- sceneInit ------------------------
	/**
	 * Initialize scene, including especially the lights
	 */
	public static void sceneInit() {
		JOGL.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);// black
		JOGL.gl.glClearDepth(1.0); // clears depth buffer
		JOGL.gl.glEnable(GL2.GL_DEPTH_TEST); // Enable depth testing
		JOGL.gl.glShadeModel(GL2.GL_SMOOTH); // Enable smooth color shading
		JOGL.gl.glEnable(GL2.GL_NORMALIZE); // Make all surface normals unit len
		JOGL.gl.glEnable(GL2.GL_COLOR_MATERIAL); // Current color used for
													// material

		// lighting set up
		JOGL.gl.glEnable(GL2.GL_LIGHTING);
		JOGL.gl.glEnable(GL2.GL_LIGHT0);

		// Set lighting intensity and color
		// GLfloat ambientLight[] = { 1f, 1f, 0.45f, 0.5f };
		float ambientLight[] = { 0.3f, 0.3f, 0.3f, 1f };
		JOGL.gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);

		/****
		 * float diffuseLight[] = { lightColor.getRed(), lightColor.getGreen(),
		 * lightColor.getBlue(), 1f }; /
		 **/
		JOGL.gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);
		System.out.println("DLight: <" + diffuseLight[0] + ", "
				+ diffuseLight[1] + ", " + diffuseLight[2] + ">");

		// Set the light position
		// GLfloat lightPosition[] = {-1, 1, 1, 0};
		float lightPosition[] = { -1, 1, 1, 0 };
		JOGL.gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPosition, 0);
	}

	// ------------------------- changeEvent -----------------------------
	static void changeEvent(String id, int value) {
		if (ControlPanel.transButton.isSelected())

		{
			System.out.println("calling translate");
			curScene.translate(id, value);
		}

		if (ControlPanel.scaleButton.isSelected()) {
			System.out.println("calling Scale");
			curScene.scale(id, value);
		}

		canvas.repaint();
	}

	static void changeEvent1() {
		if (ControlPanel.rotButton.isSelected())

		{
			// System.out.println("calling translate");
			curScene.rotate();
		}

		canvas.repaint();
	}

	// ------------------------- makeSimpleScenes --------------------------
	/**
	 * make all one object scenes
	 */
	void makeSimpleScenes() {
		Box box1 = makeBox(1, new Color(1f, 0f, 1f)); // unit magenta box
		incrementBox();

		someObjects.add(box1); // save it for future use

		Scene box1Scene = new Scene();
		box1Scene.addObject(box1);
		addScene(box1Scene);

		Box box2 = makeBox(0.5f, new Color(0f, 1f, 1f)); // smaller cyan box
		box2.setRotate(45, 0, 0, 1);
		incrementBox();
		someObjects.add(box2); // save it for future use

		Scene box2Scene = new Scene();
		box2Scene.addObject(box2);
		addScene(box2Scene);

		Sphere sp = makeBall(0.45f, new Color(0.8f, 0.8f, 0f));
		// yellow ball
		incrementSphere();
		sp.setColorIndex(2);
		someObjects.add(sp); // save it for future use

		Scene ballScene = new Scene();
		ballScene.addObject(sp);
		addScene(ballScene);

		Teapot tea = makeTea(0.75f, new Color(1f, 1f, 1f));
		incrementTea();
		someObjects.add(tea); // save it for future use

		Scene teaScene = new Scene();
		teaScene.addObject(tea);
		addScene(teaScene);

		Disk quadric = makeTetra(0.3f, new Color(1f, 1f, 1f));
		incrementDisk();

		someObjects.add(quadric); // save it for future use

		Scene quadScene = new Scene();
		quadScene.addObject(quadric);
		quadScene.setLabel("I am Quad");
		addScene(quadScene);

		Cone cone = makeCone(2.4f, new Color(1f, 1f, 1f));
		incrementCone();
		someObjects.add(cone); // save it for future use

		Scene coneScene = new Scene();
		coneScene.addObject(cone);
		addScene(coneScene);

		// composite

		ClownFriends manf = makeFriends(0.4f, new Color(0f, 0f, 1f));
		someObjects.add(manf);

		ClownFamily manfam = makeFamily(0.4f, new Color(0f, 0f, 1f));
		someObjects.add(manfam);

		Clown man = makeComp(0.4f, new Color(0f, 0f, 1f));
		// man.getBox().setColor(Color.cyan);
		// man.getBox().setRotate(45.0f, 0.0f, 1.0f, 0.0f);
		// man.getBoxNew().setColor(Color.green);

		man.getDisk().setColor(Color.red);
		// man.getDisk().setRotate(90.0f, 1.0f, 0.0f, 1.0f);
		// man.getCone().setRotate(270.0f, 1.0f, 0.0f, 0.0f);

		// man.getSphere().setColor(Color.red);

		man.getSphere1().setLocation(0.1f, 2.0f, 3.1f);
		man.getSphere1().setSize(0.1f, 0.2f, 0.3f);

		man.getSphere2().setLocation(1.1f, 2.2f, 2.1f);
		man.getSphere2().setSize(0.3f, 0.5f, 0.7f);

		someObjects.add(man);

		Scene manScene = new Scene();
		manScene.addObject(man);
		addScene(manScene);

		Scene manFriendsScene = new Scene();
		manFriendsScene.addObject(manf);
		addScene(manFriendsScene);

		Scene manFamilyScene = new Scene();
		manFamilyScene.addObject(manfam);
		addScene(manFamilyScene);

	}

	// ------------------------- makeMultiObjectScenes-------------------- //
	/**
	 * make all one object scenes
	 */
	void makeMultiObjectScenes() {
		Scene multi1 = new Scene();
		Object3D box = makeBox(1, new Color(1f, 0f, 1f)); // magenta
		box.setLocation(1f, 0f, 0f);
		box.setSize(0.4f, 0.4f, 0.4f);
		multi1.addObject(box);

		Object3D box2 = makeBox(0.5f, new Color(0f, 1f, 1f)); // cyan
		box2.setLocation(0f, 0f, 1f);
		multi1.addObject(box2);

		addScene(multi1);

	}

	public void incrementBox() {

		if (boxIncr >= 7) {
			boxIncr = 0;
		} else {
			boxIncr++;
		}

	}

	public void incrementSphere() {
		// System.out.println("insiide sphere increment : " + sphereIncr);

		if (sphereIncr >= 7) {
			sphereIncr = 0;
		} else {
			sphereIncr++;
		}

	}

	public void incrementCone() {
		if (coneIncr >= 7) {
			coneIncr = 0;
		} else {
			coneIncr++;
		}

	}

	public void incrementDisk() {
		if (diskIncr >= 7) {
			diskIncr = 0;
		} else {
			diskIncr++;
		}

	}

	public void incrementTea() {
		if (teaIncr >= 7) {
			teaIncr = 0;
		} else {
			teaIncr++;
		}

	}

	private void colorChange() {

		for (Scene scene : allScenes) {
			if (scene.objects.size() > 0) {

				for (Object obj : scene.objects) {
					if (obj instanceof Sphere) {
						((Sphere) obj).setColorIndex(sphereIncr);
					}

					if (obj instanceof Box) {
						((Box) obj).setColorIndex(boxIncr);
					}

					if (obj instanceof Cone) {
						((Cone) obj).setColorIndex(coneIncr);
					}

					if (obj instanceof Disk) {
						((Disk) obj).setColorIndex(diskIncr);
					}

					if (obj instanceof Teapot) {
						((Teapot) obj).setColorIndex(teaIncr);
					}

					// box, cone, disk ....

					if (obj instanceof Clown) {
						System.out.println(" inside clown ");
						setClownColor(obj);

					}

					if (obj instanceof ClownFriends) {
						System.out.println(" inside clown friends");
						setColorClownFriends(obj);

					}

					if (obj instanceof ClownFamily) {
						// System.out.println(" inside clown friends");
						setColorClownFamily(obj);

					}
				}
			}

		}
	}

	private void setClownColor(Object obj) {
		// face
		((Clown) obj).getSphere().setColorIndex(sphereIncr);
		// hat
		((Clown) obj).getCone().setColorIndex(coneIncr);
		// body
		((Clown) obj).getBox().setColorIndex(boxIncr);

	}

	private void setColorClownFamily(Object obj) {
		ClownFamily family = (ClownFamily) obj;

		setColorClownFriends(family.getManObjFam());
		incrementBox();
		incrementCone();
		incrementDisk();
		incrementSphere();
		setColorClownFriends(family.getManObjFam1());
	}

	private void setColorClownFriends(Object obj) {
		setClownColor((Clown) ((ClownFriends) obj).getManObj());
		incrementBox();
		incrementCone();
		incrementDisk();
		incrementSphere();
		setClownColor((Clown) ((ClownFriends) obj).getManObjNew());

	}

}
