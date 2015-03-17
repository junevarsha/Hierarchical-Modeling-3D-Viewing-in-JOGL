

public class ClownFriends extends CompositeObjectClass {

	Clown manObj;

	Clown manObjNew;

	public ClownFriends() {
		manObj = new Clown();
		manObjNew = new Clown();
	}

	public Clown getManObj() {
		return manObj;
	}

	public Clown getManObjNew() {
		return manObjNew;
	}

	@Override
	protected void drawPrimitives() {
		// TODO Auto-generated method stub
		manObj.setLocation(1.0f, 0.0f, 0.0f);
		manObj.redraw();

		manObjNew.setLocation(0.7f, 0.0f, 2.5f);
		manObjNew.redraw();

	}

}
