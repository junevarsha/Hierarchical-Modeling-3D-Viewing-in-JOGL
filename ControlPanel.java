
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

public class ControlPanel extends JPanel {
	// ---------------- class variables ------------------------------
	private static ControlPanel instance = null;
	public static JButton prevButton;
	public static JRadioButton transButton;
	public static JRadioButton rotButton;
	public static JRadioButton scaleButton;
	public static JRadioButton persRadio;
	public static JRadioButton orthoRadio;
	public static JRadioButton frustumRadio;

	public static JRadioButton view1Radio;
	public static JRadioButton view2Radio;
	public static JRadioButton view3Radio;

	public static JLabel label;

	public static JSpinner rotXSpinner;
	public static JSpinner rotYSpinner;
	public static JSpinner rotZSpinner;
	public static JSpinner angleSpinner;

	public static int rotateX = 0;
	public static int rotateY = 0;
	public static int rotateZ = 0;
	public static int angle = 0;

	private JPanel bPanel = new JPanel();

	// --------------- instance variables ----------------------------
	JPanel drawPanel = null; // this will be used for the rendering area

	// ------------------- constructor -------------------------------
	/**
	 * return singleton instance of ControlPanel
	 */
	public static ControlPanel getInstance() {
		if (instance == null)
			instance = new ControlPanel();
		return instance;
	}

	/**
	 * Constructor is private so can implement the Singleton pattern
	 */
	private ControlPanel() {
		this.setLayout(new BorderLayout());
		buildGUI();
	}

	// --------------- addDrawPanel() -----------------------------
	/**
	 * add component to draw panel
	 */
	public void addDrawPanel(Component drawArea) {
		this.add(drawArea, BorderLayout.CENTER);
	}

	// --------------- getDrawPanel() -----------------------------
	/**
	 * return reference to the drawing area
	 */
	public JPanel getDrawPanel() {
		return drawPanel;
	}

	// --------------- nextScene() -------------------------------
	/**
	 * Go to the next scene
	 */
	public void nextScene() {
		System.out.println("Next Scene");
	}

	// --------------- build GUI components --------------------
	/**
	 * Create all the components
	 */
	private void buildGUI() {
		// build the button menu
		buildButtons();

		// build sliders
		buildSliders();

		// showSpinnerDemo();

		buildSpinner();

		this.add(bPanel);

	}

