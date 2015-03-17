
public class CompositeObjectClass extends Object3D {

	@Override
	protected void drawPrimitives() {
		// TODO Auto-generated method stub

		Clown clowOne = new Clown();
		clowOne.drawPrimitives();

		ClownFriends clow = new ClownFriends();
		clow.drawPrimitives();
		ClownFamily clowTwo = new ClownFamily();
		clowTwo.drawPrimitives();

	}

}
