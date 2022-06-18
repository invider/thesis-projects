
import java.applet.*;
import java.awt.event.*;
import java.awt.*;

public class Dinamometer extends Applet implements ActionListener{
	//interface
	Panel DownPanel;
	Button Command_1, Command_2;
	TextField Field;
	Label Lbl;
	Graphics Canvas;

	//media
	Image imgCross;
	Image imgEmpty;
	Image imgDina;
	Image imgElephant;
	Image imgPlumelet;
	Image imgBrig;
	Image imgMouse;
	AudioClip auDina;

	//active objects
	Device Din;
    Sprite Elephant, Brig, Mouse, Plumelet;
	Sprite Sprites[];
	Sprite Active;
	Sprite Active2;
	int    isActive;

    public void init() {
		//interface
		setBackground(Color.white);
		setLayout(new BorderLayout());
		DownPanel = new Panel();
		add(DownPanel, BorderLayout.SOUTH);

		DownPanel.setLayout(new FlowLayout());
		Command_1 = new Button("  Відповісти  ");
		Field = new TextField("            ");
		Lbl = new Label("            Підчепіть будь-який об'єкт до приладу", Label.RIGHT);
		Command_1.addActionListener(this);
		DownPanel.add(Lbl);
		DownPanel.add(Field);
		DownPanel.add(Command_1);
		Field.setText("");


		Canvas = getGraphics();

		//load media
		imgCross = getImage(getCodeBase(), "images/cross.gif");
		imgEmpty = getImage(getCodeBase(), "images/empty.gif");
		imgDina = getImage(getCodeBase(), "images/dinamometer.gif");
		imgElephant = getImage(getCodeBase(), "images/elephant.jpg");
		imgPlumelet = getImage(getCodeBase(), "images/plumelet.jpg");
		imgBrig = getImage(getCodeBase(), "images/brig.jpg");
		imgMouse = getImage(getCodeBase(), "images/mouse.jpg");
		auDina = getAudioClip(getCodeBase(), "audio/spacemusic.au");

		//create sprites
		Din = new Device();
		Din.x = 25;
		Din.y = 0;
		Din.dx = 50;
		Din.dy = 100;
		Elephant = new Sprite();
		Brig = new Sprite();
		Mouse = new Sprite();
		Plumelet = new Sprite();
		isActive = 0;

		Elephant.id = 1;
		Elephant.x = 150;
		Elephant.y = 00;
		Elephant.status = 1;
		Elephant.weight = 2000;
		Elephant.down = 150;
		Elephant.type = 1;
		Elephant.name = "слоненя";
		Elephant.namer = "слоненяти";
		Plumelet.id = 2;
		Plumelet.x = 400;
		Plumelet.y = 50;
		Plumelet.status = 1;
		Plumelet.down = 10;
		Plumelet.weight = 0.001;
		Plumelet.type = 1;
		Plumelet.name = "пір'їнка";
		Plumelet.namer = "пір'їнки";
		Mouse.id = 3;
		Mouse.x = 150;
		Mouse.y = 200;
		Mouse.status = 1;
		Mouse.weight = 0.030;
		Mouse.down =40;
		Mouse.type = 2;
		Mouse.name = "мишеня";
		Mouse.namer = "мишеня";
		Brig.id = 4;
		Brig.x = 400;
		Brig.y = 200;
		Brig.status = 1;
		Brig.weight = 35000;
		Brig.down = 190;
		Brig.type = 2;
		Brig.name = "корабель";
		Brig.namer = "корабель";

		Sprites = new Sprite[4];
		Sprites[0] = Elephant;
		Sprites[1] = Plumelet;
		Sprites[2] = Mouse;
		Sprites[3] = Brig;
    }

	public void start() {
		auDina.play();
	}

	public void stop() {
		auDina.stop();
	}

    private void drawSprites() {
	if (Din.state == 1){
			Active2.is_changed = 1;
			Active2.x = Din.x + Din.dx - 40;
			Active2.y = Din.y + Din.dy + Din.metric - 25;
	}

	if (Elephant.status == 1 && Elephant.is_changed == 1) {
		Canvas.drawImage(imgElephant, Elephant.x, Elephant.y, this);
		Elephant.is_changed = 0;
	}
	if (Plumelet.status == 1 && Plumelet.is_changed == 1) {
		Canvas.drawImage(imgPlumelet, Plumelet.x, Plumelet.y, this);
		Plumelet.is_changed = 0;
	}
	if (Brig.status == 1 && Brig.is_changed == 1) {
		Canvas.drawImage(imgBrig, Brig.x, Brig.y, this);
		Brig.is_changed = 0;
	}
	if (Mouse.status == 1 && Mouse.is_changed == 1) {
		Canvas.drawImage(imgMouse, Mouse.x, Mouse.y, this);
		Mouse.is_changed = 0;
	}

        Canvas.setColor(Color.red);
		Canvas.drawLine(Din.x + Din.dx, Din.y + 30, Din.x + Din.dx, Din.y + Din.dy + Din.metric);
		Canvas.drawLine(Din.x + Din.dx, Din.y + Din.dy + Din.metric, Din.x + Din.dx - 25, Din.y + Din.dy + Din.metric + 25);
		Canvas.drawLine(Din.x + Din.dx, Din.y + Din.dy + Din.metric, Din.x + Din.dx + 25, Din.y + Din.dy + Din.metric + 25);
		Canvas.drawLine(Din.x + Din.dx - 25, Din.y + Din.dy + Din.metric + 25, Din.x + Din.dx + 25, Din.y + Din.dy + Din.metric + 25);

		int i, solved = 0;
		for (i = 0; i < 4; i++) if (Sprites[i].framed == 1) solved++;
		int base = 300, shift = 40;
		for (i = 0; i < solved; i++){
			Canvas.drawImage(imgCross, base, 0, this);
			base += shift;
		}
    }

