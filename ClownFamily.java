

public class ClownFamily extends CompositeObjectClass {

	Cone cone;
	Box box;
	ClownFriends manObjFam;
	ClownFriends manObjFam1;

	public ClownFamily() {
		box = new Box();
		cone = new Cone();
		manObjFam = new ClownFriends();
		manObjFam1 = new ClownFriends();

	}

	public ClownFriends getManObjFam() {
		return manObjFam;
	}

	public ClownFriends getManObjFam1() {
		return manObjFam1;
	}

	@Override
	protected void drawPrimitives() {
		cone.setLocation(3.4f, 2.8f, 1.6f);
		cone.setSize(1.1f, 1.6f, 1.3f);
		cone.setRotate(270.0f, 1, 0, 0);
		cone.setColorIndex(3);
		cone.redraw();

		box.setLocation(3.4f, 2.5f, 1.6f);
		box.setSize(0.5f, 0.6f, 0.3f);
		box.setRotate(45.0f, 0, 1, 0);
		box.setColorIndex(4);
		box.redraw();

		// TODO Auto-generated method stub
		manObjFam.setLocation(1.0f, 1.0f, 0.0f);
		manObjFam.setSize(0.4f, 0.3f, 0.4f);
		manObjFam.redraw();

		manObjFam1.setLocation(-0.4f, 0.1f, 1.2f);
		manObjFam1.setSize(0.6f, 0.7f, 0.7f);
		manObjFam1.setRotate(0.0f, 1, 1, 0);
		manObjFam1.redraw();

	}

}
