import java.applet.*;
import java.awt.event.*;
import java.awt.*;

class Sprite {
	Graphics Canvas;
	Applet App;
	DragArea dragPlace;

	public int x, y, dx, dy;
	public int lx, ly;
	public int total_frames, current_frame;
	public Image Frame[];
	public Image imgBlank;

	Sprite(Applet App, Graphics Canvas, int nx, int ny, int ndx, int ndy, int dragRadius){
		this.App = App;
		this.Canvas = Canvas;
		x = nx;
		y = ny;
		dx = ndx;
		dy = ndy;
		lx = x;
		ly = y;
		dragPlace = new DragArea(x + dx, y + dy, dragRadius);
	}

	void moveSprite(int nx, int ny) {
		nx = nx - dx;
		ny = ny - dy;
		lx = x;
		ly = y;
		x = nx;
		y = ny;
		dragPlace.moveArea(x + dx, y + dy);
		//Canvas.drawImage(imgBlank, lx, ly, App);
		//drawSprite();
	}
	
	void nextFrame() {
		current_frame++;
		if (current_frame == total_frames) current_frame = 0;
		moveSprite(this.x, this.y);
	}

	void drawSprite() {
		if (total_frames > 0) 
			Canvas.drawImage(Frame[current_frame], x, y, App);
	}

	void hotAction(HotPoint Point) {
	}
}