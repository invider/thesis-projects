import java.applet.*;
import java.awt.event.*;
import java.awt.*;

class BLink extends Sprite {
    public HotPoint Primary;
	int Counter;
	public int FirstDraw;

	public boolean isParent;
	public BLink iParent;
	public BLink iChild;

	BLink (Applet App, Graphics Canvas, int x, int y, int dx, int dy, int dragRadius) {
		super(App, Canvas, x, y, dx, dy, dragRadius);
		int nx = x + dx;
		int ny = y + dy;
		FirstDraw = 0;
		total_frames = 0;
		current_frame = 0;

		imgBlank = App.getImage(App.getCodeBase(), "images/blank_sm.gif");
		total_frames = 1;
		current_frame = 0;
		Frame = new Image[total_frames];
		Frame[0] = App.getImage(App.getCodeBase(), "images/link.gif");

		Primary = new HotPoint(App, Canvas, this, x + dx, ny + 25, 10, 0);
		isParent = true;
	}

	BLink (Applet App, Graphics Canvas, int x, int y, int dx, int dy, int dragRadius, BLink iParent) {
		super(App, Canvas, x, y, dx, dy, dragRadius);
		int nx = x + dx;
		int ny = y + dy;
		FirstDraw = 0;
		total_frames = 0;
		current_frame = 0;

		imgBlank = App.getImage(App.getCodeBase(), "images/blank_sm.gif");
		total_frames = 1;
		current_frame = 0;
		Frame = new Image[total_frames];
		Frame[0] = App.getImage(App.getCodeBase(), "images/link.gif");

		Primary = new HotPoint(App, Canvas, this, x + dx, ny + 25, 10, 0);

		this.iParent = iParent;
		iParent.iChild = this;
		isParent = false;
	}


	void moveSprite(int x, int y) {
		Primary.moveHotPoint(x, y + dy + 15);
		super.moveSprite(x, y);	
	}
	
	void drawSprite() {
		Canvas.setXORMode(Color.white);
		Canvas.setColor(Color.blue);
			Canvas.setPaintMode();
			Canvas.setColor(Color.blue);
			if (!isParent) Canvas.drawLine(x + dx, y + dy, iParent.x + iParent.dx, iParent.y + iParent.dy);
			else Canvas.drawLine(x + dx, y + dy, iChild.x + iChild.dx, iChild.y + iChild.dy);
		Canvas.setPaintMode();
		FirstDraw = 1;

		super.drawSprite();
		Primary.drawHotPoint();
	}

	void hotAction(HotPoint Point) {
		Primary.hotValue = Point.hotValue;
		if (isParent) iChild.Primary.hotValue = Primary.hotValue;
		else iParent.Primary.hotValue = Primary.hotValue;
	}

	void itIsTime() {
	}
}