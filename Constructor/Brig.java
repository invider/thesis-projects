import java.applet.*;
import java.awt.event.*;
import java.awt.*;

class Brig extends Sprite {
    public HotPoint Anchor;
	public HotPoint Sail;
	public boolean SailUp;

	Brig (Applet App, Graphics Canvas, int x, int y, int dx, int dy, int dragRadius) {
		super(App, Canvas, x, y, dx, dy, dragRadius);
		int nx = x + dx;
		int ny = y + dy;
		imgBlank = App.getImage(App.getCodeBase(), "images/blank.gif");
		total_frames = 1;
		current_frame = 0;
		Frame = new Image[total_frames];
		Frame[0] = App.getImage(App.getCodeBase(), "images/sail_brig.jpg");

		Anchor = new HotPoint(App, Canvas, this, nx - 60, ny + 20, 7, 1);
		Sail = new HotPoint(App, Canvas, this, nx, ny - 55, 7, 0);
		SailUp = false;
	}

	void moveSprite(int x, int y) {
		Anchor.moveHotPoint(x - 60, y + 20);
		Sail.moveHotPoint(x, y - 55);
		super.moveSprite(x, y);
	}
	
	void drawSprite() {
		super.drawSprite();
		Anchor.drawHotPoint();
		Sail.drawHotPoint();
	}

	void hotAction(HotPoint Point) {
		if (Point == Sail) {
			if (Point.hotValue == 1) { SailUp = true; Anchor.hotValue = 0; }
			else SailUp = false;
		}
	}

	public void SailAway() {
		if (SailUp && this.x < 630) {
			Anchor.hotValue += 2;			
			this.moveSprite(this.x + dx + 1, this.y + dy);
		} else if (SailUp && this.x >= 630) {
			SailUp = false;
			Sail.hotValue = 0;
		}
	}
}