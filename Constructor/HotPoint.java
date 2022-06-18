import java.applet.*;
import java.awt.event.*;
import java.awt.*;

class HotPoint {
	//context
	Applet App;
	Graphics Canvas;
	Math processor;
	Sprite hotContainer;

	//native
	int x, y;
	int lx, ly;
	int hotType;
	int hotRadius;
	int hotValue;
	int isInContainer;
	int isActive;
	
	HotPoint(Applet App, Graphics Canvas, int x, int y, int Radius, int isActive) {
		this.App = App;
		this.Canvas = Canvas;
		this.isInContainer = 0;
		this.x = x;
		this.y = y;
		this.lx = x;
		this.ly = y;
		this.hotRadius = Radius;
		this.hotValue = 0;
		this.isActive = isActive;
	}

	HotPoint(Applet App, Graphics Canvas, Sprite Cont, int x, int y, int Radius, int isActive) {
		this.App = App;
		this.Canvas = Canvas;
		this.hotContainer = Cont;
		this.isInContainer = 1;
		this.x = x;
		this.y = y;
		this.lx = x;
		this.ly = y;
		this.hotRadius = Radius;
		this.hotValue = 0;
		this.isActive = isActive;
	}

	void moveHotPoint(int x, int y) {
		this.lx = this.x;
		this.ly = this.y;
		this.x = x;
		this.y = y;
	}

	void drawHotPoint(){
		Font fnt = new Font("Courier", Font.BOLD, 12);
		Canvas.setFont(fnt);
		//Canvas.setColor(Color.white);
		//Canvas.fillRect(lx - hotRadius, ly - hotRadius - 2, hotRadius * 2 + 4, hotRadius * 2 + 4);

		if (this.hotValue == 0) Canvas.setColor(Color.red);
			else Canvas.setColor(Color.green);
		Canvas.drawLine(x - hotRadius, y - hotRadius, x + hotRadius, y + hotRadius);
		Canvas.drawLine(x - hotRadius, y + hotRadius, x + hotRadius, y - hotRadius);
		Canvas.drawOval(x - hotRadius, y - hotRadius, hotRadius * 2, hotRadius * 2);

		Canvas.setColor(Color.blue);
		String txtValue = "";
		txtValue = txtValue + this.hotValue;
		Canvas.drawString(txtValue, x - 5, y + 5);
	}

	private boolean inRadius(int px, int py){
		int pR;
		px = px - x;
		py = py - y;
		px *= px;
		py *= py;
		pR = (int)processor.sqrt(px + py);		
		if (pR > hotRadius) return false;
		return true;				
	}

	private boolean inDoubleRadius(int px, int py){
		int pR;
		px = px - x;
		py = py - y;
		px *= px;
		py *= py;
		pR = (int)processor.sqrt(px + py);		
		if (pR > hotRadius * 2) return false;
		return true;				
	}

	boolean touchHotPoint(HotPoint anotherPoint) {
		//touch by another hot point
		if (this.isActive == 1) return false;
		if (inDoubleRadius(anotherPoint.x, anotherPoint.y) == true) {
			if (anotherPoint.hotValue != 0) this.hotValue = anotherPoint.hotValue;
			if (this.isInContainer != 0) hotContainer.hotAction(this);
			return true;
		}
		return false;
	}

	boolean touchHotPoint(int x, int y) {
		//touch by the mouse
		if (this.isActive != 0) return false;
		if (inRadius(x, y)) {
			if(this.hotValue == 0) this.hotValue = 1;
			else this.hotValue = 0;
			if (this.isInContainer != 0) hotContainer.hotAction(this);
			return true;
		}
		return false;
	}
}