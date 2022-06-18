import java.applet.*;
import java.awt.event.*;
import java.awt.*;

class BMath extends Sprite {
    public HotPoint Primary, Secondary, Value;
	int Counter;
	int mathType;

	BMath (Applet App, Graphics Canvas, int x, int y, int dx, int dy, int dragRadius, int mathType) {
		super(App, Canvas, x, y, dx, dy, dragRadius);
		this.mathType = mathType;
		int nx = x + dx;
		int ny = y + dy;
		imgBlank = App.getImage(App.getCodeBase(), "images/blank.gif");
		total_frames = 1;
		current_frame = 0;
		Frame = new Image[total_frames];
		if (mathType == 1) Frame[0] = App.getImage(App.getCodeBase(), "images/triangle_mul.gif");
		else Frame[0] = App.getImage(App.getCodeBase(), "images/triangle_div.gif");

		Primary = new HotPoint(App, Canvas, this, x - 10, y - 10, 10, 0);
		Secondary = new HotPoint(App, Canvas, this, x + dx + dx + 10, y - 10 , 10, 0);
		Value = new HotPoint(App, Canvas, this, x + dx, y + dy + dy + 10, 10, 1);
	}

	void moveSprite(int x, int y) {
		Primary.moveHotPoint(x - dx - 10, y - dy - 10);
		Secondary.moveHotPoint(x + dx + 10, y - dy - 10);
		Value.moveHotPoint(x, y + dy + 10);
		super.moveSprite(x, y);
	}
	
	void drawSprite() {
		super.drawSprite();
		Primary.drawHotPoint();
		Secondary.drawHotPoint();
		Value.drawHotPoint();
	}

	void hotAction(HotPoint Point) {
		if (Point == Primary) {
			if (mathType == 1) {
				Value.hotValue = Primary.hotValue * Secondary.hotValue;
			} else {
				if (Secondary.hotValue == 0) Value.hotValue = -1;
				else {
					double b1, b2, bf;
					b1 = (double)Primary.hotValue;
					b2 = (double)Secondary.hotValue;
					bf = b1 / b2;
					bf = Math.round (bf);
					
					Value.hotValue = (int)bf;
				}
			}
		}
		if (Point == Secondary) {
			if (mathType == 1) {
				Value.hotValue = Primary.hotValue * Secondary.hotValue;
			} else {
				if (Secondary.hotValue == 0) Value.hotValue = -1;
				else Value.hotValue = Primary.hotValue / Secondary.hotValue;
			}
		}
	}

	void itIsTime() {
	}
}