    public void paint(Graphics g) {
		//dinamometer
		g.drawImage(imgDina, Din.x, Din.y, this);
		if (Din.state == 1) {
			long lF;
			double F;		
			F = Active2.weight * Active2.g;
			F = F * 10000;
			lF = (long)F;
			F = lF; F = F / 10000;

			String str_x = "F = " + F + " H";
			String str_y = "m = " + Active2.weight + " кг";
			g.setColor(Color.green);
			if (Active2.type == 1) {
				g.drawString(str_x, 35, 25);
				String res_str = "Яка маса " + Active2.namer + "?";
				if (Active2.frame_try != 1) Lbl.setText(res_str);
			} else {
				g.drawString(str_y, 35, 25);
				String res_str = "Яка сила діє на " + Active2.namer + "?";
				if (Active2.frame_try != 1) Lbl.setText(res_str);
			}
			DownPanel.repaint();
		}

		//sprites
		Elephant.is_changed = 1;
		Plumelet.is_changed = 1;
		Mouse.is_changed = 1;
		Brig.is_changed = 1;
		drawSprites();		

		//String str_x = "coordinates: " +  Din.x + " X " + Din.y;
		//g.drawString(str_x, 150, 10);

		//top message
		String str_msg = "Завдання: зважте кожен з доступних об'єктів";
		g.setColor(Color.blue);
		g.drawString(str_msg, 250, 10);
    }

    private int Rectangle(int x, int y, int mx, int my) {
	if ((my > y && my < y + 100) && (mx > x && mx < x + 100))
	return 1;
	return 0;
    }

    private int Rectangle2(int x, int y, int mx, int my) {
	if ((my > y && my < y + 200) && (mx > x && mx < x + 200))
	return 1;
	return 0;
    }

	private void Gravity(){
		int i;
		long j;

		if (Active2.type == 1) {
			Lbl.setText("Зачекайте... Підрахунок сили, що діє на об'єкт");
		} else {
			Lbl.setText("Зачекайте... Підрахунок маси об'єкта");
		}
		DownPanel.repaint();

		for (i=0; i<Active2.down; i++) {
			Din.metric++;
			drawSprites();
			for (j=0; j<10000000; j++) { };
		}
		repaint();
	}

	public boolean mouseDown(java.awt.Event event, int x, int y) {
		if (isActive == 0) {
			if (Elephant.status == 1 && Rectangle(Elephant.x, Elephant.y, x, y) == 1) {
				Active = Elephant;
				isActive = 1;
			}
			if (Plumelet.status == 1 && Rectangle(Plumelet.x, Plumelet.y, x, y) == 1) {
				Active = Plumelet;
				isActive = 1;
			}
			if (Mouse.status == 1 && Rectangle(Mouse.x, Mouse.y, x, y) == 1) {
				Active = Mouse;
				isActive = 1;
			}
			if (Brig.status == 1 && Rectangle(Brig.x, Brig.y, x, y) == 1) {
				Active = Brig;
				isActive = 1;
			}
			if (isActive == 1 && Din.state == 1){	
			  if (Active.id == Active2.id){
					Active2.frame_try = 0;
					Field.setText("");
					Din.state = 0;
					Din.metric = 0;
					Lbl.setText("Підчепіть будь-який об'єкт до приладу");
			  }
			}
		}
		else {
			if (Din.state == 0) {
				if (Rectangle(Din.x + Din.dx, Din.y + Din.dy + Din.metric, x+50, y+50) == 1) {
					Canvas.drawImage(imgEmpty, Active.x, Active.y, this);
					Din.state = 1;
					Active2 = Active;
					Gravity();	
				}
			}

			isActive = 0;
			repaint();

			//String str_x = "coordinate: " + Active.x + " X " + Active.y 
			//+ "    " + (Din.x + Din.dx - 100) + " X " + (Din.y + Din.dy - 100);
			//Canvas.drawString(str_x, 100, 10);
		}

		return true;
	}

	public boolean mouseMove(java.awt.Event event, int x, int y) {		
		if (isActive == 1) {
		  Canvas.drawImage(imgEmpty, Active.x, Active.y, this);
		  Active.x = x - Active.dx;
		  Active.y = y - Active.dy;
		  Active.is_changed = 1;
		  drawSprites();
		  //repaint();
		}
		return true;
	}

    public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == Command_1){ 
			double real_answ;
			double answ;
			answ = Double.parseDouble(Field.getText().trim());
			
			if (Active2.type == 1){
				real_answ = Active2.weight;
			}
			else{
				long lF;
				double F;		
				F = Active2.weight * Active2.g;
				F = F * 10000;
				lF = (long)F;
				F = lF; F = F / 10000;
				real_answ = F;
			}
			if (real_answ == answ) {
				Active2.framed = 1;
				Lbl.setText("Вірна відповідь!");
			} else {
				Lbl.setText("Невірно!");
			}
		}
		Active2.frame_try = 1;
		repaint();
    }
}