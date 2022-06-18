import java.applet.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.Timer;

public class Constructor extends Applet implements ActionListener {
	//interface
	Panel DownPanel;
	Button Command_1;
	TextField Field;
	Label Lbl;
	Graphics Canvas;
	Graphics AppCanvas;
	//buffer
	int buf_h, buf_w;
	Image imgBuffer;

	//media
	Image imgCross;

	//active data
	boolean isCorrectAnswer;
	int isActive;
	int totalSprites;
	Sprite ActiveSprite;
	Sprite AppletSprite[];
	int totalPoints;
	HotPoint hPoint[];

	//active objects
	Timer ticker;
	long iTimeCounter;
	Brig Nautilus;
	BTimer iTimer;
	BMath iMath, dMath;
	BLink pLinkA, cLinkA;
	BLink pLinkB, cLinkB;
	BLink pLinkC, cLinkC;

    public void init() {
		buf_w = getSize().width;
		buf_h = getSize().height;
		imgBuffer = createImage(getSize().width, getSize().height);
		//interface
		setBackground(Color.white);
	
		Canvas = imgBuffer.getGraphics();
		AppCanvas = getGraphics();
		//load media
		imgCross = getImage(getCodeBase(), "images/cross.gif");

		isCorrectAnswer = false;
		//create active objects
		Nautilus = new Brig(this, Canvas, 100, 100, 50, 50, 40);
		iTimer = new BTimer(this, Canvas, 100, 400, 25, 25, 20);
		iMath = new BMath(this, Canvas, 200, 400, 25, 25, 20, 1);	//mul
		dMath = new BMath(this, Canvas, 300, 400, 25, 25, 20, 2);   //div
		pLinkA = new BLink(this, Canvas, 400, 400, 20, 10, 15);
		cLinkA = new BLink(this, Canvas, 500, 400, 20, 10, 15, pLinkA);
		pLinkB = new BLink(this, Canvas, 400, 450, 20, 10, 15);
		cLinkB = new BLink(this, Canvas, 500, 450, 20, 10, 15, pLinkB);	
		pLinkC = new BLink(this, Canvas, 400, 500, 20, 10, 15);
		cLinkC = new BLink(this, Canvas, 500, 500, 20, 10, 15, pLinkC);

		//set active sprite subsystem
		isActive = 0;
		totalSprites = 10;
		AppletSprite = new Sprite[totalSprites];
		AppletSprite[0] = Nautilus;
		AppletSprite[1] = iTimer;
		AppletSprite[2] = iMath;
		AppletSprite[3] = dMath;
		AppletSprite[4] = pLinkA;
		AppletSprite[5] = cLinkA;
		AppletSprite[6] = pLinkB;
		AppletSprite[7] = cLinkB;
		AppletSprite[8] = pLinkC;
		AppletSprite[9] = cLinkC;

		//set hot points subsystem
		totalPoints = 17;
		hPoint = new HotPoint[totalPoints];
		hPoint[0] = Nautilus.Anchor;
		hPoint[1] = Nautilus.Sail;
		hPoint[2] = iTimer.Start;
		hPoint[3] = iTimer.Stop;
		hPoint[4] = iTimer.Value;
		hPoint[5] = iMath.Primary;
		hPoint[6] = iMath.Secondary;
		hPoint[7] = iMath.Value;
		hPoint[8] = dMath.Primary;
		hPoint[9] = dMath.Secondary;
		hPoint[10] = dMath.Value;
		hPoint[11] = pLinkA.Primary;
		hPoint[12] = cLinkA.Primary;
		hPoint[13] = pLinkB.Primary;
		hPoint[14] = cLinkB.Primary;
		hPoint[15] = pLinkC.Primary;
		hPoint[16] = cLinkC.Primary;

		//set timer
		ticker = new Timer(250, this);
		ticker.start();
    }

	public void start() {
	}

	public void stop() {
	}

    public void paint(Graphics g) {
		//check right answer
		if (dMath.Value.hotValue == 8) isCorrectAnswer = true;

		Canvas.setColor(Color.white);
		Canvas.fillRect(0, 0, buf_w, buf_h);
		int i;
		for (i = 0; i<totalSprites; i++) {
			AppletSprite[i].drawSprite();
		}

		//timer
		//String txtValue = "";
		//txtValue = txtValue + iTimeCounter;
		//Canvas.drawString(txtValue, 10, 10);
		
		if (isCorrectAnswer) {
	 		Canvas.drawImage(imgCross, 600, 0, this);
		}

		//draw from buffer to Applet
		AppCanvas.drawImage(imgBuffer, 0, 0, this);
	}

	void TouchHotPoints() {
		int i, j;
		for (i = 0; i<totalPoints; i++)
			for (j = 0; j<totalPoints; j++) {
				if (i != j) hPoint[i].touchHotPoint(hPoint[j]);
			}
	}

	public boolean mouseDown(java.awt.Event event, int x, int y) {
		if (isActive == 1) {
			ActiveSprite.moveSprite(x, y);
			isActive = 0;
			TouchHotPoints();
			//repaint();
		} else {
			int i;
			int j = -1;
			for (i = 0; i<totalSprites; i++){
				if (AppletSprite[i].dragPlace.isInArea(x, y) == true) j = i;
			}
			if (j != -1) {
				ActiveSprite = AppletSprite[j];
				isActive = 1;
			} else {
				//check hot points
				for (i=0; i<totalPoints; i++) {
					hPoint[i].touchHotPoint(x, y);
				}
				//repaint();
			}
		}
		repaint();
		return true;
	}

	public boolean mouseMove(java.awt.Event event, int x, int y) {		
		if (isActive == 1) {
			ActiveSprite.moveSprite(x, y);
		}
		repaint();
		return true;
	}

    public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == Command_1) { 
			Lbl.setText("Ok, ok... Hello, hello...");
		}
		if (ev.getSource() == ticker) {
			iTimeCounter++;	
			Nautilus.SailAway();
			iTimer.itIsTime();
			TouchHotPoints();
			repaint();
		}
    }

	public void repaint(){
		//super.repaint();
		paint(Canvas);
	}
}