

import java.awt.*;
import javax.swing.*;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;

import com.jogamp.opengl.util.gl2.GLUT;

//----------------- class variables -------------------------------
public class ThreeD extends JFrame {
	// -------------------- class variables ----------------------------
	private static int windowWidth = 700; // default size
	private static int windowHeight = 550;

	// ----------------- instance variables -------------------------------
	private int width, height; // current window size
	private SceneManager sceneManager = null;
	private GLCanvas glCanvas = null;
	private JFrame cp = new JFrame("Control Panel");

	// ------------------ constructors ----------------------------------
	public ThreeD(int w, int h) {
		super("ThreeD demo");
		width = w;
		height = h;

		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		sceneManager = SceneManager.getInstance();
		JLabel label = new JLabel("Default");
		this.add(label);

		setupOpenGL();

		ControlPanel controlPanel = ControlPanel.getInstance();
		// controlPanel.addDrawPanel( glCanvas );
		cp.add(controlPanel);
		cp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp.setLocation(700, -400);
		cp.setSize(500, 500);
		cp.setVisible(true);

		this.add(glCanvas);
		this.setVisible(true);
	}

	// --------------------- setupOpenGL( int win ) -------------------------
	/**
	 * Set up the open GL drawing window
	 */
	void setupOpenGL() {
		GLProfile glp = GLProfile.getDefault();
		GLCapabilities caps = new GLCapabilities(glp);
		glCanvas = new GLCanvas(caps);

		// When a GL event occurs, need to tell the canvas to send the event
		// to the ThreeD object, which knows how to draw the scene.
		glCanvas.addGLEventListener(sceneManager);
		sceneManager.setCanvas(glCanvas);

		// This program doesn't need an animator since all image changes
		// occur because of interactions with the user and should
		// get triggered as long as the GLCanvas.repaint method is called.
	}

	// ++++++++++++++++++++++++++++ main ++++++++++++++++++++++++++++++++++++++
	public static void main(String[] args) {
		ThreeD scene = new ThreeD(windowWidth, windowHeight);
	}
}
