import java.applet.*;
import java.awt.event.*;
import java.awt.*;

class BTimer extends Sprite {
    public HotPoint Start, Stop, Value;
	int Quad;
	int Counter;

	BTimer (Applet App, Graphics Canvas, int x, int y, int dx, int dy, int dragRadius) {
		super(App, Canvas, x, y, dx, dy, dragRadius);
		Quad = 0;
		int nx = x + dx;
		int ny = y + dy;
		imgBlank = App.getImage(App.getCodeBase(), "images/blank.gif");
		total_frames = 1;
		current_frame = 0;
		Frame = new Image[total_frames];
		Frame[0] = App.getImage(App.getCodeBase(), "images/triangle.gif");

		Start = new HotPoint(App, Canvas, this, x - 10, y - 10, 10, 0);
		Stop = new HotPoint(App, Canvas, this, x + dx + dx + 10, y - 10 , 10, 0);
		Value = new HotPoint(App, Canvas, this, x + dx, y + dy + dy + 10, 10, 1);
	}

	void moveSprite(int x, int y) {
		Start.moveHotPoint(x - dx - 10, y - dy - 10);
		Stop.moveHotPoint(x + dx + 10, y - dy - 10);
		Value.moveHotPoint(x, y + dy + 10);
		super.moveSprite(x, y);
	}
	
	void drawSprite() {
		super.drawSprite();
		Start.drawHotPoint();
		Stop.drawHotPoint();
		Value.drawHotPoint();

		Font fnt = new Font("Courier", Font.BOLD, 8);
		Canvas.setFont(fnt);
		Canvas.setColor(Color.blue);
		String txtValue = "";
		txtValue = txtValue + Counter;
		Canvas.drawString(txtValue, x + dx - 5, y + dy + 15);
	}

	void hotAction(HotPoint Point) {
		if (Point == Start) {
			if (Point.hotValue > 0 && Stop.hotValue == 0 && Counter == 0) {
				Value.hotValue = 0;
			} else if (Point.hotValue == 0) {
				Value.hotValue = 0;
				Counter = 0;
			}
		}
		if (Point == Stop) {
			if (Point.hotValue > 0 && Value.hotValue == 0) {				
					Start.hotValue = 0;
					Value.hotValue = Counter;
					Counter = 0;
			}
		}
	}

	void itIsTime() {
		if (Start.hotValue > 0 && Stop.hotValue == 0) {
			Quad++;
			if (Quad >= 4) {
				Quad = 0;
				Counter++;
			}
		}
	}
}