	// --------------------- buildButtons ------------------------------------
	/**
	 * build a button panel: a Next button and an DrawAxis CheckBox
	 */
	private void buildButtons() {

		// bPanel.setBorder(new LineBorder(Color.BLACK, 2));
		// bPanel.setBorder(BorderFactory.createTitledBorder("Buttons"));

		JButton nextButton = new JButton("Next Scene");
		nextButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				SceneManager.nextScene();
			}
		});
		bPanel.add(nextButton);

		prevButton = new JButton("Previous Scene");
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				SceneManager.previousScene();
			}
		});
		bPanel.add(prevButton);

		ButtonGroup btgNew = new ButtonGroup();
		transButton = new JRadioButton("Translate");
		bPanel.add(transButton);
		btgNew.add(transButton);

		rotButton = new JRadioButton("Rotate");
		bPanel.add(rotButton);
		btgNew.add(rotButton);

		scaleButton = new JRadioButton("Scale");
		bPanel.add(scaleButton);
		btgNew.add(scaleButton);

		ButtonGroup btg = new ButtonGroup();
		persRadio = new JRadioButton("Perspective");
		persRadio.setSelected(true);
		bPanel.add(persRadio);
		btg.add(persRadio);

		orthoRadio = new JRadioButton("Ortho");
		persRadio.setSelected(false);
		bPanel.add(orthoRadio);
		btg.add(orthoRadio);

		frustumRadio = new JRadioButton("Frustum");
		bPanel.add(frustumRadio);
		btg.add(frustumRadio);

		JCheckBox axisChecker = new JCheckBox("Show Axis");
		axisChecker.setSelected(true);
		axisChecker.setBorder(BorderFactory.createTitledBorder("AxisChecker"));
		axisChecker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				SceneManager.setDrawAxes(((JCheckBox) ae.getSource())
						.isSelected());
			}
		});

		bPanel.add(axisChecker);
	}

	// ------------------- buildSliders ---------------------------------
	/**
	 * Create 3 sliders and add using border layout: First argument is the
	 * containing JFrame into which the sliders will be placed 2nd argument is a
	 * reference to the JShape that is being controlled.
	 * 
	 * West region will have a vertical slider controlling the y position South
	 * region will have the slider controlling the x position East region will
	 * have a slider controlling the size
	 */
	private void buildSliders() {

		// ////////////////////////////////////////////////////////////////
		// 1. Copy and edit the above Y slider code to make an X slider
		// in the SOUTH border region.
		// Add code to the SliderListener code to process the X-slider events
		// 2. Copy and edit again to create the Size slider for controlling
		// size of the target JShape.
		// Add code to SliderListener to process S-slider events.
		// ////////////////////////////////////////////////////////////////
		// ------------- X Slider ------------------------------------
		JSlider xSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 250);
		xSlider.setBorder(BorderFactory.createTitledBorder("TransXSlider"));
		addLabels(xSlider, 100);
		xSlider.addChangeListener(new SliderListener(xSlider, "x"));

		bPanel.add(xSlider);

		// ------------- Y Slider ------------------------------------
		JSlider ySlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 250);
		ySlider.setInverted(true); // puts min slider value at top
		ySlider.setBorder(BorderFactory.createTitledBorder("TransYSlider"));
		label = new JLabel();
		label.setText("YSlider");
		ySlider.add(label);
		addLabels(ySlider, 100);
		ySlider.addChangeListener(new SliderListener(ySlider, "y"));
		bPanel.add(ySlider);

		// --------------Z Slider -------------------------------------
		JSlider zSlider = new JSlider(JSlider.HORIZONTAL, 0, 500, 250);
		zSlider.setInverted(true); // puts min slider value at top
		zSlider.setBorder(BorderFactory.createTitledBorder("TransZSlider"));
		addLabels(zSlider, 100);
		zSlider.addChangeListener(new SliderListener(zSlider, "z"));
		bPanel.add(zSlider);

		// ------------- S (size) Slider ------------------------------------
		// 2. Copy and edit again to create the Size slider for controlling
		// size of the target JShape.
		// Add code to SliderListener to process S-slider events.
		// ////////////////////////////////////////////////////////////////
		// ------------- Size Slider ------------------------------------
		JSlider sxSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		addLabels(sxSlider, 100);
		sxSlider.setBorder(BorderFactory.createTitledBorder("ScaleX"));
		sxSlider.addChangeListener(new SliderListener(sxSlider, "xs"));

		// sSlider.setBorder(new LineBorder(Color.BLACK, 2));
		bPanel.add(sxSlider);

		JSlider sySlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		addLabels(sySlider, 100);
		sySlider.setBorder(BorderFactory.createTitledBorder("ScaleY"));
		sySlider.addChangeListener(new SliderListener(sySlider, "ys"));
		// sSlider.setBorder(new LineBorder(Color.BLACK, 2));
		bPanel.add(sySlider);

		JSlider szSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		addLabels(szSlider, 100);
		szSlider.setBorder(BorderFactory.createTitledBorder("ScaleZ"));
		szSlider.addChangeListener(new SliderListener(szSlider, "zs"));
		// sSlider.setBorder(new LineBorder(Color.BLACK, 2));
		bPanel.add(szSlider);

		JSlider rxSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		addLabels(rxSlider, 100);
		rxSlider.setBorder(BorderFactory.createTitledBorder("RotateX"));
		rxSlider.addChangeListener(new SliderListener(rxSlider, "RotX"));
		// sSlider.setBorder(new LineBorder(Color.BLACK, 2));
		// bPanel.add(rxSlider);

		JSlider rySlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		addLabels(rySlider, 100);
		rySlider.setBorder(BorderFactory.createTitledBorder("RotateY"));

		rySlider.addChangeListener(new SliderListener(rySlider, "RotY"));
		// sSlider.setBorder(new LineBorder(Color.BLACK, 2));
		// bPanel.add(rySlider);

		JSlider rzSlider = new JSlider(JSlider.HORIZONTAL, 1, 10, 1);
		addLabels(rzSlider, 100);
		rzSlider.setBorder(BorderFactory.createTitledBorder("RotateZ"));
		rzSlider.addChangeListener(new SliderListener(rzSlider, "RotZ"));
		// sSlider.setBorder(new LineBorder(Color.BLACK, 2));
		// bPanel.add(rzSlider);

	}

	private void buildSpinner() {
		bPanel.add(new JLabel("XRotate"));
		SpinnerModel spinnerModel1 = new SpinnerNumberModel(0, 0, 1, 1);
		rotXSpinner = new JSpinner(spinnerModel1);
		rotXSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				rotateX = (Integer) ((JSpinner) e.getSource()).getValue();
			}
		});
		bPanel.add(rotXSpinner);

		bPanel.add(new JLabel("YRotate"));
		SpinnerModel spinnerModel2 = new SpinnerNumberModel(0, 0, 1, 1);
		rotYSpinner = new JSpinner(spinnerModel2);
		rotYSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				rotateY = (Integer) ((JSpinner) e.getSource()).getValue();
			}
		});
		bPanel.add(rotYSpinner);

		bPanel.add(new JLabel("ZRotate"));
		SpinnerModel spinnerModel3 = new SpinnerNumberModel(0, 0, 1, 1);
		rotZSpinner = new JSpinner(spinnerModel3);
		rotZSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				rotateZ = (Integer) ((JSpinner) e.getSource()).getValue();
			}
		});
		bPanel.add(rotZSpinner);

		bPanel.add(new JLabel("Angle"));
		SpinnerModel spinnerModel4 = new SpinnerNumberModel(0, 0, 360, 20);
		angleSpinner = new JSpinner(spinnerModel4);
		angleSpinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				angle = (Integer) ((JSpinner) e.getSource()).getValue();
			}
		});
		bPanel.add(angleSpinner);

	}

	// ---------------- addLabels( JSlider, int ) -----------------------
	/**
	 * a utility method to add tick marks. First argument is the slider, the
	 * second represents the major tick mark interval minor tick mark interval
	 * will be 1/10 of that.
	 */
	private void addLabels(JSlider slider, int majorTicks) {
		slider.setPaintTicks(true);
		slider.setLabelTable(slider.createStandardLabels(1));
		slider.setPaintLabels(false);
		slider.setMajorTickSpacing(majorTicks);
		// slider.setMinorTickSpacing(majorTicks / 10);
	}

	// ++++++++++++++++++++++++ SliderListener inner class
	// ++++++++++++++++++++++
	/**
	 * The SliderListener needs access to -- the slider it is associated with
	 * (to get that slider's value) -- the JShape that is being controlled. -- a
	 * string that serves as an identifier for the slider These are passed to
	 * the constructor.
	 */
	public class SliderListener implements ChangeListener {
		private JSlider _slider;
		private String _id;

		public SliderListener(JSlider slider, String id) {
			_slider = slider;
			_id = id;
		}

		// ------------------- stateChanged -----------------------------
		/**
		 * Invoked whenever user changes the state of a slider
		 */
		public void stateChanged(ChangeEvent ev) {

			System.out.println("calling change event " + _id);
			SceneManager.changeEvent(_id, _slider.getValue());
		}
	}

	public class SpinnerListener implements ChangeListener {

		public SpinnerListener() {

		}

		// ------------------- stateChanged -----------------------------
		/**
		 * Invoked whenever user changes the state of a slider
		 */
		public void stateChanged(ChangeEvent ev) {

			// System.out.println("calling change event " + _id);
			SceneManager.changeEvent1();
		}
	}
}